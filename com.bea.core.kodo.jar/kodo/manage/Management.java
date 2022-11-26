package kodo.manage;

import com.solarmetric.manage.ManagementLog;
import com.solarmetric.manage.jmx.MBeanHelper;
import com.solarmetric.manage.jmx.MBeanProvider;
import com.solarmetric.profile.ProfilingInterface;
import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import kodo.profile.KodoProfilingAgent;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.Localizer;

public class Management {
   private static final Localizer _loc = Localizer.forPackage(Management.class);
   private final ManagementConfiguration _mgmntConf;
   private final OpenJPAConfiguration _conf;
   private MBeanServer _mbeanServer = null;
   private KodoProfilingAgent _profilingAgent = null;
   private KodoTimeWatchManager _timeWatchManager = null;
   private String _visualProfiling = null;

   public Management(OpenJPAConfiguration conf, ManagementConfiguration mgmntConf) {
      this._visualProfiling = "kodo.profile.gui.ProfilingInterface";
      this._conf = conf;
      this._mgmntConf = mgmntConf;
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   private ManagementConfiguration getManagement() {
      return this._mgmntConf;
   }

   public static Management getInstance(OpenJPAConfiguration conf) {
      return JMXValue.getManagementConfiguration(conf).getManagement();
   }

   public void setVisualProfilingInterfaceClassName(String clsName) {
      this._visualProfiling = clsName;
   }

   public synchronized KodoTimeWatchManager getTimeWatchManager() {
      if (this._timeWatchManager == null) {
         this._timeWatchManager = new KodoTimeWatchManager(this);
      }

      return this._timeWatchManager;
   }

   public synchronized MBeanServer getMBeanServer() {
      if (this._mbeanServer == null) {
         ManagementConfiguration mconfig = this.getManagement();
         this._mbeanServer = mconfig.getMBeanServer();
         if (this._mbeanServer == null) {
            return null;
         }

         MBeanProvider[] providers = mconfig.getMBeanPlugins();

         for(int i = 0; i < providers.length; ++i) {
            if (providers[i] != null) {
               MBeanHelper.register(providers[i], this._mbeanServer, this._conf);
            }
         }

         try {
            mconfig.initManagement(this._mbeanServer);
         } catch (Exception var4) {
            ManagementLog.get(this._conf).warn(_loc.get("cant-init-jmx"), var4);
         }
      }

      return this._mbeanServer;
   }

   public synchronized KodoProfilingAgent getProfilingAgent() {
      if (this._profilingAgent == null) {
         ManagementConfiguration mconfig = this.getManagement();
         this._profilingAgent = mconfig.getProfilingAgent();
         if (this._profilingAgent != null) {
            mconfig.initProfiling(this._profilingAgent);
            if (this._profilingAgent instanceof DynamicMBean) {
               MBeanHelper.register(this._profilingAgent, "profilingAgent", (String)null, this.getMBeanServer(), this._conf);
            }
         }
      }

      return this._profilingAgent;
   }

   public ProfilingInterface newVisualProfilingInterface() {
      return (ProfilingInterface)Configurations.newInstance(this._visualProfiling, this._conf, (String)null, ProfilingInterface.class.getClassLoader());
   }
}
