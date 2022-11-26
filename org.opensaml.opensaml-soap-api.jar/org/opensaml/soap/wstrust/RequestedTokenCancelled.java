package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface RequestedTokenCancelled extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestedTokenCancelled";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedTokenCancelled", "wst");
   String TYPE_LOCAL_NAME = "RequestedTokenCancelledType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedTokenCancelledType", "wst");
}
