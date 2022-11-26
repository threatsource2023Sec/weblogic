package weblogic.application;

import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public interface WeblogicModuleFactory {
   Module createModule(WeblogicModuleBean var1) throws ModuleException;
}
