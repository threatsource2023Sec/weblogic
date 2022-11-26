package monfox.toolkit.snmp.engine;

public interface SnmpErrorListener {
   void handleError(SnmpBuffer var1, TransportEntity var2, int var3, int var4, SnmpCoderException var5);
}
