package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.NameIDPolicy;

public class NameIDMappingRequestUnmarshaller extends RequestAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      NameIDMappingRequest req = (NameIDMappingRequest)parentSAMLObject;
      if (childSAMLObject instanceof BaseID) {
         req.setBaseID((BaseID)childSAMLObject);
      } else if (childSAMLObject instanceof NameID) {
         req.setNameID((NameID)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedID) {
         req.setEncryptedID((EncryptedID)childSAMLObject);
      } else if (childSAMLObject instanceof NameIDPolicy) {
         req.setNameIDPolicy((NameIDPolicy)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
