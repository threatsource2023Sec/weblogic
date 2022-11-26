package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class FastSwapBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = FastSwapBean.class;

   public FastSwapBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FastSwapBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.FastSwapBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.FastSwapBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("RedefinitionTaskLimit")) {
         getterName = "getRedefinitionTaskLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRedefinitionTaskLimit";
         }

         currentResult = new PropertyDescriptor("RedefinitionTaskLimit", FastSwapBean.class, getterName, setterName);
         descriptors.put("RedefinitionTaskLimit", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RefreshInterval")) {
         getterName = "getRefreshInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRefreshInterval";
         }

         currentResult = new PropertyDescriptor("RefreshInterval", FastSwapBean.class, getterName, setterName);
         descriptors.put("RefreshInterval", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", FastSwapBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
