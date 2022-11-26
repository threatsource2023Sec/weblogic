package weblogic.management.mbeans.custom;

import java.util.Properties;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.nodemanager.mbean.NodeManagerRuntime;

public class ServerStart extends ConfigurationMBeanCustomizer {
   public ServerStart(ConfigurationMBeanCustomized base) {
      super(base);
   }

   private ServerTemplateMBean getServer() {
      return (ServerTemplateMBean)this.getMbean().getParent();
   }

   private Properties errorProps(String msg) {
      Properties props = new Properties();
      props.setProperty("Error", msg);
      return props;
   }

   public Properties getBootProperties() {
      String serverName = this.getServer().getName();
      if (serverName != null && !serverName.equals("")) {
         NodeManagerRuntime nm = NodeManagerRuntime.getInstance(this.getServer());
         return nm.getBootProperties(this.getServer());
      } else {
         String message = "Could not get boot properties for server: Server name is not set";
         return this.errorProps(message);
      }
   }

   public Properties getStartupProperties() {
      String serverName = this.getServer().getName();
      if (serverName != null && !serverName.equals("")) {
         NodeManagerRuntime nm = NodeManagerRuntime.getInstance(this.getServer());
         return nm.getStartupProperties(this.getServer());
      } else {
         String message = "Could not get startup properties for server: Server name is not set";
         return this.errorProps(message);
      }
   }
}
