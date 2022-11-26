package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface AttributeValueType extends ExpressionType, AttributeExtensibleXMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeValue";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeValue", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "AttributeValueType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeValueType", "xacml");
   String DATA_TYPE_ATTRIB_NAME = "DataType";

   String getDataType();

   void setDataType(String var1);

   String getValue();

   void setValue(String var1);
}
