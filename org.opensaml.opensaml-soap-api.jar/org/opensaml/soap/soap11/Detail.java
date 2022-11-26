package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.common.SOAPObject;

public interface Detail extends SOAPObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "detail";
   QName DEFAULT_ELEMENT_NAME = new QName("detail");
   String TYPE_LOCAL_NAME = "detail";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "detail", "soap11");
}
