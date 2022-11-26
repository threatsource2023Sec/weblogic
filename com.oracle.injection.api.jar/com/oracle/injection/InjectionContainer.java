package com.oracle.injection;

import com.oracle.injection.spi.ContainerIntegrationService;
import java.util.Collection;

public interface InjectionContainer {
   void addInjectionArchive(InjectionArchive var1) throws InjectionException;

   void setIntegrationService(ContainerIntegrationService var1) throws InjectionException;

   ContainerIntegrationService getIntegrationService();

   void initialize() throws InjectionException;

   void deploy() throws InjectionException;

   void start() throws InjectionException;

   void stop() throws InjectionException;

   InjectionDeployment getDeployment();

   Collection getServletListenerNames();

   Collection getServletFilterNames();

   Collection getPhaseListenerNames();

   Collection getELContextListenerNames();

   Collection getEjbInterceptorMappings();

   Collection getInjectionArchives();
}
