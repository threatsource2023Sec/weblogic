package org.opensaml.soap.soap12;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface RelayBearing {
   String SOAP12_RELAY_ATTR_LOCAL_NAME = "relay";
   QName SOAP12_RELAY_ATTR_NAME = new QName("http://www.w3.org/2003/05/soap-envelope", "relay", "soap12");

   @Nullable
   Boolean isSOAP12Relay();

   @Nullable
   XSBooleanValue isSOAP12RelayXSBoolean();

   void setSOAP12Relay(@Nullable Boolean var1);

   void setSOAP12Relay(@Nullable XSBooleanValue var1);
}
