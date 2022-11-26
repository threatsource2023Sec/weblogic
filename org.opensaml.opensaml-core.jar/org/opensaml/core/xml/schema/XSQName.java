package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSQName extends XMLObject {
   String TYPE_LOCAL_NAME = "QName";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "QName", "xsd");

   @Nullable
   QName getValue();

   void setValue(@Nullable QName var1);
}
