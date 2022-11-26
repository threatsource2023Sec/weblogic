package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBRelationshipRoleMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBRelationshipRoleName();

   void setEJBRelationshipRoleName(String var1);

   void setMultiplicity(String var1);

   String getMultiplicity();

   boolean getCascadeDelete();

   void setCascadeDelete(boolean var1);

   RelationshipRoleSourceMBean getRelationshipRoleSource();

   void setRelationshipRoleSource(RelationshipRoleSourceMBean var1);

   CMRFieldMBean getCMRField();

   void setCMRField(CMRFieldMBean var1);
}
