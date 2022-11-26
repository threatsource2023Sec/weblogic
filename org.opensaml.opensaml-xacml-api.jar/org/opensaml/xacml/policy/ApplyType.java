package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;

public interface ApplyType extends ExpressionType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Apply";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Apply", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "ApplyType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ApplyType", "xacml");
   String FUNCTION_ID_ATTRIB_NAME = "FunctionId";

   String getFunctionId();

   void setFunctionId(String var1);

   List getExpressions();
}
