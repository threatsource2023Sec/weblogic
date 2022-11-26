package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;

public interface RuleCombinerParametersType extends CombinerParametersType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RuleCombinerParameters";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "RuleCombinerParameters", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "RuleCombinerParametersType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "RuleCombinerParametersType", "xacml");
   String RULE_ID_REF_ATTRIB_NAME = "RuleIdRef";

   String getRuleIdRef();

   void setRuleIdRef(String var1);
}
