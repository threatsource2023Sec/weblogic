package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.TelephoneNumber;

public class TelephoneNumberImpl extends AbstractSAMLObject implements TelephoneNumber {
   private String number;

   protected TelephoneNumberImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getNumber() {
      return this.number;
   }

   public void setNumber(String newNumber) {
      this.number = this.prepareForAssignment(this.number, newNumber);
   }

   public List getOrderedChildren() {
      return null;
   }
}
