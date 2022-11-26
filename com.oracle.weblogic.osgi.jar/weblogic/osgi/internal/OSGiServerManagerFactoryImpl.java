package weblogic.osgi.internal;

import weblogic.osgi.OSGiServerManager;
import weblogic.osgi.OSGiServerManagerFactory;

public class OSGiServerManagerFactoryImpl extends OSGiServerManagerFactory {
   private final OSGiServerManager manager = new OSGiServerManagerImpl();

   public OSGiServerManager getOSGiServerManager() {
      return this.manager;
   }

   public String toString() {
      return "OSGiServerManagerFactoryImpl(" + System.identityHashCode(this) + ")";
   }
}
