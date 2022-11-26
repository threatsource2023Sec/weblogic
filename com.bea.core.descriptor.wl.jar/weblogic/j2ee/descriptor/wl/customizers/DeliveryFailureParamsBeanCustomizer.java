package weblogic.j2ee.descriptor.wl.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.j2ee.descriptor.wl.TemplateBean;

public interface DeliveryFailureParamsBeanCustomizer extends Customizer {
   TemplateBean getTemplateBean();

   String findSubDeploymentName();
}
