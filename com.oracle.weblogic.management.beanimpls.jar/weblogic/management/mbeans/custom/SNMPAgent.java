package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.SNMPAttributeChangeMBean;
import weblogic.management.configuration.SNMPCounterMonitorMBean;
import weblogic.management.configuration.SNMPGaugeMonitorMBean;
import weblogic.management.configuration.SNMPLogFilterMBean;
import weblogic.management.configuration.SNMPProxyMBean;
import weblogic.management.configuration.SNMPStringMonitorMBean;
import weblogic.management.configuration.SNMPTrapDestinationMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class SNMPAgent extends ConfigurationMBeanCustomizer {
   public SNMPAgent(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public SNMPAttributeChangeMBean createSNMPAttributeChange(String name, SNMPAttributeChangeMBean toClone) {
      try {
         SNMPAttributeChangeMBean newBean = (SNMPAttributeChangeMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPAttributeChange", toClone);
         newBean.setEnabledServers(toClone.getEnabledServers());
         return newBean;
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      } catch (InvalidAttributeValueException var5) {
         throw new Error(var5);
      } catch (ManagementException var6) {
         throw new Error(var6);
      }
   }

   public SNMPCounterMonitorMBean createSNMPCounterMonitor(String name, SNMPCounterMonitorMBean toClone) {
      try {
         SNMPCounterMonitorMBean newBean = (SNMPCounterMonitorMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPCounterMonitor", toClone);
         newBean.setEnabledServers(toClone.getEnabledServers());
         return newBean;
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      } catch (InvalidAttributeValueException var5) {
         throw new Error(var5);
      } catch (ManagementException var6) {
         throw new Error(var6);
      }
   }

   public SNMPGaugeMonitorMBean createSNMPGaugeMonitor(String name, SNMPGaugeMonitorMBean toClone) {
      try {
         SNMPGaugeMonitorMBean newBean = (SNMPGaugeMonitorMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPGaugeMonitor", toClone);
         newBean.setEnabledServers(toClone.getEnabledServers());
         return newBean;
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      } catch (InvalidAttributeValueException var5) {
         throw new Error(var5);
      } catch (ManagementException var6) {
         throw new Error(var6);
      }
   }

   public SNMPStringMonitorMBean createSNMPStringMonitor(String name, SNMPStringMonitorMBean toClone) {
      try {
         SNMPStringMonitorMBean newBean = (SNMPStringMonitorMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPStringMonitor", toClone);
         newBean.setEnabledServers(toClone.getEnabledServers());
         return newBean;
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      } catch (InvalidAttributeValueException var5) {
         throw new Error(var5);
      } catch (ManagementException var6) {
         throw new Error(var6);
      }
   }

   public SNMPLogFilterMBean createSNMPLogFilter(String name, SNMPLogFilterMBean toClone) {
      try {
         SNMPLogFilterMBean newBean = (SNMPLogFilterMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPLogFilter", toClone);
         newBean.setEnabledServers(toClone.getEnabledServers());
         return newBean;
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      } catch (InvalidAttributeValueException var5) {
         throw new Error(var5);
      } catch (ManagementException var6) {
         throw new Error(var6);
      }
   }

   public SNMPProxyMBean createSNMPProxy(String name, SNMPProxyMBean toClone) {
      try {
         return (SNMPProxyMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPProxy", toClone);
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      }
   }

   public SNMPTrapDestinationMBean createSNMPTrapDestination(String name, SNMPTrapDestinationMBean toClone) {
      try {
         return (SNMPTrapDestinationMBean)this.getMbean().createChildCopyIncludingObsolete("SNMPTrapDestination", toClone);
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      }
   }
}
