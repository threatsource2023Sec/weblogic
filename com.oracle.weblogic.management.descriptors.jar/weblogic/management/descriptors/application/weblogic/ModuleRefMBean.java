package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ModuleRefMBean extends XMLElementMBean {
   String getModuleUri();

   void setModuleUri(String var1);
}
