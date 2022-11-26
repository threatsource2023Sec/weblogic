package monfox.toolkit.snmp.engine;

public interface SnmpCoder {
   SnmpPDU decodeMessage(SnmpBuffer var1) throws SnmpCoderException;

   SnmpBuffer encodeMessage(SnmpPDU var1, int var2) throws SnmpCoderException;
}
