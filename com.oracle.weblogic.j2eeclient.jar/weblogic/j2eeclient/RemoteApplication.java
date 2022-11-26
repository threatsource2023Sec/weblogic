package weblogic.j2eeclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.application.utils.ApplicationVersionUtils;

public class RemoteApplication {
   private final String url;
   private final String username;
   private final String password;
   private final String applicationName;
   private boolean initialized = false;
   private String activeApplicationId;
   private static final ObjectName service;

   public RemoteApplication(String url, String username, String password, String applicationName) {
      this.url = url;
      this.username = username;
      this.password = password;
      this.applicationName = applicationName;
   }

   public String getActiveApplicationId() {
      this.initialize();
      return this.activeApplicationId;
   }

   private void initialize() {
      if (!this.initialized) {
         this.initialized = true;

         try {
            this.activeApplicationId = this.getApplicationIdForActiveVersion(this.url, this.username, this.password, this.applicationName);
         } catch (AttributeNotFoundException var2) {
            var2.printStackTrace();
         } catch (InstanceNotFoundException var3) {
            var3.printStackTrace();
         } catch (MalformedURLException var4) {
            var4.printStackTrace();
         } catch (MBeanException var5) {
            var5.printStackTrace();
         } catch (ReflectionException var6) {
            var6.printStackTrace();
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      }

   }

   private String getApplicationIdForActiveVersion(String url, String username, String password, String applicationName) throws AttributeNotFoundException, InstanceNotFoundException, MalformedURLException, MBeanException, ReflectionException, IOException {
      JMXConnector connector = null;

      String var10;
      try {
         connector = this.initConnector(url, username, password);
         ObjectName[] applicationRuntimes = this.getApplicationRuntimeMBeans(connector);
         ObjectName runtimeMBean = this.getActiveApplicationRuntime(connector, applicationRuntimes, applicationName);
         MBeanServerConnection connection;
         if (runtimeMBean == null) {
            connection = null;
            return connection;
         }

         connection = connector.getMBeanServerConnection();
         String versionId = (String)connection.getAttribute(runtimeMBean, "ApplicationVersion");
         if (versionId == null) {
            var10 = applicationName;
            return var10;
         }

         var10 = ApplicationVersionUtils.getApplicationId(applicationName, versionId);
      } finally {
         if (connector != null) {
            connector.close();
         }

      }

      return var10;
   }

   private JMXConnector initConnector(String url, String username, String password) throws IOException, MalformedURLException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:" + url + "/jndi/weblogic.management.mbeanservers.runtime");
      Hashtable h = new Hashtable();
      h.put("java.naming.security.principal", username);
      h.put("java.naming.security.credentials", password);
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      return JMXConnectorFactory.connect(serviceURL, h);
   }

   private ObjectName[] getApplicationRuntimeMBeans(JMXConnector connector) throws IOException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      MBeanServerConnection connection = connector.getMBeanServerConnection();
      ObjectName serverRuntime = (ObjectName)connection.getAttribute(service, "ServerRuntime");
      return (ObjectName[])((ObjectName[])connection.getAttribute(serverRuntime, "ApplicationRuntimes"));
   }

   private ObjectName getActiveApplicationRuntime(JMXConnector connector, ObjectName[] apps, String appName) throws IOException, MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
      MBeanServerConnection connection = connector.getMBeanServerConnection();
      if (appName == null) {
         return null;
      } else if (apps == null) {
         return null;
      } else {
         for(int i = 0; i < apps.length; ++i) {
            ObjectName mbean = apps[i];
            String name = (String)connection.getAttribute(mbean, "ApplicationName");
            if (appName.equals(name)) {
               int state = (Integer)connection.getAttribute(mbean, "ActiveVersionState");
               if (state == 2) {
                  return mbean;
               }
            }
         }

         return null;
      }
   }

   static {
      try {
         service = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
      } catch (MalformedObjectNameException var1) {
         throw new AssertionError(var1.getMessage());
      }
   }
}
