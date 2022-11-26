package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;

public class EncryptedElementTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      EncryptedElementType eet = (EncryptedElementType)parentSAMLObject;
      if (childSAMLObject instanceof EncryptedData) {
         eet.setEncryptedData((EncryptedData)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptedKey) {
         eet.getEncryptedKeys().add((EncryptedKey)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }
}
