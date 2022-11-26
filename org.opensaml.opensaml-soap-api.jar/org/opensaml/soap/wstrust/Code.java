package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;

public interface Code extends XSURI, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Code";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Code", "wst");
   String TYPE_LOCAL_NAME = "StatusCodeOpenEnum";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "StatusCodeOpenEnum", "wst");
   String VALID = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/status/valid";
   String INVALID = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/status/invalid";
}
