package weblogic.j2ee.descriptor.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.AbsoluteOrderingBean;

public class AbsoluteOrderingBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object o) {
      return new AbsoluteOrderingBeanCustomizerImpl((AbsoluteOrderingBean)o);
   }
}
