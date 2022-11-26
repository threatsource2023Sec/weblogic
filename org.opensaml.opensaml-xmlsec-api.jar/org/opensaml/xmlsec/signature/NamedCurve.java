package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface NamedCurve extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NamedCurve";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "NamedCurve", "ds11");
   String TYPE_LOCAL_NAME = "NamedCurveType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "NamedCurveType", "ds11");
   String URI_ATTRIB_NAME = "URI";

   @Nullable
   String getURI();

   void setURI(@Nullable String var1);
}
