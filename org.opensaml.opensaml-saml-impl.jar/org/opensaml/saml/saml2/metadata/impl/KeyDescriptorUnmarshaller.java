package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Attr;

public class KeyDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      KeyDescriptor keyDescriptor = (KeyDescriptor)parentSAMLObject;
      if (childSAMLObject instanceof KeyInfo) {
         keyDescriptor.setKeyInfo((KeyInfo)childSAMLObject);
      } else if (childSAMLObject instanceof EncryptionMethod) {
         keyDescriptor.getEncryptionMethods().add((EncryptionMethod)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      KeyDescriptor keyDescriptor = (KeyDescriptor)samlObject;
      if (attribute.getName().equals("use") && attribute.getNamespaceURI() == null) {
         try {
            UsageType usageType = (UsageType)UsageType.valueOf(UsageType.class, attribute.getValue().toUpperCase());
            if (usageType != UsageType.SIGNING && usageType != UsageType.ENCRYPTION) {
               throw new UnmarshallingException("Invalid key usage type: " + attribute.getValue());
            }

            keyDescriptor.setUse(usageType);
         } catch (IllegalArgumentException var5) {
            throw new UnmarshallingException("Invalid key usage type: " + attribute.getValue());
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
