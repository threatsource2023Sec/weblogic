package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;

public interface ComputedKey extends XSURI, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "ComputedKey";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKey", "wst");
   String TYPE_LOCAL_NAME = "ComputedKeyOpenEnum";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKeyOpenEnum", "wst");
   String PSHA1 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/CK/PSHA1";
   String HASH = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/CK/HASH";
}
