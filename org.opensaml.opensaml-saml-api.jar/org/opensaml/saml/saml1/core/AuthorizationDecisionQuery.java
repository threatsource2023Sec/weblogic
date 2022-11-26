package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AuthorizationDecisionQuery extends SubjectQuery {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthorizationDecisionQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQuery", "saml1p");
   String TYPE_LOCAL_NAME = "AuthorizationDecisionQueryType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQueryType", "saml1p");
   String RESOURCE_ATTRIB_NAME = "Resource";

   String getResource();

   void setResource(String var1);

   List getActions();

   Evidence getEvidence();

   void setEvidence(Evidence var1);
}
