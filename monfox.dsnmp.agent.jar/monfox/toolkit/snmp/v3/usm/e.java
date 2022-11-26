package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

class e extends USMAuthProtocolSpec {
   public int getDigestLength() {
      return 16;
   }

   public int getAuthProtocol() {
      return 0;
   }

   public SnmpOid getAuthProtocolOID() {
      return Snmp.MD5_AUTH_PROTOCOL_OID;
   }

   public USMAuthModule newModule() {
      return new c();
   }
}
