package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ExpressionType;
import org.opensaml.xacml.policy.VariableDefinitionType;
import org.w3c.dom.Attr;

public class VariableDefinitionTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("VariableId")) {
         VariableDefinitionType variableDefinitionType = (VariableDefinitionType)xmlObject;
         variableDefinitionType.setVariableId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      if (childXMLObject instanceof ExpressionType) {
         VariableDefinitionType variableDefinition = (VariableDefinitionType)parentXMLObject;
         variableDefinition.setExpression((ExpressionType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
