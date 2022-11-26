package weblogic.rmi.facades;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.invocation.PartitionTable;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Service
public class ServerRmiInvocationFacade extends RmiInvocationFacade {
   private static PartitionTable partitionTable;

   protected String doGetGlobalPartitionName() {
      return this.getPartitionTable().getGlobalPartitionName();
   }

   protected String doLookupPartitionNameForUrl(String url) throws URISyntaxException {
      return this.getPartitionTable().lookup(url).getPartitionName();
   }

   protected String getLookupPartitionNameForAddress(InetSocketAddress address) {
      return this.getPartitionTable().lookup(address).getPartitionName();
   }

   protected ManagedInvocationContext doSetPartitionName(AuthenticatedSubject kernelId, String partitionName) {
      return this.setPartitionName(this.getComponentInvocationContextManager(kernelId), partitionName);
   }

   private ManagedInvocationContext setPartitionName(ComponentInvocationContextManager componentInvocationContextManager, String partitionName) {
      return componentInvocationContextManager.setCurrentComponentInvocationContext(componentInvocationContextManager.createComponentInvocationContext(partitionName));
   }

   protected ManagedInvocationContext doSetInvocationContext(AuthenticatedSubject kernelId, ComponentInvocationContext invocationContext) {
      return this.getComponentInvocationContextManager(kernelId).setCurrentComponentInvocationContext(invocationContext);
   }

   protected ComponentInvocationContext doCreateInvocationContext(AuthenticatedSubject kernelId, String partitionName) {
      return this.getComponentInvocationContextManager(kernelId).createComponentInvocationContext(partitionName);
   }

   private PartitionTable getPartitionTable() {
      if (partitionTable == null) {
         partitionTable = PartitionTable.getInstance();
      }

      return partitionTable;
   }

   private ComponentInvocationContextManager getComponentInvocationContextManager(AuthenticatedSubject kernelID) {
      return ComponentInvocationContextManager.getInstance(kernelID);
   }

   public ComponentInvocationContext doGetCurrentComponentInvocationContext(AuthenticatedSubject kernelId) {
      return this.getComponentInvocationContextManager(kernelId).getCurrentComponentInvocationContext();
   }

   protected boolean doIsGlobalPartition(AuthenticatedSubject kernelId) {
      ComponentInvocationContext ctx = this.getComponentInvocationContextManager(kernelId).getCurrentComponentInvocationContext();
      return ctx == null || ctx.isGlobalRuntime();
   }

   protected String doGetCurrentPartitionName(AuthenticatedSubject kernelId) {
      ComponentInvocationContext ctx = this.getComponentInvocationContextManager(kernelId).getCurrentComponentInvocationContext();
      return ctx == null ? this.doGetGlobalPartitionName() : ctx.getPartitionName();
   }
}
