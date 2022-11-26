package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface LangBearing {
   String XML_LANG_ATTR_LOCAL_NAME = "lang";
   QName XML_LANG_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "lang", "xml");

   @Nullable
   String getXMLLang();

   void setXMLLang(@Nullable String var1);
}
