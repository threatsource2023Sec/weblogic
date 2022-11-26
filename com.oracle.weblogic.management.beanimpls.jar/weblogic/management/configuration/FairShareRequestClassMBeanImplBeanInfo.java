package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class FairShareRequestClassMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = FairShareRequestClassMBean.class;

   public FairShareRequestClassMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public FairShareRequestClassMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.FairShareRequestClassMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This mbean defines the fair share value to use for this request class. One of the components of work managers is a request class that indicates the proportional of the total server thread use time this request class gets. The default fair share value is 50. <p> Fair shares are reflected in scheduling logic such that as long as multiple fair shares compete, the average number of threads used by each is in proportion to their fair share. For example, consider we only have two fair shares, A and B, having a fair share of 80 and 20, respectively. During a period in which both fair shares are sufficiently requested, say, zero think time and more clients than threads, the probability that a thread will work on behalf of A or B will tend toward 80% or 20%, respectively. The scheduling logic ensures this even when A tends to use a thread for much longer than B. </p> <p> Fair share values can range from 1 to 1000. The default fair share value is 50. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.FairShareRequestClassMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FairShare")) {
         String getterName = "getFairShare";
         String setterName = null;
         if (!this.readOnly) {
            setterName = "setFairShare";
         }

         currentResult = new PropertyDescriptor("FairShare", FairShareRequestClassMBean.class, getterName, setterName);
         descriptors.put("FairShare", currentResult);
         currentResult.setValue("description", "Fair share value ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(1000));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
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
