package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface KeyValue extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "KeyValue";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue", "ds");
   String TYPE_LOCAL_NAME = "KeyValueType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValueType", "ds");

   @Nullable
   DSAKeyValue getDSAKeyValue();

   void setDSAKeyValue(@Nullable DSAKeyValue var1);

   @Nullable
   RSAKeyValue getRSAKeyValue();

   void setRSAKeyValue(@Nullable RSAKeyValue var1);

   @Nullable
   ECKeyValue getECKeyValue();

   void setECKeyValue(@Nullable ECKeyValue var1);

   @Nullable
   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(@Nullable XMLObject var1);
}
