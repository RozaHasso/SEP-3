using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using Microsoft.Extensions.DependencyInjection;
using Common;
namespace DataStorage
{
    /// <summary>
    /// This class will start the web service.
    /// <author>Florin Ciornei & dotnet generator</author>
    /// </summary>
    public static class Program
    {
        public static void Main(string[] args)
        {
            Console.Title = "Data Storage Server";
            IWebHost host = CreateWebHostBuilder(args).Build();
            using (var scope = host.Services.CreateScope())
            {
                var services = scope.ServiceProvider;
                try
                {
                    var context = services.GetRequiredService<InMemoryDBContext>();
                    DbSeeder.Initializa(context);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
            host.Run();
        }

        private static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}


