package weblogic.management;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class WebLogicMBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WebLogicMBean.class;

   public WebLogicMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebLogicMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("$(package).WebLogicMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration"), BeanInfoHelper.encodeEntities("weblogic.management.runtime")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management");
      String description = (new String("<p>The super interface for all WebLogic Server MBeans.</p>  <p>You can use WebLogic Server MBeans to configure, monitor, and manage WebLogic Server resources through JMX.</p>  <p>This MBean, and therefore all WebLogic Server MBeans, extend the following standard JMX interfaces:</p>  <p><a href=\"http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/DynamicMBean.html\" shape=\"rect\">javax.management.DynamicMBean</a>, <a href=\"http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/MBeanRegistration.html\" shape=\"rect\">javax.management.MBeanRegistration</a>, <a href=\"http://docs.oracle.com/javase/1.5.0/docs/api/javax/management/NotificationBroadcaster.html\" shape=\"rect\">javax.management.NotificationBroadcaster</a></p>  The name of a cluster denotes its logical cluster name.  <h3 class=\"TypeSafeDeprecation\">Deprecation of MBeanHome and Type-Safe Interfaces</h3>  <p class=\"TypeSafeDeprecation\">This is a type-safe interface for a WebLogic Server MBean, which you can import into your client classes and access through <code>weblogic.management.MBeanHome</code>. As of 9.0, the <code>MBeanHome</code> interface and all type-safe interfaces for WebLogic Server MBeans are deprecated. Instead, client classes that interact with WebLogic Server MBeans should use standard JMX design patterns in which clients use the <code>javax.management.MBeanServerConnection</code> interface to discover MBeans, attributes, and attribute types at runtime.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.WebLogicMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("MBeanInfo")) {
         getterName = "getMBeanInfo";
         setterName = null;
         currentResult = new PropertyDescriptor("MBeanInfo", WebLogicMBean.class, getterName, setterName);
         descriptors.put("MBeanInfo", currentResult);
         currentResult.setValue("description", "<p>Returns the MBean info for this MBean.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", WebLogicMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of this configuration. WebLogic Server uses an MBean to implement and persist the configuration.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("legal", "");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("ObjectName")) {
         getterName = "getObjectName";
         setterName = null;
         currentResult = new PropertyDescriptor("ObjectName", WebLogicMBean.class, getterName, setterName);
         descriptors.put("ObjectName", currentResult);
         currentResult.setValue("description", "<p>Returns the ObjectName under which this MBean is registered in the MBean server.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("Parent")) {
         getterName = "getParent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParent";
         }

         currentResult = new PropertyDescriptor("Parent", WebLogicMBean.class, getterName, setterName);
         descriptors.put("Parent", currentResult);
         currentResult.setValue("description", "<p>Return the immediate parent for this MBean</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", WebLogicMBean.class, getterName, setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>Returns the type of the MBean.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("CachingDisabled")) {
         getterName = "isCachingDisabled";
         setterName = null;
         currentResult = new PropertyDescriptor("CachingDisabled", WebLogicMBean.class, getterName, setterName);
         descriptors.put("CachingDisabled", currentResult);
         currentResult.setValue("description", "<p>Private property that disables caching in proxies.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("Registered")) {
         getterName = "isRegistered";
         setterName = null;
         currentResult = new PropertyDescriptor("Registered", WebLogicMBean.class, getterName, setterName);
         descriptors.put("Registered", currentResult);
         currentResult.setValue("description", "<p>Returns false if the the MBean represented by this object has been unregistered.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
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
