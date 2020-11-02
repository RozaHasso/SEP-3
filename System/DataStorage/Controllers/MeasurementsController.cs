using System.Collections.Generic;
using System.Linq;
using Common;
using Microsoft.AspNetCore.Mvc;

namespace DataStorage.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /// <summary>
    /// This class contains the REST routes to access measurements.
    /// <author>Florin Ciornei</author>
    /// </summary>
    public class MeasurementsController : ControllerBase
    {


        private readonly InMemoryDBContext _context;

        public MeasurementsController(InMemoryDBContext context)
        {
            _context = context;
        }



        // Get measurements with {type} from the patient with {id}
        // GET api/values/5
        [HttpGet("{type}/{id}")]
        public ActionResult<List<Measurement>> Get(string type, int id)
        {
            IEnumerable<Measurement> measurements = null;

            if (type == "temperature")
            {
                measurements = (from measurement in _context.temperatures where measurement.PatientID == id orderby measurement.Id descending select measurement).Take(10);
            }
            else if (type == "bloodPressure")
            {
                measurements = (from measurement in _context.bloodPressures where measurement.PatientID == id orderby measurement.Id descending select measurement).Take(10);
            }
            else if (type == "heartRate")
            {
                measurements = (from measurement in _context.heartRates where measurement.PatientID == id orderby measurement.Id descending select measurement).Take(10);
            }

            return measurements.ToList();
        }

        // Add a heart rate measurement  
        // POST api/measurements/heartRate
        [HttpPost("heartRate")]
        public void PostHeartRate(HeartRate measurement)
        {
            _context.heartRates.Add(measurement);
            _context.SaveChanges();
        }

        // Add a temperature measurement
        // POST api/measurements/temperature
        [HttpPost("temperature")]
        public void PostTemperature(Temperature measurement)
        {
            _context.temperatures.Add(measurement);
            _context.SaveChanges();

        }

        // Add a blood pressure measurement  
        // POST api/measurements/bloodPressure
        [HttpPost("bloodPressure")]
        public void PostBloodPressure(BloodPressure measurement)
        {
            _context.bloodPressures.Add(measurement);
            _context.SaveChanges();
        }


    }
}
