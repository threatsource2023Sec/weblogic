package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Entropy extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Entropy";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Entropy", "wst");
   String TYPE_LOCAL_NAME = "EntropyType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EntropyType", "wst");
   String DIALECT_ATTRIB_NAME = "EntropyType";
}
