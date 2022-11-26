package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface EncryptionProperty extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionProperty";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty", "xenc");
   String TYPE_LOCAL_NAME = "EncryptionPropertyType";
   QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionPropertyType", "xenc");
   String TARGET_ATTRIB_NAME = "Target";
   String ID_ATTRIB_NAME = "Id";

   @Nullable
   String getTarget();

   void setTarget(@Nullable String var1);

   @Nullable
   String getID();

   void setID(@Nullable String var1);
}
