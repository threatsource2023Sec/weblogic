package oracle.ucp.jdbc.oracle;

public interface RACAffinityContext {
   String getVersionNumber();

   String getServiceName();

   String getDatabaseUniqueName();

   String getInstanceName();

   AffinityType getAffinityType();

   public static enum AffinityType {
      WEBSESSION_BASED_AFFINITY,
      TRANSACTION_BASED_AFFINITY;
   }
}
