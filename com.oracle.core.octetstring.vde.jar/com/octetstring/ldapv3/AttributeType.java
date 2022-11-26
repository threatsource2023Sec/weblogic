package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class AttributeType extends LDAPString {
   public AttributeType() {
   }

   public AttributeType(AttributeType value) {
      super((LDAPString)value);
   }

   public AttributeType(OctetString value) {
      super(value);
   }
}
