package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface BaseBearing {
   String XML_BASE_ATTR_LOCAL_NAME = "base";
   QName XML_BASE_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "base", "xml");

   @Nullable
   String getXMLBase();

   void setXMLBase(@Nullable String var1);
}
