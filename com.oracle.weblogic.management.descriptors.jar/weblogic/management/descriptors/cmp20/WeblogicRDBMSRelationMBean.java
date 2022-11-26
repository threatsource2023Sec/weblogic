package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface WeblogicRDBMSRelationMBean extends XMLElementMBean {
   String getRelationName();

   void setRelationName(String var1);

   String getTableName();

   void setTableName(String var1);

   WeblogicRelationshipRoleMBean[] getWeblogicRelationshipRoles();

   void setWeblogicRelationshipRoles(WeblogicRelationshipRoleMBean[] var1);

   void addWeblogicRelationshipRole(WeblogicRelationshipRoleMBean var1);

   void removeWeblogicRelationshipRole(WeblogicRelationshipRoleMBean var1);
}
