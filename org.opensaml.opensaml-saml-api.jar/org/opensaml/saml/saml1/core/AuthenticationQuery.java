package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;

public interface AuthenticationQuery extends SubjectQuery {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthenticationQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery", "saml1p");
   String TYPE_LOCAL_NAME = "AuthenticationQueryType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQueryType", "saml1p");
   String AUTHENTICATIONMETHOD_ATTRIB_NAME = "AuthenticationMethod";

   String getAuthenticationMethod();

   void setAuthenticationMethod(String var1);
}
