package weblogic.j2ee.descriptor.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.ApplicationBean;

public class ApplicationBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object o) {
      return new ApplicationBeanCustomizerImpl((ApplicationBean)o);
   }
}
