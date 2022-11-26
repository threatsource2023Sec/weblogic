package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;

public interface KeyType extends XSURI, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "KeyType";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType", "wst");
   String TYPE_LOCAL_NAME = "KeyTypeOpenEnum";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyTypeOpenEnum", "wst");
   String PUBLIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey";
   String SYMMETRIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/SymmetricKey";
   String BEARER = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Bearer";
}
