package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.w3c.dom.Attr;

public class EndpointUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Endpoint endpoint = (Endpoint)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Binding")) {
            endpoint.setBinding(attribute.getValue());
         } else if (attribute.getLocalName().equals("Location")) {
            endpoint.setLocation(attribute.getValue());
         } else if (attribute.getLocalName().equals("ResponseLocation")) {
            endpoint.setResponseLocation(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         this.processUnknownAttribute(endpoint, attribute);
      }

   }

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Endpoint endpoint = (Endpoint)parentSAMLObject;
      endpoint.getUnknownXMLObjects().add(childSAMLObject);
   }
}
