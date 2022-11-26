package com.solarmetric.manage.jmx.gui;

import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.remote.RemoteMBeanServerFactory;
import java.lang.reflect.Constructor;
import javax.management.MBeanServer;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;

public class RemoteJMXTool {
   private static Localizer _loc = Localizer.forPackage(RemoteJMXTool.class);
   private static String[] _serverTypes = new String[]{"mx4j1", "com.solarmetric.manage.jmx.remote.mx4j1.MX4JRemoteMBeanServerFactory", "jmx2", "com.solarmetric.manage.jmx.remote.jmx2.JMX2RemoteMBeanServerFactory", "jboss", "com.solarmetric.manage.jmx.remote.jboss322.JBossRemoteMBeanServerFactory", "jboss4", "com.solarmetric.manage.jmx.remote.jboss4.JBossRemoteMBeanServerFactory", "weblogic81", "com.solarmetric.manage.jmx.remote.wl81.WL81RemoteMBeanServerFactory"};

   public static void main(String[] args) {
      main(new ConfigurationImpl(), args);
   }

   public static void main(Configuration conf, String[] args) {
      Options opts = new Options();
      opts.setFromCmdLine(args);
      if (!opts.containsKey("help") && !opts.containsKey("-help")) {
         boolean autoConnect = opts.removeBooleanProperty("connect", "c", false);
         String jndiName = opts.removeProperty("name", "n", "default");
         String host = opts.removeProperty("host", "h", "localhost");
         String serverType = opts.removeProperty("type", "t", "mx4j1");
         int port;
         if (serverType.equals("weblogic81")) {
            port = opts.removeIntProperty("port", "p", 7001);
         } else {
            port = opts.removeIntProperty("port", "p", 1099);
         }

         Configurations.populateConfiguration(conf, opts);
         JMXGui managerUI = new JMXGui(conf);
         if (autoConnect) {
            Log log = ManagementLog.get(conf);
            log.info(_loc.get("client-attempt-connect", new Object[]{serverType, jndiName, host, String.valueOf(port)}));
            Localizer.Message msg = null;

            try {
               MBeanServer mbServer = null;

               for(int i = 0; i < _serverTypes.length; i += 2) {
                  if (serverType.equals(_serverTypes[i])) {
                     serverType = _serverTypes[i + 1];
                     break;
                  }
               }

               RemoteMBeanServerFactory factory;
               try {
                  Class factoryClass = Class.forName(serverType);
                  Constructor constr = factoryClass.getConstructor();
                  factory = (RemoteMBeanServerFactory)constr.newInstance();
               } catch (Exception var16) {
                  msg = _loc.get("unknown-server-type", serverType);
                  throw var16;
               }

               if (jndiName.equals("default")) {
                  mbServer = factory.createDefaultRemoteMBeanServer(host, port, log);
               } else {
                  mbServer = factory.createRemoteMBeanServer(jndiName, host, port, log);
               }

               managerUI.setMBeanServer(mbServer);
            } catch (Exception var17) {
               if (msg != null) {
                  ManagementLog.get(conf).error(msg, var17);
               } else {
                  ManagementLog.get(conf).error(_loc.get("cant-start"), var17);
               }
            }
         }

         Thread uithread = new Thread(managerUI);
         uithread.start();

         try {
            uithread.join();
         } catch (InterruptedException var15) {
         }

         conf.close();
      } else {
         System.out.println(_loc.get("remote-jmx-tool-usage"));
      }
   }
}
