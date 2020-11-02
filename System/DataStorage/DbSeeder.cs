using Common;
namespace DataStorage
{
    /// <summary>
    /// This class seeds the database with some default data.
    /// <author>Florin Ciornei</author>
    /// </summary>
    class DbSeeder
    {
        public static void Initializa(InMemoryDBContext context)
        {
            context.patients.Add(new Patient("1991-05-24", 'M', "John Wick", 1, "65936503"));
            context.patients.Add(new Patient("1989-04-12", 'M', "Ethan Hunt", 1, "56112288"));
            context.patients.Add(new Patient("1988-02-12", 'M', "James Bond", 1, "562363416"));
            context.patients.Add(new Patient("1980-12-12", 'M', "Jason Bourne", 1, "145341656342"));

            context.doctors.Add(new Doctor("Dr. Manhattan", "1994-12-05", 'M', "05016881", "doctor", "5F4DCC3B5AA765D61D8327DEB882CF99"));
            context.doctors.Add(new Doctor("Dr. Jekyll", "1886-01-05", 'M', "05016881", "jekyll", "5F4DCC3B5AA765D61D8327DEB882CF99"));
            context.doctors.Add(new Doctor("Dr. Jens Jensen", "1971-01-01", 'M', "74030405", "jjs", "5F4DCC3B5AA765D61D8327DEB882CF99"));
            context.SaveChanges();
        }
    }
}