package com.oracle.injection.spi;

import java.util.EventListener;
import javax.servlet.ServletContext;

public interface WebModuleIntegrationService {
   ServletContext getServletContext();

   void registerServletListener(EventListener var1);

   String[] getFilterMappingNames();

   void setBeanManager(Object var1);

   Object getBeanManager();

   void setWebAppDestroyed(boolean var1);

   boolean isWebAppDestroyed();
}
