package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.cluster.singleton.LeasingFactory.Locator;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.server.GlobalServiceLocator;

public class SingletonServiceWrapper implements SingletonService, LeaseLostListener {
   private SingletonService delegate;
   private boolean active = false;
   private ComponentInvocationContext cic = null;

   public SingletonServiceWrapper(SingletonService delegate, Object cic) {
      this.delegate = delegate;
      if (cic instanceof ComponentInvocationContext) {
         this.cic = (ComponentInvocationContext)cic;
      }

   }

   public void onRelease() {
      this.deactivate();
   }

   public synchronized void activate() {
      if (!this.active) {
         ManagedInvocationContext mic = null;
         if (this.cic != null) {
            mic = ((ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0])).setInvocationContext(this.cic);
         }

         ManagedInvocationContext mic_tmp = mic;
         Throwable var3 = null;

         try {
            this.delegate.activate();
         } catch (Throwable var12) {
            var3 = var12;
            throw var12;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic_tmp.close();
                  } catch (Throwable var11) {
                     var3.addSuppressed(var11);
                  }
               } else {
                  mic.close();
               }
            }

         }

         this.registerWithSingletonMonitorLeaseManager();
         this.active = true;
      }
   }

   public synchronized void deactivate() {
      if (this.active) {
         this.unregisterWithSingletonMonitorLeaseManager();
         ManagedInvocationContext mic = null;
         if (this.cic != null) {
            mic = ((ClusterServicesInvocationContext)GlobalServiceLocator.getServiceLocator().getService(ClusterServicesInvocationContext.class, new Annotation[0])).setInvocationContext(this.cic);
         }

         ManagedInvocationContext mic_tmp = mic;
         Throwable var3 = null;

         try {
            this.delegate.deactivate();
         } catch (Throwable var12) {
            var3 = var12;
            throw var12;
         } finally {
            if (mic != null) {
               if (var3 != null) {
                  try {
                     mic_tmp.close();
                  } catch (Throwable var11) {
                     var3.addSuppressed(var11);
                  }
               } else {
                  mic.close();
               }
            }

         }

         this.active = false;
      }
   }

   private void registerWithSingletonMonitorLeaseManager() {
      Leasing leaseMgr = Locator.locateService().findOrCreateLeasingService("service");
      leaseMgr.addLeaseLostListener(this);
   }

   private void unregisterWithSingletonMonitorLeaseManager() {
      Leasing leaseMgr = Locator.locateService().findOrCreateLeasingService("service");
      leaseMgr.removeLeaseLostListener(this);
   }
}
