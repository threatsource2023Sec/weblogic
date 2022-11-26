package weblogic.j2ee.descriptor.wl;

public interface TableBean {
   String getTableName();

   void setTableName(String var1);

   String[] getDbmsColumns();

   void addDbmsColumn(String var1);

   void removeDbmsColumn(String var1);

   void setDbmsColumns(String[] var1);

   String getEjbRelationshipRoleName();

   void setEjbRelationshipRoleName(String var1);
}
