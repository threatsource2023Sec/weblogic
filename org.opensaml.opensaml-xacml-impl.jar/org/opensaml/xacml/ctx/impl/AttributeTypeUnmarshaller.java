package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.w3c.dom.Attr;

public class AttributeTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      AttributeType attribute = (AttributeType)parentObject;
      if (childObject instanceof AttributeValueType) {
         attribute.getAttributeValues().add((AttributeValueType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributeType attrib = (AttributeType)xmlObject;
      if (attribute.getLocalName().equals("AttributeId")) {
         attrib.setAttributeID(attribute.getValue());
      } else if (attribute.getLocalName().equals("DataType")) {
         attrib.setDataType(attribute.getValue());
      } else if (attribute.getLocalName().equals("Issuer")) {
         attrib.setIssuer(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
