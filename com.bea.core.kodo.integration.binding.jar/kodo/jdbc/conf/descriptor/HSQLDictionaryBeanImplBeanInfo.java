package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class HSQLDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = HSQLDictionaryBean.class;

   public HSQLDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public HSQLDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.HSQLDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.HSQLDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AutoAssignClause")) {
         getterName = "getAutoAssignClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignClause";
         }

         currentResult = new PropertyDescriptor("AutoAssignClause", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "IDENTITY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoAssignTypeName")) {
         getterName = "getAutoAssignTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignTypeName";
         }

         currentResult = new PropertyDescriptor("AutoAssignTypeName", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "VARBINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheTables")) {
         getterName = "getCacheTables";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTables";
         }

         currentResult = new PropertyDescriptor("CacheTables", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("CacheTables", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClosePoolSQL")) {
         getterName = "getClosePoolSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClosePoolSQL";
         }

         currentResult = new PropertyDescriptor("ClosePoolSQL", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("ClosePoolSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SHUTDOWN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrossJoinClause")) {
         getterName = "getCrossJoinClause";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrossJoinClause";
         }

         currentResult = new PropertyDescriptor("CrossJoinClause", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("CrossJoinClause", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "JOIN");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGeneratedKeyQuery")) {
         getterName = "getLastGeneratedKeyQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLastGeneratedKeyQuery";
         }

         currentResult = new PropertyDescriptor("LastGeneratedKeyQuery", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("LastGeneratedKeyQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "CALL IDENTITY()");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NextSequenceQuery")) {
         getterName = "getNextSequenceQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNextSequenceQuery";
         }

         currentResult = new PropertyDescriptor("NextSequenceQuery", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("NextSequenceQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT NEXT VALUE FOR {0} FROM INFORMATION_SCHEMA.SYSTEM_SEQUENCES");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "HSQL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RangePosition")) {
         getterName = "getRangePosition";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRangePosition";
         }

         currentResult = new PropertyDescriptor("RangePosition", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("RangePosition", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresCastForComparisons")) {
         getterName = "getRequiresCastForComparisons";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresCastForComparisons";
         }

         currentResult = new PropertyDescriptor("RequiresCastForComparisons", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresCastForComparisons", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresCastForMathFunctions")) {
         getterName = "getRequiresCastForMathFunctions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresCastForMathFunctions";
         }

         currentResult = new PropertyDescriptor("RequiresCastForMathFunctions", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresCastForMathFunctions", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresConditionForCrossJoin")) {
         getterName = "getRequiresConditionForCrossJoin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresConditionForCrossJoin";
         }

         currentResult = new PropertyDescriptor("RequiresConditionForCrossJoin", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresConditionForCrossJoin", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StringLengthFunction")) {
         getterName = "getStringLengthFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStringLengthFunction";
         }

         currentResult = new PropertyDescriptor("StringLengthFunction", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("StringLengthFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LENGTH({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAutoAssign")) {
         getterName = "getSupportsAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAutoAssign";
         }

         currentResult = new PropertyDescriptor("SupportsAutoAssign", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsAutoAssign", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDeferredConstraints")) {
         getterName = "getSupportsDeferredConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDeferredConstraints";
         }

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetIndexInfo")) {
         getterName = "getSupportsNullTableForGetIndexInfo";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetIndexInfo";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetIndexInfo", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetIndexInfo", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsNullTableForGetPrimaryKeys")) {
         getterName = "getSupportsNullTableForGetPrimaryKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsNullTableForGetPrimaryKeys";
         }

         currentResult = new PropertyDescriptor("SupportsNullTableForGetPrimaryKeys", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsNullTableForGetPrimaryKeys", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectEndIndex")) {
         getterName = "getSupportsSelectEndIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectEndIndex";
         }

         currentResult = new PropertyDescriptor("SupportsSelectEndIndex", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectEndIndex", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectForUpdate")) {
         getterName = "getSupportsSelectForUpdate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectForUpdate";
         }

         currentResult = new PropertyDescriptor("SupportsSelectForUpdate", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectForUpdate", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsSelectStartIndex")) {
         getterName = "getSupportsSelectStartIndex";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsSelectStartIndex";
         }

         currentResult = new PropertyDescriptor("SupportsSelectStartIndex", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsSelectStartIndex", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimBothFunction")) {
         getterName = "getTrimBothFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimBothFunction";
         }

         currentResult = new PropertyDescriptor("TrimBothFunction", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimBothFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LTRIM(RTRIM({0}))");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimLeadingFunction")) {
         getterName = "getTrimLeadingFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimLeadingFunction";
         }

         currentResult = new PropertyDescriptor("TrimLeadingFunction", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimLeadingFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LTRIM({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrimTrailingFunction")) {
         getterName = "getTrimTrailingFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrimTrailingFunction";
         }

         currentResult = new PropertyDescriptor("TrimTrailingFunction", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("TrimTrailingFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "RTRIM({0})");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetObjectForBlobs")) {
         getterName = "getUseGetObjectForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetObjectForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetObjectForBlobs", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetObjectForBlobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSchemaName")) {
         getterName = "getUseSchemaName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSchemaName";
         }

         currentResult = new PropertyDescriptor("UseSchemaName", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSchemaName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidationSQL")) {
         getterName = "getValidationSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidationSQL";
         }

         currentResult = new PropertyDescriptor("ValidationSQL", HSQLDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "CALL 1");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
