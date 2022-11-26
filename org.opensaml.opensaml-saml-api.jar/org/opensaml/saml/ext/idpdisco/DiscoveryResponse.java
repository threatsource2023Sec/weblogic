package org.opensaml.saml.ext.idpdisco;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;

public interface DiscoveryResponse extends IndexedEndpoint {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DiscoveryResponse";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:profiles:SSO:idp-discovery-protocol", "DiscoveryResponse", "idpdisco");
}
