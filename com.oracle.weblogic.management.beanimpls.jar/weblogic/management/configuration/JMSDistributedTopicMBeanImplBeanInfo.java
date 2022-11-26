package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMSDistributedTopicMBeanImplBeanInfo extends JMSDistributedDestinationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMSDistributedTopicMBean.class;

   public JMSDistributedTopicMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMSDistributedTopicMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMSDistributedTopicMBeanImpl");
      } catch (Throwable var6) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("obsolete", "9.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DistributedTopicBean} ");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents a JMS distributed topic, which is comprised of multiple physical JMS topics as members of a single distributed set of topics that can be served by multiple WebLogic Server instances within a cluster. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMSDistributedTopicMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDistributedTopicMembers")) {
         getterName = "getJMSDistributedTopicMembers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDistributedTopicMembers", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("JMSDistributedTopicMembers", currentResult);
         currentResult.setValue("description", "Get all the Members ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSDistributedTopicMember");
         currentResult.setValue("creator", "createJMSDistributedTopicMember");
         currentResult.setValue("destroyer", "destroyJMSDistributedTopicMember");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSTemplate")) {
         getterName = "getJMSTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJMSTemplate";
         }

         currentResult = new PropertyDescriptor("JMSTemplate", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("JMSTemplate", currentResult);
         currentResult.setValue("description", "<p>gets JMSTemplateMBean instance off this DistributedDestination</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSTemplate");
         currentResult.setValue("destroyer", "destroyJMSTemplate");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DistributedDestinationBean and weblogic.j2ee.descriptor.wl.UnifrormDistributedDestinationBean} ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The JNDI name used to look up a virtual destination within the JNDI namespace. Applications can use the JNDI name to look up the virtual destination. If not specified, then the destination is not bound into the JNDI namespace.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("LoadBalancingPolicy")) {
         getterName = "getLoadBalancingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBalancingPolicy";
         }

         currentResult = new PropertyDescriptor("LoadBalancingPolicy", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("LoadBalancingPolicy", currentResult);
         currentResult.setValue("description", "<p>Defines the load balancing policy for producers sending messages to a distributed destination in order to balance the message load across the members of the distributed set.</p>  <ul> <li><b>Round-Robin</b> <p>- The system maintains an ordering of physical topic members within the set by distributing the messaging load across the topic members one at a time in the order that they are defined in the configuration file. Each WebLogic Server maintains an identical ordering, but may be at a different point within the ordering. If weights are assigned to any of the topic members in the set, then those members appear multiple times in the ordering.</p> </li>  <li><b>Random</b> <p>- The weight assigned to the topic members is used to compute a weighted distribution for the members of the set. The messaging load is distributed across the topic members by pseudo-randomly accessing the distribution. In the short run, the load will not be directly proportional to the weight. In the long run, the distribution will approach the limit of the distribution. A pure random distribution can be achieved by setting all the weights to the same value, which is typically set to 1.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "Round-Robin");
         currentResult.setValue("legalValues", new Object[]{"Round-Robin", "Random"});
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("Members")) {
         getterName = "getMembers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMembers";
         }

         currentResult = new PropertyDescriptor("Members", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("Members", currentResult);
         currentResult.setValue("description", "<p>The members for this distributed topic.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeMember");
         currentResult.setValue("adder", "addMember");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this configuration.</p>  <p>WebLogic Server saves this note in the domain's configuration file (<code>config.xml</code>) as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <p>Note: If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         String[] roleObjectArraySet = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowedSet", roleObjectArraySet);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>You must select a target on which an MBean will be deployed from this list of the targets in the current domain on which this item can be deployed. Targets must be either servers or clusters. The deployment will only occur once if deployments overlap.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Template")) {
         getterName = "getTemplate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTemplate";
         }

         currentResult = new PropertyDescriptor("Template", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("Template", currentResult);
         currentResult.setValue("description", "<p>The JMS template from which the distributed destination is derived.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", JMSDistributedTopicMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("createJMSTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the template to create ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DistributedDestinationBean and weblogic.j2ee.descriptor.wl.UnifrormDistributedDestinationBean} ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates JMSTemplateMBean instance off this DistributedDestination</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTemplate");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("destroyJMSTemplate", JMSTemplateMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("template", "The template to delete ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.j2ee.descriptor.wl.DistributedDestinationBean and weblogic.j2ee.descriptor.wl.UnifrormDistributedDestinationBean} ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>deletes JMSTemplate from DistributedDestination</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTemplate");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("createJMSDistributedTopicMember", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new diagnostic deployment that can be targeted to a server</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopicMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("createJMSDistributedTopicMember", String.class, JMSDistributedTopicMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("toclone", "bean which need to be cloned and added to this parent ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>This method is there to support addMember which is relic of old mbean infrastructure but somehow got documented server</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopicMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("destroyJMSDistributedTopicMember", JMSDistributedTopicMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("member", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Delete a diagnostic deployment configuration from the domain.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopicMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("addMember", JMSDistributedTopicMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("Member", "The feature to be added to the Member attribute ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a member to this distributed topic.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Members");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = JMSDistributedTopicMBean.class.getMethod("addTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the Target attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>You can add a target to specify additional servers on which the deployment can be deployed. The targets must be either clusters or servers.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("removeMember", JMSDistributedTopicMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("member", "the JMSDistributedTopicMember to remove from the distributed topic ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the specified member is not a member of this distributed topic")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a member from this distributed topic.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Members");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = JMSDistributedTopicMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the value of the addTarget attribute.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#addTarget")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JMSDistributedTopicMBean.class.getMethod("lookupJMSDistributedTopicMember", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSDistributedTopicMembers");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = JMSDistributedTopicMBean.class.getMethod("destroyJMSDistributedTopicMember", String.class, JMSDistributedTopicMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name "), createParameterDescriptor("member", "JMSDistributedTopicMember ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This method is to support removeMember() which is relic of old mbean infrastructure ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = JMSDistributedTopicMBean.class.getMethod("freezeCurrentValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JMSDistributedTopicMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

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
