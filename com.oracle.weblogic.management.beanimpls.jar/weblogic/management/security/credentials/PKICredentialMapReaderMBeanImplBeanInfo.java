package weblogic.management.security.credentials;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.utils.ListerMBeanImplBeanInfo;

public class PKICredentialMapReaderMBeanImplBeanInfo extends ListerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PKICredentialMapReaderMBean.class;

   public PKICredentialMapReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PKICredentialMapReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.credentials.PKICredentialMapReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.credentials");
      String description = (new String("Provides a set of methods for reading a credential map that matches users and resources to aliases and their corresponding passwords that can then be used to retrieve key information or public certificate information from the configured keystores. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.credentials.PKICredentialMapReaderMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PKICredentialMapReaderMBean.class.getMethod("getKeystoreAlias", String.class, String.class, Boolean.TYPE, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource id that is used to map user names to keystore alias and password. A resource object such as <code>weblogic.security.service.ResourceManager</code> assigns IDs to external sources. "), createParameterDescriptor("userName", "- The username that is mapped to the alias and password. "), createParameterDescriptor("isInitiatorUserName", "- Set true if the initiator name passed in is the username. False otherwise. "), createParameterDescriptor("credAction", "- The credential action for which the mapping is created for. "), createParameterDescriptor("credType", "- The credential type. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException - This exception is thrown if                             the keystore alias is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the keystore alias that is mapped to a username for a particular resource and credential action. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("getCurrentInitiatorName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method or the <code>listMappingsByPattern</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets an username from a list that has been returned from the <code>listMappings</code> or the <code>listMappingsByPattern()</code> method. This method returns the username that corresponds to current location in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("isInitiatorUserName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method or the <code>listMappingsByPattern</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns whether the initiator name from a list that has been returned from the <code>listMappings</code> or the <code>listMappingsByPattern()</code>method is a user name or a group name. Method returns true if the username returned by the getCurrentInitiatorName is a user name. If the initiator name returned is a Group name this method returns false. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("getCurrentCredAction", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> method or the <code>listMappingsByPattern</code>. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets an credential action from a list that has been returned from the <code>listMappings</code> or the <code>listMappingsByPattern()</code> method. This method returns the credential action that corresponds to current location in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("getCurrentCredential", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code>listMappings</code> or the <code>listMappingsByPattern</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the keystore alias from a credentials map that has been returned from the <code>listMappings</code> or the <code>listMappingsByPattern()</code> method. This method returns the keystore alias that corresponds to current location in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("listMappings", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- the resource id that the credential mappings are created for. A resource object such as <code>weblogic.security.service.ResourceManager</code> assigns IDs to external sources. "), createParameterDescriptor("credType", "- The credential type ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a list of usernames, credential actions, keystore aliases and their passwords. Returns  a cursor as a string. Use the <code>getCurrentUserName()</code>,<code>getCurrentCredential()</code>, <code>getCurrentCredAction()</code> methods to get the username, keystore alias and credential action for the current item in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("getCurrentResourceId", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor that has been returned from the <code> listMappingsByPattern</code> method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets the current resource id from a list that has been returned from the and <code>listMappingsByPattern()</code> method. This method returns the resource id that corresponds to current location in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("listMappingsByPattern", String.class, Integer.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceIdPattern", "- the resource id pattern to filter the records to be returned. If you pass null or * the method will not filter and return all records. "), createParameterDescriptor("maxToReturn", "- The maximum number of records to return "), createParameterDescriptor("credType", "- The credential type ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a list of all the configured credential mappings. Returns a cursor as a string. Use the <code>getCurrentUserName()</code>,<code> getCurrentCredAction()</code>, <code> getCurrentResourceId()</code>, <code> getCurrentCredential()</code> methods to get the username, credential action, resource id and keystore alias for the current item in the list. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("listAllCertEntryAliases");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a list of all the public certificate aliases currently configured in the keystore. Console can call this method to display a list of all possible certificate aliases. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("listAllKeypairEntryAliases");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a list of all the key pair aliases that currently configured in the keystore. Console can call this method to display a list of all possible keypair aliases. ");
         currentResult.setValue("role", "operation");
      }

      mth = PKICredentialMapReaderMBean.class.getMethod("getCertificate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("keystoreAlias", "- The keystore alias. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Certificate");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "For a certificate entry this method will return the certificate corresponding to the alias. For a keyentry it will return the first Certificate entry in a CertificateChain. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Certificate");
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
