/**
 * This is the AngularJS script powering the doctor dashboard.
 * @author Florin Ciornei
 */


var app = angular.module("myApp", ["ngRoute"]);
var restURL = "https://localhost:5004/api/";
app

    .config(function ($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "Login.html",
                controller: "Login"
            })
            .when("/patientList", {
                templateUrl: "PatientList.html",
                controller: "PatientList"
            })
            .when("/patient/:id", {
                templateUrl: "Patient.html",
                controller: "Patient"
            })

    })



    //Navbar controller containing navbar functionality
    .controller("Navbar", function ($scope, $window, $location) {

        $scope.isAuthenticated = function () {
            return $window.localStorage['id'] != undefined;
        }

        $scope.logOut = function () {
            $window.localStorage.clear();
            $location.url("");
        }
    })


    //Login controller responsible for authenticating the doctor
    .controller("Login", function ($scope, $http, $window, $location) {
        if ($window.localStorage['id']) {
            $location.url("patientList");
        }
        $scope.login = function () {
            $http({
                method: "POST",
                url: restURL + "doctors/login",
                data: $scope.user
            }).then(function mySuccess(response) {
                if (!Number.isInteger(parseInt(response.data))) {
                    $scope.error = true;
                    return;
                }
                $window.localStorage['id'] = parseInt(response.data);
                $location.url("patientList");

            }, function myError(response) {
                alert("Wrong username or password");
            });
        }
    })


    //Patients list controller that displays the patient list.
    .controller("PatientList", function ($scope, $http, $window, $location) {
        //Get the patients from the server.
        $http.get(restURL + "patients/doctor/" + $window.localStorage['id'])
            .then(function (response) {
                $scope.patients = response.data;
            });

        //Open the patient view.
        $scope.viewPatient = function (id) {
            $location.url("patient/" + id);
        }
    })


    //Patient controller that's resposnible for displaying real time measurements data for a patient.
    .controller("Patient", function ($scope, $routeParams, $http, $interval) {

        //Get patient data.
        $http.get(restURL + "patients/" + $routeParams.id)
            .then(function (response) {
                $scope.patient = response.data;
            });

        //Select another type of measurement to display.
        $scope.changeMeasurementType = function (type) {
            $scope.measurementType = type;
            $scope.measurements = [];
            $scope.loadMeasurements();
        }


        //Format date string to a beautiful date.
        $scope.formatDate = function (date) {
            var date = new Date(Date.parse(date));
            return (date.getDate() + "/" + (date.getMonth() + 1));
        }



        // You may be a little bit bored already, so here is a joke:
        // Can a kangaroo jump higher than a house? 
        // Of course, a house doesn’t jump at all.


        //Load the measurements of the selected type and display them in table & chart.
        $scope.loadMeasurements = function () {
            $http.get(restURL + "measurements/" + $scope.measurementType + "/" + $routeParams.id)
                .then(function (response) {
                    $scope.measurements = response.data;
                    var labels = [], series = [[]];
                    switch ($scope.measurementType) {
                        case "heartRate":
                            $scope.measurements.forEach(function (m) {
                                labels.push($scope.formatDate(m.timestamp));
                                series[0].push(m.beatsPerMin);
                            })
                            break;
                        case "temperature":
                            $scope.measurements.forEach(function (m) {
                                labels.push($scope.formatDate(m.timestamp));
                                series[0].push(m.temperature);
                            })
                            break;
                        case "bloodPressure":
                            series[1] = [];
                            $scope.measurements.forEach(function (m) {
                                labels.push($scope.formatDate(m.timestamp));
                                series[0].push(m.systolicPressure);
                                series[1].push(m.diastolicPressure);
                            })
                            break;
                    }
                    Chartist.Line('.ct-chart',
                        {
                            labels: labels,
                            series: series
                        },
                        {
                            fullWidth: true,
                            chartPadding: {
                                right: 40
                            }
                        }
                    );
                });


        }


        //Auto refresh measurements each second.
        var interval = $interval(function () {
            $scope.loadMeasurements();
        }, 1000);

        $scope.$on("$destroy", function () {
            $interval.cancel(interval);
        });
        //Load heartRate measurements.
        $scope.changeMeasurementType("heartRate");


    })