package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.ConfirmationMethod;

public class ConfirmationMethodImpl extends AbstractSAMLObject implements ConfirmationMethod {
   private String confirmationMethod;

   protected ConfirmationMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getConfirmationMethod() {
      return this.confirmationMethod;
   }

   public void setConfirmationMethod(String method) {
      this.confirmationMethod = this.prepareForAssignment(this.confirmationMethod, method);
   }

   public List getOrderedChildren() {
      return null;
   }
}
