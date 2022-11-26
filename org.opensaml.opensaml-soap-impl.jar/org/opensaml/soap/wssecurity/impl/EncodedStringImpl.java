package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.EncodedString;

public class EncodedStringImpl extends AttributedStringImpl implements EncodedString {
   private String encodingType;

   public EncodedStringImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getEncodingType() {
      return this.encodingType;
   }

   public void setEncodingType(String newEncodingType) {
      this.encodingType = this.prepareForAssignment(this.encodingType, newEncodingType);
   }
}
