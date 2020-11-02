using System.Collections.Generic;

namespace Common
{
    public class MeasurementCollection
    {
        private List<Measurement> measurements = new List<Measurement>();

        public MeasurementCollection()
        {
        }
        public void addMeasurement(Measurement measurement)
        {
            measurements.Add(measurement);
        }
        public List<Measurement> getMeasurement()
        {
            return measurements;
        }

        public int size()
        {
            return measurements.Count;
        }
    }

}
