package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class DelRequest extends LDAPDN {
   public DelRequest() {
   }

   public DelRequest(DelRequest value) {
      super((LDAPDN)value);
   }

   public DelRequest(OctetString value) {
      super(value);
   }
}
