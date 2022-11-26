package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AuthzDecisionQuery extends SubjectQuery {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthzDecisionQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthzDecisionQuery", "saml2p");
   String TYPE_LOCAL_NAME = "AuthzDecisionQueryType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthzDecisionQueryType", "saml2p");
   String RESOURCE_ATTRIB_NAME = "Resource";

   String getResource();

   void setResource(String var1);

   List getActions();

   Evidence getEvidence();

   void setEvidence(Evidence var1);
}
