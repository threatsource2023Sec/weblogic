package weblogic.management.snmp;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPAttributeChangeMBean;
import weblogic.management.configuration.SNMPCounterMonitorMBean;
import weblogic.management.configuration.SNMPGaugeMonitorMBean;
import weblogic.management.configuration.SNMPLogFilterMBean;
import weblogic.management.configuration.SNMPProxyMBean;
import weblogic.management.configuration.SNMPStringMonitorMBean;
import weblogic.management.configuration.SNMPTrapDestinationMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class SNMPCompatibilityProcessor implements ConfigurationProcessor {
   private static final boolean DEBUG = false;

   public void updateConfiguration(DomainMBean root) throws UpdateException {
      SNMPAgentMBean snmpAgent = root.getSNMPAgent();
      SNMPProxyMBean[] proxies = root.getSNMPProxies();

      for(int i = 0; i < proxies.length; ++i) {
         upgradeSNMPProxyConfiguration(snmpAgent, proxies[i]);
      }

      SNMPTrapDestinationMBean[] dests = root.getSNMPTrapDestinations();

      for(int i = 0; i < dests.length; ++i) {
         upgradeSNMPTrapDestinationConfiguration(snmpAgent, dests[i]);
      }

      SNMPCounterMonitorMBean[] counters = root.getSNMPCounterMonitors();

      for(int i = 0; i < counters.length; ++i) {
         upgradeSNMPCounterMonitorConfiguration(snmpAgent, counters[i]);
      }

      SNMPGaugeMonitorMBean[] gauges = root.getSNMPGaugeMonitors();

      for(int i = 0; i < gauges.length; ++i) {
         upgradeSNMPGaugeMonitorConfiguration(snmpAgent, gauges[i]);
      }

      SNMPStringMonitorMBean[] strs = root.getSNMPStringMonitors();

      for(int i = 0; i < strs.length; ++i) {
         upgradeSNMPStringMonitorConfiguration(snmpAgent, strs[i]);
      }

      SNMPAttributeChangeMBean[] attrChanges = root.getSNMPAttributeChanges();

      for(int i = 0; i < attrChanges.length; ++i) {
         upgradeSNMPAttributeChangeConfiguration(snmpAgent, attrChanges[i]);
      }

      SNMPLogFilterMBean[] filters = root.getSNMPLogFilters();

      for(int i = 0; i < filters.length; ++i) {
         upgradeSNMPLogFilterConfiguration(snmpAgent, filters[i]);
      }

   }

   private static void upgradeSNMPProxyConfiguration(SNMPAgentMBean agent, SNMPProxyMBean proxy) {
      agent.createSNMPProxy(proxy.getName(), proxy);
   }

   private static void upgradeSNMPTrapDestinationConfiguration(SNMPAgentMBean agent, SNMPTrapDestinationMBean dest) {
      agent.createSNMPTrapDestination(dest.getName(), dest);
   }

   private static void upgradeSNMPCounterMonitorConfiguration(SNMPAgentMBean agent, SNMPCounterMonitorMBean ctr) {
      agent.createSNMPCounterMonitor(ctr.getName(), ctr);
   }

   private static void upgradeSNMPGaugeMonitorConfiguration(SNMPAgentMBean agent, SNMPGaugeMonitorMBean gauge) {
      agent.createSNMPGaugeMonitor(gauge.getName(), gauge);
   }

   private static void upgradeSNMPStringMonitorConfiguration(SNMPAgentMBean agent, SNMPStringMonitorMBean str) {
      agent.createSNMPStringMonitor(str.getName(), str);
   }

   private static void upgradeSNMPLogFilterConfiguration(SNMPAgentMBean agent, SNMPLogFilterMBean filter) {
      agent.createSNMPLogFilter(filter.getName(), filter);
   }

   private static void upgradeSNMPAttributeChangeConfiguration(SNMPAgentMBean agent, SNMPAttributeChangeMBean attr) {
      agent.createSNMPAttributeChange(attr.getName(), attr);
   }
}
