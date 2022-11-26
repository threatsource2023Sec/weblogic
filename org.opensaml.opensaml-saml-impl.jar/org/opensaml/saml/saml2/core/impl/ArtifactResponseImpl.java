package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.ArtifactResponse;

public class ArtifactResponseImpl extends StatusResponseTypeImpl implements ArtifactResponse {
   private SAMLObject protocolMessage;

   protected ArtifactResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public SAMLObject getMessage() {
      return this.protocolMessage;
   }

   public void setMessage(SAMLObject message) {
      this.protocolMessage = (SAMLObject)this.prepareForAssignment(this.protocolMessage, message);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.add(this.protocolMessage);
      return Collections.unmodifiableList(children);
   }
}
