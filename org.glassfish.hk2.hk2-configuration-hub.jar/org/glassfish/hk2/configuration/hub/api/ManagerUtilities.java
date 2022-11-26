package org.glassfish.hk2.configuration.hub.api;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.hub.internal.HubImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class ManagerUtilities {
   public static void enableConfigurationHub(ServiceLocator locator) {
      if (locator.getService(Hub.class, new Annotation[0]) == null) {
         ServiceLocatorUtilities.addClasses(locator, true, new Class[]{HubImpl.class});
      }
   }
}
