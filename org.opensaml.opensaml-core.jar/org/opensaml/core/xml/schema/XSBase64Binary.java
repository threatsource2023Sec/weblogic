package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface XSBase64Binary extends XMLObject {
   String TYPE_LOCAL_NAME = "base64Binary";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "base64Binary", "xsd");

   @Nullable
   String getValue();

   void setValue(@Nullable String var1);
}
