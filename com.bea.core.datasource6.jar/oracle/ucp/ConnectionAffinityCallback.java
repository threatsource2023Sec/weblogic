package oracle.ucp;

public interface ConnectionAffinityCallback {
   boolean setConnectionAffinityContext(Object var1);

   Object getConnectionAffinityContext();

   void setAffinityPolicy(AffinityPolicy var1);

   AffinityPolicy getAffinityPolicy();

   public static enum AffinityPolicy {
      WEBSESSION_BASED_AFFINITY,
      TRANSACTION_BASED_AFFINITY,
      DATA_BASED_AFFINITY;
   }
}
