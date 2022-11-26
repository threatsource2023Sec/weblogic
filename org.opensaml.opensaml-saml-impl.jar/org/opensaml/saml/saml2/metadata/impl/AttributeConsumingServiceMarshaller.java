package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.w3c.dom.Element;

public class AttributeConsumingServiceMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      AttributeConsumingService service = (AttributeConsumingService)samlObject;
      domElement.setAttributeNS((String)null, "index", Integer.toString(service.getIndex()));
      if (service.isDefaultXSBoolean() != null) {
         domElement.setAttributeNS((String)null, "isDefault", service.isDefaultXSBoolean().toString());
      }

   }
}
