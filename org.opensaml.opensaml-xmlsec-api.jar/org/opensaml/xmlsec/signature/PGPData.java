package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;

public interface PGPData extends XMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PGPData";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData", "ds");
   String TYPE_LOCAL_NAME = "PGPDataType";
   QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPDataType", "ds");

   @Nullable
   PGPKeyID getPGPKeyID();

   void setPGPKeyID(@Nullable PGPKeyID var1);

   @Nullable
   PGPKeyPacket getPGPKeyPacket();

   void setPGPKeyPacket(@Nullable PGPKeyPacket var1);
}
