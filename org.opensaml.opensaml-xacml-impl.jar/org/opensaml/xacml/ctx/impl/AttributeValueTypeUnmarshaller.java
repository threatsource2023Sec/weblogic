package org.opensaml.xacml.ctx.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.w3c.dom.Attr;

public class AttributeValueTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributeValueType attributeValue = (AttributeValueType)xmlObject;
      this.processUnknownAttribute(attributeValue, attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      AttributeValueType attributeValue = (AttributeValueType)parentXMLObject;
      attributeValue.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      AttributeValueType attributeValue = (AttributeValueType)xmlObject;
      attributeValue.setValue(StringSupport.trimOrNull(elementContent));
   }
}
