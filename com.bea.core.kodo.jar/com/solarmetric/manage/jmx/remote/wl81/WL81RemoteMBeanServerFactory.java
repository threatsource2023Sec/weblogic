package com.solarmetric.manage.jmx.remote.wl81;

import com.solarmetric.manage.jmx.remote.RemoteMBeanServerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.management.MBeanServer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class WL81RemoteMBeanServerFactory implements RemoteMBeanServerFactory {
   private static Localizer _loc = Localizer.forPackage(WL81RemoteMBeanServerFactory.class);

   public MBeanServer createRemoteMBeanServer(String jndiName, String jndiHost, int jndiPort, Log log) throws Exception {
      return this.createDefaultRemoteMBeanServer(jndiHost, jndiPort, log);
   }

   public MBeanServer createDefaultRemoteMBeanServer(String jndiHost, int jndiPort, Log log) throws Exception {
      String userName = null;
      String password = null;
      int pos = jndiHost.indexOf("@");
      if (pos < 0) {
         throw new IllegalArgumentException(_loc.get("invalid-hostname-wl").getMessage());
      } else {
         String userNamePassword = jndiHost.substring(0, pos);
         jndiHost = jndiHost.substring(pos + 1);
         pos = userNamePassword.indexOf(":");
         if (pos >= 0) {
            userName = userNamePassword.substring(0, pos);
            password = userNamePassword.substring(pos + 1);
            String connectUrl = "t3://" + jndiHost + ":" + jndiPort;

            try {
               Class cls = Class.forName("weblogic.jndi.Environment");
               Object env = cls.newInstance();
               Method method = cls.getMethod("setProviderUrl", String.class);
               method.invoke(env, connectUrl);
               method = cls.getMethod("setSecurityPrincipal", String.class);
               method.invoke(env, userName);
               method = cls.getMethod("setSecurityCredentials", Object.class);
               method.invoke(env, password);
               method = cls.getMethod("getInitialContext");
               Context ctx = (Context)method.invoke(env);
               cls = Class.forName("weblogic.management.MBeanHome");
               Field field = cls.getField("ADMIN_JNDI_NAME");
               String adminJndiName = (String)field.get((Object)null);
               Object home = ctx.lookup(adminJndiName);
               method = cls.getMethod("getMBeanServer");
               MBeanServer server = (MBeanServer)method.invoke(home);
               return new WL81RemoteMBeanServerProxy(server);
            } catch (NamingException var17) {
               log.error(_loc.get("cannot-connect-wl", userName, password, connectUrl).getMessage());
               throw var17;
            }
         } else {
            throw new IllegalArgumentException(_loc.get("invalid-hostname-wl").getMessage());
         }
      }
   }
}
