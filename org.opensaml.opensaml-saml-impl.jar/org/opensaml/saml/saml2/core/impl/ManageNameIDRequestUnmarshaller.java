package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.Terminate;

public class ManageNameIDRequestUnmarshaller extends RequestAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      ManageNameIDRequest req = (ManageNameIDRequest)parentSAMLObject;
      if (childSAMLObject instanceof NameID) {
         req.setNameID((NameID)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedID) {
         req.setEncryptedID((EncryptedID)childSAMLObject);
      } else if (childSAMLObject instanceof NewID) {
         req.setNewID((NewID)childSAMLObject);
      } else if (childSAMLObject instanceof NewEncryptedID) {
         req.setNewEncryptedID((NewEncryptedID)childSAMLObject);
      } else if (childSAMLObject instanceof Terminate) {
         req.setTerminate((Terminate)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
