package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.w3c.dom.Element;

public class EndpointMarshaller extends AbstractSAMLObjectMarshaller {
   public void marshallAttributes(XMLObject samlElement, Element domElement) {
      Endpoint endpoint = (Endpoint)samlElement;
      if (endpoint.getBinding() != null) {
         domElement.setAttributeNS((String)null, "Binding", endpoint.getBinding().toString());
      }

      if (endpoint.getLocation() != null) {
         domElement.setAttributeNS((String)null, "Location", endpoint.getLocation().toString());
      }

      if (endpoint.getResponseLocation() != null) {
         domElement.setAttributeNS((String)null, "ResponseLocation", endpoint.getResponseLocation().toString());
      }

      this.marshallUnknownAttributes(endpoint, domElement);
   }
}
