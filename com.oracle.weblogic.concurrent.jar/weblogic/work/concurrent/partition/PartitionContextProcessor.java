package weblogic.work.concurrent.partition;

import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.work.concurrent.context.ContextSetupProcessor;
import weblogic.work.concurrent.context.DefaultContextsProvider;
import weblogic.work.concurrent.context.FixedContextProcessor;
import weblogic.work.concurrent.context.StateCheckerProvider;

public class PartitionContextProcessor extends FixedContextProcessor {
   private static final long serialVersionUID = -3169838085336494014L;

   public PartitionContextProcessor(String partitionName, ClassLoader classLoader, int concurrentType) {
      super(concurrentType, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), new FixedContextProcessor.FixedCICProvider(ComponentInvocationContextManager.getInstance().createComponentInvocationContext(partitionName)), new FixedContextProcessor.FixedClassLoaderProvider(classLoader), FixedContextProcessor.FixedJndiProvider.getNullContextInstance(), FixedContextProcessor.FixedSecurityProvider.getAnonSubjectInstance(), FixedContextProcessor.FixedWorkAreaProvider.getEmptyMapInstance());
   }

   public PartitionContextProcessor(String partitionName, ClassLoader classLoader, String contextServiceID, String contextServiceInfo) {
      super(1, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), new FixedContextProcessor.FixedCICProvider(ComponentInvocationContextManager.getInstance().createComponentInvocationContext(partitionName)), new StateCheckerProvider(contextServiceID, contextServiceInfo), new FixedContextProcessor.FixedClassLoaderProvider(classLoader), FixedContextProcessor.FixedJndiProvider.getNullContextInstance(), FixedContextProcessor.FixedSecurityProvider.getAnonSubjectInstance(), FixedContextProcessor.FixedWorkAreaProvider.getEmptyMapInstance());
   }

   protected void addCustomProviderList(ContextSetupProcessor.ContextSetupHandles handles) {
   }
}
