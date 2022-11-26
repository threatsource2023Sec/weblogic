package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.w3c.dom.Element;

public class AuthnRequestMarshaller extends RequestAbstractTypeMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthnRequest req = (AuthnRequest)samlObject;
      if (req.isForceAuthnXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "ForceAuthn", req.isForceAuthnXSBoolean().toString());
      }

      if (req.isPassiveXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "IsPassive", req.isPassiveXSBoolean().toString());
      }

      if (req.getProtocolBinding() != null) {
         domElement.setAttributeNS((String)null, "ProtocolBinding", req.getProtocolBinding());
      }

      if (req.getAssertionConsumerServiceIndex() != null) {
         domElement.setAttributeNS((String)null, "AssertionConsumerServiceIndex", req.getAssertionConsumerServiceIndex().toString());
      }

      if (req.getAssertionConsumerServiceURL() != null) {
         domElement.setAttributeNS((String)null, "AssertionConsumerServiceURL", req.getAssertionConsumerServiceURL());
      }

      if (req.getAttributeConsumingServiceIndex() != null) {
         domElement.setAttributeNS((String)null, "AttributeConsumingServiceIndex", req.getAttributeConsumingServiceIndex().toString());
      }

      if (req.getProviderName() != null) {
         domElement.setAttributeNS((String)null, "ProviderName", req.getProviderName());
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
