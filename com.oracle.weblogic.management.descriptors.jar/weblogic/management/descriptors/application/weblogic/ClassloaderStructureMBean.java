package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ClassloaderStructureMBean extends XMLElementMBean {
   ModuleRefMBean[] getModuleRefs();

   void setModuleRefs(ModuleRefMBean[] var1);

   void addModuleRef(ModuleRefMBean var1);

   void removeModuleRef(ModuleRefMBean var1);

   ClassloaderStructureMBean[] getClassloaderStructures();

   void setClassloaderStructures(ClassloaderStructureMBean[] var1);

   void addClassloaderStructure(ClassloaderStructureMBean var1);

   void removeClassloaderStructure(ClassloaderStructureMBean var1);
}
