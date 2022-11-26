package weblogic.ejb.tools;

import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.CDIUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.interceptor.Interceptor;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleExtension;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.deployer.WarArchive;
import weblogic.ejb.container.metadata.EjbAnnotationProcessor;
import weblogic.ejb.spi.EJBC;
import weblogic.ejb.spi.EJBCFactory;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.kernel.KernelStatus;
import weblogic.utils.BadOptionException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

class EJBToolsModuleExtension implements ToolsModuleExtension {
   private final ModuleExtensionContext modExtCtx;
   private final ModuleContext modCtx;
   private final ToolsContext toolsCtx;
   private final ToolsModule toolsModule;
   private EjbDescriptorBean desc;

   EJBToolsModuleExtension(ModuleExtensionContext modExtCtx, ToolsContext toolsCtx, ToolsModule toolsModule) {
      this.modExtCtx = modExtCtx;
      this.toolsCtx = toolsCtx;
      this.toolsModule = toolsModule;
      this.modCtx = toolsCtx.getModuleContext(toolsModule.getURI());
   }

   public Map compile(GenericClassLoader cl, Map extensibleModuleDescriptors) throws ToolFailureException {
      if (this.toolsCtx.isVerbose() && !KernelStatus.isServer()) {
         System.setProperty("weblogic.debug.DebugEjbCompilation", "true");
      }

      Map map = new HashMap();

      try {
         this.compileEJB(cl, this.modCtx, this.toolsCtx.getOpts());
      } catch (ErrorCollectionException var6) {
         throw new ToolFailureException(J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(this.modCtx.getURI(), var6.toString()).getMessage(), var6);
      }

      if (this.toolsCtx.isWriteInferredDescriptors()) {
         try {
            this.desc = EjbDescriptorFactory.createDescriptor(this.modCtx);
         } catch (XMLProcessingException | IOException | XMLStreamException | XMLParsingException var5) {
            throw new ToolFailureException("Unable to parse EJB descriptor", var5);
         }

         if (this.desc.getEjbJarBean() == null || !this.desc.getEjbJarBean().isMetadataComplete()) {
            this.backupDescriptors(this.desc);
            this.processAnnotations();
            this.write();
         }
      }

      if (this.desc != null) {
         if (this.desc.getEjbJarBean() != null) {
            map.put("WEB-INF/ejb-jar.xml", (DescriptorBean)this.desc.getEjbJarBean());
         }

         if (this.desc.getWeblogicEjbJarBean() != null && !this.desc.isWeblogicEjbJarSynthetic()) {
            map.put("WEB-INF/weblogic-ejb-jar.xml", (DescriptorBean)this.desc.getWeblogicEjbJarBean());
         }
      }

      extensibleModuleDescriptors.putAll(map);
      return map;
   }

   private void compileEJB(GenericClassLoader cl, ModuleContext mc, Getopt2 opts) throws ErrorCollectionException {
      Getopt2 ejbOpts = (Getopt2)opts.clone();

      try {
         ejbOpts.setOption("d", mc.getOutputDir().getPath() + AppcUtils.WEBINF_CLASSES);
         if (ejbOpts.containsOption("k")) {
            ejbOpts.removeOption("k");
         }

         if (ejbOpts.containsOption("manifest")) {
            ejbOpts.removeOption("manifest");
         }

         if (ejbOpts.containsOption("ignorePlanValidation")) {
            ejbOpts.removeOption("ignorePlanValidation");
         }
      } catch (BadOptionException var10) {
         throw new AssertionError(var10);
      }

      ModuleValidationInfo mvi = mc.getValidationInfo();
      String uri = this.getUriFromPlan(mc.getPlan());
      if (uri == null) {
         uri = mc.getURI();
      }

      boolean isCDIEnabled = false;

      try {
         isCDIEnabled = CDIUtils.isModuleCdiEnabled(mc, this.modExtCtx, (ApplicationContextInternal)null);
      } catch (InjectionException var9) {
         throw new ErrorCollectionException(var9);
      }

      EJBC ejbCompiler = EJBCFactory.createEJBC(ejbOpts, mc.getOutputDir());
      ejbCompiler.compileEJB(cl, this.parseDescriptors(mc), mc.getVirtualJarFile(), mvi, mc.getRegistry(), isCDIEnabled);
   }

