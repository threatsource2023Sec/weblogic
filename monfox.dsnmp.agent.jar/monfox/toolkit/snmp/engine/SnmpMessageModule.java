package monfox.toolkit.snmp.engine;

import monfox.toolkit.snmp.ber.BERBuffer;

public interface SnmpMessageModule {
   SnmpMessage decodeMessage(int var1, BERBuffer var2) throws SnmpCoderException;

   BERBuffer encodeMessage(SnmpMessage var1, int var2) throws SnmpCoderException;
}
