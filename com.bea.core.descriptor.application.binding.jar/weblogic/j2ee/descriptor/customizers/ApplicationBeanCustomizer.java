package weblogic.j2ee.descriptor.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;

public interface ApplicationBeanCustomizer extends Customizer {
   J2eeEnvironmentBean convertToJ2eeEnvironmentBean();
}
