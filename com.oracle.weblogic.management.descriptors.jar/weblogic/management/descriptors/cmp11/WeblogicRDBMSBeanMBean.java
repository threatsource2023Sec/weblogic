package weblogic.management.descriptors.cmp11;

public interface WeblogicRDBMSBeanMBean extends BaseWeblogicRDBMSBeanMBean {
   String getEJBName();

   void setEJBName(String var1);

   String getPoolName();

   void setPoolName(String var1);

   String getDataSourceName();

   void setDataSourceName(String var1);

   String getTableName();

   void setTableName(String var1);

   FieldMapMBean[] getFieldMaps();

   void setFieldMaps(FieldMapMBean[] var1);

   void addFieldMap(FieldMapMBean var1);

   void removeFieldMap(FieldMapMBean var1);

   FinderMBean[] getFinders();

   void setFinders(FinderMBean[] var1);

   void addFinder(FinderMBean var1);

   void removeFinder(FinderMBean var1);

   boolean getEnableTunedUpdates();

   void setEnableTunedUpdates(boolean var1);
}
