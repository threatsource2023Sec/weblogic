package weblogic.jms.module.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.TopicSubscriptionParamsBean;

public class TopicSubscriptionParamsBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      TopicSubscriptionParamsBean beanForUse = (TopicSubscriptionParamsBean)bean;
      return new TopicSubscriptionsCustomizer(beanForUse);
   }
}
