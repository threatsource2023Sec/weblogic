package weblogic.servlet.internal;

import weblogic.application.ModuleException;

public interface CacheExtension {
   void setupCaches() throws ModuleException;

   void releaseCaches();
}
