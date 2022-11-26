package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface RetrievalMethod extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RetrievalMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod", "ds");
   String TYPE_LOCAL_NAME = "RetrievalMethodType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethodType", "ds");
   String URI_ATTRIB_NAME = "URI";
   String TYPE_ATTRIB_NAME = "Type";

   @Nullable
   String getURI();

   void setURI(@Nullable String var1);

   @Nullable
   String getType();

   void setType(@Nullable String var1);

   @Nullable
   Transforms getTransforms();

   void setTransforms(@Nullable Transforms var1);
}
