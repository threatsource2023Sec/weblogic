package weblogic.corba.client.spi;

import weblogic.jndi.security.SubjectPusher;

public class ServiceManager {
   public static final String DEFAULT_SECURITY_MANAGER = "weblogic.jndi.security.internal.server.ServerSubjectPusher";
   private static SubjectPusher secManager = null;

   public static SubjectPusher getSecurityManager() {
      if (secManager == null) {
         secManager = (SubjectPusher)createDefaultManager("weblogic.jndi.security.internal.server.ServerSubjectPusher");
      }

      return secManager;
   }

   public static void setSecurityManager(SubjectPusher sm) {
      secManager = sm;
   }

   private static Object createDefaultManager(String name) {
      try {
         return Class.forName(name).newInstance();
      } catch (ClassNotFoundException var2) {
         throw new Error(var2.toString());
      } catch (InstantiationException var3) {
         throw new Error(var3.toString());
      } catch (IllegalAccessException var4) {
         throw new Error(var4.toString());
      }
   }
}
