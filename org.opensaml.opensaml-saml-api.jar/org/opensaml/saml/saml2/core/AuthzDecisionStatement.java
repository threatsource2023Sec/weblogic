package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AuthzDecisionStatement extends Statement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthzDecisionStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement", "saml2");
   String TYPE_LOCAL_NAME = "AuthzDecisionStatementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatementType", "saml2");
   String RESOURCE_ATTRIB_NAME = "Resource";
   String DECISION_ATTRIB_NAME = "Decision";

   String getResource();

   void setResource(String var1);

   DecisionTypeEnumeration getDecision();

   void setDecision(DecisionTypeEnumeration var1);

   List getActions();

   Evidence getEvidence();

   void setEvidence(Evidence var1);
}
