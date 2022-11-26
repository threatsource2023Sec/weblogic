package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSURI;

public interface AttributedURI extends XSURI, AttributeExtensibleXMLObject, WSAddressingObject {
   String TYPE_LOCAL_NAME = "AttributedURIType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "AttributedURIType", "wsa");
}
