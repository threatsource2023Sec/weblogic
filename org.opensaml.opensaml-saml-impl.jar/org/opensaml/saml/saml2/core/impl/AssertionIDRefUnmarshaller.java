package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AssertionIDRef;

public class AssertionIDRefUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      AssertionIDRef assertionIDRef = (AssertionIDRef)samlObject;
      assertionIDRef.setAssertionID(elementContent);
   }
}
