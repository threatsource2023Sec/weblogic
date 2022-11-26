package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.w3c.dom.Element;

public class AdditionalMetadataLocationMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AdditionalMetadataLocation aml = (AdditionalMetadataLocation)samlElement;
      if (aml.getNamespaceURI() != null) {
         domElement.setAttributeNS((String)null, "namespace", aml.getNamespaceURI());
      }

   }

   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      super.marshallElementContent(samlObject, domElement);
      AdditionalMetadataLocation aml = (AdditionalMetadataLocation)samlObject;
      if (aml.getLocationURI() != null) {
         domElement.appendChild(domElement.getOwnerDocument().createTextNode(aml.getLocationURI()));
      }

   }
}
