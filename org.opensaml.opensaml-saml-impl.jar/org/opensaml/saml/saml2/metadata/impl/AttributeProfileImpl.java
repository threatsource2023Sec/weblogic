package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.AttributeProfile;

public class AttributeProfileImpl extends AbstractSAMLObject implements AttributeProfile {
   private String profileURI;

   protected AttributeProfileImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getProfileURI() {
      return this.profileURI;
   }

   public void setProfileURI(String theProfileURI) {
      this.profileURI = this.prepareForAssignment(this.profileURI, theProfileURI);
   }

   public List getOrderedChildren() {
      return null;
   }
}
