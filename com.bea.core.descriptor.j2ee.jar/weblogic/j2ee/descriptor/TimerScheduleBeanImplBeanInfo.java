package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class TimerScheduleBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = TimerScheduleBean.class;

   public TimerScheduleBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TimerScheduleBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.TimerScheduleBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.TimerScheduleBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DayOfMonth")) {
         getterName = "getDayOfMonth";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDayOfMonth";
         }

         currentResult = new PropertyDescriptor("DayOfMonth", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("DayOfMonth", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DayOfWeek")) {
         getterName = "getDayOfWeek";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDayOfWeek";
         }

         currentResult = new PropertyDescriptor("DayOfWeek", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("DayOfWeek", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Hour")) {
         getterName = "getHour";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHour";
         }

         currentResult = new PropertyDescriptor("Hour", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("Hour", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Minute")) {
         getterName = "getMinute";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinute";
         }

         currentResult = new PropertyDescriptor("Minute", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("Minute", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Month")) {
         getterName = "getMonth";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMonth";
         }

         currentResult = new PropertyDescriptor("Month", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("Month", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Second")) {
         getterName = "getSecond";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecond";
         }

         currentResult = new PropertyDescriptor("Second", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("Second", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Year")) {
         getterName = "getYear";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setYear";
         }

         currentResult = new PropertyDescriptor("Year", TimerScheduleBean.class, getterName, setterName);
         descriptors.put("Year", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "*");
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
