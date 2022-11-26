package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SecurityParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SecurityParamsBean.class;

   public SecurityParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SecurityParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SecurityParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Some clients may wish to customize the security information associated with them. They can use the security parameters bean to do so. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SecurityParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SecurityPolicy")) {
         getterName = "getSecurityPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityPolicy";
         }

         currentResult = new PropertyDescriptor("SecurityPolicy", SecurityParamsBean.class, getterName, setterName);
         descriptors.put("SecurityPolicy", currentResult);
         currentResult.setValue("description", "<p>Get the security policy used for the connection factory. </p>  <p>The default security policy is Thread-Based. </p>  <p>This attribute is dynamic. It can be changed at any time. However, changing the value does not affect existing connections. It only affects new connections made with this connection factory.</p> ");
         setPropertyDescriptorDefault(currentResult, "ThreadBased");
         currentResult.setValue("legalValues", new Object[]{"ThreadBased", "ObjectBasedDelegated", "ObjectBasedAnonymous", "ObjectBasedThread"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("AttachJMSXUserId")) {
         getterName = "isAttachJMSXUserId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAttachJMSXUserId";
         }

         currentResult = new PropertyDescriptor("AttachJMSXUserId", SecurityParamsBean.class, getterName, setterName);
         descriptors.put("AttachJMSXUserId", currentResult);
         currentResult.setValue("description", "<p>Specifies whether WebLogic Server attaches the authenticated user name to sent messages.</p>  <p>If enabled, the system will attach the authenticated username onto sent messages if the destination supports this behavior. The username is placed in the JMSXUserID user property. You should consult the JMSXUserID documentation in <i>Programming WebLogic JMS</i> before using this feature.</p>  <p>When dynamically changed this will affect all connections made using this connection factory after the change was made.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.j2ee.descriptor.wl.DestinationBean#getAttachSender")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
