package com.octetstring.ldapv3;

import com.asn1c.core.OctetString;

public class AttributeDescription extends LDAPString {
   public AttributeDescription() {
   }

   public AttributeDescription(AttributeDescription value) {
      super((LDAPString)value);
   }

   public AttributeDescription(OctetString value) {
      super(value);
   }
}
