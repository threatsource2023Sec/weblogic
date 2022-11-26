package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ActionMatchType;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.AttributeValueType;
import org.w3c.dom.Attr;

public class ActionMatchTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("MatchId")) {
         ActionMatchType matchType = (ActionMatchType)xmlObject;
         matchType.setMatchId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ActionMatchType matchType = (ActionMatchType)parentXMLObject;
      if (childXMLObject instanceof AttributeValueType) {
         matchType.setAttributeValue((AttributeValueType)childXMLObject);
      } else if (childXMLObject instanceof AttributeDesignatorType) {
         matchType.setActionAttributeDesignator((AttributeDesignatorType)childXMLObject);
      } else if (childXMLObject instanceof AttributeSelectorType) {
         matchType.setAttributeSelector((AttributeSelectorType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
