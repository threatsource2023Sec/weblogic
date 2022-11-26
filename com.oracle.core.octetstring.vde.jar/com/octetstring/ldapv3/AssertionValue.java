package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class AssertionValue extends OctetString {
   public AssertionValue() {
   }

   public AssertionValue(AssertionValue value) {
      super(value);
   }

   public AssertionValue(OctetString value) {
      super(value);
   }
}
