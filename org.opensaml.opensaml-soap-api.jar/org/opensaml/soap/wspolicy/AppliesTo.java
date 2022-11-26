package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface AppliesTo extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSPolicyObject {
   String ELEMENT_LOCAL_NAME = "AppliesTo";
   QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "AppliesTo", "wsp");
}
