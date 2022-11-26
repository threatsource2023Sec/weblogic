package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface VariableDefinitionType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "VariableDefinition";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableDefinition", "xacml");
   String TYPE_LOCAL_NAME = "VariableDefinitionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableDefinitionType", "xacml");
   String VARIABLE_ID_ATTRIB_NAME = "VariableId";

   ExpressionType getExpression();

   void setExpression(ExpressionType var1);

   String getVariableId();

   void setVariableId(String var1);
}
