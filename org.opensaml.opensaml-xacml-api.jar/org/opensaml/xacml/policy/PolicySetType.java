package org.opensaml.xacml.policy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.XACMLObject;

public interface PolicySetType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "PolicySet";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySet", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "PolicySetType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "PolicySetType", "xacml");
   String POLICY_SET_ID_ATTRIB_NAME = "PolicySetId";
   String VERSION_ATTRIB_NAME = "Version";
   String DEFAULT_VERSION = "1.0";
   String POLICY_COMBINING_ALG_ID_ATTRIB_NAME = "PolicyCombiningAlgId";

   DescriptionType getDescription();

   void setDescription(DescriptionType var1);

   IndexedXMLObjectChildrenList getPolicyChoiceGroup();

   DefaultsType getPolicySetDefaults();

   void setPolicySetDefaults(DefaultsType var1);

   TargetType getTarget();

   void setTarget(TargetType var1);

   List getPolicySets();

   List getPolicies();

   List getPolicySetIdReferences();

   List getPolicyIdReferences();

   List getCombinerParameters();

   List getPolicyCombinerParameters();

   List getPolicySetCombinerParameters();

   ObligationsType getObligations();

   void setObligations(ObligationsType var1);

   String getPolicySetId();

   void setPolicySetId(String var1);

   String getVersion();

   void setVersion(String var1);

   String getPolicyCombiningAlgoId();

   void setPolicyCombiningAlgoId(String var1);
}
