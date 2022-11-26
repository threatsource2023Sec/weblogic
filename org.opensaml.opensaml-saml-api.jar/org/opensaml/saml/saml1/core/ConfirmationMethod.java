package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface ConfirmationMethod extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ConfirmationMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod", "saml1");
   String TYPE_LOCAL_NAME = "ConfirmationMethodType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethodType", "saml1");
   String METHOD_BEARER = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
   String METHOD_ARTIFACT = "urn:oasis:names:tc:SAML:1.0:cm:artifact";
   /** @deprecated */
   @Deprecated
   String METHOD_ARTIFACT_DEPRECATED = "urn:oasis:names:tc:SAML:1.0:cm:artifact-01";
   String METHOD_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
   String METHOD_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";

   String getConfirmationMethod();

   void setConfirmationMethod(String var1);
}
