package weblogic.application.utils;

import weblogic.j2ee.descriptor.ApplicationBean;

public interface DiscoveredModule {
   void createModule(ApplicationBean var1);

   String getURI();
}
