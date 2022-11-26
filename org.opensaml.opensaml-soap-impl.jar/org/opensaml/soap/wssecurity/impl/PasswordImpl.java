package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Password;

public class PasswordImpl extends AttributedStringImpl implements Password {
   private String type = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#PasswordText";

   public PasswordImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getType() {
      return this.type;
   }

   public void setType(String newType) {
      this.type = this.prepareForAssignment(this.type, newType);
   }
}
