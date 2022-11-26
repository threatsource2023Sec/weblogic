package weblogic.management.utils.jmsdlb;

public class DLStoreConfig {
   private static int DEFAULT_STEADY_STATE_SEC = Integer.getInteger("weblogic.management.utils.jmsdlb.groupconfig.steadySecs", -1);
   private final POLICY policy;
   private final DISTRIBUTION distribution;
   private final boolean restartInPlace;
   private final long delaySecs;
   private final long failSeconds;
   private final int restartFailures;
   private final String name;
   private final long steadyStateTime;
   private final int restartSeconds;
   private final int failOverLimit;
   private boolean valid = false;
   private final DLId id;

   public DLStoreConfig(DLContext context, String name, POLICY policy, DISTRIBUTION distribution, boolean restartInPlace, long delaySecs, long failSeconds, int restartFailures, int restartSeconds, long steadyState, int failOverLimit) {
      this.name = name;
      this.id = context.getStoreConfigID(name);
      this.policy = policy;
      this.distribution = distribution;
      this.restartInPlace = restartInPlace;
      this.delaySecs = delaySecs;
      this.failSeconds = failSeconds;
      this.restartFailures = restartFailures;
      this.valid = true;
      this.restartSeconds = restartSeconds;
      this.steadyStateTime = steadyState;
      this.failOverLimit = failOverLimit;
   }

   public boolean validPolicy() {
      return this.policy != DLStoreConfig.POLICY.MANUAL && this.policy != DLStoreConfig.POLICY.OFF;
   }

   public DLId getStoreID() {
      return this.id;
   }

   public boolean isSingleton() {
      return this.distribution == DLStoreConfig.DISTRIBUTION.SINGLETON;
   }

   public boolean isOnFailure() {
      return this.policy == DLStoreConfig.POLICY.ON_FAILURE;
   }

   public boolean isDistributed() {
      return this.distribution == DLStoreConfig.DISTRIBUTION.DISTRIBUTED;
   }

   public static void setDefaultSteadyStateSec(int val) {
      DEFAULT_STEADY_STATE_SEC = val;
   }

   public String getName() {
      return this.name;
   }

   public POLICY getPolicy() {
      return this.policy;
   }

   public DISTRIBUTION getDistribution() {
      return this.distribution;
   }

   public boolean isRestartInPlace() {
      return this.restartInPlace;
   }

   public long getFailSeconds() {
      return this.failSeconds;
   }

   public long getRestartInterval() {
      return (long)this.restartSeconds;
   }

   public int getRestartAttempts() {
      return this.restartFailures;
   }

   public long getBootDelay() {
      return this.delaySecs;
   }

   public long getSteadyStateTime() {
      return this.steadyStateTime;
   }

   public int getFailOverLimit() {
      return this.failOverLimit;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         DLStoreConfig that = (DLStoreConfig)o;
         return this.name.equals(that.name);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public void destroy() {
      this.valid = false;
   }

   public boolean isValid() {
      return this.valid;
   }

   public boolean isPolicy(String policy) {
      POLICY p = DLStoreConfig.POLICY.getPolicy(policy);
      return p == this.policy;
   }

   public boolean isDisitribution(String distrib) {
      DISTRIBUTION d = DLStoreConfig.DISTRIBUTION.getDistribution(distrib);
      return d == this.distribution;
   }

   public String toString() {
      return "DLStore[" + this.getName() + ": dist=" + this.distribution + ", policy=" + this.policy + ", delay=" + this.delaySecs + ", restartFailures=" + this.restartFailures + ", failSeconds=" + this.failSeconds + ", steadyStateTime=" + this.steadyStateTime + ", restartSeconds=" + this.restartSeconds + ", failOverLimit=" + this.failOverLimit + "]";
   }

   static enum KNOWN_STATE {
      UNKNOWN,
      UP;
   }

   public static enum DISTRIBUTION {
      DISTRIBUTED("Distributed", 0),
      SINGLETON("Singleton", 1);

      private final String constName;
      private final int constInt;

      private DISTRIBUTION(String constName, int constInt) {
         this.constName = constName;
         this.constInt = constInt;
      }

      public static DISTRIBUTION getDistribution(String jmsConstant) {
         DISTRIBUTION[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DISTRIBUTION d = var1[var3];
            if (d.constName.equals(jmsConstant)) {
               return d;
            }
         }

         return null;
      }

      public static DISTRIBUTION getDistribution(int jmsConstant) {
         DISTRIBUTION[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DISTRIBUTION d = var1[var3];
            if (d.constInt == jmsConstant) {
               return d;
            }
         }

         return null;
      }
   }

   public static enum POLICY {
      OFF("Off", 0),
      MANUAL("Manual", 1),
      ON_FAILURE("On-Failure", 2),
      ALWAYS("Always", 3);

      private final String constName;
      private final int constInt;

      private POLICY(String constName, int constInt) {
         this.constName = constName;
         this.constInt = constInt;
      }

      public static POLICY getPolicy(String jmsConstant) {
         POLICY[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            POLICY p = var1[var3];
            if (p.constName.equals(jmsConstant)) {
               return p;
            }
         }

         return null;
      }

      public static POLICY getPolicy(int jmsConstant) {
         POLICY[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            POLICY p = var1[var3];
            if (p.constInt == jmsConstant) {
               return p;
            }
         }

         return null;
      }
   }
}
