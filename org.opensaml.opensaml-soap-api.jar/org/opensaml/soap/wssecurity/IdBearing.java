package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface IdBearing {
   String WSU_ID_ATTR_LOCAL_NAME = "Id";
   QName WSU_ID_ATTR_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");

   String getWSUId();

   void setWSUId(String var1);
}
