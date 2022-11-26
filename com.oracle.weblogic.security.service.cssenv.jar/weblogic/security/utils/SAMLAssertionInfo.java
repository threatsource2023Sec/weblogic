package weblogic.security.utils;

import java.util.Date;
import org.w3c.dom.Element;

public interface SAMLAssertionInfo {
   String SUBJ_CONF_ARTIFACT = "urn:oasis:names:tc:SAML:1.0:cm:artifact";
   String SUBJ_CONF_BEARER = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
   String SUBJ_CONF_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";
   String SUBJ_CONF_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
   String SAML2_SUBJ_CONF_ARTIFACT = "urn:oasis:names:tc:SAML:2.0:cm:artifact";
   String SAML2_SUBJ_CONF_BEARER = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
   String SAML2_SUBJ_CONF_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
   String SAML2_SUBJ_CONF_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
   String SAML2_VERSION_ATTR_NAME = "Version";
   String SAML1_MAJOR_VERSION_ATTR_NAME = "MajorVersion";
   String SAML1_MINOR_VERSION_ATTR_NAME = "MinorVersion";

   String getId();

   String getSubjectName();

   String getSubjectConfirmationMethod();

   Element getSubjectKeyInfo();

   String getVersion();

   Date getNotBefore();

   Date getNotOnOrAfter();
}
