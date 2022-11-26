package weblogic.corba.orb;

import weblogic.corba.client.spi.ServiceManager;
import weblogic.corba.server.naming.ReferenceHelperImpl;
import weblogic.iiop.IIOPLogger;
import weblogic.jndi.security.internal.server.ServerSubjectPusher;
import weblogic.kernel.Kernel;
import weblogic.protocol.ClientEnvironment;
import weblogic.rmi.extensions.server.ReferenceHelper;

public abstract class WlsIIOPInitialization {
   private static final String FALSE_PROP = "false";
   private static final String TRUE_PROP = "true";
   public static boolean initialized = false;

   public static boolean initialize() {
      if (initialized) {
         return true;
      } else if (!"false".equals(System.getProperty("weblogic.system.enableIIOP")) && !"false".equals(System.getProperty("weblogic.system.iiop.enable"))) {
         if ("true".equals(System.getProperty("weblogic.system.iiop.reconnectOnBootstrap"))) {
            ORB.reconnectOnBootstrap = true;
         }

         try {
            initialized = true;
            ClientEnvironment.loadEnvironment();
            if ("weblogic.iiop.UtilDelegateImpl".equals(System.getProperty("javax.rmi.CORBA.UtilClass"))) {
               if (Kernel.isServer()) {
                  IIOPLogger.logEnabled();
               }
            } else {
               IIOPLogger.logUtilClassNotInstalled(System.getProperty("javax.rmi.CORBA.UtilClass"));
            }

            if (!"weblogic.iiop.PortableRemoteObjectDelegateImpl".equals(System.getProperty("javax.rmi.CORBA.PortableRemoteObjectClass"))) {
               IIOPLogger.logPROClassNotInstalled(System.getProperty("javax.rmi.CORBA.PortableRemoteObjectClass"));
            }

            ReferenceHelper.setReferenceHelper(new ReferenceHelperImpl());
            ServiceManager.setSecurityManager(new ServerSubjectPusher());
            return true;
         } catch (Throwable var1) {
            return false;
         }
      } else {
         return false;
      }
   }
}
