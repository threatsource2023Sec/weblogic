package weblogic.j2ee.descriptor.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;

public interface WeblogicApplicationBeanCustomizer extends Customizer {
   WeblogicEnvironmentBean convertToWeblogicEnvironmentBean();
}
