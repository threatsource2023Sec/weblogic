package weblogic.nodemanager.mbean;

import java.io.File;
import java.util.Properties;
import org.jvnet.hk2.annotations.Service;
import weblogic.coherence.descriptor.wl.CoherenceClusterParamsBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBean;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceServerMBean;
import weblogic.nodemanager.common.CoherenceStartupConfig;
import weblogic.nodemanager.common.StartupConfig;

@Service
public class CoherencePropertiesImpl implements NodeManagerRuntime.CoherenceProperties {
   public Properties getStartupProperties(NodeManagerRuntime.AbstractStartupProperties properties, CoherenceServerMBean smb, StartupConfig.ValuesHolder conf, String serverName) {
      assert conf instanceof CoherenceStartupConfig.ValuesHolder;

      CoherenceStartupConfig.ValuesHolder confCoh = (CoherenceStartupConfig.ValuesHolder)conf;
      CoherenceClusterSystemResourceMBean ccsrmb = smb.getCoherenceClusterSystemResource();
      boolean isUsingCustomClusterConfigurationFile = false;
      String addr;
      if (ccsrmb != null) {
         if (ccsrmb.isUsingCustomClusterConfigurationFile()) {
            addr = (new File(ccsrmb.getCustomClusterConfigurationFileName())).getName();
            confCoh.setCustomClusterConfigurationFileName(addr);
            confCoh.setClusterName(ccsrmb.getName());
            isUsingCustomClusterConfigurationFile = true;
         } else {
            WeblogicCoherenceBean wlcb = ccsrmb.getCoherenceClusterResource();
            if (wlcb != null) {
               this.getStartupProperties(confCoh, wlcb.getCoherenceClusterParams());
               confCoh.setClusterName(wlcb.getName());
            }
         }
      }

      if (!isUsingCustomClusterConfigurationFile) {
         if (smb.isSet("UnicastListenAddress")) {
            addr = smb.getUnicastListenAddress();
            if (addr != null) {
               confCoh.setUnicastListenAddress(addr);
            }
         }

         if (smb.isSet("UnicastListenPort")) {
            int port = smb.getUnicastListenPort();
            if (port != 0) {
               confCoh.setUnicastListenPort(port);
            }
         }

         if (smb.isSet("UnicastPortAutoAdjust")) {
            confCoh.setUnicastPortAutoAdjust(smb.isUnicastPortAutoAdjust());
         }
      }

      return properties.getAndLogStartupProperties(conf, serverName);
   }

   private void getStartupProperties(CoherenceStartupConfig.ValuesHolder conf, CoherenceClusterParamsBean ccpm) {
      if (ccpm != null) {
         CoherenceClusterWellKnownAddressesBean wkabs = ccpm.getCoherenceClusterWellKnownAddresses();
         if (wkabs != null) {
            CoherenceClusterWellKnownAddressBean[] wkaba = wkabs.getCoherenceClusterWellKnownAddresses();
            if (wkaba != null) {
               CoherenceClusterWellKnownAddressBean[] var5 = wkaba;
               int var6 = wkaba.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  CoherenceClusterWellKnownAddressBean wka = var5[var7];
                  conf.addWellKnownAddress(wka.getName(), wka.getListenAddress());
                  wka.getName();
               }
            }
         }

         conf.setMulticastListenAddress(ccpm.getMulticastListenAddress());
         conf.setMulticastListenPort(ccpm.getMulticastListenPort());
         conf.setTimeToLive(ccpm.getTimeToLive());
         conf.setUnicastListenPort(ccpm.getUnicastListenPort());
         conf.setUnicastPortAutoAdjust(ccpm.isUnicastPortAutoAdjust());
      }

   }
}
