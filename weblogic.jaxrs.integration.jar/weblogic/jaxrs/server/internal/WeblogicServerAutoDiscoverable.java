package weblogic.jaxrs.server.internal;

import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;
import org.glassfish.jersey.spi.ExecutorServiceProvider;
import org.glassfish.jersey.spi.ScheduledExecutorServiceProvider;
import weblogic.jaxrs.monitoring.JaxRsMonitoringListener;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.configuration.WebAppContainerMBean;

@ConstrainedTo(RuntimeType.SERVER)
@Priority(1900)
public final class WeblogicServerAutoDiscoverable implements ForcedAutoDiscoverable {
   public void configure(FeatureContext context) {
      this.processMonitoring(context);
      context.register(new JaxRsMonitoringListener());
      context.register(ChunkedOutputWriter.class);
      context.register(WeblogicManagedAsyncExecutorProvider.class, 5001);
      context.register(WeblogicBackgroundSchedulerProvider.class, 5001);
   }

   private void processMonitoring(FeatureContext context) {
      Configuration config = context.getConfiguration();
      WebAppComponentMBean component = (WebAppComponentMBean)config.getProperty("weblogic.servlet.WebAppComponentMBean");
      boolean disable = false;
      if (component != null) {
         if (component.isJaxRsMonitoringDefaultBehavior() == null) {
            WebAppContainerMBean container = this.getContainerMBean(component);
            if (container != null) {
               boolean global = container.isJaxRsMonitoringDefaultBehavior();
               disable = container.isJaxRsMonitoringDefaultBehaviorSet() && !global;
            }
         } else {
            disable = !component.isJaxRsMonitoringDefaultBehavior();
         }
      }

      if (disable) {
         this.disableMonitoring(context);
      } else if (config.getProperty("jersey.config.server.monitoring.enabled") == null && config.getProperty("jersey.config.server.monitoring.statistics.enabled") == null) {
         Object property = config.getProperty("jersey.config.wls.server.monitoring.enabled");
         if (property != null && !Boolean.valueOf(property.toString())) {
            this.disableMonitoring(context);
         } else {
            context.property("jersey.config.server.monitoring.enabled", true);
            context.property("jersey.config.server.monitoring.statistics.enabled", true);
         }
      }

   }

   private void disableMonitoring(FeatureContext context) {
      context.property("jersey.config.server.monitoring.enabled", false);
      context.property("jersey.config.server.monitoring.statistics.enabled", false);
      context.property("jersey.config.server.monitoring.statistics.mbeans.enabled", false);
   }

   private WebAppContainerMBean getContainerMBean(WebAppComponentMBean component) {
      WebLogicMBean parent;
      for(parent = component.getParent(); parent != null && !(parent instanceof DomainMBean); parent = parent.getParent()) {
      }

      return parent != null ? ((DomainMBean)parent).getWebAppContainer() : null;
   }

   private static class ExecutorServiceBinder extends AbstractBinder {
      protected void configure() {
         ((ClassBinding)((ClassBinding)this.bind(WeblogicManagedAsyncExecutorProvider.class).to(ExecutorServiceProvider.class)).in(Singleton.class)).ranked(5000);
         ((ClassBinding)((ClassBinding)this.bind(WeblogicBackgroundSchedulerProvider.class).to(ScheduledExecutorServiceProvider.class)).in(Singleton.class)).ranked(5000);
      }
   }
}
