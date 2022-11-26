package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class COMMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = COMMBean.class;

   public COMMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public COMMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.COMMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean represents the server-wide configuration of COM ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.COMMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("NTAuthHost")) {
         getterName = "getNTAuthHost";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNTAuthHost";
         }

         currentResult = new PropertyDescriptor("NTAuthHost", COMMBean.class, getterName, setterName);
         descriptors.put("NTAuthHost", currentResult);
         currentResult.setValue("description", "<p>The address of the primary domain controller this server uses for authenticating clients. (If not specified, COM clients will not be authenticated.)</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApartmentThreaded")) {
         getterName = "isApartmentThreaded";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setApartmentThreaded";
         }

         currentResult = new PropertyDescriptor("ApartmentThreaded", COMMBean.class, getterName, setterName);
         descriptors.put("ApartmentThreaded", currentResult);
         currentResult.setValue("description", "<p>Controls the flag that is used to initialize COM in native mode.</p>  <p>By default, when jCOM initializes COM in native mode, it starts COM with the <code>COINIT_MULTITHREADED</code>. This causes COM to use Multi-Threaded Apartment (MTA) thread model. In the MTA model, calls to an object are not synchronized by COM. Multiple clients can concurrently call an object that supports this model on different threads, and the object must provide synchronization in its interface/method implementations using synchronization objects such as events, mutexes, semaphores, etc. MTA objects can receive concurrent calls from multiple out-of-process clients through a pool of COM-created threads belonging to the object's process.</p>  <p>If the server logs a Class Not Registered Message when starting COM in native mode, try setting this property. This will cause jCOM to start COM in native mode, using <code>COINIT_APARTMENTTHREADED</code> option instead of the <code>COINIT_MULTITHREADED</code> option. In a component that is marked as Apartment Threaded, each method of that component will execute on a thread that is associated with that component. This separates the methods into their own \"Apartments\", with each instance of a component corresponding to one apartment. While there is only one thread inside of a component, each instance of that component will have its own thread apartment.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemoryLoggingEnabled")) {
         getterName = "isMemoryLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemoryLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("MemoryLoggingEnabled", COMMBean.class, getterName, setterName);
         descriptors.put("MemoryLoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server should log memory usage.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NativeModeEnabled")) {
         getterName = "isNativeModeEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNativeModeEnabled";
         }

         currentResult = new PropertyDescriptor("NativeModeEnabled", COMMBean.class, getterName, setterName);
         descriptors.put("NativeModeEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server should use native DLLs to allow Java objects to interact with COM objects. (Supported on Windows only.)</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrefetchEnums")) {
         getterName = "isPrefetchEnums";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrefetchEnums";
         }

         currentResult = new PropertyDescriptor("PrefetchEnums", COMMBean.class, getterName, setterName);
         descriptors.put("PrefetchEnums", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this server should prefetch the next element in a <code>java.lang.Enumeration</code> (that had been improperly converted from a COM <code>VariantEnumeration</code> type) so the correct value is returned when the <code>hasMoreElements()</code> method is called.</p>  <p>Some COM methods return a COM VariantEnumeration type. The java2com tool automatically converts the returned type into a java.lang.Enumeration. This is not a perfect match since COM enumerations have no equivalent to the <code>hasMoreElements()</code> call. The client must continue to call <code>nextElement</code> until a <code>NoSuchElementException</code> occurs. Setting this property will cause jCOM to prefetch the next element in behind the scenes and return the correct value when hasMoreElements is called.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VerboseLoggingEnabled")) {
         getterName = "isVerboseLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerboseLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("VerboseLoggingEnabled", COMMBean.class, getterName, setterName);
         descriptors.put("VerboseLoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether verbose logging is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
