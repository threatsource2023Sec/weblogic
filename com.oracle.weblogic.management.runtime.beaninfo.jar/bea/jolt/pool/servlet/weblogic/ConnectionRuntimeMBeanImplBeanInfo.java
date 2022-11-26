package bea.jolt.pool.servlet.weblogic;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JoltConnectionRuntimeMBean;

public class ConnectionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JoltConnectionRuntimeMBean.class;

   public ConnectionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("bea.jolt.pool.servlet.weblogic.ConnectionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "bea.jolt.pool.servlet.weblogic");
      String description = (new String("<p>This class is used for monitoring individual WebLogic Jolt connections</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JoltConnectionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Address")) {
         getterName = "getAddress";
         setterName = null;
         currentResult = new PropertyDescriptor("Address", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Address", currentResult);
         currentResult.setValue("description", "<p>The connection Address.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ErrorCount")) {
         getterName = "getErrorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ErrorCount", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ErrorCount", currentResult);
         currentResult.setValue("description", "<p>The Request Error Count.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastAccessTime")) {
         getterName = "getLastAccessTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastAccessTime", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastAccessTime", currentResult);
         currentResult.setValue("description", "<p>The Last Access Date and Time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingRequestCount")) {
         getterName = "getPendingRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRequestCount", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRequestCount", currentResult);
         currentResult.setValue("description", "<p>The Pending Request Count.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestCount")) {
         getterName = "getRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestCount", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RequestCount", currentResult);
         currentResult.setValue("description", "<p>The Request Count.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Alive")) {
         getterName = "isAlive";
         setterName = null;
         currentResult = new PropertyDescriptor("Alive", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Alive", currentResult);
         currentResult.setValue("description", "<p>The Connection Alive indicator.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InTransaction")) {
         getterName = "isInTransaction";
         setterName = null;
         currentResult = new PropertyDescriptor("InTransaction", JoltConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InTransaction", currentResult);
         currentResult.setValue("description", "<p>The Connection in Transaction indicator.</p> ");
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
