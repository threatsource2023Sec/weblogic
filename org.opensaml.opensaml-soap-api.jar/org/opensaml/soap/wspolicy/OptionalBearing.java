package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface OptionalBearing {
   String WSP_OPTIONAL_ATTR_LOCAL_NAME = "Optional";
   QName WSP_OPTIONAL_ATTR_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "Optional", "wsp");

   Boolean isWSP12Optional();

   XSBooleanValue isWSP12OptionalXSBoolean();

   void setWSP12Optional(Boolean var1);

   void setWSP12Optional(XSBooleanValue var1);
}
