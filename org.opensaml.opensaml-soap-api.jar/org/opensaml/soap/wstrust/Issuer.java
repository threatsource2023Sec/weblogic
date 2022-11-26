package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsaddressing.EndpointReferenceType;

public interface Issuer extends EndpointReferenceType, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Issuer";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Issuer", "wst");
}