   private String getUriFromPlan(DeploymentPlanBean plan) {
      if (plan == null) {
         return null;
      } else {
         ModuleOverrideBean[] var2 = plan.getModuleOverrides();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ModuleOverrideBean mov = var2[var4];
            if (plan.rootModule(mov.getModuleName())) {
               return mov.getModuleName();
            }
         }

         return null;
      }
   }

   private EjbDescriptorBean parseDescriptors(ModuleContext mc) throws ErrorCollectionException {
      try {
         return EjbDescriptorFactory.createReadOnlyDescriptor(mc, new WarArchive(this.modExtCtx, this.modCtx), (VirtualJarFile[])null);
      } catch (XMLProcessingException | IOException | XMLStreamException | XMLParsingException var3) {
         throw new ErrorCollectionException(var3);
      }
   }

   private void backupDescriptors(EjbDescriptorBean ejbDescBean) throws ToolFailureException {
      if (this.modCtx.getVirtualJarFile().getEntry("WEB-INF/weblogic-ejb-jar.xml") != null) {
         AppcUtils.writeDescriptor(this.modCtx.getOutputDir(), "WEB-INF/weblogic-ejb-jar.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)ejbDescBean.getWeblogicEjbJarBean());
      }

      if (this.modCtx.getVirtualJarFile().getEntry("WEB-INF/ejb-jar.xml") != null) {
         AppcUtils.writeDescriptor(this.modCtx.getOutputDir(), "WEB-INF/ejb-jar.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)ejbDescBean.getEjbJarBean());
      }

   }

   public Map merge(Map extensibleModuleDescriptors) throws ToolFailureException {
      Map map = new HashMap();

      try {
         this.desc = EjbDescriptorFactory.createDescriptor(this.modCtx);
         if (this.toolsCtx.isWriteInferredDescriptors() && (this.desc.getEjbJarBean() == null || !this.desc.getEjbJarBean().isMetadataComplete())) {
            this.backupDescriptors();
         }

         this.processAnnotations();
         if (this.desc.getEjbJarBean() != null) {
            map.put("WEB-INF/ejb-jar.xml", (DescriptorBean)this.desc.getEjbJarBean());
         }

         if (this.desc.getWeblogicEjbJarBean() != null && !this.desc.isWeblogicEjbJarSynthetic()) {
            map.put("WEB-INF/weblogic-ejb-jar.xml", (DescriptorBean)this.desc.getWeblogicEjbJarBean());
         }
      } catch (Exception var4) {
         throw new ToolFailureException("Error parsing EJB descriptors.", var4);
      }

      extensibleModuleDescriptors.putAll(map);
      return map;
   }

   public void write() throws ToolFailureException {
      if (this.toolsCtx.isWriteInferredDescriptors()) {
         this.desc.getEjbJarBean().setMetadataComplete(true);
      }

      this.writeOut("WEB-INF/ejb-jar.xml", (DescriptorBean)this.desc.getEjbJarBean());
      this.writeOut("WEB-INF/weblogic-ejb-jar.xml", (DescriptorBean)this.desc.getWeblogicEjbJarBean());
   }

   private void backupDescriptors() throws ToolFailureException {
      if (this.modCtx.getVirtualJarFile().getEntry("WEB-INF/ejb-jar.xml") != null) {
         this.writeOut("WEB-INF/ejb-jar.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)this.desc.getEjbJarBean());
      }

      if (this.modCtx.getVirtualJarFile().getEntry("WEB-INF/weblogic-ejb-jar.xml") != null) {
         this.writeOut("WEB-INF/weblogic-ejb-jar.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)this.desc.getWeblogicEjbJarBean());
      }

   }

   private void processAnnotations() throws ToolFailureException {
      if (!this.toolsCtx.isBasicView() && (this.toolsCtx.isReadOnlyInvocation() || this.toolsCtx.isWriteInferredDescriptors())) {
         if (this.desc.getEjbJarBean() == null || this.desc.verSupportsAnnotatedEjbs() && !this.desc.getEjbJarBean().isMetadataComplete()) {
            try {
               this.desc.setEjb30(true);
               EjbAnnotationProcessor eap = new EjbAnnotationProcessor(this.modCtx.getClassLoader(), this.desc);
               Class[] annos = (Class[])DDConstants.COMPONENT_DEFINING_ANNOS.toArray(new Class[0]);
               eap.processAnnotations(this.modExtCtx.getAnnotatedClasses(false, annos), this.getApplicationAnnotatedClassesFromContext());
               eap.processWLSAnnotations();
            } catch (NoClassDefFoundError | ErrorCollectionException | AnnotationProcessingException | ClassNotFoundException var3) {
               var3.printStackTrace();
               if (this.toolsCtx.verifyLibraryReferences()) {
                  throw new ToolFailureException("Unable to process annotations for " + this, var3);
               }
            }

         }
      }
   }

   private Set getApplicationAnnotatedClassesFromContext() throws AnnotationProcessingException {
      return this.toolsCtx.getEar() == null ? null : this.toolsCtx.getAnnotatedClasses(new Class[]{Interceptor.class});
   }

   private void writeOut(String uri, DescriptorBean bean) throws ToolFailureException {
      AppcUtils.writeDescriptor(this.modCtx.getOutputDir(), uri, bean);
   }

   public String toString() {
      return "EJBToolsModuleExtension for ToolsModule(" + this.toolsModule + ")";
   }
}
