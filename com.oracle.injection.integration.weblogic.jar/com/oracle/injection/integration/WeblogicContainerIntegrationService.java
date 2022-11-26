package com.oracle.injection.integration;

import com.oracle.injection.spi.ContainerIntegrationService;
import java.util.Map;
import javax.enterprise.inject.spi.DefinitionException;
import javax.servlet.ServletContext;

public interface WeblogicContainerIntegrationService extends ContainerIntegrationService {
   void addInjectionEnabledWebApp(ServletContext var1);

   boolean isWebAppInjectionEnabled(ServletContext var1);

   void validateProducerFields() throws DefinitionException;

   void addEjbInterceptorGeneratedNameMapping(String var1, String var2, String var3);

   Map getEjbInterceptorGeneratedNameMappings(String var1);

   String getEjbInterceptorGeneratedNameMapping(String var1, String var2);
}
