package kodo.jdbc.conf.descriptor;

public interface PersistenceMappingDefaultsBean extends MappingDefaultsBean {
   boolean getUseClassCriteria();

   void setUseClassCriteria(boolean var1);

   String getBaseClassStrategy();

   void setBaseClassStrategy(String var1);

   String getVersionStrategy();

   void setVersionStrategy(String var1);

   String getDiscriminatorColumnName();

   void setDiscriminatorColumnName(String var1);

   String getSubclassStrategy();

   void setSubclassStrategy(String var1);

   boolean getIndexVersion();

   void setIndexVersion(boolean var1);

   boolean getDefaultMissingInfo();

   void setDefaultMissingInfo(boolean var1);

   boolean getIndexLogicalForeignKeys();

   void setIndexLogicalForeignKeys(boolean var1);

   String getNullIndicatorColumnName();

   void setNullIndicatorColumnName(String var1);

   int getForeignKeyDeleteAction();

   void setForeignKeyDeleteAction(int var1);

   String getJoinForeignKeyDeleteAction();

   void setJoinForeignKeyDeleteAction(String var1);

   String getDiscriminatorStrategy();

   void setDiscriminatorStrategy(String var1);

   boolean getDeferConstraints();

   void setDeferConstraints(boolean var1);

   String getFieldStrategies();

   void setFieldStrategies(String var1);

   String getVersionColumnName();

   void setVersionColumnName(String var1);

   String getDataStoreIdColumnName();

   void setDataStoreIdColumnName(String var1);

   boolean getIndexDiscriminator();

   void setIndexDiscriminator(boolean var1);

   boolean getStoreEnumOrdinal();

   void setStoreEnumOrdinal(boolean var1);

   boolean getOrderLists();

   void setOrderLists(boolean var1);

   String getOrderColumnName();

   void setOrderColumnName(String var1);

   boolean getAddNullIndicator();

   void setAddNullIndicator(boolean var1);

   boolean getStoreUnmappedObjectIdString();

   void setStoreUnmappedObjectIdString(boolean var1);
}
