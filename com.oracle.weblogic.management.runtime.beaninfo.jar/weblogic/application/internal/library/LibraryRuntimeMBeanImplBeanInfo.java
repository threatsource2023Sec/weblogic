package weblogic.application.internal.library;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.LibraryRuntimeMBean;

public class LibraryRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = LibraryRuntimeMBean.class;

   public LibraryRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public LibraryRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.application.internal.library.LibraryRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.application.internal.library");
      String description = (new String("RuntimeMBean for library deployments. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.LibraryRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Components")) {
         getterName = "getComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("Components", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Components", currentResult);
         currentResult.setValue("description", "<p>Returns the component MBeans for this library.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ImplementationVersion")) {
         getterName = "getImplementationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("ImplementationVersion", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ImplementationVersion", currentResult);
         currentResult.setValue("description", "<p>Returns the library implementation version. If not set, this value is null.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryIdentifier")) {
         getterName = "getLibraryIdentifier";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryIdentifier", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryIdentifier", currentResult);
         currentResult.setValue("description", "<p>Returns the library identifier.</p>  <p>The library identifier uniquely identifies this library version across all versions of all deployed applications and deployed libraries. If the library is not versioned, the library identifier is the same as the library name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryName")) {
         getterName = "getLibraryName";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryName", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LibraryName", currentResult);
         currentResult.setValue("description", "<p>Returns the library name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>The library's partition.</p>  <p>Returns the partition to which this library is deployed.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("ReferencingNames")) {
         getterName = "getReferencingNames";
         setterName = null;
         currentResult = new PropertyDescriptor("ReferencingNames", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReferencingNames", currentResult);
         currentResult.setValue("description", "<p>Returns the names of all deployed applications that reference this library.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReferencingRuntimes")) {
         getterName = "getReferencingRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ReferencingRuntimes", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReferencingRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the RuntimeMBeans of current referencers of this library. Typically, a library referencer is a deployed application.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpecificationVersion")) {
         getterName = "getSpecificationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("SpecificationVersion", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpecificationVersion", currentResult);
         currentResult.setValue("description", "<p>Returns the library specification version. If not set, this value is null.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Referenced")) {
         getterName = "isReferenced";
         setterName = null;
         currentResult = new PropertyDescriptor("Referenced", LibraryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Referenced", currentResult);
         currentResult.setValue("description", "<p>Returns true if this library is referenced by one or more referencers. Typically, a library referencer is a deployed application.</p> ");
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
