package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

class m extends USMPrivProtocolSpec {
   public int getKeyLength() {
      return 16;
   }

   public int getPrivProtocol() {
      return 4;
   }

   public SnmpOid getPrivProtocolOID() {
      return Snmp.AES128_PRIV_PROTOCOL_OID;
   }

   public USMPrivModule newModule() {
      return new h();
   }
}
