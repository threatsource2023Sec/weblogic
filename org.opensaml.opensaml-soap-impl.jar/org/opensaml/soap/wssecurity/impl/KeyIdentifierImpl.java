package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.KeyIdentifier;

public class KeyIdentifierImpl extends EncodedStringImpl implements KeyIdentifier {
   private String valueType;

   public KeyIdentifierImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getValueType() {
      return this.valueType;
   }

   public void setValueType(String newValueType) {
      this.valueType = this.prepareForAssignment(this.valueType, newValueType);
   }
}
