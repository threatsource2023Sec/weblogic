package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Response;

public class ResponseUnmarshaller extends StatusResponseTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Response resp = (Response)parentSAMLObject;
      if (childSAMLObject instanceof Assertion) {
         resp.getAssertions().add((Assertion)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedAssertion) {
         resp.getEncryptedAssertions().add((EncryptedAssertion)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
