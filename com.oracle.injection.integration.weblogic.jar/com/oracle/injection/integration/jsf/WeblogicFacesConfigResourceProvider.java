package com.oracle.injection.integration.jsf;

import com.oracle.injection.integration.WeblogicContainerIntegrationService;
import com.sun.faces.spi.FacesConfigResourceProvider;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import javax.enterprise.inject.InjectionException;
import javax.servlet.ServletContext;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;

public class WeblogicFacesConfigResourceProvider implements FacesConfigResourceProvider {
   private static final String FACES_CONFIG_LOCATION = "META-INF/services/faces-config.xml";
   private final ApplicationContextAccess m_appContextAccess;

   public WeblogicFacesConfigResourceProvider() {
      this(new DefaultApplicationContextAccess());
   }

   WeblogicFacesConfigResourceProvider(ApplicationContextAccess appContextAccess) {
      this.m_appContextAccess = appContextAccess;
   }

   public Collection getResources(ServletContext context) {
      ApplicationContextInternal applicationContextInternal = this.m_appContextAccess.getCurrentApplicationContext();
      if (applicationContextInternal != null) {
         WeblogicContainerIntegrationService containerIntegrationService = (WeblogicContainerIntegrationService)applicationContextInternal.getUserObject(WeblogicContainerIntegrationService.class.getName());
         if (containerIntegrationService != null && containerIntegrationService.isWebAppInjectionEnabled(context)) {
            URL urlToCustomFacesConfig = this.getClass().getClassLoader().getResource("META-INF/services/faces-config.xml");

            try {
               if (urlToCustomFacesConfig != null) {
                  return Collections.singletonList(new URI(urlToCustomFacesConfig.toString()));
               }
            } catch (URISyntaxException var6) {
               throw new InjectionException("URI syntax error", var6);
            }
         }
      }

      return Collections.emptyList();
   }

   private static class DefaultApplicationContextAccess implements ApplicationContextAccess {
      private DefaultApplicationContextAccess() {
      }

      public ApplicationContextInternal getCurrentApplicationContext() {
         return ApplicationAccess.getApplicationAccess() != null ? ApplicationAccess.getApplicationAccess().getCurrentApplicationContext() : null;
      }

      // $FF: synthetic method
      DefaultApplicationContextAccess(Object x0) {
         this();
      }
   }

   interface ApplicationContextAccess {
      ApplicationContextInternal getCurrentApplicationContext();
   }
}
