package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface RuleType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Rule";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Rule", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "RuleType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "RuleType", "xacml");
   String RULE_ID_ATTRIB_NAME = "RuleId";
   String EFFECT_ATTRIB_NAME = "Effect";

   DescriptionType getDescription();

   void setDescription(DescriptionType var1);

   TargetType getTarget();

   void setTarget(TargetType var1);

   ConditionType getCondition();

   void setCondition(ConditionType var1);

   String getRuleId();

   void setRuleId(String var1);

   EffectType getEffect();

   void setEffect(EffectType var1);
}
