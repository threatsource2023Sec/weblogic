package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Audience;

public class AudienceImpl extends AbstractSAMLObject implements Audience {
   private String audienceURI;

   protected AudienceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAudienceURI() {
      return this.audienceURI;
   }

   public void setAudienceURI(String newAudienceURI) {
      this.audienceURI = this.prepareForAssignment(this.audienceURI, newAudienceURI);
   }

   public List getOrderedChildren() {
      return null;
   }
}
