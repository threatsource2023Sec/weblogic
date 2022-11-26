package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;

public interface VariableReferenceType extends ExpressionType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "VariableReference";
   QName DEFAULT_ELEMENT_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableReference", "xacml");
   String TYPE_LOCAL_NAME = "VariableReferenceType";
   QName TYPE_NAME_XACML20 = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "VariableReferenceType", "xacml");
   String VARIABLE_ID_ATTRIB_NAME = "VariableId";

   List getExpressions();

   String getVariableId();

   void setVariableId(String var1);
}
