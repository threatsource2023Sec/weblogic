package weblogic.j2ee.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class DeploymentPlanBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      DeploymentPlanBean beanForUse = (DeploymentPlanBean)bean;
      return new DeploymentPlanBeanCustomizer(beanForUse);
   }
}
