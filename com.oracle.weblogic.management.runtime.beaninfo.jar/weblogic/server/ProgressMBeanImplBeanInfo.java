package weblogic.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ProgressMBean;

public class ProgressMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ProgressMBean.class;

   public ProgressMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ProgressMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.ProgressMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.3.0");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.server");
      String description = (new String("Information about the progress of a booting subsystem ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ProgressMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CurrentWork")) {
         getterName = "getCurrentWork";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentWork", ProgressMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentWork", currentResult);
         currentResult.setValue("description", "The set of work this subsystem is working on at this time.  The subsystem may be working on multiple items of work at the same time on different threads, in which case this array will be larger than size one.  The subsystem may be working on nothing at the moment (even if in state IN_PROGRESS) in which case this will return an array of length zero.  In FINAL state this will return null.  IN FAILED state this will return the work that failed (which may be zero-length as the failure may be unrelated to the work that was done) ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", ProgressMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of the subsystem reporting progress ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.WebLogicMBean#getName()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ProgressMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "The current state of this subsystem. <ol> <li>IN_PROGRESS - The subsystem is currently booting toward a final state</li> <li>FINAL - The subsystem has successfully booted</li> <li>FAILED - The subsystem has encountered a fatal error during boot and is no longer progressing</li> </ol> ");
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
