package weblogic.jndi.internal;

import java.rmi.RemoteException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class AdminRoleBasedDispatchServerRef extends BasicServerRef {
   public AdminRoleBasedDispatchServerRef(Object o) throws RemoteException {
      super(o);
   }

   public AdminRoleBasedDispatchServerRef(int oid, Object o) throws RemoteException {
      super(oid, o);
   }

   protected WorkManager getWorkManager(RuntimeMethodDescriptor md, AuthenticatedSubject subject) {
      return this.subjectHasAdminQOS(subject) ? WorkManagerFactory.getInstance().getSystem() : WorkManagerFactory.getInstance().getDefault();
   }

   private boolean subjectHasAdminQOS(AuthenticatedSubject subject) {
      if (SecurityServiceManager.isKernelIdentity(subject)) {
         return true;
      } else {
         return subject != null && subject.getQOS() == 103;
      }
   }

   public void invoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws Exception {
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_ADMIN_ROLE_BASED_DISPATCHER_SERVER_REF_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
      Throwable var7 = null;

      try {
         super.invoke(md, request, response);
      } catch (Throwable var16) {
         var7 = var16;
         throw var16;
      } finally {
         if (mic != null) {
            if (var7 != null) {
               try {
                  mic.close();
               } catch (Throwable var15) {
                  var7.addSuppressed(var15);
               }
            } else {
               mic.close();
            }
         }

      }

   }
}
