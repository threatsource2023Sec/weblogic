package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;

public class EditServiceMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = EditServiceMBean.class;

   public EditServiceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EditServiceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.EditServiceMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("<p>Provides the entry point for managing the configuration of the current WebLogic Server domain.</p>  <p>This MBean is available only on the Administration Server.</p>  <p>The <code>javax.management.ObjectName</code> of this MBean is \"<code>com.bea:Name=EditService,Type=weblogic.management.mbeanservers.edit.EditServiceMBean</code>\".</p>  <p>This is the only object name that a JMX client needs to navigate and edit the hierarchy of editiable WebLogic Server MBeans. To start editing MBean attributes, a JMX client invokes the <code>javax.management.MBeanServerConnection.getAttribute()</code> method and passes the following as parameters:</p>  <ul> <li> <p>The <code>ObjectName</code> of this service MBean</p> </li>  <li> <p><code>\"ConfigurationManager\"</code>, which is the attribute that contains the <code>ConfigurationManagerMBean</code>. The <code>ConfigurationManagerMBean</code> contains attributes and operations to start/stop edit sessions, and save, undo, and activate configuration changes.</p> </li> </ul>  <p>This method call returns the <code>ObjectName</code> of the <code>ConfigurationManagerMBean</code>. For example:</p>  <p><code>ObjectName es = new ObjectName(\"com.bea:Name=EditService,Type=weblogic.management.mbeanservers.edit.EditServiceMBean\");<br> <br>// Get the ObjectName of the domain's ConfigurationManagerMBean by getting the value<br> // of the EditServiceMBean ConfigurationManager attribute<br> <br>ObjectName cfg = (ObjectName) MBeanServerConnection.getAttribute(es,\"ConfigurationManager\");</code> </p>  <p>After getting this <code>ObjectName</code>, the client invokes the <code>ConfigurationManagerMBean</code> <code>startEdit()</code> operation, which returns the <code>ObjectName</code> of the editable <code>DomainMBean</code>. Clients can change the values of <code>DomainMBean</code> attributes or navigate the hierarchy of MBeans below <code>DomainMBean</code> and change their attribute values.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.EditServiceMBean");
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
         currentResult = new PropertyDescriptor("AppDeploymentConfigurationManager", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("AppDeploymentConfigurationManager", currentResult);
         currentResult.setValue("description", "Returns the AppDeploymentConfigurationManagerMBean, which supports changing an application's deployment configuration via JMX. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurationManager")) {
         getterName = "getConfigurationManager";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationManager", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurationManager", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>ConfigurationManagerMBean</code> for this domain, which has attributes and operations to start/stop edit sessions, navigate the pending hierarchy of configuration MBeans, and save, undo, and activate configuration changes..</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DomainConfiguration")) {
         getterName = "getDomainConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainConfiguration", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("DomainConfiguration", currentResult);
         currentResult.setValue("description", "<p>Contains the pending <code>DomainMBean</code>, which is the root of the pending configuration MBean hierarchy. You cannot use this MBean to modify a domain's configuration unless you have already started an edit session using the {@link ConfigurationManagerMBean#startEdit startEdit} operation in the <code>ConfigurationManagerMBean</code>.</p>  <p>If you have already started an edit session, you can use this attribute to get the editable <code>DomainMBean</code> and navigate its hierarchy, however the process of starting an edit session returns the editable <code>DomainMBean</code> (making it unnecessary get the value of this attribute, because you already have the <code>DomainMBean</code>).</p>  <p>The <code>ConfigurationManagerMBean</code> provides this attribute mostly to enable JMX clients to view the in-memory state of the pending configuration MBean hierarchy without having to start an edit session. For example, if userA starts an edit session and changes the value of an MBean attribute, userB can get <code>DomainMBean</code> from this (<code>ConfigurationManagerMBean DomainConfiguration</code>) attribute and see the changes from userA, even if userA's edit session is still active.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PortablePartitionManager")) {
         getterName = "getPortablePartitionManager";
         setterName = null;
         currentResult = new PropertyDescriptor("PortablePartitionManager", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("PortablePartitionManager", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>PortablePartitionManagerMBean</code> for this domain, which has attributes and operations to migrate, import,export partition related entities</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RecordingManager")) {
         getterName = "getRecordingManager";
         setterName = null;
         currentResult = new PropertyDescriptor("RecordingManager", EditServiceMBean.class, getterName, (String)setterName);
         descriptors.put("RecordingManager", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>RecordingManagerMBean</code> for this domain, which has attributes and operations to start/stop WLST recording sessions and write scripts/comments to the recording file.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", EditServiceMBean.class, getterName, (String)setterName);
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
