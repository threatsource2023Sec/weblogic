package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;
import weblogic.management.mbeanservers.edit.ServerStatus;

public class ServerStatusImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ServerStatus.class;

   public ServerStatusImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerStatusImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.ServerStatusImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("valueObject", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("Contains the name and state for a target server in a Configuration Manager deployment request. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.ServerStatus");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ServerException")) {
         getterName = "getServerException";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerException", ServerStatus.class, getterName, (String)setterName);
         descriptors.put("ServerException", currentResult);
         currentResult.setValue("description", "<p>Returns the exception that describes why the activation has failed on this server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", ServerStatus.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "<p>Returns the name of the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerState")) {
         getterName = "getServerState";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerState", ServerStatus.class, getterName, (String)setterName);
         descriptors.put("ServerState", currentResult);
         currentResult.setValue("description", " ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ActivationTaskMBean")};
         currentResult.setValue("see", seeObjectArray);
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
