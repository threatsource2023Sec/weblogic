package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface PolicySetCombinerParametersType extends CombinerParametersType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PolicySetCombinerParameters";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetCombinerParameters", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "PolicySetCombinerParametersType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetCombinerParametersType", "xacml");
   String POLICY_SET_ID_REF_ATTRIB_NAME = "PolicySetIdRef";

   String getPolicySetIdRef();

   void setPolicySetIdRef(String var1);
}
