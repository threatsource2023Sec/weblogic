package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

class n extends USMPrivProtocolSpec {
   public int getKeyLength() {
      return 24;
   }

   public int getPrivProtocol() {
      return 5;
   }

   public SnmpOid getPrivProtocolOID() {
      return Snmp.AES192_PRIV_PROTOCOL_OID;
   }

   public USMPrivModule newModule() {
      return new i();
   }
}
