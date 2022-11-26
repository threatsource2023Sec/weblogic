package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class StatusCodeTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      StatusCodeType attribute = (StatusCodeType)samlElement;
      if (attribute.getValue() != null) {
         domElement.setAttributeNS((String)null, "Value", attribute.getValue());
      }

   }
}
