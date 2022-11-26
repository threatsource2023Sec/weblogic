package weblogic.servlet.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WebServerRuntimeMBean;

public class WebServerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServerRuntimeMBean.class;

   public WebServerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.internal.WebServerRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.servlet.internal");
      String description = (new String("Describes a Web Server (HTTP Server) ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WebServerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", WebServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "<p>Provides the log runtime associated with http access log</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServerName")) {
         getterName = "getWebServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServerName", WebServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WebServerName", currentResult);
         currentResult.setValue("description", "<p>Provides the name of the WebServer.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultWebServer")) {
         getterName = "isDefaultWebServer";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultWebServer", WebServerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DefaultWebServer", currentResult);
         currentResult.setValue("description", "<p>Indicates whether it is the default WebServer or a VirtualHost</p> ");
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
