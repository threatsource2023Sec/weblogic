package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ExpressionType;
import org.opensaml.xacml.policy.VariableReferenceType;
import org.w3c.dom.Attr;

public class VariableReferenceTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("VariableId")) {
         VariableReferenceType variableReferenceType = (VariableReferenceType)xmlObject;
         variableReferenceType.setVariableId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      if (childXMLObject instanceof ExpressionType) {
         VariableReferenceType variableReferenceType = (VariableReferenceType)parentXMLObject;
         variableReferenceType.getExpressions().add((ExpressionType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
