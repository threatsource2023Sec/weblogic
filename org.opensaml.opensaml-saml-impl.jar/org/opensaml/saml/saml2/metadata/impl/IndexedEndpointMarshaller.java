package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.w3c.dom.Element;

public class IndexedEndpointMarshaller extends EndpointMarshaller {
   public void marshallAttributes(XMLObject samlObject, Element domElement) {
      IndexedEndpoint iEndpoint = (IndexedEndpoint)samlObject;
      if (iEndpoint.getIndex() != null) {
         domElement.setAttributeNS((String)null, "index", iEndpoint.getIndex().toString());
      }

      if (iEndpoint.isDefaultXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "isDefault", iEndpoint.isDefaultXSBoolean().toString());
      }

      super.marshallAttributes(samlObject, domElement);
   }
}
