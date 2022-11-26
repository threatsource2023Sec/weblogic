package weblogic.rmi.internal;

import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.WorkManager;

public final class AdminAccessOnlyServerRef extends BasicServerRef {
   public AdminAccessOnlyServerRef(Object o) throws RemoteException {
      super(o);
   }

   public AdminAccessOnlyServerRef(int oid, Object o) throws RemoteException {
      super(oid, o);
   }

   protected WorkManager getWorkManager(RuntimeMethodDescriptor md, AuthenticatedSubject subject) {
      if (!isAdminUser(subject)) {
         throw new SecurityException("Method '" + md.getMethod().getName() + "' cannot be invoked without administrator access. Subject: " + subject);
      } else {
         return md.getWorkManager();
      }
   }

   private static boolean isAdminUser(AuthenticatedSubject as) {
      return SubjectUtils.isUserInGroup(as, "Administrators") || SubjectUtils.doesUserHaveAnyAdminRoles(as);
   }
}
