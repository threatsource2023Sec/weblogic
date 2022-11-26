package weblogic.invocation;

public interface ComponentInvocationContext {
   String GLOBAL_PARTITION_NAME = "DOMAIN";
   String GLOBAL_PARTITION_ID = "0";

   String getPartitionId();

   String getPartitionName();

   String getApplicationId();

   String getApplicationName();

   String getApplicationVersion();

   String getModuleName();

   String getComponentName();

   boolean isGlobalRuntime();
}
