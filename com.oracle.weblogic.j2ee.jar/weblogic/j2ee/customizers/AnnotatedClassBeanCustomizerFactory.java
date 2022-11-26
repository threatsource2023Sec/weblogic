package weblogic.j2ee.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.AnnotatedClassBean;

public class AnnotatedClassBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      AnnotatedClassBean beanForUse = (AnnotatedClassBean)bean;
      return new AnnotatedClassBeanCustomizer(beanForUse);
   }
}
