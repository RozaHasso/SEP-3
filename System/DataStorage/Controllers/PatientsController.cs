using System;
using System.Collections.Generic;
using System.Linq;
using Common;
using Microsoft.AspNetCore.Mvc;

namespace PatientsController.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /// <summary>
    /// This class contains the REST routes to access patients.
    /// <author>Florin Ciornei</author>
    /// </summary>
    public class PatientsController : ControllerBase
    {


        private readonly InMemoryDBContext _context;

        public PatientsController(InMemoryDBContext context)
        {
            _context = context;
        }



        // Get all patients
        // GET api/patient
        [HttpGet]
        public ActionResult<List<Patient>> Get()
        {
            return _context.patients.ToList();
        }

        // Get patient with {id}
        // GET api/patient/5
        [HttpGet("{id}")]
        public ActionResult<Patient> Get(int id)
        {
            IQueryable<Patient> patients = _context.patients.Where(p => p.ID == id);
            if (patients.Count() > 0)
                return patients.Single();

            return null;

        }

        // Get all patients from doctor with {id}
        // GET api/patient/doctors/5
        [HttpGet("doctor/{id}")]
        public ActionResult<List<Patient>> GetFromDoctor(int id)
        {
            IEnumerable<Patient> patients = from patient in _context.patients where patient.doctorID == id select patient;
            return patients.ToList();
        }


        // Add a new patient
        // POST api/patients
        [HttpPost]
        public ActionResult<String> Post(Patient patient)
        {
            _context.patients.Add(patient);
            _context.SaveChanges();
            return patient.ID.ToString();
        }

        // Update the patient with {id} 
        // PUT api/patients/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Patient patient)
        {
            _context.Update(patient);
            _context.SaveChanges();
        }

        // Delete the patient with {id}
        // DELETE api/patients/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var patient = _context.patients.Single(p => p.ID == id);
            _context.patients.Remove(patient);
            _context.SaveChanges();
        }
    }
}
