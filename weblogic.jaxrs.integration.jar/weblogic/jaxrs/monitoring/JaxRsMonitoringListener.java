package weblogic.jaxrs.monitoring;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.MonitoringStatisticsListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.glassfish.jersey.server.wadl.WadlApplicationContext;
import org.glassfish.jersey.servlet.WebConfig;
import weblogic.jaxrs.monitoring.impl.JaxRsApplicationMBeanImpl;
import weblogic.jaxrs.monitoring.spi.ResourcePatternProvider;
import weblogic.jaxrs.monitoring.util.MonitoringUtil;
import weblogic.servlet.internal.WebAppRuntimeMBeanImpl;
import weblogic.servlet.internal.WebAppServletContext;

@Priority(1200)
public final class JaxRsMonitoringListener implements ApplicationEventListener, MonitoringStatisticsListener {
   private static final Logger LOGGER = Logger.getLogger(JaxRsMonitoringListener.class.getName());
   private static final boolean MONITORING_EXTENDED_ENABLED_DEFAULT = false;
   private volatile JaxRsApplicationMBeanImpl application;
   private final AtomicReference state;
   @Inject
   private WebConfig webConfig;
   @Inject
   InjectionManager manager;

   public JaxRsMonitoringListener() {
      this.state = new AtomicReference(JaxRsMonitoringListener.State.NEW);
   }

   public void onStatistics(MonitoringStatistics statistics) {
      if (this.state.compareAndSet(JaxRsMonitoringListener.State.MODEL_READY, JaxRsMonitoringListener.State.INIT_APP)) {
         JaxRsApplicationMBeanImpl app = null;

         try {
            this.application.initMonitoringStatistics(statistics);
            if (!this.state.compareAndSet(JaxRsMonitoringListener.State.INIT_APP, JaxRsMonitoringListener.State.APP_READY)) {
               throw new DestroyDetectedException();
            }
         } catch (Throwable var10) {
            this.toDestroyed(var10);
         }
      }

      if (this.state.compareAndSet(JaxRsMonitoringListener.State.APP_READY, JaxRsMonitoringListener.State.UPDATING)) {
         try {
            this.application.update(statistics);
         } catch (Exception var8) {
            LOGGER.fine("Updating monitoring statistics failed: " + var8.getMessage());
         } finally {
            this.state.set(JaxRsMonitoringListener.State.APP_READY);
         }
      }

   }

   public void onEvent(ApplicationEvent event) {
      switch (event.getType()) {
         case INITIALIZATION_FINISHED:
            if (this.state.compareAndSet(JaxRsMonitoringListener.State.NEW, JaxRsMonitoringListener.State.INIT_MODEL)) {
               this.initJaxRsApplicationMBean(event);
            }
            break;
         case DESTROY_FINISHED:
            while(true) {
               if (this.state.get() == JaxRsMonitoringListener.State.DESTROYED || this.state.compareAndSet(JaxRsMonitoringListener.State.NEW, JaxRsMonitoringListener.State.DESTROYED) || this.state.compareAndSet(JaxRsMonitoringListener.State.MODEL_READY, JaxRsMonitoringListener.State.DESTROYED) || this.state.compareAndSet(JaxRsMonitoringListener.State.APP_READY, JaxRsMonitoringListener.State.DESTROYED) || this.state.compareAndSet(JaxRsMonitoringListener.State.INIT_APP, JaxRsMonitoringListener.State.DESTROYED)) {
                  JaxRsApplicationMBeanImpl app = this.application;
                  this.application = null;
                  if (app != null) {
                     try {
                        app.unregister();
                     } catch (Exception var4) {
                        LOGGER.fine("Unregister failed for existing application MBean: " + var4.getMessage());
                     }
                  }
                  break;
               }
            }
      }

   }

   private void initJaxRsApplicationMBean(ApplicationEvent applicationEvent) {
      try {
         WebAppServletContext context = (WebAppServletContext)this.webConfig.getServletContext();
         WebAppRuntimeMBeanImpl parent = context.getRuntimeMBean();
         String name = this.webConfig.getName() != null ? this.webConfig.getName() : "";
         boolean extendedEnabled = MonitoringUtil.convertBooleanProperty(applicationEvent.getResourceConfig().getProperty("jersey.config.wls.server.monitoring.extended.enabled"), false);
         LOGGER.fine("Registration of extended resources' and resource methods' monitoring beans is " + (extendedEnabled ? "+enabled+" : "-disabled-") + " for JAX-RS application " + name + " in " + parent.getComponentName());
         WadlApplicationContext wadlContext = null;

         try {
            wadlContext = (WadlApplicationContext)Injections.getOrCreate(this.manager, WadlApplicationContext.class);
         } catch (Exception var8) {
         }

         this.application = new JaxRsApplicationMBeanImpl(name, context, parent, wadlContext, applicationEvent, extendedEnabled);
         ResourcePatternProvider patternProvider = (ResourcePatternProvider)this.manager.getInstance(ResourcePatternProvider.class);
         if (patternProvider != null) {
            this.application.setResourcePattern(patternProvider.getResourcePattern(context));
         } else {
            LOGGER.log(Level.FINER, "Cannot obtain provider to retrieve a Resource Pattern for this application.");
         }

         parent.addJaxRsApplication(this.application);
         this.state.set(JaxRsMonitoringListener.State.MODEL_READY);
      } catch (Throwable var9) {
         this.toDestroyed(var9);
      }

   }

   private void toDestroyed(Throwable throwable) {
      this.state.set(JaxRsMonitoringListener.State.DESTROYED);
      if (this.application != null) {
         try {
            this.application.unregister();
         } catch (Exception var3) {
            LOGGER.fine("Unregister failed for existing application MBean: " + var3.getMessage());
         }
      }

      this.application = null;
      if (!(throwable instanceof DestroyDetectedException)) {
         String err = "Application MBean not initialized for application '" + this.webConfig.getName() + "'";
         LOGGER.log(Level.WARNING, err, throwable);
         throw new RuntimeException(throwable);
      }
   }

   public RequestEventListener onRequest(RequestEvent requestEvent) {
      return null;
   }

   private static enum State {
      NEW,
      INIT_MODEL,
      MODEL_READY,
      INIT_APP,
      APP_READY,
      UPDATING,
      DESTROYED;
   }

   private static final class DestroyDetectedException extends Exception {
      private DestroyDetectedException() {
      }

      // $FF: synthetic method
      DestroyDetectedException(Object x0) {
         this();
      }
   }
}
