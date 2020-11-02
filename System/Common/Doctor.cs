using System;
using System.Collections;

namespace Common
{
    public class Doctor
    {

        // public ICollection<Patient> patients;
        public long ID { get; set; }
        public string name { get; set; }
        public string dateOfBirth { get; set; }
        public char gender { get; set; }
        public string phoneNumber { get; set; }
        public string username { get; set; }
        public string password { get; set; }


        public Doctor(string name, string dateOfBirth, char gender, string phoneNumber, string username, string password)
        {
            // patients = new ArrayList();
            //this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
            this.username = username;
            this.password = password;
        }

        // public void setId(long id)
        // {
        //     this.id = id;
        // }

        // public long getId()
        // {
        //     return id;
        // }

        // public ArrayList getPatients()
        // {
        //     return patients;
        // }

        // public void setPatients(ArrayList patients)
        // {
        //     this.patients = patients;
        // }








        // public void addPatient(Patient patient)
        // {
        //     patients.Add(patient);
        // }

        // public void removePatient(Patient patient)
        // {
        //     patients.Remove(patient);
        // }

        // public ArrayList getPatient()
        // {
        //     return patients;
        // }

    }
}