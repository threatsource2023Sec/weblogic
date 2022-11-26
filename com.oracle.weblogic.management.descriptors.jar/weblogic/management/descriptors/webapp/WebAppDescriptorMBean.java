package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.XMLDeclarationMBean;

public interface WebAppDescriptorMBean extends WebElementMBean, XMLDeclarationMBean {
   void setUIData(UIMBean var1);

   UIMBean getUIData();

   void setDistributable(boolean var1);

   boolean isDistributable();

   void setContextParams(ParameterMBean[] var1);

   ParameterMBean[] getContextParams();

   void addContextParam(ParameterMBean var1);

   void removeContextParam(ParameterMBean var1);

   void setFilters(FilterMBean[] var1);

   FilterMBean[] getFilters();

   void addFilter(FilterMBean var1);

   void removeFilter(FilterMBean var1);

   void setFilterMappings(FilterMappingMBean[] var1);

   FilterMappingMBean[] getFilterMappings();

   void addFilterMapping(FilterMappingMBean var1);

   void removeFilterMapping(FilterMappingMBean var1);

   void setListeners(ListenerMBean[] var1);

   ListenerMBean[] getListeners();

   void addListener(ListenerMBean var1);

   void removeListener(ListenerMBean var1);

   void setServlets(ServletMBean[] var1);

   ServletMBean[] getServlets();

   void addServlet(ServletMBean var1);

   void removeServlet(ServletMBean var1);

   void setServletMappings(ServletMappingMBean[] var1);

   ServletMappingMBean[] getServletMappings();

   void addServletMapping(ServletMappingMBean var1);

   void removeServletMapping(ServletMappingMBean var1);

   void setSessionConfig(SessionConfigMBean var1);

   SessionConfigMBean getSessionConfig();

   void setMimeMappings(MimeMappingMBean[] var1);

   MimeMappingMBean[] getMimeMappings();

   void addMimeMapping(MimeMappingMBean var1);

   void removeMimeMapping(MimeMappingMBean var1);

   void setWelcomeFiles(WelcomeFileListMBean var1);

   WelcomeFileListMBean getWelcomeFiles();

   void setErrorPages(ErrorPageMBean[] var1);

   ErrorPageMBean[] getErrorPages();

   void addErrorPage(ErrorPageMBean var1);

   void removeErrorPage(ErrorPageMBean var1);

   void setTagLibs(TagLibMBean[] var1);

   TagLibMBean[] getTagLibs();

   void addTagLib(TagLibMBean var1);

   void removeTagLib(TagLibMBean var1);

   void setResourceEnvRefs(ResourceEnvRefMBean[] var1);

   ResourceEnvRefMBean[] getResourceEnvRefs();

   void addResourceEnvRef(ResourceEnvRefMBean var1);

   void removeResourceEnvRef(ResourceEnvRefMBean var1);

   void setResourceReferences(ResourceRefMBean[] var1);

   ResourceRefMBean[] getResourceReferences();

   void addResourceReference(ResourceRefMBean var1);

   void removeResourceReference(ResourceRefMBean var1);

   void setSecurityConstraints(SecurityConstraintMBean[] var1);

   SecurityConstraintMBean[] getSecurityConstraints();

   void addSecurityConstraint(SecurityConstraintMBean var1);

   void removeSecurityConstraint(SecurityConstraintMBean var1);

   void setLoginConfig(LoginConfigMBean var1);

   LoginConfigMBean getLoginConfig();

   void setSecurityRoles(SecurityRoleMBean[] var1);

   SecurityRoleMBean[] getSecurityRoles();

   void addSecurityRole(SecurityRoleMBean var1);

   void removeSecurityRole(SecurityRoleMBean var1);

   void setEnvironmentEntries(EnvEntryMBean[] var1);

   EnvEntryMBean[] getEnvironmentEntries();

   void addEnvironmentEntry(EnvEntryMBean var1);

   void removeEnvironmentEntry(EnvEntryMBean var1);

   void setEJBReferences(EjbRefMBean[] var1);

   EjbRefMBean[] getEJBReferences();

   void addEJBReference(EjbRefMBean var1);

   void removeEJBReference(EjbRefMBean var1);

   void setEJBLocalReferences(EjbRefMBean[] var1);

   EjbRefMBean[] getEJBLocalReferences();

   void addEJBLocalReference(EjbRefMBean var1);

   void removeEJBLocalReference(EjbRefMBean var1);
}
