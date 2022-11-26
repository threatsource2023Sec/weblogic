package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;

public class NameIDMappingResponseUnmarshaller extends StatusResponseTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      NameIDMappingResponse resp = (NameIDMappingResponse)parentSAMLObject;
      if (childSAMLObject instanceof NameID) {
         resp.setNameID((NameID)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedID) {
         resp.setEncryptedID((EncryptedID)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
