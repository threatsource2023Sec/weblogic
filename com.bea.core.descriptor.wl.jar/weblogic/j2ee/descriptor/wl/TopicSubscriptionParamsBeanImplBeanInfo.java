package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class TopicSubscriptionParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TopicSubscriptionParamsBean.class;

   public TopicSubscriptionParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TopicSubscriptionParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TopicSubscriptionParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>These parameters allow the administrator to control the behavior of topic subscriptions, such as the maximum number of messages that can be stored in a subscription.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TopicSubscriptionParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MessagesLimitOverride")) {
         getterName = "getMessagesLimitOverride";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesLimitOverride";
         }

         currentResult = new PropertyDescriptor("MessagesLimitOverride", TopicSubscriptionParamsBean.class, getterName, setterName);
         descriptors.put("MessagesLimitOverride", currentResult);
         currentResult.setValue("description", "<p>The maximum number of messages that can be stored in a topic subscription.</p>  <p>If a new message arrives on subscriptions that have reached the specified limit, then the first-most available messages on these subscriptions will be deleted to make room for the new message. Subscription messages are immune from this deletion if they're participating in a pending transaction, have already been passed to a consumer and are awaiting acknowledgement, or if they are part of a Unit-of-Work that is still waiting to accumulate all of its messages. Note that if all messages are immune from deletion, then a new message can cause a subscription size to exceed its limit.</p>  <p>A value of -1 means no limit override.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemplateBean")) {
         getterName = "getTemplateBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TemplateBean", TopicSubscriptionParamsBean.class, getterName, setterName);
         descriptors.put("TemplateBean", currentResult);
         currentResult.setValue("description", "<p>Finds the template bean for this destination.</p> ");
         currentResult.setValue("relationship", "reference");
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
