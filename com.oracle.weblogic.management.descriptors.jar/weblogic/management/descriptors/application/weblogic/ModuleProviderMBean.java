package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ModuleProviderMBean extends XMLElementMBean {
   String getName();

   void setName(String var1);

   String getModuleFactoryClassName();

   void setModuleFactoryClassName(String var1);
}
