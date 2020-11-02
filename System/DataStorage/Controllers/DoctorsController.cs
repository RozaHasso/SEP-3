using System;
using System.Collections.Generic;
using System.Linq;
using Common;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Text;


namespace PatientsController.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /// <summary>
    /// This class contains the REST routes to access doctors.
    /// <author>Florin Ciornei</author>
    /// </summary>
    public class DoctorsController : ControllerBase
    {


        private readonly InMemoryDBContext _context;

        public DoctorsController(InMemoryDBContext context)
        {
            _context = context;
        }


        // Get all doctors
        // GET api/doctors
        [HttpGet]
        public ActionResult<List<Doctor>> Get()
        {
            return _context.doctors.ToList();
        }

        // Get doctor with {id}
        // GET api/doctors/5
        [HttpGet("{id}")]
        public ActionResult<Doctor> Get(int id)
        {
            return _context.doctors.Where(d => d.ID == id).Single();
        }

        // Add a new doctor
        // POST api/doctors
        [HttpPost]
        public ActionResult<String> Post([FromBody]Doctor doctor)
        {
            doctor.password = CreateMD5(doctor.password);
            _context.doctors.Add(doctor);
            _context.SaveChanges();
            return doctor.ID.ToString();
        }


        // Get login token
        // POST api/doctors/login
        [HttpPost("login")]
        public ActionResult<String> GetLoginToken([FromBody] Dictionary<string, string> data)
        {
            string username, password;
            data.TryGetValue("username", out username);
            data.TryGetValue("password", out password);
            password = CreateMD5(password);

            var doctor = _context.doctors.Single(d => d.username == username && d.password == password);
            return doctor.ID.ToString();
        }


        // Replace current doctor with the new one
        // PUT api/doctors/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Doctor doctor)
        {
            string currentPassword = _context.doctors.AsNoTracking().Where(d => d.ID == id).Single().password;

            //if doctor doesn't have any password, the previous password is reused
            if (doctor.password == null || doctor.password == "")
                doctor.password = currentPassword;

            //if there is a password, we hash it
            else
                doctor.password = CreateMD5(doctor.password);

            _context.Update(doctor);
            _context.SaveChanges();
        }

        // DELETE api/doctors/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            var doctor = _context.doctors.Single(d => d.ID == id);
            _context.doctors.Remove(doctor);
            _context.SaveChanges();
        }

        public string CreateMD5(string input)
        {
            using (System.Security.Cryptography.MD5 md5 = System.Security.Cryptography.MD5.Create())
            {
                byte[] inputBytes = System.Text.Encoding.ASCII.GetBytes(input);
                byte[] hashBytes = md5.ComputeHash(inputBytes);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hashBytes.Length; i++)
                {
                    sb.Append(hashBytes[i].ToString("X2"));
                }
                return sb.ToString();
            }
        }
    }
}
