package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;

public abstract class XOPMarshaller {
   public static final JavaTypeName JAVA_BYTE_ARRAY_TYPE;
   public static final XmlTypeName XML_BASE64BINARY_TYPE;
   public static final XmlTypeName SOAP_ENC_BASE64_TYPE;

   public abstract void marshalXOP(Object var1, Node var2) throws XmlException;

   public abstract void recordBase64Element(Node var1);

   public boolean canMarshalXOP(BindingTypeName bindingTypeName) {
      return false;
   }

   public boolean isMtomOverSecurity(BindingTypeName bindingTypeName) {
      return false;
   }

   static {
      JAVA_BYTE_ARRAY_TYPE = JavaTypeName.forArray(JavaTypeName.forString(Byte.TYPE.getName()), 1);
      XML_BASE64BINARY_TYPE = XmlTypeName.forTypeNamed(new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
      SOAP_ENC_BASE64_TYPE = XmlTypeName.forTypeNamed(new QName("http://schemas.xmlsoap.org/soap/encoding/", "base64"));
   }
}
