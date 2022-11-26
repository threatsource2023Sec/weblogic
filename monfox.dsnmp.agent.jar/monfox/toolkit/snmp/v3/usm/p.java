package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

class p extends USMPrivProtocolSpec {
   public int getKeyLength() {
      return 16;
   }

   public int getPrivProtocol() {
      return 2;
   }

   public SnmpOid getPrivProtocolOID() {
      return Snmp.DES_PRIV_PROTOCOL_OID;
   }

   public USMPrivModule newModule() {
      return new k();
   }
}
