package weblogic.diagnostics.archive;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.WLDFFileArchiveRuntimeMBean;

public class WLDFDiagnosticFileArchiveRuntimeBeanInfo extends WLDFDiagnosticArchiveRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFFileArchiveRuntimeMBean.class;

   public WLDFDiagnosticFileArchiveRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFDiagnosticFileArchiveRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.archive.WLDFDiagnosticFileArchiveRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.archive");
      String description = (new String("<p>Use this interface to collect statistical information about file-based WLDF archives.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFFileArchiveRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("IncrementalIndexCycleCount")) {
         getterName = "getIncrementalIndexCycleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("IncrementalIndexCycleCount", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IncrementalIndexCycleCount", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("IncrementalIndexTime")) {
         getterName = "getIncrementalIndexTime";
         setterName = null;
         currentResult = new PropertyDescriptor("IncrementalIndexTime", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IncrementalIndexTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("IndexCycleCount")) {
         getterName = "getIndexCycleCount";
         setterName = null;
         currentResult = new PropertyDescriptor("IndexCycleCount", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IndexCycleCount", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("IndexTime")) {
         getterName = "getIndexTime";
         setterName = null;
         currentResult = new PropertyDescriptor("IndexTime", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IndexTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RecordRetrievalTime")) {
         getterName = "getRecordRetrievalTime";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordRetrievalTime", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordRetrievalTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RecordSeekCount")) {
         getterName = "getRecordSeekCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordSeekCount", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordSeekCount", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RecordSeekTime")) {
         getterName = "getRecordSeekTime";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordSeekTime", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecordSeekTime", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RetrievedRecordCount")) {
         getterName = "getRetrievedRecordCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RetrievedRecordCount", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RetrievedRecordCount", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("RotatedFilesCount")) {
         getterName = "getRotatedFilesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RotatedFilesCount", WLDFFileArchiveRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RotatedFilesCount", currentResult);
         currentResult.setValue("description", " ");
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
