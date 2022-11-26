package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;

public class PortablePartitionManagerMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = PortablePartitionManagerMBean.class;

   public PortablePartitionManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PortablePartitionManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.PortablePartitionManagerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.PortablePartitionManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", PortablePartitionManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", PortablePartitionManagerMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", PortablePartitionManagerMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", PortablePartitionManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", PortablePartitionManagerMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
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
      Method mth = PortablePartitionManagerMBean.class.getMethod("migrateResourceGroup", TargetMBean.class, TargetMBean.class, TargetMBean.class, Long.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "VirtualTargetMBean The VirtualTarget associated with the ResourceGroup. "), createParameterDescriptor("currentTarget", "The current physical target for the Resource Group which can be a server or cluster. This is what is got from the targetMbean of the VirtualTarget associated with the ResourceGroup. "), createParameterDescriptor("newTarget", "The new physical target to which the ResourceGroup is being migrated which can be a server or cluster. This will be set as the targetMbean of the VirtualTarget associated with the ResourceGroup. "), createParameterDescriptor("timeout", "Timeout for the resource group migration task. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Migrates resource group from one physical target to another. The new physical target specified is used as the target for the VirtualTarget associated with the ResourceGroup and can be a cluster or a server. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("importPartition", String.class, String.class, Boolean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("archiveFileName", "full path String to the archive to import as seen by the admin server (not the client). importPartition will also look for a file, <code>partitionName</code>-attributes.json, in the same directory as the partition archive. If it is found then the values in that file will override those in the partition archive. "), createParameterDescriptor("partitionName", "name of the newly created partition. Defaults to name specified in partition archive "), createParameterDescriptor("createNew", "Boolean flag controlling creating of ResourceGroupTemplate during importPartition. If no flag is specified importPartition fails if there is an existing RGT with same name. If false use the existing RGT if there is a clash during import. If true create a new RGT if there is a clash "), createParameterDescriptor("keyFile", "full path String for user provided file containing the string that was used as the encryption key for encrypted attributes during export ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Imports a partition into existing domain, from an archive exported using exportPartition </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("importPartition", String.class, Boolean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("archiveFileName", "full path String to the archive to import as seen by the admin server (not the client). importPartition will also look for a file, <code>partitionName</code>-attributes.json, in the same directory as the partition archive. If it is found then the values in that file will override those in the partition archive. "), createParameterDescriptor("createNew", "Boolean flag controlling creating of ResourceGroupTemplate during importPartition. If no flag is specified importPartition fails if there is an existing RGT with same name. If false use the existing RGT if there is a clash during import. If true create a new RGT if there is a clash ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Imports a partition into existing domain, from an archive exported using exportPartition </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("importPartition", String.class, Boolean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("archiveFileName", "full path String to the archive to import as seen by the admin server (not the client). importPartition will also look for a file, <code>partitionName</code>-attributes.json, in the same directory as the partition archive. If it is found then the values in that file will override those in the partition archive. "), createParameterDescriptor("createNew", "Boolean flag controlling creating of ResourceGroupTemplate during importPartition. If no flag is specified importPartition fails if there is an existing RGT with same name. If false use the existing RGT if there is a clash during import. If true create a new RGT if there is a clash "), createParameterDescriptor("keyFile", "full path String for user provided file containing the string that was used as the encryption key for encrypted attributes during export ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Imports a partition into existing domain, from an archive exported using exportPartition </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("importPartition", String.class, String.class, Boolean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("archiveFileName", "full path String to the archive to import as seen by the admin server (not the client). importPartition will also look for a file, <code>partitionName</code>-attributes.json, in the same directory as the partition archive. If it is found then the values in that file will override those in the partition archive. "), createParameterDescriptor("partitionName", "name of the newly created partition. Defaults to name specified in partition archive "), createParameterDescriptor("createNew", "Boolean flag controlling creating of ResourceGroupTemplate during importPartition. If no flag is specified importPartition fails if there is an existing RGT with same name. If false use the existing RGT if there is a clash during import. If true create a new RGT if there is a clash ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Imports a partition into existing domain, from an archive exported using exportPartition </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("exportPartition", String.class, String.class, Boolean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "is the name of the partition to export "), createParameterDescriptor("expArchPath", "full path String to the archive file to create as seen by the admin server (not the client). In addition to creating the archive exportPartition will place a copy of the <code>partitionName</code>-attributes.json file in the same directory as the partition archive. "), createParameterDescriptor("includeAppsNLibs", "is a boolean that influences how application binaries are handled. Defaults to true "), createParameterDescriptor("keyFile", "full path String for user provided file containing a string to use as the encryption key for encrypted attributes ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Exports a partition from current domain into an archive, to be used for importing the partition into a different domain </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("exportPartition", String.class, String.class, Boolean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "is the name of the partition to export "), createParameterDescriptor("expArchPath", "full path String to the archive file to create as seen by the admin server (not the client). In addition to creating the archive exportPartition will place a copy of the <code>partitionName</code>-attributes.json file in the same directory as the partition archive. "), createParameterDescriptor("includeAppsNLibs", "is a boolean that influences how application binaries are handled. Defaults to true ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Exports a partition from current domain into an archive, to be used for importing the partition into a different domain </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("exportPartition", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "is the name of the partition to export "), createParameterDescriptor("expArchPath", "full path String to the archive file to create as seen by the admin server (not the client). In addition to creating the archive exportPartition will place a copy of the <code>partitionName</code>-attributes.json file in the same directory as the partition archive. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Exports a partition from current domain into an archive, to be used for importing the partition into a different domain </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      mth = PortablePartitionManagerMBean.class.getMethod("exportPartition", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "is the name of the partition to export "), createParameterDescriptor("expArchPath", "full path String to the archive file to create as seen by the admin server (not the client). In addition to creating the archive exportPartition will place a copy of the <code>partitionName</code>-attributes.json file in the same directory as the partition archive. "), createParameterDescriptor("keyFile", "full path String for user provided file containing a string to use as the encryption key for encrypted attributes ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Exception")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Exports a partition from current domain into an archive, to be used for importing the partition into a different domain </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
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
