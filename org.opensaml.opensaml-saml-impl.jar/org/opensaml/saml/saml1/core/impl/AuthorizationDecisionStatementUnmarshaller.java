package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml1.core.Evidence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public class AuthorizationDecisionStatementUnmarshaller extends SubjectStatementUnmarshaller {
   private final Logger log = LoggerFactory.getLogger(AuthorizationDecisionStatementUnmarshaller.class);

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement)parentSAMLObject;
      if (childSAMLObject instanceof Action) {
         authorizationDecisionStatement.getActions().add((Action)childSAMLObject);
      } else if (childSAMLObject instanceof Evidence) {
         authorizationDecisionStatement.setEvidence((Evidence)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("Decision".equals(attribute.getLocalName())) {
            String value = attribute.getValue();
            if (value.equals(DecisionTypeEnumeration.PERMIT.toString())) {
               authorizationDecisionStatement.setDecision(DecisionTypeEnumeration.PERMIT);
            } else if (value.equals(DecisionTypeEnumeration.DENY.toString())) {
               authorizationDecisionStatement.setDecision(DecisionTypeEnumeration.DENY);
            } else {
               if (!value.equals(DecisionTypeEnumeration.INDETERMINATE.toString())) {
                  this.log.error("Unknown value for DecisionType '" + value + "'");
                  throw new UnmarshallingException("Unknown value for DecisionType '" + value + "'");
               }

               authorizationDecisionStatement.setDecision(DecisionTypeEnumeration.INDETERMINATE);
            }
         } else if ("Resource".equals(attribute.getLocalName())) {
            authorizationDecisionStatement.setResource(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
