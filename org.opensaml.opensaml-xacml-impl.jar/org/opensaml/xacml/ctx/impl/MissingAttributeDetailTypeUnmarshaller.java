package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.ctx.MissingAttributeDetailType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.w3c.dom.Attr;

public class MissingAttributeDetailTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      MissingAttributeDetailType madt = (MissingAttributeDetailType)xmlObject;
      if (attribute.getLocalName().equals("AttributeId")) {
         madt.setAttributeId(attribute.getValue());
      } else if (attribute.getLocalName().equals("DataType")) {
         madt.setDataType(attribute.getValue());
      } else if (attribute.getLocalName().equals("Issuer")) {
         madt.setIssuer(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      MissingAttributeDetailType madt = (MissingAttributeDetailType)parentXMLObject;
      if (childXMLObject instanceof AttributeValueType) {
         madt.getAttributeValues().add((AttributeValueType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
