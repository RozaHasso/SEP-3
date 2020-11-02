using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Common;
using System.Net.Http;
using System.Net;




namespace Business.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /// <summary>
    /// This class contains the REST routes to access doctors from the data tier.
    /// <author>Florin Ciornei</author>
    /// </summary>
    public class DoctorsController : ControllerBase
    {


        private readonly HttpClient client = new HttpClient();
        WebClient webClient = new WebClient();



        // Get all doctors 
        // GET api/doctors
        [HttpGet]
        public ActionResult<String> Get()
        {
            return webClient.DownloadString(Program.dataStorageUrl + "doctors/");
        }

        // Get login token
        // POST api/doctors/login
        [HttpPost("login")]
        public ActionResult<String> GetLoginToken([FromBody] Dictionary<string, string> data)
        {
            Task<string> response = client.PostAsJsonAsync(Program.dataStorageUrl + "doctors/login", data).Result.Content.ReadAsStringAsync();
            return response.Result.ToString();
        }


        // Get doctor with {id}
        // GET api/doctors/5
        [HttpGet("{id}")]
        public ActionResult<string> Get(int id)
        {
            return webClient.DownloadString(Program.dataStorageUrl + "doctors/" + id);
        }


        // Add a new doctor
        // POST api/doctors

        [HttpPost]
        public ActionResult<string> Post([FromBody]Doctor doctor)
        {
            Task<string> response = client.PostAsJsonAsync(Program.dataStorageUrl + "doctors", doctor)
            .Result.Content.ReadAsStringAsync();
            return response.Result.ToString();
        }

        // Replace current doctor with the new one
        // PUT api/doctors/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] Doctor doctor)
        {
            client.PutAsJsonAsync(Program.dataStorageUrl + "doctors/" + id, doctor);
        }

        // Delete the doctor with {id}
        // DELETE api/doctors/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
            client.DeleteAsync(Program.dataStorageUrl + "doctors/" + id);
        }
    }
}
