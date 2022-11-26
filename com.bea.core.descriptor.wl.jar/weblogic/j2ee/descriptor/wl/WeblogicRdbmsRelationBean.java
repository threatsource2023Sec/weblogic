package weblogic.j2ee.descriptor.wl;

public interface WeblogicRdbmsRelationBean {
   String getRelationName();

   void setRelationName(String var1);

   String getTableName();

   void setTableName(String var1);

   WeblogicRelationshipRoleBean[] getWeblogicRelationshipRoles();

   WeblogicRelationshipRoleBean createWeblogicRelationshipRole();

   void destroyWeblogicRelationshipRole(WeblogicRelationshipRoleBean var1);

   String getId();

   void setId(String var1);
}
