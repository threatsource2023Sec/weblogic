package weblogic.jms.module.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.ThresholdParamsBean;
import weblogic.j2ee.descriptor.wl.customizers.ThresholdParamsBeanCustomizer;

public class ThresholdsCustomizer extends ParamsCustomizer implements ThresholdParamsBeanCustomizer {
   public ThresholdsCustomizer(ThresholdParamsBean paramCustomized) {
      super((DescriptorBean)paramCustomized);
   }

   public TemplateBean getTemplateBean() {
      return super.getTemplateBean();
   }
}
