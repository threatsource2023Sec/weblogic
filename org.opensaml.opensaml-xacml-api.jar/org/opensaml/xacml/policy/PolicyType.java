package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface PolicyType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Policy";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Policy", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "PolicyType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicyType", "xacml");
   String POLICY_ID_ATTRIB_NAME = "PolicyId";
   String VERSION_ATTRIB_NAME = "Version";
   String VERSION_DEFAULT_VALUE = "1.0";
   String RULE_COMBINING_ALG_ID_ATTRIB_NAME = "RuleCombiningAlgId";

   DescriptionType getDescription();

   void setDescription(DescriptionType var1);

   DefaultsType getPolicyDefaults();

   void setPolicyDefaults(DefaultsType var1);

   TargetType getTarget();

   void setTarget(TargetType var1);

   List getCombinerParameters();

   List getRuleCombinerParameters();

   List getVariableDefinitions();

   List getRules();

   ObligationsType getObligations();

   void setObligations(ObligationsType var1);

   String getPolicyId();

   void setPolicyId(String var1);

   String getVersion();

   void setVersion(String var1);

   String getRuleCombiningAlgoId();

   void setRuleCombiningAlgoId(String var1);
}
