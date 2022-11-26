package weblogic.j2ee.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.MemberBean;

public class MemberBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      MemberBean beanForUse = (MemberBean)bean;
      return new MemberBeanCustomizer(beanForUse);
   }
}
