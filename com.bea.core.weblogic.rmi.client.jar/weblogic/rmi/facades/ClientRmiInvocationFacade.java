package weblogic.rmi.facades;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Service
@Rank(-1)
public class ClientRmiInvocationFacade extends RmiInvocationFacade {
   private static final String GLOBAL_PARTITION_NAME = "DOMAIN";
   private static final ComponentInvocationContext GLOBAL_CONTEXT = new ClientComponentInvocationContext();
   private static final ManagedInvocationContext NULL_MANAGED_INVOCATION_CONTEXT = new NullManagedInvocationContext();

   protected String doGetGlobalPartitionName() {
      return "DOMAIN";
   }

   protected String doLookupPartitionNameForUrl(String requestUrlString) throws URISyntaxException {
      return "DOMAIN";
   }

   protected String getLookupPartitionNameForAddress(InetSocketAddress address) {
      return "DOMAIN";
   }

   protected ManagedInvocationContext doSetPartitionName(AuthenticatedSubject kernelId, String partitionName) {
      return NULL_MANAGED_INVOCATION_CONTEXT;
   }

   protected ManagedInvocationContext doSetInvocationContext(AuthenticatedSubject kernelId, ComponentInvocationContext invocationContext) {
      return NULL_MANAGED_INVOCATION_CONTEXT;
   }

   protected ComponentInvocationContext doCreateInvocationContext(AuthenticatedSubject kernelId, String partitionName) {
      return null;
   }

   protected ComponentInvocationContext doGetCurrentComponentInvocationContext(AuthenticatedSubject kernelId) {
      return GLOBAL_CONTEXT;
   }

   protected boolean doIsGlobalPartition(AuthenticatedSubject kernelId) {
      return true;
   }

   protected String doGetCurrentPartitionName(AuthenticatedSubject kernelId) {
      return "DOMAIN";
   }

   private static class NullManagedInvocationContext implements ManagedInvocationContext {
      private NullManagedInvocationContext() {
      }

      public void close() {
      }

      // $FF: synthetic method
      NullManagedInvocationContext(Object x0) {
         this();
      }
   }

   private static class ClientComponentInvocationContext implements ComponentInvocationContext {
      private ClientComponentInvocationContext() {
      }

      public String getPartitionId() {
         return "0";
      }

      public String getPartitionName() {
         return "DOMAIN";
      }

      public String getApplicationId() {
         return "";
      }

      public String getApplicationName() {
         return "";
      }

      public String getApplicationVersion() {
         return "";
      }

      public String getModuleName() {
         return "";
      }

      public String getComponentName() {
         return "";
      }

      public boolean isGlobalRuntime() {
         return true;
      }

      // $FF: synthetic method
      ClientComponentInvocationContext(Object x0) {
         this();
      }
   }
}
