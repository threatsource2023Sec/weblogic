package weblogic.j2ee.descriptor.wl;

public interface SqlShapeBean {
   String getDescription();

   void setDescription(String var1);

   String getSqlShapeName();

   void setSqlShapeName(String var1);

   TableBean[] getTables();

   TableBean createTable();

   void destroyTable(TableBean var1);

   int getPassThroughColumns();

   void setPassThroughColumns(int var1);

   String[] getEjbRelationNames();

   void addEjbRelationName(String var1);

   void removeEjbRelationName(String var1);

   void setEjbRelationNames(String[] var1);
}
