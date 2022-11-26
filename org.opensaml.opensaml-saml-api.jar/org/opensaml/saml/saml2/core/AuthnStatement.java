package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;

public interface AuthnStatement extends Statement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml2");
   String TYPE_LOCAL_NAME = "AuthnStatementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatementType", "saml2");
   String AUTHN_INSTANT_ATTRIB_NAME = "AuthnInstant";
   String SESSION_INDEX_ATTRIB_NAME = "SessionIndex";
   String SESSION_NOT_ON_OR_AFTER_ATTRIB_NAME = "SessionNotOnOrAfter";

   DateTime getAuthnInstant();

   void setAuthnInstant(DateTime var1);

   String getSessionIndex();

   void setSessionIndex(String var1);

   DateTime getSessionNotOnOrAfter();

   void setSessionNotOnOrAfter(DateTime var1);

   SubjectLocality getSubjectLocality();

   void setSubjectLocality(SubjectLocality var1);

   AuthnContext getAuthnContext();

   void setAuthnContext(AuthnContext var1);
}
