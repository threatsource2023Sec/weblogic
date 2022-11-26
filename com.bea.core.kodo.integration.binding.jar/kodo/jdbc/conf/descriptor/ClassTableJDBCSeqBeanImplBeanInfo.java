package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import kodo.conf.descriptor.SequenceBeanImplBeanInfo;

public class ClassTableJDBCSeqBeanImplBeanInfo extends SequenceBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClassTableJDBCSeqBean.class;

   public ClassTableJDBCSeqBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClassTableJDBCSeqBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String("A single JDBC table to store sequences ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBean");
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

         currentResult = new PropertyDescriptor("Allocate", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Allocate", currentResult);
         currentResult.setValue("description", "Get the allocation. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreUnmapped")) {
         getterName = "getIgnoreUnmapped";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreUnmapped";
         }

         currentResult = new PropertyDescriptor("IgnoreUnmapped", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("IgnoreUnmapped", currentResult);
         currentResult.setValue("description", "Whether to ignore unmapped. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreVirtual")) {
         getterName = "getIgnoreVirtual";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreVirtual";
         }

         currentResult = new PropertyDescriptor("IgnoreVirtual", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("IgnoreVirtual", currentResult);
         currentResult.setValue("description", "Whether to ignore virtual. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Increment")) {
         getterName = "getIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncrement";
         }

         currentResult = new PropertyDescriptor("Increment", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Increment", currentResult);
         currentResult.setValue("description", "The increment. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryKeyColumn")) {
         getterName = "getPrimaryKeyColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryKeyColumn";
         }

         currentResult = new PropertyDescriptor("PrimaryKeyColumn", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("PrimaryKeyColumn", currentResult);
         currentResult.setValue("description", "The pk column. ");
         setPropertyDescriptorDefault(currentResult, "ID");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SequenceColumn")) {
         getterName = "getSequenceColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequenceColumn";
         }

         currentResult = new PropertyDescriptor("SequenceColumn", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("SequenceColumn", currentResult);
         currentResult.setValue("description", "The sequence column. ");
         setPropertyDescriptorDefault(currentResult, "SEQUENCE_VALUE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Table")) {
         getterName = "getTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTable";
         }

         currentResult = new PropertyDescriptor("Table", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Table", currentResult);
         currentResult.setValue("description", "The table. ");
         setPropertyDescriptorDefault(currentResult, "OPENJPA_SEQUENCES_TABLE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
         currentResult.setValue("description", "The name of the table. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setType";
         }

         currentResult = new PropertyDescriptor("Type", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "Set the type. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("UseAliases")) {
         getterName = "getUseAliases";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseAliases";
         }

         currentResult = new PropertyDescriptor("UseAliases", ClassTableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("UseAliases", currentResult);
         currentResult.setValue("description", "Whether to use aliases. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
