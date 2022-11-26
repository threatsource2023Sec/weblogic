package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class PoolParamsBeanImplBeanInfo extends ConnectionPoolParamsBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PoolParamsBean.class;

   public PoolParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PoolParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.PoolParamsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML pool-paramsType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.PoolParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionReserveTimeoutSeconds")) {
         getterName = "getConnectionReserveTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionReserveTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", PoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionReserveTimeoutSeconds", currentResult);
         currentResult.setValue("description", "Gets the \"connection-reserve-timeout-seconds\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumWaiters")) {
         getterName = "getHighestNumWaiters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHighestNumWaiters";
         }

         currentResult = new PropertyDescriptor("HighestNumWaiters", PoolParamsBean.class, getterName, setterName);
         descriptors.put("HighestNumWaiters", currentResult);
         currentResult.setValue("description", "Gets the \"highest-num-waiters\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCapacity";
         }

         currentResult = new PropertyDescriptor("MaxCapacity", PoolParamsBean.class, getterName, setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "Gets the \"max-capacity\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MatchConnectionsSupported")) {
         getterName = "isMatchConnectionsSupported";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMatchConnectionsSupported";
         }

         currentResult = new PropertyDescriptor("MatchConnectionsSupported", PoolParamsBean.class, getterName, setterName);
         descriptors.put("MatchConnectionsSupported", currentResult);
         currentResult.setValue("description", "Gets the \"match-connections-supported\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseFirstAvailable")) {
         getterName = "isUseFirstAvailable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseFirstAvailable";
         }

         currentResult = new PropertyDescriptor("UseFirstAvailable", PoolParamsBean.class, getterName, setterName);
         descriptors.put("UseFirstAvailable", currentResult);
         currentResult.setValue("description", "Gets the \"use-first-available\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
