package org.jboss.weld.injection.spi;

import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.api.Service;

public interface ResourceInjectionServices extends Service {
   ResourceReferenceFactory registerResourceInjectionPoint(InjectionPoint var1);

   ResourceReferenceFactory registerResourceInjectionPoint(String var1, String var2);
}
