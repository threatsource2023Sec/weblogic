package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface PolicyCombinerParametersType extends CombinerParametersType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PolicyCombinerParameters";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyCombinerParameters", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "PolicyCombinerParametersType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyCombinerParametersType", "xacml");
   String POLICY_ID_REF_ATTRIB_NAME = "PolicyIdRef";

   String getPolicyIdRef();

   void setPolicyIdRef(String var1);
}
