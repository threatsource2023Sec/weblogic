package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public class AssertionIDReferenceUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      AssertionIDReference assertionIDReference = (AssertionIDReference)samlObject;
      assertionIDReference.setReference(elementContent);
   }
}
