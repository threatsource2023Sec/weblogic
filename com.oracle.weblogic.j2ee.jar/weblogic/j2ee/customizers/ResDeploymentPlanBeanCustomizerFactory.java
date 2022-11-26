package weblogic.j2ee.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;

public class ResDeploymentPlanBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      ResourceDeploymentPlanBean beanForUse = (ResourceDeploymentPlanBean)bean;
      return new ResDeploymentPlanBeanCustomizer(beanForUse);
   }
}
