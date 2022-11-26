package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Artifact;

public class ArtifactUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      Artifact artifact = (Artifact)samlObject;
      artifact.setArtifact(elementContent);
   }
}
