package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface KeyExchangeToken extends ElementExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "KeyExchangeToken";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyExchangeToken", "wst");
   String TYPE_LOCAL_NAME = "KeyExchangeTokenType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyExchangeTokenType", "wst");
}
