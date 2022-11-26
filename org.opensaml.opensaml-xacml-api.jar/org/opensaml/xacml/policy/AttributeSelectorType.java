package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;

public interface AttributeSelectorType extends ExpressionType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeSelector";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeSelector", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "AttributeSelectorType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "AttributeSelectorType", "xacml");
   String REQUEST_CONTEXT_PATH_ATTRIB_NAME = "RequestContextPath";
   String DATA_TYPE_ATTRIB_NAME = "DataType";
   String MUST_BE_PRESENT_ATTRIB_NAME = "MustBePresent";

   String getRequestContextPath();

   void setRequestContextPath(String var1);

   String getDataType();

   void setDataType(String var1);

   Boolean getMustBePresent();

   XSBooleanValue getMustBePresentXSBoolean();

   void setMustBePresent(Boolean var1);

   void setMustBePresentXSBoolean(XSBooleanValue var1);
}
