package weblogic.server;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.T3SrvrLogger;

public abstract class AbstractServerService implements ServerService {
   private static final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   private static AtomicReference currentService = new AtomicReference();
   private final String serviceName = this.getClass().getSimpleName();

   public String getName() {
      String className = this.getClass().getName();
      int idx = className.lastIndexOf(".");
      return idx <= 0 ? className : className.substring(idx + 1, className.length());
   }

   public String getVersion() {
      return null;
   }

   public void start() throws ServiceFailureException {
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
   }

   public void stop(Map directives) throws ServiceFailureException {
      this.stop();
   }

   @PostConstruct
   public void postConstruct() {
      try {
         this.start();
      } catch (ServiceFailureException var2) {
         throw new RuntimeException(var2);
      }
   }

   @PreDestroy
   public void preDestroy() {
      long elapsedTime = 0L;
      boolean success = false;
      if (isDebugEnabled()) {
         elapsedTime = System.currentTimeMillis();
      }

      try {
         currentService.set(this.serviceName);
         if (ShutdownParametersBean.getInstance().isGraceful()) {
            if (ShutdownParametersBean.getInstance().ignoreSessions()) {
               if (isDebugEnabled()) {
                  debug("Calling STOP method of with Shutdown Directives of " + this.serviceName);
               }

               this.stop(ShutdownParametersBean.getInstance().getShutdownDirectives());
            } else {
               if (isDebugEnabled()) {
                  debug("Calling STOP method of " + this.serviceName);
               }

               this.stop();
            }

            success = true;
         } else {
            if (isDebugEnabled()) {
               debug("Calling HALT method of " + this.serviceName);
            }

            try {
               this.halt();
               success = true;
            } catch (Throwable var9) {
               T3SrvrLogger.logServiceFailure("halt call on " + this.serviceName + " failed", var9);
            }
         }
      } catch (ServiceFailureException var10) {
         throw new RuntimeException(var10);
      } finally {
         if (isDebugEnabled()) {
            elapsedTime = System.currentTimeMillis() - elapsedTime;
            debug("Shutdown of " + this.serviceName + " completed " + (success ? "succesfully" : "with failure") + " in " + elapsedTime + " ms");
         }

      }

   }

   private static void debug(String str) {
      debugSLCWLDF.debug(str);
   }

   private static boolean isDebugEnabled() {
      return debugSLCWLDF.isDebugEnabled();
   }
}
