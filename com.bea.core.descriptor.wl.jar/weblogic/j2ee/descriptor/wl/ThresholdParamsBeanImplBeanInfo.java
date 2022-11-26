package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ThresholdParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ThresholdParamsBean.class;

   public ThresholdParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ThresholdParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ThresholdParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Thresholds are a point that must be exceeded in order to produce a given effect. These action points may cause logging or flow control or other actions, as defined by the specific points whose values have been exceeded.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ThresholdParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BytesHigh")) {
         getterName = "getBytesHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesHigh";
         }

         currentResult = new PropertyDescriptor("BytesHigh", ThresholdParamsBean.class, getterName, setterName);
         descriptors.put("BytesHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold (total number of bytes in this destination) that triggers logging or flow control events. The value of java.lang.Long.MAX_VALUE disables logging and flow control events for the destination.</p>  <p>If the number of bytes exceeds this threshold, the triggered events are:</p>  <ul> <li> <p>Log Messages</p>  <p>A message is logged on the server indicating a high threshold condition.</p> </li>  <li> <p>Flow Control</p>  <p>If flow control is enabled, the destination becomes armed and instructs producers to begin decreasing their message flow.</p> </li> </ul>  <p><b>Range of Values:</b> &lt;= BytesMaximum; &gt;BytesThresholdLow</p>  <p>Any change to this threshold affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesLow")) {
         getterName = "getBytesLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesLow";
         }

         currentResult = new PropertyDescriptor("BytesLow", ThresholdParamsBean.class, getterName, setterName);
         descriptors.put("BytesLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold (total number of bytes in this destination) that triggers logging or flow control events. The value of java.lang.Long.MAX_VALUE disables logging and flow control events for the destination.</p>  <p>If the number of bytes falls below this threshold, the triggered events are:</p>  <ul> <li> <p>Log Messages</p>  <p>A message is logged on the server indicating a low threshold condition.</p> </li>  <li> <p>Flow Control</p>  <p>If flow control is enabled, the destination becomes disarmed and instructs producers to begin increasing their message flow.</p> </li> </ul>  <p><b>Range of Values:</b> &lt; BytesThresholdHigh</p>  <p>Any change to this threshold affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesHigh")) {
         getterName = "getMessagesHigh";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesHigh";
         }

         currentResult = new PropertyDescriptor("MessagesHigh", ThresholdParamsBean.class, getterName, setterName);
         descriptors.put("MessagesHigh", currentResult);
         currentResult.setValue("description", "<p>The upper threshold (total number of messages in this destination) that triggers logging or flow control events. The value of java.lang.Long.MAX_VALUE disables logging and flow control events for the destination.</p>  <p>If the number of messages exceeds this threshold, the triggered events are:</p>  <ul> <li> <p>Log Messages</p>  <p>A message is logged on the server indicating a high threshold condition.</p> </li>  <li> <p>Flow Control</p>  <p>If flow control is enabled, the destination becomes armed and instructs producers to begin decreasing their message flow.</p> </li> </ul>  <p><b>Range of Values:</b> &lt;= MessagesMaximum; &gt;MessagesThresholdLow</p>  <p>Any change to this threshold affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesLow")) {
         getterName = "getMessagesLow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesLow";
         }

         currentResult = new PropertyDescriptor("MessagesLow", ThresholdParamsBean.class, getterName, setterName);
         descriptors.put("MessagesLow", currentResult);
         currentResult.setValue("description", "<p>The lower threshold (total number of messages in this destination) that triggers logging or flow control events. The value of java.lang.Long.MAX_VALUE disables logging and flow control events for the destination.</p>  <p>If the number of messages falls below this threshold, the triggered events are:</p>  <ul> <li> <p>Log Messages</p>  <p>A message is logged on the server indicating a low threshold condition.</p> </li>  <li> <p>Flow Control</p>  <p>If flow control is enabled, the destination becomes disarmed and instructs producers to begin increasing their message flow.</p> </li> </ul>  <p><b>Range of Values:</b> &lt; MessagesThresholdHigh</p>  <p>Any change to this threshold affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TemplateBean")) {
         getterName = "getTemplateBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TemplateBean", ThresholdParamsBean.class, getterName, setterName);
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
