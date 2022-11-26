package com.solarmetric.manage.jmx.remote.jmx2;

import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.remote.RemoteJMXAdaptor;
import java.util.HashMap;
import java.util.Map;
import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class JMX2RemoteJMXAdaptorImpl implements RemoteJMXAdaptor {
   private static final Localizer s_loc = Localizer.forPackage(JMX2RemoteJMXAdaptorImpl.class);
   private MBeanServer _mbeanServer;
   private String _serviceUrl = "service:jmx:rmi://localhost/jndi/jmxservice";
   private String _namingImpl = "mx4j.tools.naming.NamingService";
   private String _hostname = "localhost";
   private int _port = 1099;
   private Log _log;
   private JMXConnectorServer _connectorServer = null;
   private ObjectName _naming = null;

   public void setMBeanServer(MBeanServer mbeanServer) {
      this._mbeanServer = mbeanServer;
   }

   public void setServiceURL(String serviceUrl) {
      this._serviceUrl = serviceUrl;
   }

   public void setNamingImpl(String namingImpl) {
      this._namingImpl = namingImpl;
   }

   public void setHost(String hostname) {
      this._hostname = hostname;
   }

   public void setPort(int port) {
      this._port = port;
   }

   public void setLog(Log log) {
      this._log = log;
   }

   private Log getLog() {
      return this._log;
   }

   public void setConfiguration(Configuration conf) {
      this.setLog(ManagementLog.get(conf));
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void init() {
      try {
         if (this._namingImpl != null && this._namingImpl.length() > 0) {
            this._naming = new ObjectName("Naming:type=rmiregistry");
            this._mbeanServer.createMBean(this._namingImpl, this._naming, (ObjectName)null);
            this._mbeanServer.setAttribute(this._naming, new Attribute("Port", new Integer(this._port)));
            this._mbeanServer.invoke(this._naming, "start", (Object[])null, (String[])null);
         }

         JMXServiceURL address = new JMXServiceURL(this._serviceUrl);
         Map environment = new HashMap();
         environment.put("java.naming.factory.initial", "com.sun.jndi.rmi.registry.RegistryContextFactory");
         environment.put("java.naming.provider.url", "rmi://" + this._hostname + ":" + this._port);
         this._connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(address, environment, (MBeanServer)null);
         ObjectName connectorServerName = ObjectName.getInstance("connectors:product=com.solarmetric");
         this._mbeanServer.registerMBean(this._connectorServer, connectorServerName);
         this._connectorServer.start();
      } catch (Exception var4) {
         this.getLog().error(s_loc.get("cannot-start-adaptor"), var4);
      }

   }

   public void close() {
      if (this._mbeanServer != null) {
         try {
            if (this._connectorServer != null) {
               this._connectorServer.stop();
            }
         } catch (Exception var3) {
            this.getLog().error(s_loc.get("cannot-stop-adaptor"), var3);
         }

         try {
            if (this._naming != null) {
               this._mbeanServer.invoke(this._naming, "stop", (Object[])null, (String[])null);
            }
         } catch (Exception var2) {
            this.getLog().error(s_loc.get("cannot-stop-adaptor"), var2);
         }

      }
   }
}
