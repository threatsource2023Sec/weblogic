package weblogic.management.descriptors.application;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.utils.io.XMLWriter;

public interface J2EEApplicationDescriptorMBean extends XMLElementMBean, UIMBean, XMLDeclarationMBean {
   String getDescription();

   void setDescription(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   String getSmallIconFileName();

   void setSmallIconFileName(String var1);

   String getLargeIconFileName();

   void setLargeIconFileName(String var1);

   SecurityRoleMBean[] getSecurityRoles();

   void setSecurityRoles(SecurityRoleMBean[] var1);

   void addSecurityRole(SecurityRoleMBean var1);

   void removeSecurityRole(SecurityRoleMBean var1);

   ModuleMBean[] getModules();

   ModuleMBean[] getEJBModules();

   ModuleMBean[] getJavaModules();

   ModuleMBean[] getWebModules();

   ModuleMBean[] getConnectorModules();

   void setModules(ModuleMBean[] var1);

   void addModule(ModuleMBean var1);

   void addEJBModule(EJBModuleMBean var1);

   void addJavaModule(JavaModuleMBean var1);

   void addWebModule(WebModuleMBean var1);

   void addConnectorModule(ConnectorModuleMBean var1);

   void removeEJBModule(EJBModuleMBean var1);

   void removeJavaModule(JavaModuleMBean var1);

   void removeWebModule(WebModuleMBean var1);

   void removeConnectorModule(ConnectorModuleMBean var1);

   void toXML(XMLWriter var1);
}
