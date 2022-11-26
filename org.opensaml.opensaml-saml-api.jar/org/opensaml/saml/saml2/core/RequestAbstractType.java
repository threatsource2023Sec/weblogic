package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;

public interface RequestAbstractType extends SignableSAMLObject {
   String TYPE_LOCAL_NAME = "RequestAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "RequestAbstractType", "saml2p");
   String ID_ATTRIB_NAME = "ID";
   String VERSION_ATTRIB_NAME = "Version";
   String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";
   String DESTINATION_ATTRIB_NAME = "Destination";
   String CONSENT_ATTRIB_NAME = "Consent";
   String UNSPECIFIED_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:unspecified";
   String OBTAINED_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:obtained";
   String PRIOR_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:prior";
   String IMPLICIT_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:current-implicit";
   String EXPLICIT_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:current-explicit";
   String UNAVAILABLE_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:unavailable";
   String INAPPLICABLE_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:inapplicable";

   SAMLVersion getVersion();

   void setVersion(SAMLVersion var1);

   String getID();

   void setID(String var1);

   DateTime getIssueInstant();

   void setIssueInstant(DateTime var1);

   String getDestination();

   void setDestination(String var1);

   String getConsent();

   void setConsent(String var1);

   Issuer getIssuer();

   void setIssuer(Issuer var1);

   Extensions getExtensions();

   void setExtensions(Extensions var1);
}
