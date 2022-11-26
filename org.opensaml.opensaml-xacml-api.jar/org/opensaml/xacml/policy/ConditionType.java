package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface ConditionType extends ExpressionType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Condition";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Condition", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "ConditionType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "ConditionType", "xacml");

   ExpressionType getExpression();

   void setExpression(ExpressionType var1);
}
