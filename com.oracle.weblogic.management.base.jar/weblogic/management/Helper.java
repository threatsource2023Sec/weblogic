package weblogic.management;

import java.lang.annotation.Annotation;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.server.GlobalServiceLocator;

/** @deprecated */
@Deprecated
public final class Helper {
   public static final MBeanHome getAdminMBeanHome(String user, String password, String adminServerURL) throws IllegalArgumentException {
      return getMBeanHomeForName(user, password, adminServerURL, "weblogic.management.adminhome");
   }

   public static final MBeanHome getMBeanHome(String user, String password, String serverURL, String serverName) throws IllegalArgumentException {
      return getMBeanHomeForName(user, password, serverURL, "weblogic.management.home." + serverName);
   }

   private static MBeanHome getMBeanHomeForName(String user, String password, String url, String jndiName) throws IllegalArgumentException {
      Context ctx = null;
      MBeanHome mbeanHome = null;
      if (user != null && password != null && url != null) {
         MBeanHome var7;
         try {
            ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
            env.setProviderUrl(url);
            env.setSecurityPrincipal(user);
            env.setSecurityCredentials(password);
            env.setEnableDefaultUser(true);
            ctx = env.getInitialContext();
            mbeanHome = (MBeanHome)ctx.lookup(jndiName);
            var7 = mbeanHome;
         } catch (AuthenticationException var18) {
            throw new IllegalArgumentException("Invalid user name or password, " + var18);
         } catch (CommunicationException var19) {
            throw new IllegalArgumentException("Failed to contact " + url + ", " + var19);
         } catch (NamingException var20) {
            throw new IllegalArgumentException("JNDI naming exception: " + var20);
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var17) {
               }
            }

         }

         return var7;
      } else {
         throw new IllegalArgumentException("All arguments must be non null");
      }
   }
}
