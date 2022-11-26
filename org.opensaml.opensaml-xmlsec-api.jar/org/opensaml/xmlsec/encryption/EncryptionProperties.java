package org.opensaml.xmlsec.encryption;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface EncryptionProperties extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionProperties";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties", "xenc");
   String TYPE_LOCAL_NAME = "EncryptionPropertiesType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionPropertiesType", "xenc");
   String ID_ATTRIB_NAME = "Id";

   @Nullable
   String getID();

   void setID(@Nullable String var1);

   @Nonnull
   List getEncryptionProperties();
}
