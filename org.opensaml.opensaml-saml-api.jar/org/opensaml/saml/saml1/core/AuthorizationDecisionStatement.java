package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthorizationDecisionStatement extends SAMLObject, SubjectStatement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthorizationDecisionStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement", "saml1");
   String TYPE_LOCAL_NAME = "AuthorizationDecisionStatementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatementType", "saml1");
   String RESOURCE_ATTRIB_NAME = "Resource";
   String DECISION_ATTRIB_NAME = "Decision";

   String getResource();

   void setResource(String var1);

   DecisionTypeEnumeration getDecision();

   void setDecision(DecisionTypeEnumeration var1);

   List getActions();

   Evidence getEvidence();

   void setEvidence(Evidence var1) throws IllegalArgumentException;
}
