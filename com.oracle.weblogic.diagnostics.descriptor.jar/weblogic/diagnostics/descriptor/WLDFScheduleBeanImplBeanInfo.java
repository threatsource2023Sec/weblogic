package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFScheduleBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFScheduleBean.class;

   public WLDFScheduleBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFScheduleBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFScheduleBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p> Defines a timer schedule for use with WLDF policies and actions and Harvester. </p>  <p> Each field uses syntax as defined by the EJB Timer <a href= \"http://docs.oracle.com/javaee/7/api/javax/ejb/ScheduleExpression.html\" >ScheduleExpression</a> class. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFScheduleBean");
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

         currentResult = new PropertyDescriptor("DayOfMonth", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("DayOfMonth", currentResult);
         currentResult.setValue("description", "<p>Indicates the day of the month for the schedule.</p>  <p>Defaults to \"*\" (every day).</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DayOfWeek")) {
         getterName = "getDayOfWeek";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDayOfWeek";
         }

         currentResult = new PropertyDescriptor("DayOfWeek", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("DayOfWeek", currentResult);
         currentResult.setValue("description", "<p>Indicates the day of the week for the schedule.</p>  <p>Defaults to \"*\" (every day).</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Hour")) {
         getterName = "getHour";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHour";
         }

         currentResult = new PropertyDescriptor("Hour", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("Hour", currentResult);
         currentResult.setValue("description", "<p>Indicates the hour(s) of the day for the schedule.</p>  <p>Defaults to \"*\" (every hour).</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Minute")) {
         getterName = "getMinute";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinute";
         }

         currentResult = new PropertyDescriptor("Minute", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("Minute", currentResult);
         currentResult.setValue("description", "<p>Indicates the minutes of the hour for the schedule.</p>  <p>Defaults to every five minutes of the hour.</p> ");
         setPropertyDescriptorDefault(currentResult, WLDFWatchCustomizer.getDefaultMinuteSchedule());
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Month")) {
         getterName = "getMonth";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMonth";
         }

         currentResult = new PropertyDescriptor("Month", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("Month", currentResult);
         currentResult.setValue("description", "<p>Indicates the month for the schedule.</p>  <p>Defaults to \"*\" (every month).</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Second")) {
         getterName = "getSecond";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecond";
         }

         currentResult = new PropertyDescriptor("Second", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("Second", currentResult);
         currentResult.setValue("description", "<p>Indicates the second(s) of the minute for the schedule.</p>  <p>Defaults to \"0\" (at the \"0\" second of each minute).</p> ");
         setPropertyDescriptorDefault(currentResult, "0");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Timezone")) {
         getterName = "getTimezone";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimezone";
         }

         currentResult = new PropertyDescriptor("Timezone", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("Timezone", currentResult);
         currentResult.setValue("description", "<p>Indicates the time zone for the schedule.</p>  <p>Defaults to the default time zone for the local VM.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Year")) {
         getterName = "getYear";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setYear";
         }

         currentResult = new PropertyDescriptor("Year", WLDFScheduleBean.class, getterName, setterName);
         descriptors.put("Year", currentResult);
         currentResult.setValue("description", "<p>Indicates the year for the schedule.</p>  <p>Defaults to \"*\" (every year).</p> ");
         setPropertyDescriptorDefault(currentResult, "*");
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
