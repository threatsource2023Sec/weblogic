package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;

public interface Reason extends XSString, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Reason";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Reason", "wst");
}
