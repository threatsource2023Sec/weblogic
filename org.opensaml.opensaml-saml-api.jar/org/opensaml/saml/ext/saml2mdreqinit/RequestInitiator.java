package org.opensaml.saml.ext.saml2mdreqinit;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.Endpoint;

public interface RequestInitiator extends Endpoint {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequestInitiator";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:profiles:SSO:request-init", "RequestInitiator", "init");
   String REQUIRED_BINDING_VALUE = "urn:oasis:names:tc:SAML:profiles:SSO:request-init";
}
