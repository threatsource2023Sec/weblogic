package weblogic.corba.idl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.AccessController;
import org.omg.CORBA.Object;
import weblogic.corba.cos.naming.NamingContextImpl;
import weblogic.corba.cos.naming.RootNamingContextImpl;
import weblogic.iiop.RequestUrl;
import weblogic.iiop.ior.IOR;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class NamingServiceInitialReferenceDelegateImpl extends DelegateImpl {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   NamingServiceInitialReferenceDelegateImpl(IOR ior) {
      super(ior);
   }

   public boolean non_existent(Object self) {
      if (self instanceof org.omg.CORBA.portable.ObjectImpl) {
         ((org.omg.CORBA.portable.ObjectImpl)self)._set_delegate(DelegateFactory.createDelegate(this.getPartitionRootNamingContextIor(RequestUrl.get())));
      }

      return super.non_existent(self);
   }

   private IOR getPartitionRootNamingContextIor(String requestUrl) {
      try {
         return getPartitionRootNamingContext(requestUrl).getIOR();
      } catch (IOException var3) {
         throw new RuntimeException("Unable to redirect to partition context", var3);
      }
   }

   public static NamingContextImpl getPartitionRootNamingContext(String requestUrl) {
      try {
         ManagedInvocationContext ignored = RmiInvocationFacade.setPartitionName(KERNEL_ID, RmiInvocationFacade.getPartitionNameForUrl(requestUrl));
         Throwable var2 = null;

         NamingContextImpl var3;
         try {
            var3 = RootNamingContextImpl.getInitialReference().getRootContextForCurrentPartition();
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (ignored != null) {
               if (var2 != null) {
                  try {
                     ignored.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  ignored.close();
               }
            }

         }

         return var3;
      } catch (URISyntaxException var15) {
         throw new RuntimeException("Unable to redirect to partition context", var15);
      }
   }
}
