package weblogic.application.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import weblogic.application.ApplicationFileManager;
import weblogic.application.DescriptorUpdater;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.internal.library.LibraryManagerAggregate;
import weblogic.application.io.Ear;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.utils.annotation.AnnotationMappings;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public interface CompilerCtx extends DescriptorUpdater, ToolsContext {
   void setAnnotationMappings(AnnotationMappings var1);

   void setApplicationArchive(ApplicationArchive var1);

   void setApplicationFileManager(ApplicationFileManager var1);

   void setBasicView();

   ClassFinder getClassFinder();

   String getClasspathArg();

   void setClasspathArg(String var1);

   void setConfigDir(File var1);

   Map getCustomModuleFactories();

   void setCustomModuleFactories(Map var1);

   ToolsModule[] getCustomModules();

   void setCustomModules(ToolsModule[] var1);

   EditableDeployableObject getDeployableApplication();

   void setDeployableApplication(EditableDeployableObject var1);

   void setEar(Ear var1);

   LibraryManagerAggregate getLibraryManagerAggregate();

   boolean isLibraryURI(String var1);

   String getLightWeightAppName();

   void setLightWeightAppName(String var1);

   File getManifestFile();

   void setManifestFile(File var1);

   boolean isMergeDisabled();

   ModuleRegistry getModuleRegistry(String var1);

   void setModules(ToolsModule[] var1);

   ModuleState getModuleState(ToolsModule var1);

   EditableDeployableObjectFactory getObjectFactory();

   void setObjectFactory(EditableDeployableObjectFactory var1);

   void setOpts(Getopt2 var1);

   void setOutputDir(File var1);

   void setPartialOutputTarget(String var1);

   void setPlanBean(DeploymentPlanBean var1);

   File getPlanFile();

   void setPlanFile(File var1);

   Object getPlanName();

   void setPlanName(String var1);

   void setReadOnlyInvocation();

   void setSourceFile(File var1);

   String getSourceName();

   boolean isSplitDir();

   void setSplitDir();

   void setSplitDirectoryInfo(SplitDirectoryInfo var1);

   String getTargetArchive();

   void setTargetArchive(String var1);

   void setTempDir(File var1);

   ToolsExtension[] getToolsExtensions();

   void setToolsExtensions(ToolsExtension[] var1);

   File getURILink(String var1);

   void setVerbose(boolean var1);

   void setVerifyLibraryReferences(boolean var1);

   void setVSource(VirtualJarFile var1);

   WeblogicApplicationBean getWLApplicationDD();

   WeblogicExtensionBean getWLExtensionDD();

   void setWriteInferredDescriptors();

   ModuleState createState(ToolsModule var1);

   void disableLibraryMerge();

   void init();

   void keepLibraryRegistrationOnExit();

   void saveState(ToolsModule var1, ModuleState var2);

   void setupApplicationFileManager(File var1) throws IOException;

   boolean unregisterLibrariesOnExit();

   void enableBeanScaffolding();

   File getGeneratedOutputDir();

   File getCacheDir();

   List getLibraryClassInfoFinders();

   void addAppAnnotationScanningClassPathFirst(String var1);

   String getAppAnnotationScanningClassPath();

   boolean isGenerateVersion();

   void setGenerateVersion(boolean var1);

   String getVersionGeneratorAlgorithm();

   void setVersionGeneratorAlgorithm(String var1);

   String getApplicationVersion();

   void setApplicationVersion(String var1);
}
