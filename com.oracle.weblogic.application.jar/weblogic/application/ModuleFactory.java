package weblogic.application;

import weblogic.j2ee.descriptor.ModuleBean;

public interface ModuleFactory extends AppSupportDeclaration {
   Module createModule(ModuleBean var1) throws ModuleException;
}
