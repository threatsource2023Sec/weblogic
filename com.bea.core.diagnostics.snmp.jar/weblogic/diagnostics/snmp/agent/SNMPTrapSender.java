package weblogic.diagnostics.snmp.agent;

import java.util.List;

public interface SNMPTrapSender {
   void sendTrap(String var1, List var2) throws SNMPTrapException;
}
