package kodo.manage;

import com.solarmetric.manage.ManageRuntimeException;
import java.lang.reflect.Method;
import javax.management.MBeanServer;
import org.apache.openjpa.lib.util.Localizer;

public class ManagementConfigurationManagementWL81 extends ManagementConfigurationManagement {
   private static final Localizer _loc = Localizer.forPackage(ManagementConfigurationManagementWL81.class);
   private String username;
   private String password;
   private String serverName;
   private String url = "t3://localhost:7001";

   public void setUserName(String username) {
      this.username = username;
   }

   /** @deprecated */
   public void setUsername(String username) {
      this.setUserName(username);
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setServerName(String serverName) {
      this.serverName = serverName;
   }

   public void setURL(String url) {
      this.url = url;
   }

   /** @deprecated */
   public void setUrl(String url) {
      this.setURL(url);
   }

   public MBeanServer getMBeanServer() {
      try {
         Class helperClass = Class.forName("weblogic.management.Helper");
         Method method = helperClass.getMethod("getMBeanHome", String.class, String.class, String.class, String.class);
         Object localHome = method.invoke((Object)null, this.username, this.password, this.url, this.serverName);
         method = localHome.getClass().getMethod("getMBeanServer", (Class[])null);
         MBeanServer server = (MBeanServer)method.invoke(localHome, (Object[])null);
         return server;
      } catch (Exception var5) {
         throw new ManageRuntimeException(_loc.get("cant-create-wl-mbeanserver"), var5);
      }
   }
}
