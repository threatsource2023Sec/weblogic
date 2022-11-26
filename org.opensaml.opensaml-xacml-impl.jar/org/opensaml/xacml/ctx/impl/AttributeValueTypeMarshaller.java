package org.opensaml.xacml.ctx.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class AttributeValueTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributeValueType attributeValue = (AttributeValueType)xmlObject;
      this.marshallUnknownAttributes(attributeValue, domElement);
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      AttributeValueType attributeValue = (AttributeValueType)xmlObject;
      if (attributeValue.getValue() != null) {
         ElementSupport.appendTextContent(domElement, attributeValue.getValue());
      }

   }
}
