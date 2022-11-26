package weblogic.server;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.AssertionError;

public class ServiceActivator extends AbstractServerService {
   private static final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   private ActivatedService serviceObj;
   protected String serviceClass;
   private boolean started;
   private boolean isServiceAvailableBeforeStart;

   public ServiceActivator(String serviceClass) {
      this.serviceClass = serviceClass;
      this.serviceObj = null;
      this.started = false;
      this.isServiceAvailableBeforeStart = false;
   }

   protected void setServiceAvailableBeforeStart(boolean available) {
      this.isServiceAvailableBeforeStart = available;
   }

   public String getName() {
      int idx = this.serviceClass.lastIndexOf(".");
      return idx <= 0 ? this.serviceClass : this.serviceClass.substring(idx + 1, this.serviceClass.length());
   }

   protected ActivatedService instantiateService() throws ServiceFailureException {
      ActivatedService serviceObj = null;

      try {
         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         serviceObj = (ActivatedService)serviceLocator.getService(Class.forName(this.serviceClass), new Annotation[0]);
         if (serviceObj == null) {
            throw new ServiceFailureException("Cannot find " + this.serviceClass);
         } else {
            serviceObj.setActivator(this);
            return serviceObj;
         }
      } catch (ClassCastException var3) {
         throw new AssertionError("ServerService class " + this.serviceClass + " doesn't extend ActivatedService", var3);
      } catch (ClassNotFoundException var4) {
         throw new AssertionError("ActivatedService class " + this.serviceClass + " not found", var4);
      }
   }

   public final void start() throws ServiceFailureException {
      if (this.serviceObj == null) {
         this.serviceObj = this.instantiateService();
      }

      if (this.serviceObj != null) {
         if (this.isDebugEnabled()) {
            print("start() : Calling startService() on " + this.serviceObj.getClass().getName());
         }

         this.started = this.serviceObj.startService();
      } else if (this.isDebugEnabled()) {
         print("start() : ServiceActivator.start(): serviceObj == null, serviceClass = " + this.serviceClass);
      }

   }

   public final void stop() throws ServiceFailureException {
      if (this.serviceObj != null) {
         this.serviceObj.stopService();
         this.started = false;
      }

   }

   public final void halt() throws ServiceFailureException {
      if (this.serviceObj != null) {
         this.serviceObj.haltService();
         this.started = false;
      }

   }

   public boolean isStarted() {
      return this.started;
   }

   public Object getServiceObj() {
      if (this.started) {
         return this.serviceObj;
      } else if (this.isServiceAvailableBeforeStart) {
         try {
            this.serviceObj = this.instantiateService();
            return this.serviceObj;
         } catch (ServiceFailureException var2) {
            throw new AssertionError(var2.getMessage());
         }
      } else {
         return null;
      }
   }

   private static void print(String message) {
      debugSLCWLDF.debug("ServiceActivator." + message);
   }

   private boolean isDebugEnabled() {
      return debugSLCWLDF.isDebugEnabled();
   }
}
