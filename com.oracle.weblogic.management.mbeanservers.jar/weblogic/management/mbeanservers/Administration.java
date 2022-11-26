package weblogic.management.mbeanservers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

public class Administration {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXCore");
   private static final int EDIT = 1;
   private static final int RUNTIME = 2;
   private static final int DOMAIN_RUNTIME = 3;
   private MBeanServerConnection runtime;
   private MBeanServerConnection domain_runtime;
   private MBeanServerConnection edit;
   private JMXConnector runtime_connector;
   private JMXConnector domain_runtime_connector;
   private JMXConnector edit_connector;
   private static ObjectName runtime_on;
   private static ObjectName domain_runtime_on;
   private static ObjectName edit_on;

   public Administration(Context ctx) throws ManagementException {
      this.runtime = this.getConnection(2, ctx);
      ServerRuntimeMBean server_runtime = this.getServerRuntimeMBean();
      if (server_runtime.isAdminServer()) {
         this.edit = this.getConnection(1, ctx);
         this.domain_runtime = this.getConnection(3, ctx);
      }

   }

   public Administration(String u, String p, String url) throws ManagementException {
      this.runtime = this.getConnection(2, u, p, url);
      ServerRuntimeMBean server_runtime = this.getServerRuntimeMBean();
      if (server_runtime.isAdminServer()) {
         this.edit = this.getConnection(1, u, p, url);
         this.domain_runtime = this.getConnection(3, u, p, url);
      }

   }

   public RuntimeServiceMBean getRuntimeServiceMBean() {
      return (RuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.runtime, runtime_on);
   }

   public DomainRuntimeServiceMBean getDomainRuntimeServiceMBean() {
      if (this.domain_runtime == null) {
         String msg = "This method (getDomainRuntimeServiceMBean) can only be called when connected to an admin server";
         throw new AssertionError(msg);
      } else {
         return (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.domain_runtime, domain_runtime_on);
      }
   }

   public EditServiceMBean getEditServiceMBean() {
      if (this.edit == null) {
         String msg = "This method (getEditServiceMBean) can only be called when connected to an admin server";
         throw new AssertionError(msg);
      } else {
         return (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.edit, edit_on);
      }
   }

   public ServerRuntimeMBean getServerRuntimeMBean() {
      RuntimeServiceMBean service = this.getRuntimeServiceMBean();
      return service.getServerRuntime();
   }

   public DomainMBean getDomainMBean() {
      RuntimeServiceMBean service = this.getRuntimeServiceMBean();
      return service.getDomainConfiguration();
   }

   private MBeanServerConnection getConnection(int type, Context context) throws ManagementException {
      String jndiName = this.getJndiName(type);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Get mbean server connection for " + jndiName);
      }

      MBeanServerConnection connection = null;

      try {
         JMXServiceURL serviceURL = new JMXServiceURL("wlx", (String)null, 0, jndiName);
         Hashtable h = new Hashtable();
         h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         h.put("weblogic.context", context);
         switch (type) {
            case 1:
               this.edit_connector = JMXConnectorFactory.connect(serviceURL, h);
               return this.edit_connector.getMBeanServerConnection();
            case 2:
               this.runtime_connector = JMXConnectorFactory.connect(serviceURL, h);
               return this.runtime_connector.getMBeanServerConnection();
            case 3:
               this.domain_runtime_connector = JMXConnectorFactory.connect(serviceURL, h);
               return this.domain_runtime_connector.getMBeanServerConnection();
            default:
               return null;
         }
      } catch (MalformedURLException var7) {
         throw new ManagementException(var7);
      } catch (IOException var8) {
         throw new ManagementException(var8);
      }
   }

   private MBeanServerConnection getConnection(int type, String u, String p, String address) throws ManagementException {
      String jndiName = this.getJndiName(type);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Get mbean server connection for " + jndiName + " address " + address);
      }

      try {
         URI uri = new URI(address);
         String protocol = uri.getScheme();
         String host = uri.getHost();
         int port = uri.getPort();
         Hashtable env = new Hashtable();
         env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         env.put("java.naming.provider.url", address);
         env.put("java.naming.security.principal", u);
         env.put("java.naming.security.credentials", p);
         Context ctx = new InitialContext(env);
         Hashtable hashtable = ctx.getEnvironment();
         JMXServiceURL serviceURL = new JMXServiceURL(protocol, host, port, jndiName);
         Hashtable h = new Hashtable();
         h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         if (u != null && p != null) {
            h.put("java.naming.security.principal", u);
            h.put("java.naming.security.credentials", p);
         }

         switch (type) {
            case 1:
               this.edit_connector = JMXConnectorFactory.connect(serviceURL, h);
               return this.edit_connector.getMBeanServerConnection();
            case 2:
               this.runtime_connector = JMXConnectorFactory.connect(serviceURL, h);
               return this.runtime_connector.getMBeanServerConnection();
            case 3:
               this.domain_runtime_connector = JMXConnectorFactory.connect(serviceURL, h);
               return this.domain_runtime_connector.getMBeanServerConnection();
            default:
               return null;
         }
      } catch (URISyntaxException var15) {
         throw new ManagementException(var15);
      } catch (MalformedURLException var16) {
         throw new ManagementException(var16);
      } catch (NamingException var17) {
         throw new ManagementException(var17);
      } catch (IOException var18) {
         throw new ManagementException(var18);
      }
   }

   private String getJndiName(int type) {
      String jndiName = null;
      switch (type) {
         case 1:
            jndiName = "weblogic.management.mbeanservers.edit";
            break;
         case 2:
            jndiName = "weblogic.management.mbeanservers.runtime";
            break;
         case 3:
            jndiName = "weblogic.management.mbeanservers.domainruntime";
            break;
         default:
            jndiName = "weblogic.management.mbeanservers.runtime";
      }

      return "/jndi/" + jndiName;
   }

   public void close() {
      if (this.runtime_connector != null) {
         try {
            this.runtime_connector.close();
         } catch (IOException var4) {
         }
      }

      if (this.domain_runtime_connector != null) {
         try {
            this.domain_runtime_connector.close();
         } catch (IOException var3) {
         }
      }

      if (this.edit_connector != null) {
         try {
            this.edit_connector.close();
         } catch (IOException var2) {
         }
      }

   }

   static {
      try {
         runtime_on = new ObjectName(RuntimeServiceMBean.OBJECT_NAME);
         domain_runtime_on = new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME);
         edit_on = new ObjectName(EditServiceMBean.OBJECT_NAME);
      } catch (Exception var1) {
         throw new AssertionError(var1);
      }
   }
}
