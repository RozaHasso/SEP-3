using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Security.Cryptography;
using System.Threading.Tasks;
using System.Net.Http;
using System.Text.RegularExpressions;

/// <summary>
/// This class receives measurements from the device and forwards them to the Data Storage tier.
/// <author>Florin Ciornei</author>
/// </summary>

namespace Business
{

    public class SocketServer
    {
        // string publicKeyString;
        // RSACryptoServiceProvider csp;
        // RSAParameters privateKey, publicKey;
        WebClient webClient = new WebClient();
        HttpClient client = new HttpClient();


        //Starting the socket server
        public void StartServer()
        {
            //  GenerateKeys();
            Listen();
        }


        // Artifacts from encrypted socket communication

        // public void GenerateKeys()
        // {
        //     csp = new RSACryptoServiceProvider(2048);
        //     privateKey = csp.ExportParameters(true);
        //     publicKey = csp.ExportParameters(false);


        //     byte[] bts = csp.Encrypt(Encoding.UTF8.GetBytes("asd"), RSAEncryptionPadding.Pkcs1);
        //     Console.WriteLine(bts.Length);
        //     foreach (byte b in bts)
        //     {
        //         Console.Write(b + " ");
        //     }

        //     //Exponent.Modulus
        //     publicKeyString = Convert.ToBase64String(publicKey.Exponent) + "." + Convert.ToBase64String(publicKey.Modulus);
        // }



        //Listening to incoming connections.
        public void Listen()
        {


            IPHostEntry ipHostInfo = Dns.GetHostEntry("localhost");
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint localEndPoint = new IPEndPoint(ipAddress, 11000);
            Socket listener = new Socket(ipAddress.AddressFamily,
                SocketType.Stream, ProtocolType.Tcp);


            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(10);
                while (true)
                {
                    Console.WriteLine("Waiting for a connection...");
                    Socket handler = listener.Accept();
                    Console.WriteLine("Connection established");
                    Task task = new Task(delegate { SocketTask(handler); });
                    task.Start();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }


        //This task is started for each socket, and it will read data from socket and send it to data storage
        public void SocketTask(Socket handler)
        {


            // PROTOCOL START

            string protocolQuestion = ReadFromSocket(handler);

            long patientId = long.Parse(Regex.Split(protocolQuestion, @"\D+")[1]);

            string patient = webClient.DownloadString(Program.dataStorageUrl + "patients/" + patientId);

            if (patient.Length > 0)
                handler.Send(new byte[] { 1 });
            else
                handler.Send(new byte[] { 0 });

            //PROTOCOL END 



            while (true)
            {
                // byte[] msg = Encoding.UTF8.GetBytes(publicKeyString);
                // handler.Send(msg);
                // Console.WriteLine("Public key sent");
                // int length = handler.Receive(bytes);
                // for (int i = 0; i < length; i++)
                //     Console.Write(bytes[i] + " ");
                // bytes = csp.Decrypt(bytes, RSAEncryptionPadding.Pkcs1);
                // data = Encoding.UTF8.GetString(bytes);
                // Console.WriteLine("Received data: ", data);


                string json = ReadFromSocket(handler);


                // if (json.IndexOf("beatsPerMin") > 0)
                // {
                //     HeartRate hearRate = JsonConvert.DeserializeObject<HeartRate>(json);
                //     client.PostAsJsonAsync("http://localhost:5000/api/measurements/heartRate", hearRate);
                //     // Console.WriteLine(json);
                // } 

                HttpContent measurement = new StringContent(json, Encoding.UTF8, "application/json");

                if (json.IndexOf("beatsPerMin") > 0)
                {
                    client.PostAsync(Program.dataStorageUrl + "measurements/heartRate", measurement);
                }

                if (json.IndexOf("temperature") > 0)
                {
                    client.PostAsync(Program.dataStorageUrl + "measurements/temperature", measurement);
                }

                if (json.IndexOf("systolicPressure") > 0)
                {
                    client.PostAsync(Program.dataStorageUrl + "measurements/bloodPressure", measurement);
                }
            }
        }


        //Read a string from a socket.
        string ReadFromSocket(Socket handler)
        {
            byte[] buffer = new byte[500];
            int length = handler.Receive(buffer);
            byte[] clipped = new byte[length];
            Array.Copy(buffer, clipped, length);
            string data = Encoding.UTF8.GetString(clipped);
            return data;
        }
    }
}