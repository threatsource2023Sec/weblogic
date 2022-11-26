package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface LogoutResponse extends StatusResponseType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "LogoutResponse";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponse", "saml2p");
   String TYPE_LOCAL_NAME = "LogoutResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutResponseType", "saml2p");
   String USER_LOGOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:user";
   String ADMIN_LOGOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:admin";
   String GLOBAL_TIMEOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:global-timeout";
   String SP_TIMEOUT_URI = "urn:oasis:names:tc:SAML:2.0:logout:sp-timeout";
}
