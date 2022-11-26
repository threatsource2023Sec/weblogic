package org.glassfish.hk2.configuration.api;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.hub.api.ManagerUtilities;
import org.glassfish.hk2.configuration.internal.ChildInjectResolverImpl;
import org.glassfish.hk2.configuration.internal.ConfigurationListener;
import org.glassfish.hk2.configuration.internal.ConfigurationValidationService;
import org.glassfish.hk2.configuration.internal.ConfiguredByContext;
import org.glassfish.hk2.configuration.internal.ConfiguredByInjectionResolver;
import org.glassfish.hk2.configuration.internal.ConfiguredValidator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class ConfigurationUtilities {
   public static void enableConfigurationSystem(ServiceLocator locator) {
      ServiceHandle alreadyThere = locator.getServiceHandle(ConfiguredByContext.class, new Annotation[0]);
      if (alreadyThere == null) {
         ManagerUtilities.enableConfigurationHub(locator);
         ServiceLocatorUtilities.addClasses(locator, true, new Class[]{ConfiguredValidator.class});
         ServiceLocatorUtilities.addClasses(locator, true, new Class[]{ConfiguredByContext.class, ConfigurationValidationService.class, ConfiguredByInjectionResolver.class, ConfigurationListener.class, ChildInjectResolverImpl.class});
         locator.getService(ConfigurationListener.class, new Annotation[0]);
      }
   }
}
