package weblogic.application.compiler;

import java.io.File;
import java.util.Set;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationFileManager;
import weblogic.application.GenericApplicationContext;
import weblogic.application.ModuleContext;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.io.Ear;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProvider;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public interface ToolsContext extends LibraryContext, GenericApplicationContext {
   File getConfigDir();

   DeploymentPlanBean getPlanBean();

   String getApplicationId();

   File getSourceFile();

   VirtualJarFile getVSource();

   File getOutputDir();

   File getTempDir();

   boolean isVerbose();

   Getopt2 getOpts();

   boolean isWriteInferredDescriptors();

   boolean isBasicView();

   boolean isReadOnlyInvocation();

   boolean verifyLibraryReferences();

   ApplicationFileManager getApplicationFileManager();

   Ear getEar();

   SplitDirectoryInfo getSplitDirectoryInfo();

   ToolsModule[] getModules();

   void addLibraryManager(String var1, LibraryManager var2);

   LibraryProvider getLibraryProvider(String var1);

   String getPartialOutputTarget();

   GenericClassLoader getAppClassLoader();

   ModuleContext getModuleContext(String var1);

   Set getAnnotatedClasses(Class... var1) throws AnnotationProcessingException;

   /** @deprecated */
   @Deprecated
   ApplicationArchive getApplicationArchive();

   boolean isBeanScaffoldingEnabled();
}
