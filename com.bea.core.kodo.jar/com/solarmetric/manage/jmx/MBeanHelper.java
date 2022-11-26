package com.solarmetric.manage.jmx;

import com.solarmetric.manage.ManagementLog;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class MBeanHelper {
   private static final Localizer s_loc = Localizer.forPackage(MBeanHelper.class);

   public static boolean register(MBeanProvider mbProvider, MBeanServer mbeanServer, Configuration conf) {
      return mbeanServer == null ? false : register(mbProvider.getMBean(), mbProvider.getMBeanName(), mbeanServer, conf);
   }

   public static boolean register(Object mbean, String type, String name, MBeanServer mbeanServer, Configuration conf) {
      if (mbeanServer == null) {
         return false;
      } else {
         try {
            String nameStr = conf.getProductName() + ":type=" + type;
            if (name != null) {
               nameStr = nameStr + ",name=" + name;
            }

            ObjectName mbName = new ObjectName(nameStr);
            return register(mbean, mbName, mbeanServer, conf);
         } catch (Exception var7) {
            Log l = ManagementLog.get(conf);
            if (l.isWarnEnabled()) {
               l.warn(s_loc.get("cant-reg-mbean", type, name), var7);
            }

            return false;
         }
      }
   }

   public static boolean register(Object mbean, ObjectName mbName, MBeanServer mbeanServer, Configuration conf) {
      if (mbeanServer == null) {
         return false;
      } else {
         Log l = ManagementLog.get(conf);

         try {
            if (mbeanServer.isRegistered(mbName)) {
               String nameStr = mbName.getDomain() + ":" + mbName.getKeyPropertyListString() + ",conf=" + conf.hashCode();
               mbName = new ObjectName(nameStr);
               if (mbeanServer.isRegistered(mbName)) {
                  return false;
               }
            }

            mbeanServer.registerMBean(mbean, mbName);
            if (l.isTraceEnabled()) {
               l.info(s_loc.get("reg-mbean", mbName));
            }

            return true;
         } catch (Exception var6) {
            if (l.isWarnEnabled()) {
               l.warn(s_loc.get("cant-reg-mbean-objname", mbName), var6);
            }

            return false;
         }
      }
   }

   public static MBeanServer getMBeanServer(String strat, Configuration conf) {
      String agentIdStr = "agentId:";
      if (strat.startsWith(agentIdStr)) {
         String agentId = strat.substring(agentIdStr.length());
         List serverList = MBeanServerFactory.findMBeanServer(agentId);
         if (serverList.size() > 0) {
            return (MBeanServer)serverList.get(0);
         } else {
            ManagementLog.get(conf).warn(s_loc.get("cant-find-agentid", agentId));
            return MBeanServerFactory.createMBeanServer();
         }
      } else if ("create".equals(strat)) {
         return MBeanServerFactory.createMBeanServer();
      } else if ("any-create".equals(strat)) {
         List serverList = MBeanServerFactory.findMBeanServer((String)null);
         return serverList.size() > 0 ? (MBeanServer)serverList.get(0) : MBeanServerFactory.createMBeanServer();
      } else {
         ManagementLog.get(conf).warn(s_loc.get("unknown-mbeanserverstrategy", strat));
         return null;
      }
   }
}
