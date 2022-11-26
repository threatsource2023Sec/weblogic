package weblogic.application.compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.application.Extensible;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableDeployableObjectFactory;
import weblogic.application.internal.ModuleContextImpl;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public class ModuleState extends ModuleContextImpl {
   private ToolsModule module;
   private boolean isLibrary = false;
   private boolean needsPackaging;
   private Map descriptors;
   private final ModuleValidationInfo mvi;
   private final File generatedOutputDir;
   private final File cacheDir;
   private Collection extensions = new ArrayList();
   private final CompilerCtx ctx;

   public ModuleState(ToolsModule module, CompilerCtx ctx) {
      super(ctx.getApplicationId(), ApplicationVersionUtils.getApplicationName(ctx.getApplicationId()), module.getModuleType() == null ? null : module.getModuleType().toString(), ctx.getModuleRegistry(module.getURI()), ctx.getConfigDir(), ctx.getPlanBean());
      if (module instanceof ToolsModuleWrapper) {
         this.module = ((ToolsModuleWrapper)module).unwrap();
      } else {
         this.module = module;
      }

      this.mvi = module.getURI() == null ? null : new ModuleValidationInfo(module.getURI());
      this.generatedOutputDir = new File(ctx.getOutputDir(), "META-INF/.WL_internal/generated/" + module.getURI());
      this.cacheDir = new File(ctx.getOutputDir(), "META-INF/.WL_internal/cache/" + module.getURI());
      this.ctx = ctx;
   }

   public void init(GenericClassLoader parentClassLoader, ClassFinder moduleClassFinder) {
      this.moduleClassFinder = moduleClassFinder;
      if (this.module.needsClassLoader()) {
         this.moduleClassLoader = new GenericClassLoader(moduleClassFinder, parentClassLoader);
         this.moduleClassLoader.setAnnotation(new Annotation(this.applicationId, this.module.getURI()));
      } else {
         this.moduleClassLoader = parentClassLoader;
      }

   }

   public void markAsLibrary() {
      this.isLibrary = true;
   }

   public boolean isLibrary() {
      return this.isLibrary;
   }

   public void setArchive(boolean val) {
      this.isArchive = val;
   }

   public boolean needsPackaging() {
      return this.needsPackaging;
   }

   public void setNeedsPackaging(boolean val) {
      this.needsPackaging = val;
   }

   public void setOutputDir(File f) {
      this.outputDir = f;
   }

   public void setOutputFileName(String name) {
      this.outputFileName = name;
   }

   public void setVirtualJarFile(VirtualJarFile val) {
      this.vjf = val;
   }

   public void addRootBean(String descriptorUri, DescriptorBean bean) {
      this.descriptors.put(descriptorUri, bean);
   }

   public void setParsedDescriptors(Map rootBeanMap) {
      this.descriptors = rootBeanMap;
   }

   public String[] getDescriptorUris() {
      return (String[])this.descriptors.keySet().toArray(new String[0]);
   }

   public DescriptorBean getDescriptor(String descriptorUri) {
      return (DescriptorBean)this.descriptors.get(descriptorUri);
   }

   public EditableDeployableObject buildDeploymentView(EditableDeployableObjectFactory objectFactory) throws IOException {
      EditableDeployableObject deployableObject = objectFactory.createDeployableObject(this.module.getURI(), this.module.getAltDD(), this.module.getModuleType());
      deployableObject.setVirtualJarFile(this.vjf);
      DescriptorBean standardDescriptor = (DescriptorBean)this.descriptors.get(this.module.getStandardDescriptorURI());
      if (standardDescriptor != null) {
         deployableObject.setRootBean(standardDescriptor);
      }

      Iterator var4 = this.descriptors.keySet().iterator();

      while(var4.hasNext()) {
         String descriptorUri = (String)var4.next();
         DescriptorBean descriptorBean = (DescriptorBean)this.descriptors.get(descriptorUri);
         if (descriptorBean != null) {
            deployableObject.addRootBean(descriptorUri, descriptorBean, this.module.getModuleType());
         }
      }

      deployableObject.setClassLoader(this.moduleClassLoader);
      return deployableObject;
   }

   public void cleanup() {
      super.cleanup();
      if (this.moduleClassLoader != null) {
         this.moduleClassLoader.close();
      }

      if (!this.module.needsClassLoader() && this.moduleClassFinder != null) {
         this.moduleClassFinder.close();
      }

   }

   public String getId() {
      return null;
   }

   public String getURI() {
      return this.module.getURI();
   }

   public ModuleValidationInfo getValidationInfo() {
      return this.mvi;
   }

   public File getCacheDir() {
      return this.cacheDir;
   }

   public File getGeneratedOutputDir() {
      return this.generatedOutputDir;
   }

   public boolean isExtensible() {
      return this.module instanceof Extensible;
   }

   public Set getExtensionAnnotationClasses() {
      if (!this.isExtensible()) {
         return Collections.EMPTY_SET;
      } else {
         Set extensionAnnotationClasses = new HashSet();
         Iterator iterator = ToolsFactoryManager.getModuleExtensionFactories(this.getType());

         while(iterator.hasNext()) {
            ToolsModuleExtensionFactory factory = (ToolsModuleExtensionFactory)iterator.next();
            Class[] annotationClasses = factory.getSupportedClassLevelAnnotations();
            Class[] var5 = annotationClasses;
            int var6 = annotationClasses.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Class annotation = var5[var7];
               extensionAnnotationClasses.add(annotation);
            }
         }

         return extensionAnnotationClasses;
      }
   }

   public void initExtensions() throws ToolFailureException {
      if (this.isExtensible()) {
         Extensible extensible = (Extensible)this.module;
         ModuleExtensionContext extContext = extensible.getModuleExtensionContext();
         if (extContext != null) {
            Iterator factories = ToolsFactoryManager.getModuleExtensionFactories(extensible.getType());

            while(factories.hasNext()) {
               ToolsModuleExtensionFactory factory = (ToolsModuleExtensionFactory)factories.next();
               ToolsModuleExtension extension = factory.create(extContext, this.ctx, this.module, extensible.getStandardDescriptor());
               if (extension != null) {
                  this.extensions.add(extension);
               }
            }
         }
      }

   }

   public Collection getExtensions() {
      return this.extensions;
   }
}
