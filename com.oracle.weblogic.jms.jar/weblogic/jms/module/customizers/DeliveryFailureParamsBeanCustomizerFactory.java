package weblogic.jms.module.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;

public class DeliveryFailureParamsBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      DeliveryFailureParamsBean beanForUse = (DeliveryFailureParamsBean)bean;
      return new DeliveryFailureCustomizer(beanForUse);
   }
}
