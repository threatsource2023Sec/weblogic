package com.solarmetric.manage.jmx.remote.mx4j1;

import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.remote.RemoteJMXAdaptor;
import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import mx4j.adaptor.rmi.jrmp.JRMPAdaptorMBean;
import mx4j.util.StandardMBeanProxy;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class MX4JRemoteJMXAdaptorImpl implements RemoteJMXAdaptor {
   private static final Localizer s_loc = Localizer.forPackage(MX4JRemoteJMXAdaptorImpl.class);
   private MBeanServer _mbeanServer;
   private String _hostname = "localhost";
   private int _port = 1099;
   private String _jndiName = "jrmp";
   private Log _log;
   private ObjectName _naming = null;
   private ObjectName _adaptor = null;
   private JRMPAdaptorMBean _mbean = null;

   public void setMBeanServer(MBeanServer mbeanServer) {
      this._mbeanServer = mbeanServer;
   }

   public void setHost(String hostname) {
      this._hostname = hostname;
   }

   public void setPort(int port) {
      this._port = port;
   }

   public void setJNDIName(String jndiName) {
      this._jndiName = jndiName;
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
         this._naming = new ObjectName("Naming:type=rmiregistry");
         this._mbeanServer.createMBean("mx4j.tools.naming.NamingService", this._naming, (ObjectName)null);
         this._mbeanServer.setAttribute(this._naming, new Attribute("Port", new Integer(this._port)));
         this._mbeanServer.invoke(this._naming, "start", (Object[])null, (String[])null);
         this._adaptor = new ObjectName("Adaptor:protocol=JRMP");
         this._mbeanServer.createMBean("mx4j.adaptor.rmi.jrmp.JRMPAdaptor", this._adaptor, (ObjectName)null);
         this._mbean = (JRMPAdaptorMBean)StandardMBeanProxy.create(JRMPAdaptorMBean.class, this._mbeanServer, this._adaptor);
         this._mbean.setJNDIName(this._jndiName);
         this._mbean.putJNDIProperty("java.naming.factory.initial", "com.sun.jndi.rmi.registry.RegistryContextFactory");
         this._mbean.putJNDIProperty("java.naming.provider.url", "rmi://" + this._hostname + ":" + this._port);
         this._mbean.start();
      } catch (Exception var2) {
         this.getLog().error(s_loc.get("cannot-start-adaptor"), var2);
      }

   }

   public void close() {
      if (this._mbeanServer != null) {
         try {
            if (this._mbean != null) {
               this._mbean.stop();
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
