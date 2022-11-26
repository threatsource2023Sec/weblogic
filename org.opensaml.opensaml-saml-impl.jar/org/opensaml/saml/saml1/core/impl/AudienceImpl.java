package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Audience;

public class AudienceImpl extends AbstractSAMLObject implements Audience {
   private String uri;

   protected AudienceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String s) {
      this.uri = this.prepareForAssignment(this.uri, s);
   }

   public List getOrderedChildren() {
      return null;
   }
}
