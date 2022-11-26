package weblogic.jms.module.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.MulticastParamsBean;

public class MulticastParamsBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      MulticastParamsBean beanForUse = (MulticastParamsBean)bean;
      return new MulticastCustomizer(beanForUse);
   }
}
