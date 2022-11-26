package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface CombinedHash extends XSBase64Binary, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "CombinedHash";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CombinedHash", "wst");
   String AUTH_HASH = "AUTH-HASH";
}
