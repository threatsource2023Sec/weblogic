package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class RelativeLDAPDN extends LDAPString {
   public RelativeLDAPDN() {
   }

   public RelativeLDAPDN(RelativeLDAPDN value) {
      super((LDAPString)value);
   }

   public RelativeLDAPDN(OctetString value) {
      super(value);
   }
}
