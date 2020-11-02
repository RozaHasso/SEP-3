using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using System.Net;
using Common;

namespace Business.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /// <summary>
    /// This class contains the REST routes to access patients.
    /// <author>Florin Ciornei</author>
    /// </summary>
    public class PatientsController : ControllerBase
    {


        private readonly HttpClient client = new HttpClient();
        WebClient webClient = new WebClient();

        // Get all patients
        // GET api/patients
        [HttpGet]
        public ActionResult<String> Get()
        {
            return webClient.DownloadString(Program.dataStorageUrl + "patients");
        }

        // Gets patient with {id}
        // GET api/patients/5
        [HttpGet("{id}")]
        public ActionResult<string> Get(int id)
        {
            return webClient.DownloadString(Program.dataStorageUrl + "patients/" + id);
        }

        // Gets patients of the doctor with {id}
        // GET api/patients/doctor/5
        [HttpGet("doctor/{id}")]
        public ActionResult<string> GetFromDoctor(int id)
        {
            return webClient.DownloadString(Program.dataStorageUrl + "patients/doctor/" + id);
        }


        // Add a patient
        // POST api/patients
        [HttpPost]
        public ActionResult<string> Post([FromBody] Patient patient)
        {
            Task<string> response = client.PostAsJsonAsync(Program.dataStorageUrl + "patients", patient).Result.Content.ReadAsStringAsync();
            return response.Result.ToString();
        }

        // Update patient with {id}
        // PUT api/patients/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Patient patient)
        {
            client.PutAsJsonAsync(Program.dataStorageUrl + "patients/" + id, patient);
        }

        // Delete patient with {id}
        // DELETE api/patients/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            client.DeleteAsync(Program.dataStorageUrl + "patients/" + id);
        }

        // Thanks for reading the comments, here is a joke:
        // Question: Does an apple a day keep the doctor away?
        // Answer: Only if you aim it well enough.
    }
}
