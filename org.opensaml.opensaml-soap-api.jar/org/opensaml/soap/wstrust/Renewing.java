package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface Renewing extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Renewing";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Renewing", "wst");
   String TYPE_LOCAL_NAME = "RenewingType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewingType", "wst");
   String ALLOW_ATTRIB_NAME = "Allow";
   String OK_ATTRIB_NAME = "OK";

   Boolean isAllow();

   XSBooleanValue isAllowXSBoolean();

   void setAllow(Boolean var1);

   void setAllow(XSBooleanValue var1);

   Boolean isOK();

   XSBooleanValue isOKXSBoolean();

   void setOK(Boolean var1);

   void setOK(XSBooleanValue var1);
}
