package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface MustUnderstandBearing {
   String SOAP12_MUST_UNDERSTAND_ATTR_LOCAL_NAME = "mustUnderstand";
   QName SOAP12_MUST_UNDERSTAND_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "mustUnderstand", "soap12");

   @Nullable
   Boolean isSOAP12MustUnderstand();

   @Nullable
   XSBooleanValue isSOAP12MustUnderstandXSBoolean();

   void setSOAP12MustUnderstand(@Nullable Boolean var1);

   void setSOAP12MustUnderstand(@Nullable XSBooleanValue var1);
}
