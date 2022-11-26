package weblogic.application.config;

import weblogic.application.ModuleException;
import weblogic.descriptor.DescriptorBean;

public interface ConfigModuleCallbackHandler {
   void prepare(String var1, String var2, DescriptorBean var3) throws ModuleException;

   void activate(String var1, String var2, DescriptorBean var3) throws ModuleException;

   void deactivate(String var1, String var2, DescriptorBean var3);

   void unprepare(String var1, String var2, DescriptorBean var3);
}
