package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.ObligationType;
import org.w3c.dom.Attr;

public class ObligationTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      ObligationType obligation = (ObligationType)parentObject;
      if (childObject instanceof AttributeAssignmentType) {
         obligation.getAttributeAssignments().add((AttributeAssignmentType)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ObligationType obligation = (ObligationType)xmlObject;
      if (attribute.getLocalName().equals("ObligationId")) {
         obligation.setObligationId(attribute.getValue());
      } else if (attribute.getLocalName().equals("FulfillOn")) {
         if (attribute.getValue().equals(EffectType.Permit.toString())) {
            obligation.setFulfillOn(EffectType.Permit);
         } else {
            obligation.setFulfillOn(EffectType.Deny);
         }
      } else {
         super.processAttribute(xmlObject, attribute);
      }

   }
}
