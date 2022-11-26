package weblogic.management.rest.lib.utils;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.GlobalServiceLocator;

public class ServerUtils {
   public static String getLocalServerName() {
      return ServerUtils.RAInit.instance.getServerRuntime().getName();
   }

   public static boolean isAdminServer() {
      return ServerUtils.RAInit.instance.getServerRuntime().isAdminServer();
   }

   private static final class RAInit {
      private static final RuntimeAccess instance = (RuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public RuntimeAccess run() {
            return (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
         }
      });
   }
}
