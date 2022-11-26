package org.jboss.weld.injection.spi;

import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.api.Service;

public interface JpaInjectionServices extends Service {
   ResourceReferenceFactory registerPersistenceContextInjectionPoint(InjectionPoint var1);

   ResourceReferenceFactory registerPersistenceUnitInjectionPoint(InjectionPoint var1);
}
