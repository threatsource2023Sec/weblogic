package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface ReferenceParameters extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSAddressingObject {
   String ELEMENT_LOCAL_NAME = "ReferenceParameters";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "ReferenceParameters", "wsa");
   String TYPE_LOCAL_NAME = "ReferenceParametersType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "ReferenceParametersType", "wsa");
}
