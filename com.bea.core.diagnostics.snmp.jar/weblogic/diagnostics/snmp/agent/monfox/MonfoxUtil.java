package weblogic.diagnostics.snmp.agent.monfox;

import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.output.MibOutputter;
import monfox.toolkit.snmp.util.TextBuffer;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;

public class MonfoxUtil {
   protected static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugSNMPAgent");

   public static String outputMIBModule(SnmpMetadata snmpMetadata, String moduleName) throws SNMPAgentToolkitException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Generating MIB for subagent");
      }

      MibOutputter mo = new MibOutputter();

      try {
         TextBuffer tb = mo.outputModule(snmpMetadata, moduleName);
         return tb.toString();
      } catch (SnmpValueException var4) {
         throw new SNMPAgentToolkitException(var4);
      }
   }
}
