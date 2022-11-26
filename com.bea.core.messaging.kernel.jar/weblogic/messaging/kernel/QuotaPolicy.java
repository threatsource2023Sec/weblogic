package weblogic.messaging.kernel;

public class QuotaPolicy {
   private static final String FIFO_NAME = "FIFO";
   private static final String PREEMPTIVE_NAME = "PREEMPTIVE";
   public static final QuotaPolicy FIFO = new QuotaPolicy("FIFO");
   public static final QuotaPolicy PREEMPTIVE = new QuotaPolicy("PREEMPTIVE");
   private String policyName;

   private QuotaPolicy(String policyName) {
      this.policyName = policyName;
   }

   public static QuotaPolicy get(String name) {
      if (name == null) {
         return FIFO;
      } else if (name.equalsIgnoreCase("PREEMPTIVE")) {
         return PREEMPTIVE;
      } else if (name.equalsIgnoreCase("FIFO")) {
         return FIFO;
      } else {
         throw new IllegalArgumentException("Unknown quota policy: " + name);
      }
   }

   public String toString() {
      return this.policyName;
   }
}
