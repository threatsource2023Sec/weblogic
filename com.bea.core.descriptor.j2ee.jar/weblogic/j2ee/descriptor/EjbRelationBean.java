package weblogic.j2ee.descriptor;

public interface EjbRelationBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getEjbRelationName();

   void setEjbRelationName(String var1);

   EjbRelationshipRoleBean[] getEjbRelationshipRoles();

   EjbRelationshipRoleBean createEjbRelationshipRole();

   void destroyEjbRelationshipRole(EjbRelationshipRoleBean var1);

   String getId();

   void setId(String var1);
}
