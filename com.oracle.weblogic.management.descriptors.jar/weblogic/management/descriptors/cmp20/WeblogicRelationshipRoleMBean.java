package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface WeblogicRelationshipRoleMBean extends XMLElementMBean {
   String getRelationshipRoleName();

   void setRelationshipRoleName(String var1);

   String getGroupName();

   void setGroupName(String var1);

   RelationshipRoleMapMBean getRelationshipRoleMap();

   void setRelationshipRoleMap(RelationshipRoleMapMBean var1);

   boolean getDBCascadeDelete();

   void setDBCascadeDelete(boolean var1);
}
