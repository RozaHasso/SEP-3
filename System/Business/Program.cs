using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Threading;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

/// <summary>
/// This class will start the socket server and the web service.
/// <author>Florin Ciornei & dotnet generator</author>
/// </summary>

namespace Business
{



    public class Program
    {

        public static string dataStorageUrl = "https://localhost:5001/api/";

        public static void Main(string[] args)
        {
            Console.Title = "Business Server";
            SocketServer socketServer = new SocketServer();
            Thread scoketServerThread = new Thread(new ThreadStart(socketServer.StartServer));
            scoketServerThread.Start();
            CreateWebHostBuilder(args).Build().Run();
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
