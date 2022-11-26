package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class EmpressDictionaryBeanImplBeanInfo extends BuiltInDBDictionaryBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EmpressDictionaryBean.class;

   public EmpressDictionaryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EmpressDictionaryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.EmpressDictionaryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.EmpressDictionaryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllowConcurrentRead")) {
         getterName = "getAllowConcurrentRead";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowConcurrentRead";
         }

         currentResult = new PropertyDescriptor("AllowConcurrentRead", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("AllowConcurrentRead", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BigintTypeName")) {
         getterName = "getBigintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBigintTypeName";
         }

         currentResult = new PropertyDescriptor("BigintTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("BigintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DECIMAL(38)");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BitTypeName")) {
         getterName = "getBitTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBitTypeName";
         }

         currentResult = new PropertyDescriptor("BitTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("BitTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BlobTypeName")) {
         getterName = "getBlobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBlobTypeName";
         }

         currentResult = new PropertyDescriptor("BlobTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("BlobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "BULK");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClobTypeName")) {
         getterName = "getClobTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClobTypeName";
         }

         currentResult = new PropertyDescriptor("ClobTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("ClobTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TEXT");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DoubleTypeName")) {
         getterName = "getDoubleTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDoubleTypeName";
         }

         currentResult = new PropertyDescriptor("DoubleTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("DoubleTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SMALLINT");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoinSyntax")) {
         getterName = "getJoinSyntax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJoinSyntax";
         }

         currentResult = new PropertyDescriptor("JoinSyntax", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("JoinSyntax", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "traditional");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxColumnNameLength")) {
         getterName = "getMaxColumnNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxColumnNameLength";
         }

         currentResult = new PropertyDescriptor("MaxColumnNameLength", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxColumnNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(28));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConstraintNameLength")) {
         getterName = "getMaxConstraintNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConstraintNameLength";
         }

         currentResult = new PropertyDescriptor("MaxConstraintNameLength", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxConstraintNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(28));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIndexNameLength")) {
         getterName = "getMaxIndexNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIndexNameLength";
         }

         currentResult = new PropertyDescriptor("MaxIndexNameLength", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxIndexNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(28));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxTableNameLength")) {
         getterName = "getMaxTableNameLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxTableNameLength";
         }

         currentResult = new PropertyDescriptor("MaxTableNameLength", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("MaxTableNameLength", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(28));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Platform")) {
         getterName = "getPlatform";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatform";
         }

         currentResult = new PropertyDescriptor("Platform", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("Platform", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "Empress");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RealTypeName")) {
         getterName = "getRealTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRealTypeName";
         }

         currentResult = new PropertyDescriptor("RealTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("RealTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "FLOAT(8)");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresAliasForSubselect")) {
         getterName = "getRequiresAliasForSubselect";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresAliasForSubselect";
         }

         currentResult = new PropertyDescriptor("RequiresAliasForSubselect", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("RequiresAliasForSubselect", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SchemaCase")) {
         getterName = "getSchemaCase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchemaCase";
         }

         currentResult = new PropertyDescriptor("SchemaCase", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("SchemaCase", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "preserve");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportsDeferredConstraints")) {
         getterName = "getSupportsDeferredConstraints";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSupportsDeferredConstraints";
         }

         currentResult = new PropertyDescriptor("SupportsDeferredConstraints", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("SupportsDeferredConstraints", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimestampTypeName")) {
         getterName = "getTimestampTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimestampTypeName";
         }

         currentResult = new PropertyDescriptor("TimestampTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("TimestampTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DATE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TinyintTypeName")) {
         getterName = "getTinyintTypeName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTinyintTypeName";
         }

         currentResult = new PropertyDescriptor("TinyintTypeName", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("TinyintTypeName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DOUBLE PRECISION");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ToLowerCaseFunction")) {
         getterName = "getToLowerCaseFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setToLowerCaseFunction";
         }

         currentResult = new PropertyDescriptor("ToLowerCaseFunction", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("ToLowerCaseFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TOLOWER({0})");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ToUpperCaseFunction")) {
         getterName = "getToUpperCaseFunction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setToUpperCaseFunction";
         }

         currentResult = new PropertyDescriptor("ToUpperCaseFunction", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("ToUpperCaseFunction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "TOUPPER({0})");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetBytesForBlobs")) {
         getterName = "getUseGetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseGetBytesForBlobs", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetBytesForBlobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseGetStringForClobs")) {
         getterName = "getUseGetStringForClobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseGetStringForClobs";
         }

         currentResult = new PropertyDescriptor("UseGetStringForClobs", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("UseGetStringForClobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSetBytesForBlobs")) {
         getterName = "getUseSetBytesForBlobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSetBytesForBlobs";
         }

         currentResult = new PropertyDescriptor("UseSetBytesForBlobs", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetBytesForBlobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSetStringForClobs")) {
         getterName = "getUseSetStringForClobs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSetStringForClobs";
         }

         currentResult = new PropertyDescriptor("UseSetStringForClobs", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("UseSetStringForClobs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidationSQL")) {
         getterName = "getValidationSQL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidationSQL";
         }

         currentResult = new PropertyDescriptor("ValidationSQL", EmpressDictionaryBean.class, getterName, setterName);
         descriptors.put("ValidationSQL", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SELECT DISTINCT today FROM sys_tables");
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
