package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface IdBearing {
   String XML_ID_ATTR_LOCAL_NAME = "id";
   QName XML_ID_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "id", "xml");

   @Nullable
   String getXMLId();

   void setXMLId(@Nullable String var1);
}
