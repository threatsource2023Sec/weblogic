package weblogic.jdbc.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ModuleContext;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.module.JDBCDeploymentHelper;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public class JDBCModule implements ToolsModule {
   private EditableDescriptorManager edm;
   private final String altDD;
   private final String moduleUri;
   protected ModuleContext state = null;
   private ToolsContext ctx;
   private final String moduleName;

   public JDBCModule(String uri, String altDD, String moduleName) {
      this.moduleUri = uri;
      this.altDD = altDD;
      this.moduleName = moduleName;
      if (this.edm == null) {
         this.edm = new EditableDescriptorManager();
      }
   }

   public String getAltDD() {
      return this.altDD;
   }

   public String getURI() {
      return this.moduleUri;
   }

   public Map merge() throws ToolFailureException {
      Map descriptors = new HashMap();

      try {
         JDBCDeploymentHelper deploymentHelper = new JDBCDeploymentHelper();
         JDBCDataSourceBean rootBean = null;
         if (this.ctx.getEar() != null) {
            rootBean = deploymentHelper.createJDBCDataSourceDescriptor(this.edm, this.getCL(this.getURI()), this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.ctx.getEar().getURI(), this.getURI());
         } else {
            rootBean = deploymentHelper.createJDBCDataSourceDescriptor(this.edm, this.getCL(deploymentHelper.getModuleUri(this.getURI())), this.ctx.getConfigDir(), this.ctx.getPlanBean(), deploymentHelper.getModuleName(this.getURI()), deploymentHelper.getModuleUri(this.getURI()));
         }

         if (rootBean != null) {
            descriptors.put(this.getURI(), (DescriptorBean)rootBean);
         }
      } catch (Exception var4) {
      }

      return descriptors;
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
      try {
         this.state.getValidationInfo().setModuleName(this.moduleName);
         JDBCDeploymentHelper deploymentHelper = new JDBCDeploymentHelper();
         JDBCDataSourceBean rootBean = deploymentHelper.createJDBCDataSourceDescriptor(this.state.getOutputFileName());
         this.state.getValidationInfo().setJDBCDataSourceBean(rootBean);
         if (this.ctx.isVerbose() && rootBean != null) {
            JDBCDeploymentHelper.writeModuleAsXML((DescriptorBean)rootBean);
         }

      } catch (Exception var6) {
         String errorMsg = null;
         Throwable th = var6.getCause();
         if (th != null) {
            errorMsg = th.getMessage();
         } else {
            errorMsg = var6.getMessage();
         }

         Exception e = new Exception("Failed to compile JDBC module " + this.state.getOutputFileName() + ": " + errorMsg);
         throw new ToolFailureException(J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(this.state.getOutputFileName(), e.toString()).getMessage(), e);
      }
   }

   public ModuleType getModuleType() {
      return WebLogicModuleType.JDBC;
   }

   public String toString() {
      return this.getURI();
   }

   public ClassFinder init(ModuleContext state, ToolsContext ctx, GenericClassLoader parentClassLoader) throws ToolFailureException {
      this.state = state;
      this.ctx = ctx;
      if (this.moduleName != null) {
         state.getValidationInfo().setModuleName(this.moduleName);
      }

      MultiClassFinder moduleClassFinder = new MultiClassFinder();
      moduleClassFinder.addFinder(new ClasspathClassFinder2(state.getOutputDir().getPath()));
      if (ContextUtils.isSplitDir(ctx)) {
         File[] moduleRoots = ctx.getEar().getModuleRoots(this.getURI());

         for(int i = 0; i < moduleRoots.length; ++i) {
            moduleClassFinder.addFinder(new ClasspathClassFinder2(moduleRoots[i].getAbsolutePath()));
         }
      }

      return moduleClassFinder;
   }

   public boolean needsClassLoader() {
      return true;
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      this.populateValidationInfo(cl);
      return Collections.emptyMap();
   }

   private GenericClassLoader getCL(String uri) throws IOException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = ClassLoader.getSystemClassLoader();
      }

      ClassFinder mcf = NullClassFinder.NULL_FINDER;
      if (cl instanceof GenericClassLoader) {
         mcf = ((GenericClassLoader)cl).getClassFinder();
      }

      return this.createClassLoader(mcf, cl, uri);
   }

   private GenericClassLoader createClassLoader(ClassFinder cf, ClassLoader parent, final String currentURI) throws IOException {
      return new GenericClassLoader(cf, parent) {
         public InputStream getResourceAsStream(String givenURI) {
            if (givenURI.equals(currentURI)) {
               try {
                  return new FileInputStream(new File(JDBCModule.this.state.getOutputFileName()));
               } catch (FileNotFoundException var3) {
               }
            }

            return super.getResourceAsStream(givenURI);
         }
      };
   }

   public String getStandardDescriptorURI() {
      return this.moduleUri;
   }

   public String[] getApplicationNameXPath() {
      return null;
   }

   public boolean isDeployableObject() {
      return false;
   }

   public void cleanup() {
   }

   public void write() throws ToolFailureException {
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
   }
}
