package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSInteger;

public interface KeySize extends XSInteger, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "KeySize";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeySize", "wst");
}
