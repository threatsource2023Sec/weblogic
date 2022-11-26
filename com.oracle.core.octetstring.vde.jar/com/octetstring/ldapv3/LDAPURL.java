package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class LDAPURL extends LDAPString {
   public LDAPURL() {
   }

   public LDAPURL(LDAPURL value) {
      super((LDAPString)value);
   }

   public LDAPURL(OctetString value) {
      super(value);
   }
}
