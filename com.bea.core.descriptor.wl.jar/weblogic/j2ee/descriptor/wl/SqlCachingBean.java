package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.EmptyBean;

public interface SqlCachingBean {
   String getDescription();

   void setDescription(String var1);

   String getSqlCachingName();

   void setSqlCachingName(String var1);

   EmptyBean[] getResultColumns();

   EmptyBean createResultColumn();

   void destroyResultColumn(EmptyBean var1);

   TableBean[] getTables();

   TableBean createTable();

   void destroyTable(TableBean var1);
}
