package org.opensaml.xacml.policy.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeValueType;
import org.w3c.dom.Attr;

public class AttributeValueTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      AttributeValueType attributeValue = (AttributeValueType)xmlObject;
      QName attribQName = QNameSupport.getNodeQName(attribute);
      if (attribute.isId()) {
         attributeValue.getUnknownAttributes().registerID(attribQName);
      }

      attributeValue.getUnknownAttributes().put(attribQName, attribute.getValue());
      if (attribute.getLocalName().equals("DataType")) {
         attributeValue.setDataType(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         this.processUnknownAttribute(attributeValue, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      AttributeValueType attributeValue = (AttributeValueType)parentXMLObject;
      attributeValue.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      AttributeValueType attributeValue = (AttributeValueType)xmlObject;
      attributeValue.setValue(elementContent);
   }
}
