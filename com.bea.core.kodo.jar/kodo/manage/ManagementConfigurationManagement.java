package kodo.manage;

import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.LogMBean;
import com.solarmetric.manage.jmx.MBeanProvider;
import com.solarmetric.manage.jmx.RuntimeMBeanFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import org.apache.openjpa.lib.util.Localizer;

public class ManagementConfigurationManagement extends AbstractManagementConfiguration {
   private static final Localizer _loc = Localizer.forPackage(ManagementConfigurationManagement.class);
   private String _mbeanServerStrategy = "any-create";
   private MBeanProvider _logMBeanProvider = null;
   private MBeanProvider _runtimeMBeanProvider = null;

   public String getMBeanServerStrategy() {
      return this._mbeanServerStrategy;
   }

   public void setMBeanServerStrategy(String mbeanServerStrategy) {
      this._mbeanServerStrategy = mbeanServerStrategy;
   }

   public MBeanServer getMBeanServer() {
      String strat = this.getMBeanServerStrategy();
      if (strat != null && !"none".equals(strat)) {
         String agentIdStr = "agentId:";
         MBeanServer mbeanServer;
         if (strat.startsWith(agentIdStr)) {
            String agentId = strat.substring(agentIdStr.length());
            List serverList = MBeanServerFactory.findMBeanServer(agentId);
            if (serverList.size() > 0) {
               mbeanServer = (MBeanServer)serverList.get(0);
            } else {
               mbeanServer = MBeanServerFactory.createMBeanServer();
               ManagementLog.get(this.conf).warn(_loc.get("cant-find-agentid", agentId));
            }
         } else if ("create".equals(strat)) {
            mbeanServer = MBeanServerFactory.createMBeanServer();
         } else {
            if (!"any-create".equals(strat)) {
               ManagementLog.get(this.conf).warn(_loc.get("unknown-mbeanserverstrategy", strat));
               return null;
            }

            List serverList = MBeanServerFactory.findMBeanServer((String)null);
            if (serverList.size() > 0) {
               mbeanServer = (MBeanServer)serverList.get(0);
            } else {
               mbeanServer = MBeanServerFactory.createMBeanServer();
            }
         }

         return mbeanServer;
      } else {
         return null;
      }
   }

   public void setEnableLogMBean(boolean enable) {
      if (enable) {
         this._logMBeanProvider = new LogMBean(this.conf);
      } else {
         this._logMBeanProvider = null;
      }

   }

   public void setEnableRuntimeMBean(boolean enable) {
      if (enable) {
         this._runtimeMBeanProvider = new RuntimeMBeanFactory(this.conf);
      } else {
         this._runtimeMBeanProvider = null;
      }

   }

   public MBeanProvider[] getMBeanPlugins() {
      Collection c = new ArrayList(2);
      if (this._logMBeanProvider != null) {
         c.add(this._logMBeanProvider);
      }

      if (this._runtimeMBeanProvider != null) {
         c.add(this._runtimeMBeanProvider);
      }

      return (MBeanProvider[])((MBeanProvider[])c.toArray(new MBeanProvider[c.size()]));
   }
}
