package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface ReferenceType extends ElementExtensibleXMLObject {
   String TYPE_LOCAL_NAME = "ReferenceType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceType", "xenc");
   String URI_ATTRIB_NAME = "URI";

   @Nullable
   String getURI();

   void setURI(@Nullable String var1);
}
