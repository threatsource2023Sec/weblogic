package weblogic.j2ee.descriptor.wl;

public interface ClassloaderStructureBean {
   ModuleRefBean[] getModuleRefs();

   ModuleRefBean createModuleRef();

   void destroyModuleRef(ModuleRefBean var1);

   ClassloaderStructureBean[] getClassloaderStructures();

   ClassloaderStructureBean createClassloaderStructure();

   void destroyClassloaderStructure(ClassloaderStructureBean var1);
}
