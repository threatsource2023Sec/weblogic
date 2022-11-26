package weblogic.transaction.internal;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;

final class SecureAction {
   static final Object runAction(AuthenticatedSubject kernelID, PrivilegedExceptionAction action, String url, String actionNm) throws Exception {
      AuthenticatedSubject user = null;
      if (!PlatformHelper.getPlatformHelper().isServer()) {
         user = SecurityServiceManager.getCurrentSubject(kernelID);
      } else {
         if (url != null) {
            user = (AuthenticatedSubject)PlatformHelper.getPlatformHelper().getRemoteSubject(url);
         }

         if (user == null) {
            user = kernelID;
            PlatformHelper platformHelper = PlatformHelper.getPlatformHelper();
            if (platformHelper.getInteropMode() == 1 || platformHelper.getInteropMode() == 0 && kernelID.getQOS() != 103) {
               user = SubjectUtils.getAnonymousSubject();
            }
         }
      }

      try {
         if (TxDebug.JTANaming.isDebugEnabled()) {
            TxDebug.JTANaming.debug("SecureAction.runAction Use  Subject= " + SubjectUtils.getUsername(user) + "for action:" + actionNm + " to " + url);
         }

         return SecurityServiceManager.runAs(kernelID, user, action);
      } catch (PrivilegedActionException var6) {
         throw var6.getException();
      }
   }

   static final Object runKernelAction(AuthenticatedSubject kernelID, PrivilegedExceptionAction action, String actionNm) throws Exception {
      if (TxDebug.JTANaming.isDebugEnabled()) {
         TxDebug.JTANaming.debug("SecureAction.runKernelAction for action:" + actionNm);
      }

      AuthenticatedSubject user = kernelID;

      try {
         return SecurityServiceManager.runAs(kernelID, user, action);
      } catch (PrivilegedActionException var5) {
         throw var5.getException();
      }
   }
}
