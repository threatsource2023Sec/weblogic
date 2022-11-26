package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.AttributeValueType;
import org.opensaml.xacml.policy.ResourceMatchType;
import org.w3c.dom.Attr;

public class ResourceMatchTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("MatchId")) {
         ResourceMatchType matchType = (ResourceMatchType)xmlObject;
         matchType.setMatchId(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ResourceMatchType matchType = (ResourceMatchType)parentXMLObject;
      if (childXMLObject instanceof AttributeValueType) {
         matchType.setAttributeValue((AttributeValueType)childXMLObject);
      } else if (childXMLObject instanceof AttributeSelectorType) {
         matchType.setAttributeSelector((AttributeSelectorType)childXMLObject);
      } else if (childXMLObject.getElementQName().equals(AttributeDesignatorType.RESOURCE_ATTRIBUTE_DESIGNATOR_ELEMENT_NAME)) {
         matchType.setResourceAttributeDesignator((AttributeDesignatorType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
