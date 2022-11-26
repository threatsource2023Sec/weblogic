package weblogic.protocol;

import weblogic.kernel.KernelStatus;
import weblogic.rjvm.RJVMEnvironment;

public final class ClientEnvironment {
   public static void loadEnvironment() {
      boolean ret = ClientEnvironment.EnvironmentLoader.LOADED;
   }

   private static final class EnvironmentLoader {
      private static final boolean LOADED = loadEnvironment();

      private static final boolean loadEnvironment() {
         if (KernelStatus.isApplet()) {
            return false;
         } else {
            if (System.getProperty("javax.rmi.CORBA.UtilClass") == null) {
               System.setProperty("javax.rmi.CORBA.UtilClass", "weblogic.iiop.UtilDelegateImpl");
            }

            RJVMEnvironment.getEnvironment().setPortableRemoteObjectDelegate();
            if (System.getProperty("org.omg.CORBA.ORBClass") == null) {
               System.setProperty("org.omg.CORBA.ORBClass", "weblogic.corba.orb.ORB");
            }

            if (System.getProperty("org.omg.CORBA.ORBSingletonClass") == null) {
               System.setProperty("org.omg.CORBA.ORBSingletonClass", "weblogic.corba.orb.ORB");
            }

            String packagePrefixes = System.getProperty("java.naming.factory.url.pkgs");
            if (packagePrefixes != null && packagePrefixes.length() != 0) {
               System.setProperty("java.naming.factory.url.pkgs", packagePrefixes + ":weblogic.jndi.factories:weblogic.corba.j2ee.naming.url");
            } else {
               System.setProperty("java.naming.factory.url.pkgs", "weblogic.jndi.factories:weblogic.corba.j2ee.naming.url");
            }

            return true;
         }
      }
   }
}
