package weblogic.jms.module.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBean;

public class DeliveryParamsOverridesBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      DeliveryParamsOverridesBean beanForUse = (DeliveryParamsOverridesBean)bean;
      return new DeliveryOverridesCustomizer(beanForUse);
   }
}
