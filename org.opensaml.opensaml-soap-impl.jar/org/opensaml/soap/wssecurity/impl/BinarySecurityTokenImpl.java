package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.BinarySecurityToken;

public class BinarySecurityTokenImpl extends EncodedStringImpl implements BinarySecurityToken {
   private String valueType;

   public BinarySecurityTokenImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
      this.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
   }

   public String getValueType() {
      return this.valueType;
   }

   public void setValueType(String newValueType) {
      this.valueType = this.prepareForAssignment(this.valueType, newValueType);
   }
}
