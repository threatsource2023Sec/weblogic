package monfox.toolkit.snmp.v3.usm;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;

class f extends USMAuthProtocolSpec {
   public int getDigestLength() {
      return 20;
   }

   public int getAuthProtocol() {
      return 1;
   }

   public SnmpOid getAuthProtocolOID() {
      return Snmp.SHA_AUTH_PROTOCOL_OID;
   }

   public USMAuthModule newModule() {
      return new d();
   }
}
