package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import kodo.conf.descriptor.SequenceBeanImplBeanInfo;

public class TableJDBCSeqBeanImplBeanInfo extends SequenceBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TableJDBCSeqBean.class;

   public TableJDBCSeqBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TableJDBCSeqBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.TableJDBCSeqBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String("A JDBC sequence which stores in a table. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.TableJDBCSeqBean");
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

         currentResult = new PropertyDescriptor("Allocate", TableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Allocate", currentResult);
         currentResult.setValue("description", "Allocation size. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Increment")) {
         getterName = "getIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncrement";
         }

         currentResult = new PropertyDescriptor("Increment", TableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Increment", currentResult);
         currentResult.setValue("description", "Increment size. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryKeyColumn")) {
         getterName = "getPrimaryKeyColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrimaryKeyColumn";
         }

         currentResult = new PropertyDescriptor("PrimaryKeyColumn", TableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("PrimaryKeyColumn", currentResult);
         currentResult.setValue("description", "pk column. ");
         setPropertyDescriptorDefault(currentResult, "ID");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SequenceColumn")) {
         getterName = "getSequenceColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequenceColumn";
         }

         currentResult = new PropertyDescriptor("SequenceColumn", TableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("SequenceColumn", currentResult);
         currentResult.setValue("description", "sequence column. ");
         setPropertyDescriptorDefault(currentResult, "SEQUENCE_VALUE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Table")) {
         getterName = "getTable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTable";
         }

         currentResult = new PropertyDescriptor("Table", TableJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Table", currentResult);
         currentResult.setValue("description", "The table name. ");
         setPropertyDescriptorDefault(currentResult, "OPENJPA_SEQUENCE_TABLE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", TableJDBCSeqBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Type", TableJDBCSeqBean.class, getterName, setterName);
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
