package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class VersionLockManagerBeanImplBeanInfo extends LockManagerBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = VersionLockManagerBean.class;

   public VersionLockManagerBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public VersionLockManagerBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.VersionLockManagerBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String("Uses version to lock objects. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.VersionLockManagerBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("VersionCheckOnReadLock")) {
         getterName = "getVersionCheckOnReadLock";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersionCheckOnReadLock";
         }

         currentResult = new PropertyDescriptor("VersionCheckOnReadLock", VersionLockManagerBean.class, getterName, setterName);
         descriptors.put("VersionCheckOnReadLock", currentResult);
         currentResult.setValue("description", "Whether to check version on read lock. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionUpdateOnWriteLock")) {
         getterName = "getVersionUpdateOnWriteLock";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersionUpdateOnWriteLock";
         }

         currentResult = new PropertyDescriptor("VersionUpdateOnWriteLock", VersionLockManagerBean.class, getterName, setterName);
         descriptors.put("VersionUpdateOnWriteLock", currentResult);
         currentResult.setValue("description", "Whether to update version on write lock. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
