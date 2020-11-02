using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;



namespace Business.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /// <summary>
    /// This class contains the REST routes to access measurements from the data tier.
    /// <author>Florin Ciornei</author>
    /// </summary>
    public class MeasurementsController : ControllerBase
    {


        // Gets latest measurements with {type} from patient with {id}
        // GET api/measurements/temperature/4
        [HttpGet("{type}/{id}")]
        public ActionResult<string> Get(string type, int id)
        {
            using (var webClient = new System.Net.WebClient())
            {
                String json = webClient.DownloadString(Program.dataStorageUrl + "measurements/" + type + "/" + id);
                return json;
            }
        }

    }
}
