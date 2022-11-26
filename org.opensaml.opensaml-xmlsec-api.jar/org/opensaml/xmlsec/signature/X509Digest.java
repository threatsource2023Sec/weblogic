package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface X509Digest extends XSBase64Binary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "X509Digest";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "X509Digest", "ds11");
   String TYPE_LOCAL_NAME = "X509DigestType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "X509DigestType", "ds11");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);
}
