package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;
import weblogic.management.descriptors.application.ModuleMBean;

public interface WeblogicApplicationMBean extends XMLElementMBean, XMLDeclarationMBean {
   EjbMBean getEjb();

   void setEjb(EjbMBean var1);

   XMLMBean getXML();

   void setXML(XMLMBean var1);

   SecurityMBean getSecurity();

   void setSecurity(SecurityMBean var1);

   ApplicationParamMBean[] getParameters();

   void setParameters(ApplicationParamMBean[] var1);

   void addParameter(ApplicationParamMBean var1);

   void removeParameter(ApplicationParamMBean var1);

   ClassloaderStructureMBean getClassloaderStructure();

   void setClassloaderStructure(ClassloaderStructureMBean var1);

   ListenerMBean[] getListeners();

   void setListeners(ListenerMBean[] var1);

   void addListener(ListenerMBean var1);

   void removeListener(ListenerMBean var1);

   StartupMBean[] getStartups();

   void setStartups(StartupMBean[] var1);

   void addStartup(StartupMBean var1);

   void removeStartup(StartupMBean var1);

   ShutdownMBean[] getShutdowns();

   void setShutdowns(ShutdownMBean[] var1);

   void addShutdown(ShutdownMBean var1);

   void removeShutdown(ShutdownMBean var1);

   ModuleProviderMBean[] getModuleProviders();

   void setModuleProviders(ModuleProviderMBean[] var1);

   void addModuleProvider(ModuleProviderMBean var1);

   void removeModuleProvider(ModuleProviderMBean var1);

   ModuleMBean[] getModules();

   void setModules(ModuleMBean[] var1);

   void addModule(ModuleMBean var1);

   void removeModule(ModuleMBean var1);

   LibraryRefMBean[] getLibraries();

   void setLibraries(LibraryRefMBean[] var1);

   void addLibrary(LibraryRefMBean var1);

   void removeLibrary(LibraryRefMBean var1);
}
