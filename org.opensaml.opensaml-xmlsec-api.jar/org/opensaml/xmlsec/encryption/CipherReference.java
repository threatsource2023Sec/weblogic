package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface CipherReference extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "CipherReference";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReference", "xenc");
   String TYPE_LOCAL_NAME = "CipherReferenceType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReferenceType", "xenc");
   String URI_ATTRIB_NAME = "URI";

   @Nullable
   String getURI();

   void setURI(@Nullable String var1);

   @Nullable
   Transforms getTransforms();

   void setTransforms(@Nullable Transforms var1);
}
