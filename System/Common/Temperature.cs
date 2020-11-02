using System;
using System.Dynamic;

namespace Common
{
    public class Temperature : Measurement
    {
        public long PatientID { get; set; }

        public double temperature { get; set; }

        public Temperature(long PatientID, string timestamp, double temperature)
        {
            this.PatientID = PatientID;
            this.Timestamp = timestamp;
            this.temperature = temperature;
        }

        public Boolean isCritical()
        {
            if (temperature < 36.5 || temperature > 37.5)
                return true;
            else
                return false;
        }

    }
}