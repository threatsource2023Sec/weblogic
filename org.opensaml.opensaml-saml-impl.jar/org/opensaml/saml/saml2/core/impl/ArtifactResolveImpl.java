package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.saml.saml2.core.Artifact;
import org.opensaml.saml.saml2.core.ArtifactResolve;

public class ArtifactResolveImpl extends RequestAbstractTypeImpl implements ArtifactResolve {
   private Artifact artifact;

   protected ArtifactResolveImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Artifact getArtifact() {
      return this.artifact;
   }

   public void setArtifact(Artifact newArtifact) {
      this.artifact = (Artifact)this.prepareForAssignment(this.artifact, newArtifact);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (super.getOrderedChildren() != null) {
         children.addAll(super.getOrderedChildren());
      }

      if (this.artifact != null) {
         children.add(this.artifact);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
