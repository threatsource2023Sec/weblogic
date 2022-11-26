package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.common.SOAPObject;

public interface FaultActor extends SOAPObject, XSURI {
   String DEFAULT_ELEMENT_LOCAL_NAME = "faultactor";
   QName DEFAULT_ELEMENT_NAME = new QName("faultactor");
}
