package weblogic.j2ee.descriptor.wl;

public interface CompatibilityBean {
   boolean isSerializeByteArrayToOracleBlob();

   void setSerializeByteArrayToOracleBlob(boolean var1);

   boolean isSerializeCharArrayToBytes();

   void setSerializeCharArrayToBytes(boolean var1);

   boolean isAllowReadonlyCreateAndRemove();

   void setAllowReadonlyCreateAndRemove(boolean var1);

   boolean isDisableStringTrimming();

   void setDisableStringTrimming(boolean var1);

   boolean isFindersReturnNulls();

   void setFindersReturnNulls(boolean var1);

   boolean isLoadRelatedBeansFromDbInPostCreate();

   void setLoadRelatedBeansFromDbInPostCreate(boolean var1);

   String getId();

   void setId(String var1);
}
