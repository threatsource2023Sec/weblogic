package weblogic.application;

import weblogic.descriptor.Descriptor;

public interface Extensible {
   String getType();

   ModuleExtensionContext getModuleExtensionContext();

   Descriptor getStandardDescriptor();
}
