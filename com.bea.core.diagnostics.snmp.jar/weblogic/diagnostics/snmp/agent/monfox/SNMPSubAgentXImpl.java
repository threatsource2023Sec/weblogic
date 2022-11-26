package weblogic.diagnostics.snmp.agent.monfox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.x.sub.SubAgentX;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.builder.MibApi;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPSubAgentX;

public class SNMPSubAgentXImpl implements SNMPSubAgentX {
   protected static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");
   protected SnmpAgent snmpAgent;
   protected SubAgentX subAgent;
   protected SnmpMetadata snmpMetadata;
   protected SnmpMib snmpMib;
   protected MibApi mibApi;
   protected SnmpOid rootOid;
   protected String moduleName;
   protected Map snmpTables = new HashMap();

   public SNMPSubAgentXImpl(String masterAgentHost, int masterAgentPort, String subAgentId, String oidSubTree) throws SNMPAgentToolkitException {
      try {
         this.snmpMetadata = SnmpFramework.loadMibs("SNMPv2-SMI:SNMPv2-MIB", "mibs");
         this.snmpMib = new SnmpMib(this.snmpMetadata);
         this.snmpMib.isPrepareForAccessSupported(true);
         this.snmpAgent = new SnmpAgent(this.snmpMib, new TransportProvider[0]);
         SubAgentX.Config config = new SubAgentX.Config(masterAgentHost, masterAgentPort);
         config.setSubAgentId(new SnmpOid(subAgentId));
         config.isNetworkByteOrder(true);
         this.subAgent = new SubAgentX(this.snmpAgent, config, false);
         this.subAgent.startup();
         this.rootOid = new SnmpOid(oidSubTree);
         this.subAgent.addSubtree(new SubAgentX.Subtree(this.rootOid, (String)null, 0, 0, 0, 127, false));
         this.mibApi = new MibApi(this.snmpMetadata);
      } catch (Exception var6) {
         throw new SNMPAgentToolkitException(var6);
      }
   }

   public void createMIBModule(String moduleName, String moduleIdentityName, String descriptor, String organization, String contactInfo) throws SNMPAgentToolkitException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Creating subagent mib module " + moduleName);
      }

      try {
         this.moduleName = moduleName;
         this.mibApi.createModule(moduleName, moduleIdentityName, this.rootOid, descriptor, organization, contactInfo);
      } catch (Exception var7) {
         throw new SNMPAgentToolkitException(var7);
      }
   }

   public String outputMIBModule() throws SNMPAgentToolkitException {
      return MonfoxUtil.outputMIBModule(this.snmpMetadata, this.moduleName);
   }

   public void deleteAllSNMPTableRows() {
      Iterator i = this.snmpTables.keySet().iterator();

      while(i.hasNext()) {
         Object key = i.next();
         SnmpMibTable table = (SnmpMibTable)this.snmpTables.get(key);
         table.removeAllRows();
      }

   }

   public void shutdown() {
      try {
         this.snmpAgent.shutdown();
      } catch (SnmpTransportException var2) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Error shutting down agent for the subagent", var2);
         }
      }

      this.subAgent.shutdown();
   }
}
