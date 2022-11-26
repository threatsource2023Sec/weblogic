package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.security.credential.UsageType;
import org.w3c.dom.Element;

public class KeyDescriptorMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      KeyDescriptor keyDescriptor = (KeyDescriptor)xmlObject;
      if (keyDescriptor.getUse() != null) {
         UsageType use = keyDescriptor.getUse();
         if (!use.equals(UsageType.SIGNING) && !use.equals(UsageType.ENCRYPTION)) {
            if (!use.equals(UsageType.UNSPECIFIED)) {
               throw new MarshallingException("KeyDescriptor had illegal value for use attribute: " + use.toString());
            }
         } else {
            domElement.setAttributeNS((String)null, "use", use.toString().toLowerCase());
         }
      }

   }
}
