package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.DecisionType.DECISION;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class DecisionTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      DecisionType decision = (DecisionType)samlObject;
      decision.setDecision(DECISION.valueOf(elementContent));
   }
}
