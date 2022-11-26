package org.opensaml.xacml.policy;

import javax.xml.namespace.QName;
import org.opensaml.xacml.XACMLObject;

public interface TargetType extends XACMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Target";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "Target", "xacml");
   String SCHEMA_TYPE_LOCAL_NAME = "TargetType";
   QName SCHEMA_TYPE_NAME = new QName("urn:oasis:names:tc:xacml:2.0:policy:schema:os", "TargetType", "xacml");

   SubjectsType getSubjects();

   ResourcesType getResources();

   ActionsType getActions();

   EnvironmentsType getEnvironments();

   void setSubjects(SubjectsType var1);

   void setActions(ActionsType var1);

   void setResources(ResourcesType var1);

   void setEnvironments(EnvironmentsType var1);
}
