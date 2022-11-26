package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DestinationKeyBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DestinationKeyBean.class;

   public DestinationKeyBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DestinationKeyBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DestinationKeyBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Destination Key beans control the sorting criteria of JMS destinations ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DestinationKeyBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("KeyType")) {
         getterName = "getKeyType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyType";
         }

         currentResult = new PropertyDescriptor("KeyType", DestinationKeyBean.class, getterName, setterName);
         descriptors.put("KeyType", currentResult);
         currentResult.setValue("description", "<p>The expected property type for this destination key. </p>  Gets the \"key-type\" element ");
         setPropertyDescriptorDefault(currentResult, "String");
         currentResult.setValue("legalValues", new Object[]{"Boolean", "Byte", "Short", "Int", "Long", "Float", "Double", "String"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Property")) {
         getterName = "getProperty";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProperty";
         }

         currentResult = new PropertyDescriptor("Property", DestinationKeyBean.class, getterName, setterName);
         descriptors.put("Property", currentResult);
         currentResult.setValue("description", "<p>Specifies a message property name or the name of a message header field on which to sort messages. Message header field keys ignore the key type and reference message header fields rather than message properties. </p>  <p><i>Note:</i> For better performance, use message header fields as sorting keys, rather than message properties. </p>  <p><i>Range of Values:</i> </p>  <p>The JMS Property name (including user properties) or message header fields that can be sorted on are:</p> <ul> <li> JMSMessageID</li> <li> JMSTimestamp</li> <li> JMSCorrelationID</li> <li> JMSPriority</li> <li> JMSExpiration</li> <li> JMSType</li> <li> JMSRedelivered</li> <li> JMSDeliveryTime</li> <li> JMS_BEA_Size</li> <li> JMS_BEA_UnitOfOrder</li> </ul>   <p>This attribute is not dynamically configurable. Gets the \"property\" element</p> ");
         setPropertyDescriptorDefault(currentResult, "JMSMessageID");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SortOrder")) {
         getterName = "getSortOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSortOrder";
         }

         currentResult = new PropertyDescriptor("SortOrder", DestinationKeyBean.class, getterName, setterName);
         descriptors.put("SortOrder", currentResult);
         currentResult.setValue("description", "<p>The direction (Ascending or Descending) in which this key will sort messages. </p>  <p>Selecting the <i>Ascending</i> option for the JMSMessageID property implies a FIFO (first in, first out) sort order (the default for destinations). Select the <i>Descending</i> option for a LIFO (last in, first out) sort order. </p>  <p>This attribute is not dynamically configurable. </p>  <p>Gets the \"sort-order\" element.</p> ");
         setPropertyDescriptorDefault(currentResult, "Ascending");
         currentResult.setValue("legalValues", new Object[]{"Ascending", "Descending"});
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
