package weblogic.management.mbeanservers.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;
import weblogic.management.mbeanservers.MBeanTypeService;

public class MBeanTypeServiceImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = MBeanTypeService.class;

   public MBeanTypeServiceImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MBeanTypeServiceImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.internal.MBeanTypeServiceImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.internal");
      String description = (new String("<p>Provides operations for discovering the attributes and operations of an MBean type that has not yet been instantiated.</p>  <p>The <code>javax.management.ObjectName</code> of this MBean is \"<code>com.bea:Name=MBeanTypeService,Type=weblogic.management.mbeanservers.MBeanTypeService</code>\".</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.MBeanTypeService");
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
      Method mth = MBeanTypeService.class.getMethod("getMBeanInfo", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beanInterface", "The fully-qualified interface name of the MBean. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the MBean info object for the specified interface.</p>  <p>For example,<br/> <code>MBeanServerConnection.invoke(MBeanTypeServiceMBean, \"getMBeanInfo\",</code><br/> <code>      new Object[] { \"weblogic.security.providers.authorization.DefaultAuthorizationProviderMBean\" }</code><br> <code>      new String[] { \"java.lang.String\" });</code> </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MBeanTypeService.class.getMethod("getSubtypes", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("baseInterface", "The fully-qualified interface name of the base MBean. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the names of all MBean types that extend or implement the specified MBean.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MBeanTypeService.class.getMethod("validateAttribute", String.class, Attribute.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beanInterface", "The fully-qualified interface name of the MBean that contains the attribute. "), createParameterDescriptor("attribute", "The name of the attribute and a proposed value. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("AttributeNotFoundException     if the attribute is not specified for the interface."), BeanInfoHelper.encodeEntities("InvalidAttributeValueException if the value violates any of the constraints"), BeanInfoHelper.encodeEntities("MBeanException                 if the interface is not recognized."), BeanInfoHelper.encodeEntities("ReflectionException            if the attribute type or the bean interface cannot be loaded.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Validates that the specified attribute value complies with the contraints for that attribute.</p> <p>This operation evaluates the following types of constraints:</p> <ul> <li>If the value you specify falls within an allowed minimum or maximum range.</li> <li>If the value you specify is one of a set of enumerated allowed values.</li> <li>If you pass a null value, this operation evaluates whether the attribute is allowed to contain a null value.</li> </ul> <p>There are two signatures for this operation. One takes the interface name of an MBean type and the other takes the <code>javax.management.ObjectName</code> of an MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MBeanTypeService.class.getMethod("validateAttributes", String.class, AttributeList.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beanInterface", "The fully-qualified interface name of the MBean that contains the attributes. "), createParameterDescriptor("attributes", "The names of the attributes and proposed values. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("MBeanException                 if the interface is not recognized."), BeanInfoHelper.encodeEntities("ReflectionException            if the attribute type or the bean interface cannot be loaded.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Validates a set of attributes with a single invocation.</p> <p>If all of the attribute values are valid, this operation returns an empty <code>AttributeList</code>. For each invalid attribute value, operation stores an exception the <code>AttributeList</code> that is returned.</p> <p>There are two signatures for this operation. One takes the interface name of an MBean type and the other takes the <code>javax.management.ObjectName</code> of an MBean instance.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#validateAttribute")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = MBeanTypeService.class.getMethod("validateAttribute", ObjectName.class, Attribute.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beanInstance", "An MBean instance. "), createParameterDescriptor("attribute", "The name of the attribute and a proposed value. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("AttributeNotFoundException     if the attribute is not specified for the interface."), BeanInfoHelper.encodeEntities("InvalidAttributeValueException if the value violates any of the constraints"), BeanInfoHelper.encodeEntities("MBeanException                 if the interface is not recognized."), BeanInfoHelper.encodeEntities("ReflectionException            if the attribute type or the bean interface cannot be loaded.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Validates that the specified attribute value complies with the contraints for that attribute.</p> <p>This operation evaluates the following types of constraints:</p> <ul> <li>If the value you specify falls within an allowed minimum or maximum range.</li> <li>If the value you specify is one of a set of enumerated allowed values.</li> <li>If you pass a null value, this operation evaluates whether the attribute is allowed to contain a null value.</li> </ul> <p>There are two signatures for this operation. One takes the interface name of an MBean type and the other takes the <code>javax.management.ObjectName</code> of an MBean instance.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = MBeanTypeService.class.getMethod("validateAttributes", ObjectName.class, AttributeList.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("beanInstance", "An MBean instance. "), createParameterDescriptor("attributes", "the names of the attributes and a proposed values. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("MBeanException                 if the interface is not recognized."), BeanInfoHelper.encodeEntities("ReflectionException            if the attribute type or the bean interface cannot be loaded.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Validates a set of attributes with a single invocation.</p> <p>If all of the attribute values are valid, this operation returns an empty <code>AttributeList</code>. For each invalid attribute value, operation stores an exception the <code>AttributeList</code> that is returned.</p> <p>There are two signatures for this operation. One takes the interface name of an MBean type and the other takes the <code>javax.management.ObjectName</code> of an MBean instance.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#validateAttributes(String beanInterface, AttributeList)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
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
