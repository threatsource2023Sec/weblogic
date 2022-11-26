package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class SecurityWorkContextBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = SecurityWorkContextBean.class;

   public SecurityWorkContextBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SecurityWorkContextBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SecurityWorkContextBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "Connector 1.6/Cordell");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML security-work-contextType. This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SecurityWorkContextBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CallerPrincipalDefaultMapped")) {
         getterName = "getCallerPrincipalDefaultMapped";
         setterName = null;
         currentResult = new PropertyDescriptor("CallerPrincipalDefaultMapped", SecurityWorkContextBean.class, getterName, setterName);
         descriptors.put("CallerPrincipalDefaultMapped", currentResult);
         currentResult.setValue("description", "<p>Define the default caller principal at app server side.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CallerPrincipalMappings")) {
         getterName = "getCallerPrincipalMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("CallerPrincipalMappings", SecurityWorkContextBean.class, getterName, setterName);
         descriptors.put("CallerPrincipalMappings", currentResult);
         currentResult.setValue("description", "<p>Define a caller principal mapping from EIS side to app server side.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCallerPrincipalMapping");
         currentResult.setValue("creator", "createCallerPrincipalMapping");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupPrincipalDefaultMapped")) {
         getterName = "getGroupPrincipalDefaultMapped";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGroupPrincipalDefaultMapped";
         }

         currentResult = new PropertyDescriptor("GroupPrincipalDefaultMapped", SecurityWorkContextBean.class, getterName, setterName);
         descriptors.put("GroupPrincipalDefaultMapped", currentResult);
         currentResult.setValue("description", "<p>Define the default group principal at app server side.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupPrincipalMappings")) {
         getterName = "getGroupPrincipalMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("GroupPrincipalMappings", SecurityWorkContextBean.class, getterName, setterName);
         descriptors.put("GroupPrincipalMappings", currentResult);
         currentResult.setValue("description", "<p>Define a group principal mapping from EIS side to app server side.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createGroupPrincipalMapping");
         currentResult.setValue("destroyer", "destroyGroupPrincipalMapping");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", SecurityWorkContextBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Gets the \"id\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InboundMappingRequired")) {
         getterName = "isInboundMappingRequired";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInboundMappingRequired";
         }

         currentResult = new PropertyDescriptor("InboundMappingRequired", SecurityWorkContextBean.class, getterName, setterName);
         descriptors.put("InboundMappingRequired", currentResult);
         currentResult.setValue("description", "<p>Indicate if inbound principal mapping is required.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SecurityWorkContextBean.class.getMethod("createCallerPrincipalMapping", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CallerPrincipalMappings");
      }

      mth = SecurityWorkContextBean.class.getMethod("destroyCallerPrincipalMapping", InboundCallerPrincipalMappingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CallerPrincipalMappings");
      }

      mth = SecurityWorkContextBean.class.getMethod("createGroupPrincipalMapping", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GroupPrincipalMappings");
      }

      mth = SecurityWorkContextBean.class.getMethod("destroyGroupPrincipalMapping", InboundGroupPrincipalMappingBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GroupPrincipalMappings");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SecurityWorkContextBean.class.getMethod("lookupCallerPrincipalMapping", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CallerPrincipalMappings");
      }

      mth = SecurityWorkContextBean.class.getMethod("lookupGroupPrincipalMapping", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "GroupPrincipalMappings");
      }

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
