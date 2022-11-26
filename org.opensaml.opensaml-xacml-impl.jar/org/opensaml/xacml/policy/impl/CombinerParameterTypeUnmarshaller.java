package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeValueType;
import org.opensaml.xacml.policy.CombinerParameterType;
import org.w3c.dom.Attr;

public class CombinerParameterTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      CombinerParameterType combinerParameterType = (CombinerParameterType)xmlObject;
      if (attribute.getLocalName().equals("ParameterName")) {
         combinerParameterType.setParameterName(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      CombinerParameterType combinerParameterType = (CombinerParameterType)parentXMLObject;
      if (childXMLObject instanceof AttributeValueType) {
         combinerParameterType.setAttributeValue((AttributeValueType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
