package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.Artifact;

public class ArtifactImpl extends AbstractSAMLObject implements Artifact {
   private String artifact;

   protected ArtifactImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getArtifact() {
      return this.artifact;
   }

   public void setArtifact(String newArtifact) {
      this.artifact = this.prepareForAssignment(this.artifact, newArtifact);
   }

   public List getOrderedChildren() {
      return null;
   }
}
