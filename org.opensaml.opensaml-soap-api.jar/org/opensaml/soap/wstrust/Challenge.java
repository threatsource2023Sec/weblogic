package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;

public interface Challenge extends XSString, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Challenge";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Challenge", "wst");
}
