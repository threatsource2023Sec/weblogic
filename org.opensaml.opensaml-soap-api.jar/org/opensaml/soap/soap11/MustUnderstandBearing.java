package org.opensaml.soap.soap11;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface MustUnderstandBearing {
   String SOAP11_MUST_UNDERSTAND_ATTR_LOCAL_NAME = "mustUnderstand";
   QName SOAP11_MUST_UNDERSTAND_ATTR_NAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand", "soap11");

   @Nullable
   Boolean isSOAP11MustUnderstand();

   @Nullable
   XSBooleanValue isSOAP11MustUnderstandXSBoolean();

   void setSOAP11MustUnderstand(@Nullable Boolean var1);

   void setSOAP11MustUnderstand(@Nullable XSBooleanValue var1);
}
