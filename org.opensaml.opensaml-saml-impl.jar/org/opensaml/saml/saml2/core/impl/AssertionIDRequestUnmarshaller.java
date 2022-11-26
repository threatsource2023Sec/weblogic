package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionIDRequest;

public class AssertionIDRequestUnmarshaller extends RequestAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AssertionIDRequest idRequest = (AssertionIDRequest)parentSAMLObject;
      if (childSAMLObject instanceof AssertionIDRef) {
         idRequest.getAssertionIDRefs().add((AssertionIDRef)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
