package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSString extends XMLObject {
   String TYPE_LOCAL_NAME = "string";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "string", "xsd");

   @Nullable
   String getValue();

   void setValue(@Nullable String var1);
}
