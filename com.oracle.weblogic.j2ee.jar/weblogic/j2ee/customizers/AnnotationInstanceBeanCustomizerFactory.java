package weblogic.j2ee.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.AnnotationInstanceBean;

public class AnnotationInstanceBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      AnnotationInstanceBean beanForUse = (AnnotationInstanceBean)bean;
      return new AnnotationInstanceBeanCustomizer(beanForUse);
   }
}
