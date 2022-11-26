package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.CombinerParameterType;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.w3c.dom.Attr;

public class RuleCombinerParametersTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      if (attribute.getLocalName().equals("RuleIdRef")) {
         RuleCombinerParametersType ruleCombinerParametersType = (RuleCombinerParametersType)xmlObject;
         ruleCombinerParametersType.setRuleIdRef(StringSupport.trimOrNull(attribute.getValue()));
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      RuleCombinerParametersType ruleCombinerParametersType = (RuleCombinerParametersType)parentXMLObject;
      if (childXMLObject instanceof CombinerParameterType) {
         ruleCombinerParametersType.getCombinerParameters().add((CombinerParameterType)childXMLObject);
      } else {
         super.processChildElement(parentXMLObject, childXMLObject);
      }

   }
}
