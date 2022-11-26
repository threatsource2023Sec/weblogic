package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public class AdviceUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Advice advice = (Advice)parentSAMLObject;
      if (childSAMLObject instanceof Assertion) {
         advice.getAssertions().add((Assertion)childSAMLObject);
      } else if (childSAMLObject instanceof AssertionIDReference) {
         advice.getAssertionIDReferences().add((AssertionIDReference)childSAMLObject);
      } else {
         advice.getUnknownXMLObjects().add(childSAMLObject);
      }

   }
}
