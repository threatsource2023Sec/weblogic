package org.jboss.weld.injection.spi;

import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.api.Service;

public interface JaxwsInjectionServices extends Service {
   ResourceReferenceFactory registerWebServiceRefInjectionPoint(InjectionPoint var1);
}
