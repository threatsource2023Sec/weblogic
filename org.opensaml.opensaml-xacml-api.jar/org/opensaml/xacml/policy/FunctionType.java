package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface FunctionType extends ExpressionType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Function";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Function", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "FunctionType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "FunctionType", "xacml");
   String FUNCTION_ID_ATTRIB_NAME = "FunctionId";

   String getFunctionId();

   void setFunctionId(String var1);
}
