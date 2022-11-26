package monfox.toolkit.snmp.agent;

public interface SnmpRequestProcessor {
   void handleRequest(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3);
}
