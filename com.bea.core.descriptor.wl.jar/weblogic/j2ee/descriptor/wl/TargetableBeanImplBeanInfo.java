package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class TargetableBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TargetableBean.class;

   public TargetableBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TargetableBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TargetableBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("All JMS beans that can be targeted extend the targetable bean.  It represents an entity that may be targeted at one or more entities in the domain. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TargetableBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("SubDeploymentName")) {
         getterName = "getSubDeploymentName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubDeploymentName";
         }

         currentResult = new PropertyDescriptor("SubDeploymentName", TargetableBean.class, getterName, setterName);
         descriptors.put("SubDeploymentName", currentResult);
         currentResult.setValue("description", "<p>Gets the name of the sub-deployment to use when targeting this entity</p>  <p> Entities are targeted using a sub-deployment with this name.  The targets of the sub-deployment will be the targets of this entity. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultTargetingEnabled")) {
         getterName = "isDefaultTargetingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultTargetingEnabled";
         }

         currentResult = new PropertyDescriptor("DefaultTargetingEnabled", TargetableBean.class, getterName, setterName);
         descriptors.put("DefaultTargetingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this JMS resource defaults to the parent module's targeting or uses the subdeployment targeting mechanism.</p>  <p>When set to true, this resource implicitly inherits the targeting of its parent module. When set to false, this resource gets targeted based its subdeployment's targets, if one is specified. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
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
