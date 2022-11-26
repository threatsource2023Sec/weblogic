package weblogic.j2ee.descriptor;

public interface RelationshipsBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   EjbRelationBean[] getEjbRelations();

   EjbRelationBean createEjbRelation();

   void destroyEjbRelation(EjbRelationBean var1);

   String getId();

   void setId(String var1);
}
