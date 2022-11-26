package org.opensaml.saml.ext.saml2mdrpi;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.LocalizedURI;

public interface RegistrationPolicy extends LocalizedURI {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RegistrationPolicy";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationPolicy", "mdrpi");
}
