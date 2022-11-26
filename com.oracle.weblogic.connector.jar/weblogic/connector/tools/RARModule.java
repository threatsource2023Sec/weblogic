package weblogic.connector.tools;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.configuration.AdditionalAnnotatedClassesProvider;
import weblogic.connector.configuration.ConnectorDescriptor;
import weblogic.connector.deploy.RarArchive;
import weblogic.connector.external.ConnectorUtils;
import weblogic.connector.external.RAComplianceException;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorHelper;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.kernel.KernelStatus;
import weblogic.utils.BadOptionException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class RARModule implements ToolsModule {
   MultiClassFinder moduleClassFinder = new MultiClassFinder();
   private ModuleContext state;
   private final String altDD;
   private final String moduleUri;
   private ToolsContext ctx;
   private GenericClassLoader parent;
   private ConnectorDescriptor connectorDes;
   private ToolsContextBasedAdditionalAnnotatedClassesProvider aacProvider;
   private boolean isEmbededInEar;
   private RarArchive rar;

   private RARModule(String uri, String altDD, boolean isEmbededInEar) {
      this.moduleUri = uri;
      this.altDD = altDD;
      this.isEmbededInEar = isEmbededInEar;
      if (this.verbose()) {
         this.log("Creating RARModule " + (isEmbededInEar ? "Embeded inside EAR" : "Standalone") + " with uri = " + uri + "; altDD = " + altDD);
      }

   }

   public static RARModule createStandaloneRARModule(String uri, String altDD) {
      RARModule module = new RARModule(uri, altDD, false);
      return module;
   }

   public static RARModule createEmbededRARModule(String uri, String altDD) {
      RARModule module = new RARModule(uri, altDD, true);
      return module;
   }

   public String getAltDD() {
      return this.altDD;
   }

   public String getURI() {
      return this.moduleUri;
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      if (this.verbose()) {
         this.log("compile: " + this.moduleUri + "; altDD: " + this.altDD + "; cl:" + cl);
      }

      ClasspathClassFinder2 finder = null;
      GenericClassLoader wcl = null;

      try {
         finder = new ClasspathClassFinder2(this.state.getOutputDir().getPath());
         wcl = new GenericClassLoader(finder, cl);

         try {
            this.ctx.getOpts().setOption("classpath", wcl.getClassPath());
         } catch (BadOptionException var10) {
            throw new AssertionError(var10);
         }

         try {
            ConnectorUtils.createRAComplianceChecker().validate(wcl, this.rar, this.state.getAltDDFile(), this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.isEmbededInEar, this.aacProvider);
         } catch (RAComplianceException var9) {
            throw new ToolFailureException(J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(this.getURI(), var9.toString()).getMessage(), var9);
         }
      } finally {
         if (finder != null) {
            finder.close();
         }

         if (wcl != null) {
            wcl.close();
         }

      }

      return Collections.emptyMap();
   }

   public Map merge() throws ToolFailureException {
      if (this.verbose()) {
         this.log("merge: " + this.moduleUri + "; altDD: " + this.altDD + "; ear:" + this.ctx.getEar());
      }

      Map descriptors = new HashMap();
      String moduleName = this.getURI();

      try {
         this.connectorDes = ConnectorDescriptor.buildDescriptor(this.state.getAltDDFile(), (File)null, this.rar, this.ctx.getConfigDir(), this.ctx.getPlanBean(), moduleName, this.parent, this.isEmbededInEar, this.aacProvider);
         ConnectorAPContext apCtx = this.connectorDes.getAnnotationProcessingContext();
         String msg;
         if (!apCtx.getWarnings().isEmpty()) {
            RAComplianceException warnings = new RAComplianceException();
            Iterator var5 = apCtx.getWarnings().iterator();

            while(var5.hasNext()) {
               msg = (String)var5.next();
               warnings.addMessage(msg);
            }

            ConnectorLogger.logAnnotationScanWarnings(moduleName, apCtx.getWarnings().size(), warnings.getMessage());
         }

         List nonCriticalErrors = apCtx.getNonCriticalErrors();
         if (!nonCriticalErrors.isEmpty()) {
            RAComplianceException nonCriticalErrorEx = new RAComplianceException();
            Iterator var16 = nonCriticalErrors.iterator();

            while(var16.hasNext()) {
               String nonCriticalError = (String)var16.next();
               nonCriticalErrorEx.addMessage(nonCriticalError);
            }

            ToolFailureException te = new ToolFailureException("should not get non-critical errors during annotation scan since they should all be critical! " + nonCriticalErrors);
            throw te;
         } else {
            List errors = apCtx.getCriticalErrors();
            if (errors.isEmpty()) {
               if (this.connectorDes.getConnectorBean() != null) {
                  descriptors.put("META-INF/ra.xml", (DescriptorBean)this.connectorDes.getConnectorBean());
               }

               if (this.connectorDes.getWeblogicConnectorBean() != null) {
                  descriptors.put("META-INF/weblogic-ra.xml", (DescriptorBean)this.connectorDes.getWeblogicConnectorBean());
               }

               DescriptorBean diagDD = WLDFDescriptorHelper.getDiagnosticDescriptor(this.getURI(), this.getModuleType().toString(), this.state.getVirtualJarFile(), this.ctx.getPlanBean(), this.ctx.getConfigDir(), this.ctx.getEar() == null);
               if (diagDD != null) {
                  descriptors.put("META-INF/weblogic-diagnostics.xml", diagDD);
               }

               return descriptors;
            } else {
               msg = ConnectorLogger.logMergeFailedDueToAnnotationScanErrorsLoggable(moduleName, errors.size()).getMessage();
               msg = msg + ":";
               int i = 1;

               String error;
               for(Iterator var8 = errors.iterator(); var8.hasNext(); msg = msg + "\n[" + i++ + "] " + error) {
                  error = (String)var8.next();
               }

               ToolFailureException te = new ToolFailureException(msg);
               throw te;
            }
         }
      } catch (ToolFailureException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new ToolFailureException("Failed to merge adapter module:" + var11, var11);
      }
   }

   public ClassFinder init(ModuleContext state, ToolsContext toolsCtx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.state = state;
      this.ctx = toolsCtx;
      this.parent = parentClassLoader;
      if (this.ctx.isVerbose() && !KernelStatus.isServer()) {
         System.setProperty("weblogic.debug.DebugRACompliance", "true");
      }

      if (this.verbose()) {
         this.log("init: moduleUri:" + this.moduleUri + "; appId:" + state.getApplicationId() + "; appName:" + state.getApplicationName() + "; uri:" + state.getURI() + "; altDD: " + this.altDD + "; " + parentClassLoader + "; ear:" + this.ctx.getEar());
      }

      this.aacProvider = new ToolsContextBasedAdditionalAnnotatedClassesProvider(this.ctx);
      this.moduleClassFinder.addFinder(new ClasspathClassFinder2(state.getOutputDir().getPath()));
      if (this.verbose()) {
         this.log("init: added ClassFinder: " + state.getOutputDir().getPath());
      }

      if (ContextUtils.isSplitDir(this.ctx)) {
         File[] moduleRoots = this.ctx.getEar().getModuleRoots(this.getURI());

         for(int i = 0; i < moduleRoots.length; ++i) {
            this.moduleClassFinder.addFinder(new ClasspathClassFinder2(moduleRoots[i].getAbsolutePath()));
            if (this.verbose()) {
               this.log("init: added ClassFinder for split dir: " + moduleRoots[i].getAbsolutePath());
            }
         }
      }

      try {
         VirtualJarFile vjar = VirtualJarFactory.createVirtualJar(state.getOutputDir());
         this.rar = new RarArchive(state.getApplicationId(), (ApplicationContextInternal)null, state, this.moduleUri, this.isEmbededInEar, vjar, true);
         if (this.verbose()) {
            this.log("init: created Rar Archive: " + this.rar);
         }
      } catch (Exception var6) {
         if (this.verbose()) {
            this.log("init: created Rar Archive failed: " + var6);
         }

         throw new ToolFailureException("Failed to init RAR Archive: " + var6, var6);
      }

      return this.moduleClassFinder;
   }

   public boolean needsClassLoader() {
      return true;
   }

   public ModuleType getModuleType() {
      return ModuleType.RAR;
   }

   public String toString() {
      return this.getURI();
   }

   public String getStandardDescriptorURI() {
      return "META-INF/ra.xml";
   }

   public String[] getApplicationNameXPath() {
      return new String[]{"connector", "module-name"};
   }

   public boolean isDeployableObject() {
      return true;
   }

   public void cleanup() {
      if (this.verbose()) {
         this.log("cleanup: " + this.moduleUri + "; altDD: " + this.altDD + "; will close ClassFinder: " + this.moduleClassFinder);
      }

      this.moduleClassFinder.close();
      this.rar.close();
      this.rar.remove();
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
      if (this.verbose()) {
         this.log("populateValidationInfo: " + this.moduleUri + "; altDD: " + this.altDD + "; cl:" + cl);
      }

   }

   public void write() throws ToolFailureException {
      if (this.verbose()) {
         this.log("write: " + this.moduleUri + "; altDD: " + this.altDD);
      }

      try {
         if (this.ctx.isWriteInferredDescriptors()) {
            ConnectorBean connBean = this.connectorDes.getConnectorBean();
            if (connBean != null) {
               this.connectorDes.getConnectorBean().setMetadataComplete(true);
               if (this.verbose()) {
                  this.log("write: " + this.moduleUri + ": write out ra.xml to " + this.state.getOutputDir() + "/META-INF/ra.xml");
               }

               AppcUtils.writeDescriptor(this.state.getOutputDir(), "META-INF/ra.xml", (DescriptorBean)this.connectorDes.getConnectorBean());
            } else if (this.verbose()) {
               this.log("write: " + this.moduleUri + ": There is no ra.xml. skip write.");
            }
         }

      } catch (ToolFailureException var2) {
         if (this.verbose()) {
            this.log("write: " + this.moduleUri, var2);
         }

         throw var2;
      } catch (Exception var3) {
         if (this.verbose()) {
            this.log("write: " + this.moduleUri, var3);
         }

         throw new ToolFailureException(var3 == null ? "unknown reason" : var3.toString(), var3);
      }
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
      if (this.verbose()) {
         this.log("enhanceDeploymentView: " + this.moduleUri + "; altDD: " + this.altDD + "; deployableObject:" + deployableObject);
      }

   }

   private void log(Object msg) {
      Debug.deployment("RARModule@" + this.hashCode() + ": " + msg);
   }

   private void log(Object msg, Throwable t) {
      Debug.deployment("RARModule@" + this.hashCode() + ": " + msg + "; exception occured:" + t, t);
   }

   private boolean verbose() {
      return Debug.isDeploymentEnabled();
   }

   public static class ToolsContextBasedAdditionalAnnotatedClassesProvider implements AdditionalAnnotatedClassesProvider {
      private final ToolsContext ctx;

      ToolsContextBasedAdditionalAnnotatedClassesProvider(ToolsContext ctx) {
         this.ctx = ctx;
      }

      public Set getAnnotatedClasses() throws AnnotationProcessingException {
         return this.ctx.getAnnotatedClasses((Class[])AdditionalAnnotatedClassesProvider.ANNOTATIONS.toArray(new Class[AdditionalAnnotatedClassesProvider.ANNOTATIONS.size()]));
      }
   }
}
