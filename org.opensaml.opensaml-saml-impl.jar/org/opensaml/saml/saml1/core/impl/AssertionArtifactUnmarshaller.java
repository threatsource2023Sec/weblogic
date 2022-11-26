package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.AssertionArtifact;

public class AssertionArtifactUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      AssertionArtifact assertionArtifact = (AssertionArtifact)samlObject;
      assertionArtifact.setAssertionArtifact(elementContent);
   }
}
