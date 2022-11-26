package weblogic.diagnostics.lifecycle;

import weblogic.diagnostics.healthcheck.HealthCheckMBeanImpl;
import weblogic.diagnostics.image.HealthSource;
import weblogic.logging.NonCatalogLogger;

public class HealthCheckLifecycleImpl implements DiagnosticComponentLifecycle {
   private static HealthCheckLifecycleImpl singleton = new HealthCheckLifecycleImpl();
   private static final NonCatalogLogger logger = new NonCatalogLogger("HealthCheckService");
   private int status = 4;
   private HealthCheckMBeanImpl healthCheckMBean;

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return this.status;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      this.healthCheckMBean = HealthCheckMBeanImpl.getInstance();
      if (this.healthCheckMBean != null) {
         this.status = 1;
         HealthSource healthSource = HealthSource.getInstance();
         HealthCheckMBeanImpl.getInstance().registerHealthCheck(healthSource);
         logger.info("Health check service enabled");
      } else {
         this.status = 4;
         logger.info("Health check service not available");
      }

   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
   }
}
