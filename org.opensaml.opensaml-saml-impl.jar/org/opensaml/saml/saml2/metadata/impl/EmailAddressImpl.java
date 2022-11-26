package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.EmailAddress;

public class EmailAddressImpl extends AbstractSAMLObject implements EmailAddress {
   private String address;

   protected EmailAddressImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAddress() {
      return this.address;
   }

   public void setAddress(String addr) {
      this.address = this.prepareForAssignment(this.address, addr);
   }

   public List getOrderedChildren() {
      return null;
   }
}
