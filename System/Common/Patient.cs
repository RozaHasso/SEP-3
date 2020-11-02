using System.Collections;

namespace Common
{
    public class Patient
    {
        public long ID { get; set; }
        public long doctorID { get; set; }
        public string dateOfBirth { get; set; }
        public char gender { get; set; }
        public string name { get; set; }
        public string phoneNumber { get; set; }
        // private ArrayList measurements;

        public Patient(string dateOfBirth, char gender, string name, long doctorID, string phoneNumber)
        {
            //  measurements = new ArrayList();
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.name = name;
            this.doctorID = doctorID;
            this.phoneNumber = phoneNumber;
        }

        // public ArrayList getMeasurements()
        // {
        //     return this.measurements;
        // }
    }
}