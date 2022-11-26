package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface EncryptionMethod extends ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod", "xenc");
   String TYPE_LOCAL_NAME = "EncryptionMethodType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethodType", "xenc");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);

   @Nullable
   KeySize getKeySize();

   void setKeySize(@Nullable KeySize var1);

   @Nullable
   OAEPparams getOAEPparams();

   void setOAEPparams(@Nullable OAEPparams var1);
}
