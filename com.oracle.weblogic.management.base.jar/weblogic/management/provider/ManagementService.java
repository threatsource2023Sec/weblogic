package weblogic.management.provider;

import javax.management.MBeanServer;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.core.ManagementCoreService;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class ManagementService extends ManagementCoreService {
   private static RuntimeAccess runtimeAccess;
   private static DomainAccess domainAccess;
   private static MBeanServer runtimeMBeanServer;
   private static MBeanServer domainRuntimeMBeanServer;
   private static MBeanServer editMBeanServer;

   /** @deprecated */
   @Deprecated
   public static RuntimeAccess getRuntimeAccess(AuthenticatedSubject sub) {
      assert runtimeAccess != null : "The ManagementService has not been initialized & runtimeAccess is null";

      SecurityHelper.assertIfNotKernel(sub);
      return runtimeAccess;
   }

   public static DomainAccess getDomainAccess(AuthenticatedSubject sub) {
      assert domainAccess != null;

      SecurityHelper.assertIfNotKernel(sub);
      return domainAccess;
   }

   public static void initializeRuntime(RuntimeAccess runtime) {
      if (runtimeAccess != null) {
         throw new AssertionError("The managment service can only be initialized once");
      } else {
         runtimeAccess = runtime;
      }
   }

   public static boolean isRuntimeAccessInitialized() {
      return runtimeAccess != null;
   }

   public static void initializeDomain(DomainAccess domain) {
      if (domainAccess != null) {
         throw new AssertionError("The domain access can only be initialized once");
      } else {
         domainAccess = domain;
      }
   }

   public static void initializeRuntimeMBeanServer(AuthenticatedSubject sub, MBeanServer s) {
      SecurityHelper.assertIfNotKernel(sub);
      if (runtimeMBeanServer != null) {
         throw new AssertionError("MBeanServer may not be reset.");
      } else {
         runtimeMBeanServer = s;
      }
   }

   public static void initializeDomainRuntimeMBeanServer(AuthenticatedSubject sub, MBeanServer s) {
      SecurityHelper.assertIfNotKernel(sub);
      if (domainRuntimeMBeanServer != null) {
         throw new AssertionError("DomainRuntime MBeanServer may not be reset.");
      } else {
         domainRuntimeMBeanServer = s;
      }
   }

   public static void initializeEditMBeanServer(AuthenticatedSubject sub, MBeanServer s) {
      SecurityHelper.assertIfNotKernel(sub);
      if (editMBeanServer != null) {
         throw new AssertionError("Edit MBeanServer may not be reset.");
      } else {
         editMBeanServer = s;
      }
   }

   public static MBeanServer getRuntimeMBeanServer(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      return runtimeMBeanServer;
   }

   public static MBeanServer getDomainRuntimeMBeanServer(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      return domainRuntimeMBeanServer;
   }

   public static MBeanServer getEditMBeanServer(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      return editMBeanServer;
   }

   /** @deprecated */
   @Deprecated
   public static PropertyService getPropertyService(AuthenticatedSubject sub) {
      return PropertyService.getPropertyService(sub);
   }
}
