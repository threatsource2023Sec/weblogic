package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AssertionURIRef;

public class AssertionURIRefUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      AssertionURIRef assertionURIRef = (AssertionURIRef)samlObject;
      assertionURIRef.setAssertionURI(elementContent);
   }
}
