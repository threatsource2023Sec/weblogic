package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.LocalizedURI;

public interface PrivacyStatementURL extends LocalizedURI {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PrivacyStatementURL";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "PrivacyStatementURL", "mdui");
}
