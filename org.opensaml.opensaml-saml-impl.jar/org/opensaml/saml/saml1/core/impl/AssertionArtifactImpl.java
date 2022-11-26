package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.AssertionArtifact;

public class AssertionArtifactImpl extends AbstractSAMLObject implements AssertionArtifact {
   private String assertionArtifact;

   protected AssertionArtifactImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getAssertionArtifact() {
      return this.assertionArtifact;
   }

   public void setAssertionArtifact(String artifact) {
      this.assertionArtifact = this.prepareForAssignment(this.assertionArtifact, artifact);
   }

   public List getOrderedChildren() {
      return null;
   }
}
