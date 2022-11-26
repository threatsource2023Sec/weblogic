package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;

public interface TokenType extends XSURI, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "TokenType";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "TokenType", "wst");
   String STATUS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/Status";
}
