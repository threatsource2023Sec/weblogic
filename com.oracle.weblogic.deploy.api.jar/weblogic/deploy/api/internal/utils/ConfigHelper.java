package weblogic.deploy.api.internal.utils;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.spi.BaseApplicationVersionUtils;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.utils.ApplicationNameUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class ConfigHelper extends ConfigHelperLowLevel {
   public static File getAppRootFromPlan(DeploymentPlanBean plan) {
      if (plan != null && plan.getConfigRoot() != null) {
         File root = (new File(plan.getConfigRoot())).getParentFile();
         return root;
      } else {
         return null;
      }
   }

   public static void initPlanDirFromPlan(DeploymentPlanBean plan, InstallDir installDir) {
      if (plan != null && plan.getConfigRoot() != null) {
         File pd = (new File(plan.getConfigRoot())).getAbsoluteFile();
         validateConfigRoot(plan.getConfigRoot());
         if (pd.exists() && pd.isDirectory()) {
            installDir.setConfigDir(pd);
            plan.setConfigRoot(installDir.getConfigDir().getPath());
         } else {
            installDir.setConfigDir((File)null);
            plan.setConfigRoot(installDir.getConfigDir().getPath());
         }
      } else {
         installDir.setConfigDir((File)null);
      }

   }

   private static String extractLibraryName(File app, DeploymentOptions opts) {
      try {
         if (opts != null && opts.isLibrary() && app != null) {
            VirtualJarFile libarc = VirtualJarFactory.createVirtualJar(app);
            return BaseApplicationVersionUtils.getLibName(libarc);
         }
      } catch (IOException var3) {
      }

      return null;
   }

   public static String normalize(String dir) {
      if (dir == null) {
         return null;
      } else if ("/".equals(File.separator)) {
         if (dir.length() >= 2 && dir.charAt(1) == ':') {
            dir = dir.substring(2);
         }

         return dir.replace('\\', File.separatorChar);
      } else {
         return dir.replace('/', File.separatorChar);
      }
   }

   public static File normalize(File f) {
      if (f == null) {
         return null;
      } else {
         String s = normalize(f.getPath());
         return new File(s);
      }
   }

   public static String getAppName(DeploymentOptions opts, File app, DeploymentPlanBean plan) {
      String name = null;
      name = extractLibraryName(normalize(app), opts);
      if (name != null) {
         return name;
      } else if (opts != null && opts.getName() != null) {
         return appendVersionToAppName(opts.getName(), opts);
      } else if (plan != null && plan.getApplicationName() != null && plan.getApplicationName().length() > 0) {
         return plan.getApplicationName();
      } else if (opts != null && opts.isLibrary() && app != null) {
         name = normalize(app).getName();
         name = ApplicationNameUtils.computeApplicationName(new File(name));
         return appendVersionToAppName(name, opts);
      } else {
         return name;
      }
   }

   public static String appendVersionToAppName(String appName, DeploymentOptions opts) {
      if (appName != null && opts != null && opts.getArchiveVersion() != null) {
         StringBuffer buff = new StringBuffer(appName);
         buff.append('#');
         buff.append(opts.getArchiveVersion());
         if (opts.getPlanVersion() != null) {
            buff.append('#');
            buff.append(opts.getPlanVersion());
         }

         appName = buff.toString();
      }

      return appName;
   }

   public static String getAppName(TargetModuleID[] tmids, DeploymentOptions opts) {
      if (opts != null && opts.getName() != null) {
         return opts.getName();
      } else if (tmids != null && tmids.length != 0) {
         return getAppName(tmids[0]);
      } else {
         throw new IllegalArgumentException(SPIDeployerLogger.nullTmids());
      }
   }

   public static String getAppName(TargetModuleID tmid) {
      return tmid.getModuleID();
   }

   public static void validateConfigRoot(String name) throws IllegalArgumentException {
      if (name == null || name.trim().length() == 0) {
         throw new IllegalArgumentException(SPIDeployerLogger.logConfigRootEmpty());
      }
   }
}
