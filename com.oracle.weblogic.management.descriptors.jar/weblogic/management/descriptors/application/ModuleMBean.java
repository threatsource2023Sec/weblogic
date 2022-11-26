package weblogic.management.descriptors.application;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.XMLElementMBean;

public interface ModuleMBean extends XMLElementMBean {
   String getAltDDURI();

   void setAltDDURI(String var1);

   String getModuleURI();

   void setModuleURI(String var1);

   String getModuleKey();

   String getAdminMBeanType(ApplicationFileManager var1);
}
