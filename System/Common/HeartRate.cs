using System;

namespace Common
{
    public class HeartRate : Measurement
    {
        public long PatientID { get; set; }

        public int beatsPerMin { get; set; }

        public HeartRate(long PatientID,string timestamp, int beatsPerMin)
        {
            this.PatientID = PatientID;
            this.Timestamp = timestamp;
            this.beatsPerMin = beatsPerMin;
        }


        public Boolean isCritical()
        {
            if (beatsPerMin < 60 || beatsPerMin > 100)
                return true;
            else
                return false;
        }

    }
}