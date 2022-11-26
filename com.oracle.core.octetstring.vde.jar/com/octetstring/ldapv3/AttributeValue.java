package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class AttributeValue extends OctetString {
   public AttributeValue() {
   }

   public AttributeValue(AttributeValue value) {
      super(value);
   }

   public AttributeValue(OctetString value) {
      super(value);
   }
}
