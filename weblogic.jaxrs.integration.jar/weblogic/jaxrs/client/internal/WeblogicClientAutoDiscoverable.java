package weblogic.jaxrs.client.internal;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.spi.ExecutorServiceProvider;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;

@ConstrainedTo(RuntimeType.CLIENT)
@Priority(1900)
public final class WeblogicClientAutoDiscoverable implements ForcedAutoDiscoverable {
   public void configure(FeatureContext context) {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String applicationName = cic.getApplicationName();
      if (applicationName != null) {
         context.register(WeblogicClientAsyncExecutorProvider.class, 5001);
      }

   }

   private static class ExecutorServiceBinder extends AbstractBinder {
      protected void configure() {
         ((ClassBinding)((ClassBinding)this.bind(WeblogicClientAsyncExecutorProvider.class).to(ExecutorServiceProvider.class)).in(Singleton.class)).ranked(5000);
      }
   }
}
