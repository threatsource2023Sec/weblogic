package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Audience extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Audience";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Audience", "saml2");
   String TYPE_LOCAL_NAME = "AudienceType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceType", "saml2");

   String getAudienceURI();

   void setAudienceURI(String var1);
}
