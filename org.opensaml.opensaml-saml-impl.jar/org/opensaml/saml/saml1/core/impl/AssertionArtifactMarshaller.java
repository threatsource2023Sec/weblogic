package org.opensaml.saml.saml1.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.w3c.dom.Element;

public class AssertionArtifactMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      AssertionArtifact assertionArtifact = (AssertionArtifact)samlObject;
      if (assertionArtifact.getAssertionArtifact() != null) {
         ElementSupport.appendTextContent(domElement, assertionArtifact.getAssertionArtifact());
      }

   }
}
