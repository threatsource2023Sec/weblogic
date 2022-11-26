package weblogic.jms.module.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.TopicSubscriptionParamsBean;
import weblogic.j2ee.descriptor.wl.customizers.TopicSubscriptionParamsBeanCustomizer;

public class TopicSubscriptionsCustomizer extends ParamsCustomizer implements TopicSubscriptionParamsBeanCustomizer {
   public TopicSubscriptionsCustomizer(TopicSubscriptionParamsBean paramCustomized) {
      super((DescriptorBean)paramCustomized);
   }

   public TemplateBean getTemplateBean() {
      return super.getTemplateBean();
   }
}
