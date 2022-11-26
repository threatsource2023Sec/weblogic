package weblogic.transaction.internal;

public class InterpositionTier {
   public static final InterpositionTier WLS_INTERNAL_SYNCHRONIZATION = new InterpositionTier("WLS_INTERNAL_SYNCHRONIZATION");
   public static final InterpositionTier JTA_INTERPOSED_SYNCHRONIZATION = new InterpositionTier("JTA_INTERPOSED_SYNCHRONIZATION");
   private final String tier;

   private InterpositionTier(String s) {
      this.tier = s;
   }

   public String toString() {
      return this.tier;
   }
}
