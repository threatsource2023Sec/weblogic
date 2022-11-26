package weblogic.work.concurrent.context;

import javax.naming.Context;
import weblogic.invocation.ComponentInvocationContextManager;

public class ApplicationContextProcessor extends FixedContextProcessor {
   private static final long serialVersionUID = 6410608451159450170L;

   public ApplicationContextProcessor(String appId, ClassLoader classLoader, Context jndi, int concurrentType) {
      super(concurrentType, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), new FixedContextProcessor.FixedCICProvider(ComponentInvocationContextManager.getInstance().createComponentInvocationContext(appId, (String)null, (String)null)), new FixedContextProcessor.FixedClassLoaderProvider(classLoader), new FixedContextProcessor.FixedJndiProvider(jndi), FixedContextProcessor.FixedSecurityProvider.getAnonSubjectInstance(), FixedContextProcessor.FixedWorkAreaProvider.getEmptyMapInstance());
   }

   public ApplicationContextProcessor(String appId, ClassLoader classLoader, Context jndi, String contextServiceID, String contextServiceInfo) {
      super(1, DefaultContextsProvider.getContextServiceInstance(), DefaultContextsProvider.getNonContextServiceInstance(), new FixedContextProcessor.FixedCICProvider(ComponentInvocationContextManager.getInstance().createComponentInvocationContext(appId, (String)null, (String)null)), new StateCheckerProvider(contextServiceID, contextServiceInfo), new FixedContextProcessor.FixedClassLoaderProvider(classLoader), new FixedContextProcessor.FixedJndiProvider(jndi), FixedContextProcessor.FixedSecurityProvider.getAnonSubjectInstance(), FixedContextProcessor.FixedWorkAreaProvider.getEmptyMapInstance());
   }

   protected void addCustomProviderList(ContextSetupProcessor.ContextSetupHandles handles) {
   }
}
