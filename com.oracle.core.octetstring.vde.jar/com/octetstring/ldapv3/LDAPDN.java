package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class LDAPDN extends LDAPString {
   public LDAPDN() {
   }

   public LDAPDN(LDAPDN value) {
      super((LDAPString)value);
   }

   public LDAPDN(OctetString value) {
      super(value);
   }
}
