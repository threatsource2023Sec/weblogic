package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface KeyInfoReference extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "KeyInfoReference";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "KeyInfoReference", "ds11");
   String TYPE_LOCAL_NAME = "KeyInfoReferenceType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "KeyInfoReferenceType", "ds11");
   String ID_ATTRIB_NAME = "Id";
   String URI_ATTRIB_NAME = "URI";

   @Nullable
   String getID();

   void setID(@Nullable String var1);

   @Nullable
   String getURI();

   void setURI(@Nullable String var1);
}
