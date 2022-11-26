package weblogic.security.utils;

import weblogic.management.configuration.ConfigurationMBean;

public class PartitionUtils {
   private static final String DELEGATE_CLASS_NAME = "weblogic.security.utils.PartitionUtilsDelegateImpl";
   private static PartitionUtilsDelegate singleton;
   private static RuntimeException instantiationFailure;

   public static String getPartitionName() {
      return getDelegate().getPartitionName();
   }

   public static String getRealmName(String partitionName) {
      return getDelegate().getRealmName(partitionName, (ConfigurationMBean)null);
   }

   public static String getRealmName(String partitionName, ConfigurationMBean proposedDomain) {
      return getDelegate().getRealmName(partitionName, proposedDomain);
   }

   public static String getPrimaryIdentityDomain(String partitionName) {
      return getDelegate().getPrimaryIdentityDomain(partitionName);
   }

   public static String getAdminIdentityDomain() {
      return getDelegate().getAdminIdentityDomain();
   }

   public static String getCurrentIdentityDomain() {
      return getDelegate().getCurrentIdentityDomain();
   }

   private static PartitionUtilsDelegate getDelegate() {
      if (singleton == null && instantiationFailure != null) {
         throw instantiationFailure;
      } else {
         return singleton;
      }
   }

   static {
      try {
         singleton = (PartitionUtilsDelegate)Class.forName("weblogic.security.utils.PartitionUtilsDelegateImpl").newInstance();
      } catch (Throwable var1) {
         instantiationFailure = new IllegalStateException("Unable to instantiate PartitionUtilsDelegateImpl", var1);
      }

   }
}
