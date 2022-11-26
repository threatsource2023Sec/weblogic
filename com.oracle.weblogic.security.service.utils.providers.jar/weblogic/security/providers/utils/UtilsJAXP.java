package weblogic.security.providers.utils;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.engine.Services;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import com.bea.common.security.service.JAXPFactoryService;
import weblogic.security.spi.SecurityServices;

public final class UtilsJAXP {
   public static JAXPFactoryService getJAXPFactoryService(SecurityServices secServices) {
      ExtendedSecurityServices xSecurityService = (ExtendedSecurityServices)secServices;
      LegacyConfigInfoSpi legacyConfigInfoSpi = xSecurityService.getLegacyConfig();
      Services services = xSecurityService.getServices();

      try {
         return (JAXPFactoryService)services.getService(legacyConfigInfoSpi.getJAXPFactoryServiceName());
      } catch (ServiceNotFoundException var5) {
      } catch (ServiceInitializationException var6) {
      }

      return null;
   }
}
