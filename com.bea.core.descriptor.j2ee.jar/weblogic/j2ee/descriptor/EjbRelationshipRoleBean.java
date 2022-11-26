package weblogic.j2ee.descriptor;

public interface EjbRelationshipRoleBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getEjbRelationshipRoleName();

   void setEjbRelationshipRoleName(String var1);

   String getMultiplicity();

   void setMultiplicity(String var1);

   EmptyBean getCascadeDelete();

   EmptyBean createCascadeDelete();

   void destroyCascadeDelete(EmptyBean var1);

   RelationshipRoleSourceBean getRelationshipRoleSource();

   RelationshipRoleSourceBean createRelationshipRoleSource();

   void destroyRelationshipRoleSource(RelationshipRoleSourceBean var1);

   CmrFieldBean getCmrField();

   CmrFieldBean createCmrField();

   void destroyCmrField(CmrFieldBean var1);

   String getId();

   void setId(String var1);
}
