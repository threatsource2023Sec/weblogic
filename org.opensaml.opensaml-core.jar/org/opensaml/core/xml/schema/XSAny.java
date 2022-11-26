package org.opensaml.core.xml.schema;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface XSAny extends ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String TYPE_LOCAL_NAME = "anyType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "anyType", "xsd");

   @Nullable
   String getTextContent();

   void setTextContent(@Nullable String var1);
}
