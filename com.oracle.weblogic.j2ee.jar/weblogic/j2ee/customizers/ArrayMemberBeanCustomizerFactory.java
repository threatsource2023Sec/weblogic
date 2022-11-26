package weblogic.j2ee.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.ArrayMemberBean;

public class ArrayMemberBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      ArrayMemberBean beanForUse = (ArrayMemberBean)bean;
      return new ArrayMemberBeanCustomizer(beanForUse);
   }
}
