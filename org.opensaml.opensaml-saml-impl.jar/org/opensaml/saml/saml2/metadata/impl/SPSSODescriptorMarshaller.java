package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.w3c.dom.Element;

public class SPSSODescriptorMarshaller extends SSODescriptorMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      SPSSODescriptor descriptor = (SPSSODescriptor)samlObject;
      if (descriptor.isAuthnRequestsSignedXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "AuthnRequestsSigned", descriptor.isAuthnRequestsSignedXSBoolean().toString());
      }

      if (descriptor.getWantAssertionsSignedXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "WantAssertionsSigned", descriptor.getWantAssertionsSignedXSBoolean().toString());
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
