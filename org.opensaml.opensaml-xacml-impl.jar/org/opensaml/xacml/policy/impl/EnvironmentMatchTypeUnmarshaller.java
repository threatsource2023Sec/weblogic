package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.AttributeValueType;
import org.opensaml.xacml.policy.EnvironmentMatchType;
import org.w3c.dom.Attr;

public class EnvironmentMatchTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("MatchId")) {
         EnvironmentMatchType matchType = (EnvironmentMatchType)xmlObject;
         matchType.setMatchId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      EnvironmentMatchType matchType = (EnvironmentMatchType)parentXMLObject;
      if (childXMLObject instanceof AttributeValueType) {
         matchType.setAttributeValue((AttributeValueType)childXMLObject);
      } else if (childXMLObject instanceof AttributeDesignatorType) {
         matchType.setEnvironmentAttributeDesignator((AttributeDesignatorType)childXMLObject);
      } else if (childXMLObject instanceof AttributeSelectorType) {
         matchType.setAttributeSelector((AttributeSelectorType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
