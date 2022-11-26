package org.opensaml.soap.soap11;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSQName;
import org.opensaml.soap.common.SOAPObject;

public interface FaultCode extends SOAPObject, XSQName {
   String DEFAULT_ELEMENT_LOCAL_NAME = "faultcode";
   QName DEFAULT_ELEMENT_NAME = new QName("faultcode");
   QName VERSION_MISMATCH = new QName("http://schemas.xmlsoap.org/soap/envelope/", "VersionMismatch", "soap11");
   QName MUST_UNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand", "soap11");
   QName SERVER = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server", "soap11");
   QName CLIENT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client", "soap11");
}
