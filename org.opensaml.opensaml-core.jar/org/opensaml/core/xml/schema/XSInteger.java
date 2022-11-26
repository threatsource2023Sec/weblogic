package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSInteger extends XMLObject {
   String TYPE_LOCAL_NAME = "integer";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer", "xsd");

   @Nullable
   Integer getValue();

   void setValue(@Nullable Integer var1);
}
