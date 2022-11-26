package weblogic.j2ee.descriptor.wl;

public interface SqlQueryBean {
   String getSqlShapeName();

   void setSqlShapeName(String var1);

   String getSql();

   void setSql(String var1);

   DatabaseSpecificSqlBean[] getDatabaseSpecificSqls();

   DatabaseSpecificSqlBean createDatabaseSpecificSql();

   void destroyDatabaseSpecificSql(DatabaseSpecificSqlBean var1);
}
