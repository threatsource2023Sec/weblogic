package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface Request extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Request";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Request", "saml1p");
   String TYPE_LOCAL_NAME = "RequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestType", "saml1p");

   Query getQuery();

   SubjectQuery getSubjectQuery();

   AuthenticationQuery getAuthenticationQuery();

   AttributeQuery getAttributeQuery();

   AuthorizationDecisionQuery getAuthorizationDecisionQuery();

   void setQuery(Query var1);

   List getAssertionIDReferences();

   List getAssertionArtifacts();
}
