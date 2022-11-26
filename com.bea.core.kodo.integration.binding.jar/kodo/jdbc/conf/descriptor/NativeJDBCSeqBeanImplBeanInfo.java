package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import kodo.conf.descriptor.SequenceBeanImplBeanInfo;

public class NativeJDBCSeqBeanImplBeanInfo extends SequenceBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = NativeJDBCSeqBean.class;

   public NativeJDBCSeqBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public NativeJDBCSeqBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.NativeJDBCSeqBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.NativeJDBCSeqBean");
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

         currentResult = new PropertyDescriptor("Allocate", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Allocate", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Format")) {
         getterName = "getFormat";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFormat";
         }

         currentResult = new PropertyDescriptor("Format", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Format", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Increment")) {
         getterName = "getIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncrement";
         }

         currentResult = new PropertyDescriptor("Increment", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Increment", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialValue")) {
         getterName = "getInitialValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialValue";
         }

         currentResult = new PropertyDescriptor("InitialValue", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("InitialValue", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Sequence")) {
         getterName = "getSequence";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequence";
         }

         currentResult = new PropertyDescriptor("Sequence", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("Sequence", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "OPENJPA_SEQUENCE");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SequenceName")) {
         getterName = "getSequenceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequenceName";
         }

         currentResult = new PropertyDescriptor("SequenceName", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("SequenceName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", NativeJDBCSeqBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "DUAL");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setType";
         }

         currentResult = new PropertyDescriptor("Type", NativeJDBCSeqBean.class, getterName, setterName);
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
