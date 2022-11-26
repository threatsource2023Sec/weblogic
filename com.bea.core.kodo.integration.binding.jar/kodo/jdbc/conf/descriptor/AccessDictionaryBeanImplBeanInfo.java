package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class AccessDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = AccessDictionaryBean.class;

   public AccessDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AccessDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.AccessDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AutoAssignTypeName")) {
         getterName = "getAutoAssignTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoAssignTypeName";
         }

         currentResult = new PropertyDescriptor("AutoAssignTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("AutoAssignTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "COUNTER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BigintTypeName")) {
         getterName = "getBigintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBigintTypeName";
         }

         currentResult = new PropertyDescriptor("BigintTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "REAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BinaryTypeName")) {
         getterName = "getBinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBinaryTypeName";
         }

         currentResult = new PropertyDescriptor("BinaryTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("BinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LONGBINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LONGBINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LONGCHAR");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IntegerTypeName")) {
         getterName = "getIntegerTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIntegerTypeName";
         }

         currentResult = new PropertyDescriptor("IntegerTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("IntegerTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "INTEGER");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinSyntax")) {
         getterName = "getJoinSyntax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinSyntax";
         }

         currentResult = new PropertyDescriptor("JoinSyntax", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("JoinSyntax", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "traditional");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGeneratedKeyQuery")) {
         getterName = "getLastGeneratedKeyQuery";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLastGeneratedKeyQuery";
         }

         currentResult = new PropertyDescriptor("LastGeneratedKeyQuery", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("LastGeneratedKeyQuery", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT @@identity");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarbinaryTypeName")) {
         getterName = "getLongVarbinaryTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarbinaryTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarbinaryTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarbinaryTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LONGBINARY");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LongVarcharTypeName")) {
         getterName = "getLongVarcharTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLongVarcharTypeName";
         }

         currentResult = new PropertyDescriptor("LongVarcharTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("LongVarcharTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "LONGCHAR");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxColumnNameLength")) {
         getterName = "getMaxColumnNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxColumnNameLength";
         }

         currentResult = new PropertyDescriptor("MaxColumnNameLength", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxColumnNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(64));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConstraintNameLength")) {
         getterName = "getMaxConstraintNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConstraintNameLength";
         }

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(64));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(64));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexesPerTable")) {
         getterName = "getMaxIndexesPerTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexesPerTable";
         }

         currentResult = new PropertyDescriptor("MaxIndexesPerTable", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexesPerTable", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(32));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTableNameLength")) {
         getterName = "getMaxTableNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTableNameLength";
         }

         currentResult = new PropertyDescriptor("MaxTableNameLength", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(64));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumericTypeName")) {
         getterName = "getNumericTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumericTypeName";
         }

         currentResult = new PropertyDescriptor("NumericTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("NumericTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "REAL");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Microsoft Access");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SmallintTypeName")) {
         getterName = "getSmallintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSmallintTypeName";
         }

         currentResult = new PropertyDescriptor("SmallintTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("SmallintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsAutoAssign")) {
         getterName = "getSupportsAutoAssign";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsAutoAssign";
         }

         currentResult = new PropertyDescriptor("SupportsAutoAssign", AccessDictionaryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsForeignKeys")) {
         getterName = "getSupportsForeignKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsForeignKeys";
         }

         currentResult = new PropertyDescriptor("SupportsForeignKeys", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsForeignKeys", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TinyintTypeName")) {
         getterName = "getTinyintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTinyintTypeName";
         }

         currentResult = new PropertyDescriptor("TinyintTypeName", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBestRowIdentifierForPrimaryKeys")) {
         getterName = "getUseGetBestRowIdentifierForPrimaryKeys";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBestRowIdentifierForPrimaryKeys";
         }

         currentResult = new PropertyDescriptor("UseGetBestRowIdentifierForPrimaryKeys", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetBestRowIdentifierForPrimaryKeys", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBytesForBlobs")) {
         getterName = "getUseGetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetBytesForBlobs", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetBytesForBlobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidationSQL")) {
         getterName = "getValidationSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidationSQL";
         }

         currentResult = new PropertyDescriptor("ValidationSQL", AccessDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT 1");
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
