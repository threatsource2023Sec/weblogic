package weblogic.management.workflow;

import weblogic.utils.LocatorUtilities;

public class GlobalDescriptorLock {
   public static DescriptorLock getGlobalDescriptorLock() {
      return GlobalDescriptorLock.GlobalDescriptorLockInitializer.INSTANCE;
   }

   private static final class GlobalDescriptorLockInitializer {
      private static final DescriptorLock INSTANCE = (DescriptorLock)LocatorUtilities.getService(DescriptorLock.class);
   }
}
