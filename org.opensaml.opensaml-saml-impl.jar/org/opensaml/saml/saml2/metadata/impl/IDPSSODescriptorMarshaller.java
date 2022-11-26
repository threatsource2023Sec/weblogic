package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.w3c.dom.Element;

public class IDPSSODescriptorMarshaller extends SSODescriptorMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      IDPSSODescriptor descriptor = (IDPSSODescriptor)samlObject;
      if (descriptor.getWantAuthnRequestsSignedXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "WantAuthnRequestsSigned", descriptor.getWantAuthnRequestsSignedXSBoolean().toString());
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
