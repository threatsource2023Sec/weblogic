package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface SignatureConfirmation extends IdBearing, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "SignatureConfirmation";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmation", "wsse11");
   String TYPE_LOCAL_NAME = "SignatureConfirmationType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "SignatureConfirmationType", "wsse11");
   String VALUE_ATTRIB_NAME = "Value";

   String getValue();

   void setValue(String var1);
}
