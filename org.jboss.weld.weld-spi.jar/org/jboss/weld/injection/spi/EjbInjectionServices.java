package org.jboss.weld.injection.spi;

import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.api.Service;

public interface EjbInjectionServices extends Service {
   ResourceReferenceFactory registerEjbInjectionPoint(InjectionPoint var1);
}
