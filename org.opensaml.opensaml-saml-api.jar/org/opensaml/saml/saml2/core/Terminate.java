package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Terminate extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Terminate";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Terminate", "saml2p");
   String TYPE_LOCAL_NAME = "TerminateType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "TerminateType", "saml2p");
}
