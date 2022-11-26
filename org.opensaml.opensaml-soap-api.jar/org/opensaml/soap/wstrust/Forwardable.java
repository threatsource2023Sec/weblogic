package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBoolean;

public interface Forwardable extends XSBoolean, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Forwardable";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Forwardable", "wst");
}
