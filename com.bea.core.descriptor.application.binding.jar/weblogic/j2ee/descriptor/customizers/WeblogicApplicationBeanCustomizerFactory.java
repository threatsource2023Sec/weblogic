package weblogic.j2ee.descriptor.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;

public class WeblogicApplicationBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object o) {
      return new WeblogicApplicationBeanCustomizerImpl((WeblogicApplicationBean)o);
   }
}
