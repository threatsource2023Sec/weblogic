package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class OsgiFrameworkReferenceBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = OsgiFrameworkReferenceBean.class;

   public OsgiFrameworkReferenceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OsgiFrameworkReferenceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.OsgiFrameworkReferenceBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.OsgiFrameworkReferenceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ApplicationBundleSymbolicName")) {
         getterName = "getApplicationBundleSymbolicName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationBundleSymbolicName";
         }

         currentResult = new PropertyDescriptor("ApplicationBundleSymbolicName", OsgiFrameworkReferenceBean.class, getterName, setterName);
         descriptors.put("ApplicationBundleSymbolicName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationBundleVersion")) {
         getterName = "getApplicationBundleVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApplicationBundleVersion";
         }

         currentResult = new PropertyDescriptor("ApplicationBundleVersion", OsgiFrameworkReferenceBean.class, getterName, setterName);
         descriptors.put("ApplicationBundleVersion", currentResult);
         currentResult.setValue("description", "The version(s) which the attached bundle must match.  If this attribute is set then the bundleSymbolicName must also be set.  The format of the string is a version range, where the inclusive element of the range is denoted by '[' or ']'.  The exclusive element of the range is denoted by '(' or ')'.  A range is denoted by two versions, separated by a comma.  * may be used as the upper range, to indicate infinite upper range.  If the version is not a comma separated item then it must be a single version that is the only version that should match. <p> Examples include: <p> [1.0,2.0) : This would indicate any bundle between version 1 inclusive and 2 exclusive <p> 2.3.7.beta-1 : This would indicate an exact bundle version to attach to <p> (3.7,*) : This would indicate a lower version higher than 3.7 and no upper version limit <p> The versions themselves must conform to the OSGi standard for ranges, implying that the first three elements of the version must be numbers, while the last element of the version can be any string at all. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BundlesDirectory")) {
         getterName = "getBundlesDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBundlesDirectory";
         }

         currentResult = new PropertyDescriptor("BundlesDirectory", OsgiFrameworkReferenceBean.class, getterName, setterName);
         descriptors.put("BundlesDirectory", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "osgi-lib");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", OsgiFrameworkReferenceBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
