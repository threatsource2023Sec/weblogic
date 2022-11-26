package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.soap.common.SOAPObject;

public interface FaultString extends SOAPObject, XSString {
   String DEFAULT_ELEMENT_LOCAL_NAME = "faultstring";
   QName DEFAULT_ELEMENT_NAME = new QName("faultstring");
}
