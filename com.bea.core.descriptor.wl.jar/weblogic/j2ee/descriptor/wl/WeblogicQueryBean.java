package weblogic.j2ee.descriptor.wl;

public interface WeblogicQueryBean {
   String getDescription();

   void setDescription(String var1);

   QueryMethodBean getQueryMethod();

   QueryMethodBean createQueryMethod();

   void destroyQueryMethod(QueryMethodBean var1);

   EjbQlQueryBean getEjbQlQuery();

   EjbQlQueryBean createEjbQlQuery();

   void destroyEjbQlQuery(EjbQlQueryBean var1);

   SqlQueryBean getSqlQuery();

   SqlQueryBean createSqlQuery();

   void destroySqlQuery(SqlQueryBean var1);

   int getMaxElements();

   void setMaxElements(int var1);

   boolean isIncludeUpdates();

   boolean isIncludeUpdatesSet();

   void setIncludeUpdates(boolean var1);

   boolean isSqlSelectDistinct();

   void setSqlSelectDistinct(boolean var1);

   String getId();

   void setId(String var1);

   boolean getEnableQueryCaching();

   void setEnableQueryCaching(boolean var1);

   boolean getEnableEagerRefresh();

   void setEnableEagerRefresh(boolean var1);

   boolean isIncludeResultCacheHint();

   void setIncludeResultCacheHint(boolean var1);
}
