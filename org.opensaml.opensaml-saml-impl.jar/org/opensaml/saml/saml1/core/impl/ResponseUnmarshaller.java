package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Status;

public class ResponseUnmarshaller extends ResponseAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Response response = (Response)parentSAMLObject;
      if (childSAMLObject instanceof Assertion) {
         response.getAssertions().add((Assertion)childSAMLObject);
      } else if (childSAMLObject instanceof Status) {
         response.setStatus((Status)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
