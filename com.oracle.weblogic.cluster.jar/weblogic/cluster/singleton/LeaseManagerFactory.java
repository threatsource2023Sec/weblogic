package weblogic.cluster.singleton;

import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;

@Service
public class LeaseManagerFactory implements LeasingFactory {
   private static final String TIMER_LEASING_SERVICE_NAME = "timer";
   private static final String REMOTE_LEASINGBASIS_JNDI_NAME = "RemoteLeasingBasis";
   private boolean createdRemoteLeasingBasis = false;
   private LeasingBasis defaultBasis;
   private int heartbeatPeriod;
   private int healthCheckPeriod;
   private int gracePeriod;
   private HashMap leaseManagers = new HashMap(3);

   private LeaseManagerFactory() {
   }

   public void initialize(LeasingBasis basis, int heartbeatPeriod, int healthCheckPeriod, int gracePeriod) {
      this.defaultBasis = basis;
      this.heartbeatPeriod = heartbeatPeriod;
      this.healthCheckPeriod = healthCheckPeriod;
      this.gracePeriod = gracePeriod;
   }

   /** @deprecated */
   @Deprecated
   public LeaseManager getLeaseManager(String leaseType) {
      return this.internalFindOrCreate(leaseType, this.defaultBasis, this.heartbeatPeriod, this.healthCheckPeriod, this.gracePeriod, false);
   }

   public Leasing findOrCreateLeasingService(String leaseType) {
      return this.internalFindOrCreate(leaseType, this.defaultBasis, this.heartbeatPeriod, this.healthCheckPeriod, this.gracePeriod, false);
   }

   public Leasing createLeasingService(String leaseType, LeasingBasis basis, int heartbeatPeriod, int healthCheckPeriod, int gracePeriod) {
      LeaseManager lm = this.internalFindOrCreate(leaseType, basis, heartbeatPeriod, healthCheckPeriod, gracePeriod, true);
      lm.start();
      return lm;
   }

   public Leasing findOrCreateTimerLeasingService() throws LeasingException {
      if (Boolean.getBoolean("weblogic.UseSimpleLeasingForJobScheduler") && !this.createdRemoteLeasingBasis) {
         Leasing leasing = this.createLeasingService("timer", this.createRemoteLeasingBasis(), 500, 1000, 1000);
         this.createdRemoteLeasingBasis = true;
         return leasing;
      } else {
         return this.findOrCreateLeasingService("timer");
      }
   }

   private synchronized LeaseManager internalFindOrCreate(String leaseType, LeasingBasis basis, int heartbeatPeriod, int healthCheckPeriod, int gracePeriod, boolean checkForDuplicates) {
      LeaseManager lm = (LeaseManager)this.leaseManagers.get(leaseType);
      if (lm != null) {
         if (checkForDuplicates) {
            throw new IllegalArgumentException("Leasing service exists for leaseType: " + leaseType);
         }
      } else {
         lm = new LeaseManager(basis, heartbeatPeriod, healthCheckPeriod, gracePeriod, leaseType);
         this.leaseManagers.put(leaseType, lm);
      }

      return lm;
   }

   private RemoteLeasingBasisImpl createRemoteLeasingBasis() throws LeasingException {
      try {
         Context ctx = new InitialContext();
         RemoteLeasingBasisImpl basis = new RemoteLeasingBasisImpl(new SimpleLeasingBasis());
         ctx.bind("RemoteLeasingBasis", basis);
         return basis;
      } catch (NamingException var3) {
         throw new LeasingException("Error creating simpleleasing basis", var3);
      }
   }
}
