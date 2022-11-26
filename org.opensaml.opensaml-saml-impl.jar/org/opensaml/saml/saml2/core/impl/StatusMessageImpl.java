package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.StatusMessage;

public class StatusMessageImpl extends AbstractSAMLObject implements StatusMessage {
   private String message;

   protected StatusMessageImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String newMessage) {
      this.message = this.prepareForAssignment(this.message, newMessage);
   }

   public List getOrderedChildren() {
      return null;
   }
}
