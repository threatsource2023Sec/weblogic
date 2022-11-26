package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.ObligationsType;
import org.w3c.dom.Attr;

public class ResultTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ResultType result = (ResultType)xmlObject;
      if (attribute.getLocalName().equals("ResourceId")) {
         result.setResourceId(attribute.getValue());
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }

   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      ResultType result = (ResultType)parentObject;
      if (childObject instanceof ObligationsType) {
         result.setObligations((ObligationsType)childObject);
      } else if (childObject instanceof StatusType) {
         result.setStatus((StatusType)childObject);
      } else if (childObject instanceof DecisionType) {
         result.setDecision((DecisionType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
