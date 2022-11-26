package weblogic.j2ee.descriptor.wl;

public interface RelationshipRoleMapBean {
   String getForeignKeyTable();

   void setForeignKeyTable(String var1);

   String getPrimaryKeyTable();

   void setPrimaryKeyTable(String var1);

   ColumnMapBean[] getColumnMaps();

   ColumnMapBean createColumnMap();

   void destroyColumnMap(ColumnMapBean var1);

   String getId();

   void setId(String var1);
}
