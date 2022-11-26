package weblogic.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import javax.enterprise.deploy.shared.ModuleType;
import javax.persistence.spi.PersistenceUnitInfo;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.Module;
import weblogic.application.ModuleLocationInfo;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.application.utils.PersistenceUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class ModulePersistenceUnitRegistry extends AbstractPersistenceUnitRegistry {
   private final EarPersistenceUnitRegistry parent;

   public ModulePersistenceUnitRegistry(GenericClassLoader cl, ApplicationContextInternal appCtx, Module module, boolean process) throws EnvironmentException {
      super(cl, module.getId(), EarUtils.getConfigDir(appCtx), appCtx.findDeploymentPlan(), appCtx);
      this.parent = (EarPersistenceUnitRegistry)appCtx.getUserObject(EarPersistenceUnitRegistry.class.getName());

      try {
         File rootFile = this.getRootFile(appCtx, module);
         URL rootUrl = rootFile.toURL();
         if (module.getType().equals(ModuleType.WAR.toString())) {
            this.loadPersistenceDescriptors(process);
         } else {
            VirtualJarFile vjf = null;

            try {
               vjf = appCtx.getApplicationFileManager().getVirtualJarFile(((ModuleLocationInfo)module).getModuleURI());
               this.loadPersistenceDescriptor(vjf, process, rootFile);
            } finally {
               IOUtils.forceClose(vjf);
            }
         }

      } catch (EnvironmentException var13) {
         throw var13;
      } catch (Exception var14) {
         throw new EnvironmentException(var14);
      }
   }

   protected File getRootFile(ApplicationContextInternal appCtx, Module m) throws IOException {
      ModuleLocationInfo moduleInfo = (ModuleLocationInfo)m;
      ApplicationFileManager appFileManager = appCtx.getApplicationFileManager();
      File rootFile = appFileManager.getOutputPath(moduleInfo.getModuleURI());
      if (appCtx.isEar()) {
         return rootFile;
      } else {
         if (!appFileManager.isOutputPathExtractDir(moduleInfo.getModuleURI())) {
            rootFile = appFileManager.getSourcePath(moduleInfo.getModuleURI());
         }

         return rootFile;
      }
   }

   public PersistenceUnitInfo getPersistenceUnit(String name) throws IllegalArgumentException {
      if (name == null) {
         Collection names = this.getPersistenceUnitNames();
         if (names.size() == 1) {
            name = (String)names.iterator().next();
         }
      }

      PersistenceUnitInfo unit = (PersistenceUnitInfo)this.persistenceUnits.get(name);
      if (unit != null) {
         return unit;
      } else {
         if (this.parent != null) {
            unit = this.parent.getPersistenceUnit(name);
         }

         if (unit == null) {
            throw new IllegalArgumentException("No persistence unit named '" + name + "' is available in scope " + this.getScopeName() + ". Available persistence units: " + this.getPersistenceUnitNames());
         } else {
            return unit;
         }
      }
   }

   public void close() {
      super.close();
   }

   public Collection getPersistenceUnitNames() {
      Collection names = super.getPersistenceUnitNames();
      if (this.parent != null) {
         names.addAll(this.parent.getPersistenceUnitNames());
      }

      return names;
   }

   protected void putPersistenceUnit(BasePersistenceUnitInfo unit) throws EnvironmentException {
      if (unit.getPersistenceUnitName().startsWith("../")) {
         throw new IllegalArgumentException("'" + unit.getPersistenceUnitName() + "' is not a valid persistence unit name. A persistence unit name cannot start with '../'.");
      } else {
         super.putPersistenceUnit(unit);
      }
   }

   public String getQualifiedName(URL rootURL) {
      return this.parent == null ? this.getScopeName() : this.parent.getScopeName() + "#" + this.getScopeName();
   }

   protected File[] getSplitDirSourceRoots(GenericClassLoader loader, String applicationName, boolean canonicalize) throws IOException {
      return PersistenceUtils.getSplitDirSourceRoots(loader, applicationName, "WEB-INF/classes", canonicalize);
   }
}
