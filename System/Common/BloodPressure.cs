using System;

namespace Common
{
    public class BloodPressure : Measurement
    {

        public long PatientID { get; set; }
        public int systolicPressure { get; set; }
        public int diastolicPressure { get; set; }


        public BloodPressure(long PatientID, string timestamp, int systolicPressure, int diastolicPressure)
        {
            this.PatientID = PatientID;
            this.Timestamp = timestamp;
            this.systolicPressure = systolicPressure;
            this.diastolicPressure = diastolicPressure;
        }


        public Boolean isCritical()
        {
            if (systolicPressure < 90 || systolicPressure > 140 || diastolicPressure < 60 || diastolicPressure > 90)
                return true;
            else
                return false;
        }
    }
}