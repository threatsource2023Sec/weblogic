package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.common.SOAPObject;

public interface Body extends SOAPObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Body";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body", "soap11");
   String TYPE_LOCAL_NAME = "Body";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body", "soap11");
}
