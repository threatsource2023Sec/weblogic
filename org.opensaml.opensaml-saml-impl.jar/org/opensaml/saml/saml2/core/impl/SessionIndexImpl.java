package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.SessionIndex;

public class SessionIndexImpl extends AbstractSAMLObject implements SessionIndex {
   private String sessionIndex;

   protected SessionIndexImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getSessionIndex() {
      return this.sessionIndex;
   }

   public void setSessionIndex(String newSessionIndex) {
      this.sessionIndex = this.prepareForAssignment(this.sessionIndex, newSessionIndex);
   }

   public List getOrderedChildren() {
      return null;
   }
}
