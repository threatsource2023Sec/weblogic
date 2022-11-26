package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface OnBehalfOf extends ElementExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "OnBehalfOf";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOf", "wst");
   String TYPE_LOCAL_NAME = "OnBehalfOfType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOfType", "wst");
}
