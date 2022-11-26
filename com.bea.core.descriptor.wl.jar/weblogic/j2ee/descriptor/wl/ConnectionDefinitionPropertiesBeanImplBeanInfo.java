package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ConnectionDefinitionPropertiesBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectionDefinitionPropertiesBean.class;

   public ConnectionDefinitionPropertiesBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionDefinitionPropertiesBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML connection-definition-propertiesType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ConnectionDefinitionPropertiesBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AuthenticationMechanisms")) {
         getterName = "getAuthenticationMechanisms";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthenticationMechanisms", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("AuthenticationMechanisms", currentResult);
         currentResult.setValue("description", "Gets array of all \"authentication-mechanism\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAuthenticationMechanism");
         currentResult.setValue("creator", "createAuthenticationMechanism");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Logging")) {
         getterName = "getLogging";
         setterName = null;
         currentResult = new PropertyDescriptor("Logging", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("Logging", currentResult);
         currentResult.setValue("description", "Gets the \"logging\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolParams")) {
         getterName = "getPoolParams";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolParams", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("PoolParams", currentResult);
         currentResult.setValue("description", "Gets the \"pool-params\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Gets the \"properties\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResAuth")) {
         getterName = "getResAuth";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResAuth";
         }

         currentResult = new PropertyDescriptor("ResAuth", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("ResAuth", currentResult);
         currentResult.setValue("description", "Gets the \"res-auth\" element ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionSupport")) {
         getterName = "getTransactionSupport";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionSupport";
         }

         currentResult = new PropertyDescriptor("TransactionSupport", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("TransactionSupport", currentResult);
         currentResult.setValue("description", "Gets the \"transaction-support\" element ");
         setPropertyDescriptorDefault(currentResult, "NoTransaction");
         currentResult.setValue("legalValues", new Object[]{"NoTransaction", "LocalTransaction", "XATransaction"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReauthenticationSupport")) {
         getterName = "isReauthenticationSupport";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReauthenticationSupport";
         }

         currentResult = new PropertyDescriptor("ReauthenticationSupport", ConnectionDefinitionPropertiesBean.class, getterName, setterName);
         descriptors.put("ReauthenticationSupport", currentResult);
         currentResult.setValue("description", "Gets the \"reauthentication-support\" element ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConnectionDefinitionPropertiesBean.class.getMethod("createAuthenticationMechanism");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthenticationMechanisms");
      }

      mth = ConnectionDefinitionPropertiesBean.class.getMethod("destroyAuthenticationMechanism", AuthenticationMechanismBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthenticationMechanisms");
      }

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
