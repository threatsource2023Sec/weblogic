package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

class o extends USMPrivProtocolSpec {
   public int getKeyLength() {
      return 32;
   }

   public int getPrivProtocol() {
      return 6;
   }

   public SnmpOid getPrivProtocolOID() {
      return Snmp.AES256_PRIV_PROTOCOL_OID;
   }

   public USMPrivModule newModule() {
      return new j();
   }
}
