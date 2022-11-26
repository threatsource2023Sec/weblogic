package weblogic.application;

import weblogic.j2ee.descriptor.wl.ModuleProviderBean;

public interface CustomModuleContext {
   String getParentModuleUri();

   String getParentModuleId();

   ModuleProviderBean getModuleProviderBean();
}
