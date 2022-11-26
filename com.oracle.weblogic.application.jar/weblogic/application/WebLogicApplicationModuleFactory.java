package weblogic.application;

import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;

public interface WebLogicApplicationModuleFactory {
   Module[] createModule(WeblogicApplicationBean var1) throws ModuleException;
}
