package weblogic.osgi;

import weblogic.osgi.internal.OSGiServerManagerFactoryImpl;

public abstract class OSGiServerManagerFactory {
   private static final OSGiServerManagerFactory INSTANCE = new OSGiServerManagerFactoryImpl();

   public static OSGiServerManagerFactory getInstance() {
      return INSTANCE;
   }

   public abstract OSGiServerManager getOSGiServerManager();
}
