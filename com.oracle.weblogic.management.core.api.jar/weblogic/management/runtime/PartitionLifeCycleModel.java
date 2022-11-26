package weblogic.management.runtime;

public class PartitionLifeCycleModel {
   public static final boolean OLD_LIFECYCLE_MODEL = false;
   public static final boolean ALLOW_DESTROY_WO_SHUTDOWN = Boolean.parseBoolean(System.getProperty("partitionlifecycle.allowDestroyWithoutShutdown", "false"));
   public static final boolean PARTITION_ADMIN_TARGETING = Boolean.parseBoolean(System.getProperty("partitionAdminTargeting", "true"));
   public static final boolean AUTO_BOOT_ON_PARTITION_CREATE = Boolean.parseBoolean(System.getProperty("autoBootOnPartitionCreate", "false"));
   public static final boolean SHUTDOWN_TO_HALTED = Boolean.parseBoolean(System.getProperty("shutdownToHalted", "false"));
}
