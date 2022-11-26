package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface AuthnQuery extends SubjectQuery {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnQuery";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnQuery", "saml2p");
   String TYPE_LOCAL_NAME = "AuthnQueryType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnQueryType", "saml2p");
   String SESSION_INDEX_ATTRIB_NAME = "SessionIndex";

   String getSessionIndex();

   void setSessionIndex(String var1);

   RequestedAuthnContext getRequestedAuthnContext();

   void setRequestedAuthnContext(RequestedAuthnContext var1);
}
