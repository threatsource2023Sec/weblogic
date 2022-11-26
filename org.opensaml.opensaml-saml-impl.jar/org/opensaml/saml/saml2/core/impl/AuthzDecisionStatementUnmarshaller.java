package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Action;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml2.core.Evidence;
import org.w3c.dom.Attr;

public class AuthzDecisionStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      AuthzDecisionStatement authzDS = (AuthzDecisionStatement)parentObject;
      if (childObject instanceof Action) {
         authzDS.getActions().add((Action)childObject);
      } else if (childObject instanceof Evidence) {
         authzDS.setEvidence((Evidence)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthzDecisionStatement authzDS = (AuthzDecisionStatement)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Resource")) {
            authzDS.setResource(attribute.getValue());
         } else if (attribute.getLocalName().equals("Decision")) {
            String value = attribute.getValue();
            if (value.equals(DecisionTypeEnumeration.PERMIT.toString())) {
               authzDS.setDecision(DecisionTypeEnumeration.PERMIT);
            } else if (value.equals(DecisionTypeEnumeration.DENY.toString())) {
               authzDS.setDecision(DecisionTypeEnumeration.DENY);
            } else {
               if (!value.equals(DecisionTypeEnumeration.INDETERMINATE.toString())) {
                  throw new UnmarshallingException("Unknown value for DecisionType '" + value + "'");
               }

               authzDS.setDecision(DecisionTypeEnumeration.INDETERMINATE);
            }
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
