package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class AttributeTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AttributeType attribute = (AttributeType)samlElement;
      if (attribute.getIssuer() != null) {
         domElement.setAttributeNS((String)null, "Issuer", attribute.getIssuer());
      }

      if (attribute.getDataType() != null) {
         domElement.setAttributeNS((String)null, "DataType", attribute.getDataType());
      }

      if (attribute.getAttributeId() != null) {
         domElement.setAttributeNS((String)null, "AttributeId", attribute.getAttributeId());
      }

   }
}
