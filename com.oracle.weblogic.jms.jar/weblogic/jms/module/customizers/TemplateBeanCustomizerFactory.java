package weblogic.jms.module.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.TemplateBean;

public class TemplateBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      TemplateBean beanForUse = (TemplateBean)bean;
      return new TemplateCustomizer(beanForUse);
   }
}
