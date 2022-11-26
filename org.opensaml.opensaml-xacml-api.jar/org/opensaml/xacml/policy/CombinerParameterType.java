package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface CombinerParameterType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "CombinerParameter";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "CombinerParameter", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "CombinerParameterType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "CombinerParameterType", "xacml");
   String PARAMETER_NAMEATTRIB_NAME = "ParameterName";

   AttributeValueType getAttributeValue();

   void setAttributeValue(AttributeValueType var1);

   String getParameterName();

   void setParameterName(String var1);
}
