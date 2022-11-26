package weblogic.rmi.client.facades;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.LocatorUtilities;

public abstract class RmiClientSecurityFacade {
   private static RmiClientSecurityFacadeDelegate getInstance() {
      return RmiClientSecurityFacade.FacadeInitializer.instance;
   }

   public static AuthenticatedSubject getCurrentSubject(AuthenticatedSubject kernelId) {
      return getInstance().doGetCurrentSubject(kernelId);
   }

   public static boolean isKernelIdentity(AuthenticatedSubject subject) {
      return getInstance().doIsKernelIdentity(subject);
   }

   public static boolean isSubjectAnonymous(AuthenticatedSubject subject) {
      return getInstance().doIsUserAnonymous(subject);
   }

   public static String getUsername(AuthenticatedSubject subject) {
      return getInstance().doGetUsername(subject);
   }

   private static final class FacadeInitializer {
      private static final RmiClientSecurityFacadeDelegate instance = (RmiClientSecurityFacadeDelegate)LocatorUtilities.getService(RmiClientSecurityFacadeDelegate.class);
   }
}
