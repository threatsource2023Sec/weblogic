package weblogic.rmi.facades;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.LocatorUtilities;

@Contract
public abstract class RmiInvocationFacade {
   public static String getGlobalPartitionName() {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doGetGlobalPartitionName();
   }

   public static String getPartitionNameForUrl(String url) throws URISyntaxException {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doLookupPartitionNameForUrl(url);
   }

   public static String getPartitionNameForAddress(InetSocketAddress address) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.getLookupPartitionNameForAddress(address);
   }

   public static ComponentInvocationContext createInvocationContext(AuthenticatedSubject kernelId, String partitionName) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doCreateInvocationContext(kernelId, partitionName);
   }

   public static ManagedInvocationContext setPartitionName(AuthenticatedSubject kernelId, String partitionName) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doSetPartitionName(kernelId, partitionName);
   }

   public static ManagedInvocationContext setInvocationContext(AuthenticatedSubject kernelId, ComponentInvocationContext invocationContext) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doSetInvocationContext(kernelId, invocationContext);
   }

   public static ComponentInvocationContext getCurrentComponentInvocationContext(AuthenticatedSubject kernelId) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doGetCurrentComponentInvocationContext(kernelId);
   }

   public static String getCurrentPartitionName(AuthenticatedSubject kernelId) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doGetCurrentPartitionName(kernelId);
   }

   public static boolean isGlobalPartition(AuthenticatedSubject kernelId) {
      return RmiInvocationFacade.RmiInvocationFacadeInitializer.instance.doIsGlobalPartition(kernelId);
   }

   protected abstract String doGetGlobalPartitionName();

   protected abstract String doLookupPartitionNameForUrl(String var1) throws URISyntaxException;

   protected abstract String getLookupPartitionNameForAddress(InetSocketAddress var1);

   protected abstract ManagedInvocationContext doSetPartitionName(AuthenticatedSubject var1, String var2);

   protected abstract ManagedInvocationContext doSetInvocationContext(AuthenticatedSubject var1, ComponentInvocationContext var2);

   protected abstract ComponentInvocationContext doCreateInvocationContext(AuthenticatedSubject var1, String var2);

   protected abstract ComponentInvocationContext doGetCurrentComponentInvocationContext(AuthenticatedSubject var1);

   protected abstract boolean doIsGlobalPartition(AuthenticatedSubject var1);

   protected abstract String doGetCurrentPartitionName(AuthenticatedSubject var1);

   private static final class RmiInvocationFacadeInitializer {
      private static final RmiInvocationFacade instance = (RmiInvocationFacade)LocatorUtilities.getService(RmiInvocationFacade.class);
   }
}
