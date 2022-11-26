package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class LDAPString extends OctetString {
   public LDAPString() {
   }

   public LDAPString(LDAPString value) {
      super(value);
   }

   public LDAPString(OctetString value) {
      super(value);
   }
}
