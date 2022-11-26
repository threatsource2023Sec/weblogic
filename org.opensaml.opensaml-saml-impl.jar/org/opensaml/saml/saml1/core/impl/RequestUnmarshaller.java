package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.Query;
import org.opensaml.saml.saml1.core.Request;

public class RequestUnmarshaller extends RequestAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentElement, XMLObject childElement) throws UnmarshallingException {
      Request request = (Request)parentElement;

      try {
         if (childElement instanceof Query) {
            request.setQuery((Query)childElement);
         } else if (childElement instanceof AssertionIDReference) {
            request.getAssertionIDReferences().add((AssertionIDReference)childElement);
         } else if (childElement instanceof AssertionArtifact) {
            request.getAssertionArtifacts().add((AssertionArtifact)childElement);
         } else {
            super.processChildElement(parentElement, childElement);
         }

      } catch (IllegalArgumentException var5) {
         throw new UnmarshallingException(var5);
      }
   }
}
