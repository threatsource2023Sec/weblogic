package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.common.SOAPObject;

public interface Envelope extends SOAPObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Envelope";
   QName DEFAULT_ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope", "soap11");
   String TYPE_LOCAL_NAME = "Envelope";
   QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Envelope", "soap11");

   @Nullable
   Header getHeader();

   void setHeader(@Nullable Header var1);

   @Nullable
   Body getBody();

   void setBody(@Nullable Body var1);
}
