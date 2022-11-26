package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.EditSessionServiceMBean;

public class EditSessionServiceMBeanImplBeanInfo extends EditServiceMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EditSessionServiceMBean.class;

   public EditSessionServiceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EditSessionServiceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.EditSessionServiceMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.EditSessionServiceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AppDeploymentConfigurationManager")) {
         getterName = "getAppDeploymentConfigurationManager";
         setterName = null;
         currentResult = new PropertyDescriptor("AppDeploymentConfigurationManager", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("AppDeploymentConfigurationManager", currentResult);
         currentResult.setValue("description", "Returns the AppDeploymentConfigurationManagerMBean, which supports changing an application's deployment configuration via JMX. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurationManager")) {
         getterName = "getConfigurationManager";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationManager", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurationManager", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>ConfigurationManagerMBean</code> for this domain, which has attributes and operations to start/stop edit sessions, navigate the pending hierarchy of configuration MBeans, and save, undo, and activate configuration changes..</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DomainConfiguration")) {
         getterName = "getDomainConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainConfiguration", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("DomainConfiguration", currentResult);
         currentResult.setValue("description", "<p>Contains the pending <code>DomainMBean</code>, which is the root of the pending configuration MBean hierarchy. You cannot use this MBean to modify a domain's configuration unless you have already started an edit session using the {@link ConfigurationManagerMBean#startEdit startEdit} operation in the <code>ConfigurationManagerMBean</code>.</p>  <p>If you have already started an edit session, you can use this attribute to get the editable <code>DomainMBean</code> and navigate its hierarchy, however the process of starting an edit session returns the editable <code>DomainMBean</code> (making it unnecessary get the value of this attribute, because you already have the <code>DomainMBean</code>).</p>  <p>The <code>ConfigurationManagerMBean</code> provides this attribute mostly to enable JMX clients to view the in-memory state of the pending configuration MBean hierarchy without having to start an edit session. For example, if userA starts an edit session and changes the value of an MBean attribute, userB can get <code>DomainMBean</code> from this (<code>ConfigurationManagerMBean DomainConfiguration</code>) attribute and see the changes from userA, even if userA's edit session is still active.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PortablePartitionManager")) {
         getterName = "getPortablePartitionManager";
         setterName = null;
         currentResult = new PropertyDescriptor("PortablePartitionManager", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("PortablePartitionManager", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>PortablePartitionManagerMBean</code> for this domain, which has attributes and operations to migrate, import,export partition related entities</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RecordingManager")) {
         getterName = "getRecordingManager";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordingManager", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("RecordingManager", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>RecordingManagerMBean</code> for this domain, which has attributes and operations to start/stop WLST recording sessions and write scripts/comments to the recording file.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", EditSessionServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
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
