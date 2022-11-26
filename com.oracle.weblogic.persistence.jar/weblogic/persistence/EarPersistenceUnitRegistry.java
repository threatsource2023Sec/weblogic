package weblogic.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import javax.persistence.spi.PersistenceUnitInfo;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.PersistenceUtils;
import weblogic.j2ee.J2EEUtils;
import weblogic.utils.classloaders.GenericClassLoader;

public class EarPersistenceUnitRegistry extends AbstractPersistenceUnitRegistry {
   private Map persistenceUnitDescriptors;
   private Map persistenceConfigDescriptors;

   public EarPersistenceUnitRegistry(GenericClassLoader cl, ApplicationContextInternal appCtx) throws EnvironmentException {
      super(cl, appCtx.getApplicationId(), EarUtils.getConfigDir(appCtx), appCtx.findDeploymentPlan(), appCtx);

      try {
         this.loadPersistenceDescriptors(true);
      } catch (EnvironmentException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new EnvironmentException(var5);
      }
   }

   public void initialize() throws EnvironmentException {
      super.storeDescriptors(this.persistenceUnitDescriptors, this.persistenceConfigDescriptors);
   }

   protected void storeDescriptors(Map units, Map configs) {
      this.persistenceUnitDescriptors = units;
      this.persistenceConfigDescriptors = configs;
   }

   public PersistenceUnitInfo getPersistenceUnit(String name) throws IllegalArgumentException {
      if (name == null) {
         Collection names = this.getPersistenceUnitNames();
         if (names.size() == 1) {
            name = (String)names.iterator().next();
         }
      }

      if (name.startsWith("../")) {
         int pound = name.indexOf("#");
         if (pound < 0) {
            return null;
         }

         name = name.substring(pound + 1);
      }

      return (PersistenceUnitInfo)this.persistenceUnits.get(name);
   }

   protected File[] getSplitDirSourceRoots(GenericClassLoader loader, String applicationName, boolean canonicalize) throws IOException {
      return PersistenceUtils.getSplitDirSourceRoots(loader, applicationName, "APP-INF/classes", canonicalize);
   }

   public String getQualifiedName(URL rootURL) {
      String rootPath;
      String earName;
      try {
         rootPath = (new File(rootURL.getPath())).getCanonicalPath().replace('\\', '/');
         earName = this.appCtx.getApplicationFileManager().getVirtualJarFile().getName().replace('\\', '/');
      } catch (IOException var6) {
         throw new AssertionError("Error getting file path for the JPA archive or the .ear archive: " + var6.getMessage());
      }

      String prefix = this.appCtx.getApplicationDD().getLibraryDirectory().replace('\\', '/');
      if (!rootPath.startsWith(earName + "/" + prefix)) {
         prefix = J2EEUtils.APP_INF_LIB.replace('\\', '/');
         if (!rootPath.startsWith(earName + "/" + prefix)) {
            return this.extractArchiveName(rootPath);
         }
      }

      if (!prefix.endsWith("/")) {
         prefix = prefix + "/";
      }

      String archiveName = rootPath.substring(rootPath.lastIndexOf(prefix));
      return this.getScopeName() + "#" + archiveName;
   }

   private String extractArchiveName(String archivePath) {
      int ndx = archivePath.lastIndexOf("/");
      return ndx != -1 && archivePath.length() != 1 ? archivePath.substring(ndx + 1) : archivePath;
   }
}
