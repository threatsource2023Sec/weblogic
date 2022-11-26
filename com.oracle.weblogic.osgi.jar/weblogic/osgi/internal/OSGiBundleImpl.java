package weblogic.osgi.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import weblogic.osgi.OSGiBundle;
import weblogic.osgi.OSGiException;

public class OSGiBundleImpl implements OSGiBundle {
   private final Object lock = new Object();
   private final Bundle bundle;
   private boolean uninstalled = false;

   public OSGiBundleImpl(Bundle myBundle) {
      this.bundle = myBundle;
   }

   public void start() throws OSGiException {
      synchronized(this.lock) {
         if (this.uninstalled) {
            throw new OSGiException("This bundle was uninstalled " + this);
         }
      }

      try {
         this.bundle.start(2);
      } catch (BundleException var3) {
         throw new OSGiException(var3);
      }
   }

   public void uninstall() {
      synchronized(this.lock) {
         if (this.uninstalled) {
            return;
         }

         this.uninstalled = true;
      }

      try {
         this.bundle.uninstall();
      } catch (BundleException var3) {
         if (Logger.isDebugEnabled()) {
            Logger.getLogger().debug(var3.getMessage(), var3);
         }
      }

   }

   public void stop() {
      synchronized(this.lock) {
         if (this.uninstalled) {
            return;
         }
      }

      try {
         this.bundle.stop();
      } catch (BundleException var3) {
         if (Logger.isDebugEnabled()) {
            Logger.getLogger().debug(var3.getMessage(), var3);
         }
      }

   }

   public String toString() {
      return "OSGiBundleImpl(" + this.bundle.getSymbolicName() + "," + System.identityHashCode(this) + ")";
   }
}
