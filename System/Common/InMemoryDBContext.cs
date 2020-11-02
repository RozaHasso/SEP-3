using Microsoft.EntityFrameworkCore;

namespace Common
{
    public class InMemoryDBContext : DbContext
    {
        public DbSet<HeartRate> heartRates { get; set; }
        public DbSet<BloodPressure> bloodPressures { get; set; }
        public DbSet<Temperature> temperatures { get; set; }
        public DbSet<Patient> patients { get; set; }
        public DbSet<Doctor> doctors { get; set; }


        public InMemoryDBContext(DbContextOptions<InMemoryDBContext> options)
            : base(options)
        {
            
        }


    }
}