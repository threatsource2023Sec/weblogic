package org.opensaml.soap.soap11;

import java.util.List;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface EncodingStyleBearing {
   String SOAP11_ENCODING_STYLE_ATTR_LOCAL_NAME = "encodingStyle";
   QName SOAP11_ENCODING_STYLE_ATTR_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle", "soap11");

   @Nullable
   List getSOAP11EncodingStyles();

   void setSOAP11EncodingStyles(@Nullable List var1);
}
