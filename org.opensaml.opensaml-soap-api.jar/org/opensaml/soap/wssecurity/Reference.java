package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;

public interface Reference extends AttributeExtensibleXMLObject, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "Reference";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Reference", "wsse");
   String TYPE_LOCAL_NAME = "ReferenceType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "ReferenceType", "wsse");
   String URI_ATTRIB_NAME = "URI";
   String VALUE_TYPE_ATTRIB_NAME = "ValueType";

   String getURI();

   void setURI(String var1);

   String getValueType();

   void setValueType(String var1);
}
