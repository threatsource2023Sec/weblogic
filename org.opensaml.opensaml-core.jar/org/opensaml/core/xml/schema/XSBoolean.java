package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSBoolean extends XMLObject {
   String TYPE_LOCAL_NAME = "boolean";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "boolean", "xsd");

   @Nullable
   XSBooleanValue getValue();

   void setValue(@Nullable XSBooleanValue var1);
}
