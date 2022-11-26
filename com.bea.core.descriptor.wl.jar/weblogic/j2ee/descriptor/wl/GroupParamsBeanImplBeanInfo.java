package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class GroupParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GroupParamsBean.class;

   public GroupParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GroupParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.GroupParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("The group params bean represents items that may be templated based on the sub-deployment of the host DestinationBean.  While many attributes of a Destination can be templated regardless of where the Destination is targeted, others cannot.  Those elements that must be templated based on the target group of the destination are represented in this bean.  A Destination will use the values in a GroupParamsBean if and only if the corresponding value is not explicitly set in the DestinationBean AND the sub-deployment-name of the Destination matches the sub-deployment-name attribute of this bean AND this GroupParamsBean is a child of the template pointed to by the Destination. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.GroupParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ErrorDestination")) {
         getterName = "getErrorDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setErrorDestination";
         }

         currentResult = new PropertyDescriptor("ErrorDestination", GroupParamsBean.class, getterName, setterName);
         descriptors.put("ErrorDestination", currentResult);
         currentResult.setValue("description", "<p> Gets the name of the error destination that should be used for members who have the same sub-deployment-name.  If this value is not set then the value from the templates DeliveryFailureParamsBean will be used if it is set. </p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubDeploymentName")) {
         getterName = "getSubDeploymentName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSubDeploymentName";
         }

         currentResult = new PropertyDescriptor("SubDeploymentName", GroupParamsBean.class, getterName, setterName);
         descriptors.put("SubDeploymentName", currentResult);
         currentResult.setValue("description", "<p>The name of the subdeployment that template parameters apply to. A subdeployment with the specified name must exist in the topic or queue for the parameters to apply. </p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("key", Boolean.TRUE);
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
