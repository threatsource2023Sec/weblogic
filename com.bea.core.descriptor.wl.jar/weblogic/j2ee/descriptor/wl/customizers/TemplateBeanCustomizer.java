package weblogic.j2ee.descriptor.wl.customizers;

import weblogic.descriptor.beangen.Customizer;
import weblogic.j2ee.descriptor.wl.DestinationBean;

public interface TemplateBeanCustomizer extends Customizer {
   DestinationBean findErrorDestination(String var1);
}
