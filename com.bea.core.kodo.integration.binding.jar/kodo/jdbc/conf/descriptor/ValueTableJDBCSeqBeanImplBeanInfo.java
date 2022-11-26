package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import kodo.conf.descriptor.SequenceBeanImplBeanInfo;

public class ValueTableJDBCSeqBeanImplBeanInfo extends SequenceBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ValueTableJDBCSeqBean.class;

   public ValueTableJDBCSeqBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ValueTableJDBCSeqBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.ValueTableJDBCSeqBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.ValueTableJDBCSeqBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Allocate")) {
         getterName = "getAllocate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllocate";
         }

         currentResult = new PropertyDescriptor("Allocate", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Allocate", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Increment")) {
         getterName = "getIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncrement";
         }

         currentResult = new PropertyDescriptor("Increment", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Increment", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryKeyColumn")) {
         getterName = "getPrimaryKeyColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryKeyColumn";
         }

         currentResult = new PropertyDescriptor("PrimaryKeyColumn", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("PrimaryKeyColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "ID");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryKeyValue")) {
         getterName = "getPrimaryKeyValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryKeyValue";
         }

         currentResult = new PropertyDescriptor("PrimaryKeyValue", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("PrimaryKeyValue", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DEFAULT");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SequenceColumn")) {
         getterName = "getSequenceColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequenceColumn";
         }

         currentResult = new PropertyDescriptor("SequenceColumn", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("SequenceColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "SEQUENCE_VALUE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Table")) {
         getterName = "getTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTable";
         }

         currentResult = new PropertyDescriptor("Table", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Table", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "OPENJPA_SEQUENCES_TABLE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setType";
         }

         currentResult = new PropertyDescriptor("Type", ValueTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
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
