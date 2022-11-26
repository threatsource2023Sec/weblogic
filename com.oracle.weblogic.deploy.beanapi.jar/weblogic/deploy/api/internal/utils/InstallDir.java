package weblogic.deploy.api.internal.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.management.DomainDir;

public class InstallDir {
   private static final boolean debug = Debug.isDebug("utils");
   public static final String CONFIG_DIR = "plan";
   public static final String APP_DIR = "app";
   public static final String ALTDD_DIR = "altdd";
   private static final String DEFAULT_INSTALL_DIR = DomainDir.getDeploymentsDirNonCanonical();
   private File installDir;
   private String appName;
   private File configDir;
   private File appDir;
   private File plan;
   private File app;
   private File altDDDir;
   private File altAppDD;

   public InstallDir(String appName, String dir) throws IOException {
      this(appName, dir == null ? null : new File(dir));
   }

   public InstallDir(String appName, File dir) throws IOException {
      this(appName, dir, true);
   }

   public InstallDir(String appName, File dir, boolean createDirIfNull) throws IOException {
      this.configDir = null;
      this.appDir = null;
      this.plan = null;
      this.app = null;
      this.altDDDir = null;
      this.altAppDD = null;
      ConfigHelperLowLevel.checkParam("appName", appName);
      this.appName = appName;
      this.installDir = dir;
      boolean createDir = true;
      if (this.installDir == null) {
         this.installDir = this.create();
         if (!createDirIfNull) {
            createDir = false;
         }
      }

      if (createDir) {
         if (!this.installDir.exists()) {
            this.installDir.mkdirs();
         }

         if (!this.installDir.exists()) {
            this.installDir.mkdirs();
         }

         if (!this.installDir.exists() || this.installDir.isFile()) {
            throw new IOException(SPIDeployerLogger.invalidInstallDir(this.installDir.getPath()));
         }
      }

      this.installDir = this.installDir.getAbsoluteFile();
      if (debug) {
         Debug.say("Install dir defined at " + this.installDir.getPath());
      }

   }

   public InstallDir(File dir) throws IOException {
      this(dir.getName(), dir);
   }

   public File getDDFile(String modName, String uri) {
      return new File(new File(this.getConfigDir(), modName), uri);
   }

   public File getAppDDFile(String uri) {
      return new File((new File(this.getConfigDir(), uri)).getPath());
   }

   public File getInstallDir() {
      return this.installDir;
   }

   public void resetInstallDir(File f) {
      this.installDir = f;
      this.configDir = null;
      this.appDir = null;
      this.app = null;
      this.plan = null;
   }

   public File getConfigDir() {
      if (this.configDir == null) {
         this.configDir = new File(this.installDir, "plan");
      }

      return this.configDir;
   }

   public void setConfigDir(File f) {
      this.configDir = f;
   }

   public File getAppDir() {
      if (this.appDir == null) {
         this.appDir = new File(this.installDir, "app");
      }

      return this.appDir;
   }

   public void setAppDir(File f) {
      this.appDir = f;
   }

   public File getAltDDDir() {
      if (this.altDDDir == null) {
         this.altDDDir = new File(this.installDir, "altdd");
      }

      return this.altDDDir;
   }

   public void setAltDDDir(File f) {
      this.altDDDir = f;
   }

   public File getArchive() {
      if (this.app == null) {
         this.app = new File(this.getAppDir(), this.appName);
      }

      return this.app;
   }

   public void setArchive(File f) {
      this.app = f;
   }

   public File getPlan() {
      return this.plan;
   }

   public void setPlan(File f) {
      this.plan = f;
   }

   public File getAltAppDD() {
      return this.altAppDD;
   }

   public void setAltAppDD(File f) {
      this.altAppDD = f;
   }

   public boolean isInAppDir(File app) {
      if (app == null) {
         return true;
      } else {
         File a = new File(app.getAbsolutePath());
         return a.getParentFile().equals(this.getAppDir().getAbsoluteFile());
      }
   }

   public boolean isInConfigDir(File plan) {
      if (plan == null) {
         return true;
      } else {
         File p = null;
         p = new File(plan.getAbsolutePath());
         return p.getParentFile().equals(this.getConfigDir().getAbsoluteFile());
      }
   }

   public boolean isInInstallDir(File f) {
      if (f == null) {
         return true;
      } else {
         File p = null;
         p = new File(f.getAbsolutePath());
         return p.getParentFile().equals(this.getInstallDir());
      }
   }

   public FileOutputStream getOutputStream(File f) throws IOException {
      if (!f.getParentFile().exists()) {
         f.getParentFile().mkdirs();
      }

      return new FileOutputStream(f);
   }

   private File create() {
      File tmpf = new File(DEFAULT_INSTALL_DIR);
      String u = System.getProperty("user.name");
      if (!tmpf.exists() && !tmpf.isAbsolute()) {
         new File(tmpf, u);
         String tmp = System.getProperty("java.io.tmpdir");
         if (tmp == null) {
            tmp = "/tmp";
         }

         tmpf = new File(tmp, u);
         tmpf = new File(tmpf, DEFAULT_INSTALL_DIR);
      }

      return new File(tmpf, this.appName);
   }

   public boolean isProper() {
      return this.isInInstallDir(this.getAppDir()) && this.isInInstallDir(this.getConfigDir()) && this.isInAppDir(this.getArchive()) && this.isInConfigDir(this.getPlan());
   }
}
