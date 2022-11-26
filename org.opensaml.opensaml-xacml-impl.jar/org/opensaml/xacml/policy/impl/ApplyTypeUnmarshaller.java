package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ApplyType;
import org.opensaml.xacml.policy.ExpressionType;
import org.w3c.dom.Attr;

public class ApplyTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("FunctionId")) {
         ApplyType applyType = (ApplyType)xmlObject;
         applyType.setFunctionId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ApplyType applayType = (ApplyType)parentXMLObject;
      if (childXMLObject instanceof ExpressionType) {
         ExpressionType expression = (ExpressionType)childXMLObject;
         applayType.getExpressions().add(expression);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
