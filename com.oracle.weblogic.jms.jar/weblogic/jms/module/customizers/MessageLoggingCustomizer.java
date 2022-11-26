package weblogic.jms.module.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.MessageLoggingParamsBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.customizers.MessageLoggingParamsBeanCustomizer;

public class MessageLoggingCustomizer extends ParamsCustomizer implements MessageLoggingParamsBeanCustomizer {
   public MessageLoggingCustomizer(MessageLoggingParamsBean paramCustomized) {
      super((DescriptorBean)paramCustomized);
   }

   public TemplateBean getTemplateBean() {
      return super.getTemplateBean();
   }
}
