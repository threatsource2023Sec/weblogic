package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.EmptyBean;

public interface WeblogicRelationshipRoleBean {
   String getRelationshipRoleName();

   void setRelationshipRoleName(String var1);

   String getGroupName();

   void setGroupName(String var1);

   RelationshipRoleMapBean getRelationshipRoleMap();

   RelationshipRoleMapBean createRelationshipRoleMap();

   void destroyRelationshipRoleMap(RelationshipRoleMapBean var1);

   EmptyBean getDbCascadeDelete();

   EmptyBean createDbCascadeDelete();

   void destroyDbCascadeDelete(EmptyBean var1);

   void setEnableQueryCaching(boolean var1);

   boolean getEnableQueryCaching();

   String getId();

   void setId(String var1);
}
