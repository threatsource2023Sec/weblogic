package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Metadata extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSAddressingObject {
   String ELEMENT_LOCAL_NAME = "Metadata";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "Metadata", "wsa");
   String TYPE_LOCAL_NAME = "MetadataType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "MetadataType", "wsa");
}
