package weblogic.ant.taskdefs.ear;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import weblogic.application.ApplicationDescriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class AddEarModules extends Task {
   private static final int TYPE_None = 0;
   private static final int TYPE_Ejb = 1;
   private static final int TYPE_WebApp = 2;
   private static final String EXT_War = ".war";
   private static final String EXT_Jar = ".jar";
   private static final String DIR_AppLib = "app-inf/lib";
   private static final String DIR_WebLib = "web-inf/lib";
   private static final String WEBAPP = "web-inf/web.xml";
   private static final String EJB = "meta-inf/ejb-jar.xml";
   private File _earDir;
   private File _earFile;
   private EarFile _appXml;
   private boolean _update;
   private Vector _modules = new Vector();

   public void setEar(String ear) {
      this._earDir = new File(ear);
      if (!this._earDir.isDirectory()) {
         this._earFile = this._earDir;
         this._earDir = null;
      }

   }

   public void setUpdate(boolean update) {
      this._update = update;
   }

   public void addEjb(Ejb ejb) {
      this._modules.add(ejb);
   }

   public void addWeb(Web web) {
      this._modules.add(web);
   }

   public void execute() throws BuildException {
      if (this._earDir == null && this._earFile != null) {
         if (!this._earFile.exists()) {
            throw new BuildException("'" + this._earFile + "' is not a file or directory");
         }

         this.removeTmpDir();
         this._earDir = this.getTmpDir();
         this.expand();
      }

      if (this._earDir.exists() && this._earDir.isDirectory()) {
         this._appXml = new EarFile(this._earDir);
         Enumeration e = this._modules.elements();

         while(e.hasMoreElements()) {
            Ejb ejb = (Ejb)e.nextElement();
            File base = new File(this._earDir, ejb.getUri());
            if (ejb instanceof Web) {
               Web web = (Web)ejb;
               this.copyModule(web);
               if (this.getModuleType(base) != 2) {
                  this.log("Module at '" + base + "' is not a web-app", 1);
               }

               this.addWebModule(base, web.getContext());
            } else {
               this.copyModule(ejb);
               if (this.getModuleType(base) != 1) {
                  this.log("Module at '" + base + "' is not an ejb", 1);
               }

               this.addEjbModule(base);
            }
         }

         if (this._update) {
            this.checkDirectory();
         }

         try {
            this._appXml.write();
         } catch (IOException var5) {
            throw new BuildException("Error updating application.xml in ear");
         }

         if (this._earFile != null) {
            this.zip();
         }

      } else {
         throw new BuildException("'" + this._earDir + "' is not a directory");
      }
   }

   private void removeTmpDir() throws BuildException {
      File dir = this.getTmpDir();
      if (dir.exists()) {
         this.log("Deleting: " + dir.getAbsolutePath());
         this.removeDir(dir);
         dir.delete();
      }
   }

   private void removeDir(File dir) throws BuildException {
      String[] list = dir.list();

      for(int i = 0; i < list.length; ++i) {
         String s = list[i];
         File f = new File(dir, s);
         if (f.isDirectory()) {
            this.removeDir(f);
         } else if (!f.delete()) {
            throw new BuildException("Unable to delete file " + f.getAbsolutePath());
         }
      }

      if (!dir.delete()) {
         throw new BuildException("Unable to delete directory " + dir.getAbsolutePath());
      }
   }

   private void checkDirectory() {
      this.check(this._earDir);
   }

   private void check(File dir) {
      File[] files = dir.listFiles();

      for(int i = 0; i < files.length; ++i) {
         File curr = files[i];
         if (curr.isDirectory()) {
            this.check(curr);
         } else {
            String path = curr.getAbsolutePath();
            path = path.replace('\\', '/');
            if (path.toLowerCase().indexOf("app-inf/lib") < 0 && path.toLowerCase().indexOf("web-inf/lib") < 0) {
               switch (this.checkArchive(curr)) {
                  case 1:
                     this.addEjbModule(curr);
                     break;
                  case 2:
                     this.addWebModule(curr, (String)null);
               }
            }

            if (path.toLowerCase().endsWith("meta-inf/ejb-jar.xml")) {
               path = curr.getAbsolutePath();
               path = path.substring(0, path.length() - "meta-inf/ejb-jar.xml".length() - 1);
               this.addEjbModule(new File(path));
            }

            if (path.toLowerCase().endsWith("web-inf/web.xml")) {
               path = curr.getAbsolutePath();
               path = path.substring(0, path.length() - "web-inf/web.xml".length() - 1);
               this.addWebModule(new File(path), (String)null);
            }
         }
      }

   }

   private int getModuleType(File file) {
      int type = this.checkArchive(file);
      if (type != 0) {
         return type;
      } else {
         File path = new File(file, "web-inf/web.xml");
         if (path.exists()) {
            return 2;
         } else {
            path = new File(file, "meta-inf/ejb-jar.xml");
            return path.exists() ? 1 : 0;
         }
      }
   }

   public void copyModule(Ejb module) {
      Enumeration fss = module.getFileSets();
      String uri = module.getUri();
      String src = module.getSrc();
      File destDir = new File(this._earDir, uri);
      if (src != null) {
         File file = new File(src, uri);
         FileSet fileset = new FileSet();
         if (file.isDirectory()) {
            fileset.setDir(file);
         } else {
            destDir = destDir.getParentFile();
            fileset.setFile(file);
         }

         this.copyFileset(fileset, destDir);
      }

      while(fss.hasMoreElements()) {
         FileSet fs = (FileSet)fss.nextElement();
         this.copyFileset(fs, destDir);
      }

   }

   private void copyFileset(FileSet fs, File destDir) throws BuildException {
      Hashtable filecopyList = new Hashtable();

      try {
         String[] files = fs.getDirectoryScanner(this.getProject()).getIncludedFiles();
         this.scanDir(filecopyList, fs.getDir(this.getProject()), destDir, files);
         if (filecopyList.size() == 0) {
            throw new BuildException("No files in fileset dir=" + fs.getDir(this.getProject()));
         }

         if (filecopyList.size() > 0) {
            this.log("Copying " + filecopyList.size() + " file" + (filecopyList.size() == 1 ? "" : "s") + " to " + destDir.getAbsolutePath());
            Enumeration e = filecopyList.keys();

            while(e.hasMoreElements()) {
               String fromFile = (String)e.nextElement();
               String toFile = (String)filecopyList.get(fromFile);

               try {
                  this.getProject().copyFile(fromFile, toFile, false, true);
               } catch (IOException var13) {
                  String msg = "Failed to copy " + fromFile + " to " + toFile + " due to " + var13.getMessage();
                  throw new BuildException(msg, var13, this.getLocation());
               }
            }
         }
      } finally {
         filecopyList.clear();
      }

   }

   private void scanDir(Hashtable filecopyList, File from, File to, String[] files) {
      for(int i = 0; i < files.length; ++i) {
         String filename = files[i];
         File srcFile = new File(from, filename);
         File destFile = new File(to, filename);
         filecopyList.put(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
      }

   }

   private File getTmpDir() {
      return new File(System.getProperty("java.io.tmpdir"), this._earFile.getName());
   }

   private int checkArchive(File file) throws BuildException {
      String path = file.getAbsolutePath();
      if (!path.toLowerCase().endsWith(".jar") && !path.toLowerCase().endsWith(".war")) {
         return 0;
      } else {
         try {
            ZipFile zi = new ZipFile(file);
            Enumeration e = zi.getEntries();

            String name;
            do {
               if (!e.hasMoreElements()) {
                  zi.close();
                  return 0;
               }

               ZipEntry ze = (ZipEntry)e.nextElement();
               name = ze.getName();
               name = name.replace('\\', '/');
               if (name.toLowerCase().equals("web-inf/web.xml")) {
                  return 2;
               }
            } while(!name.toLowerCase().equals("meta-inf/ejb-jar.xml"));

            return 1;
         } catch (IOException var7) {
            throw new BuildException("Error processing " + file);
         }
      }
   }

   protected void addWebModule(File file, String context) throws BuildException {
      try {
         String path = file.getAbsolutePath();
         boolean hasmodule = this._appXml.isWebModule(file);
         if (context == null) {
            context = path.substring(this._earDir.getAbsolutePath().length() + 1);
            if (!file.isDirectory() && (context.endsWith(".jar") || context.endsWith(".war"))) {
               context = context.substring(0, context.length() - ".jar".length());
            }
         }

         this.log((hasmodule ? "Updating" : "Adding") + " web module '" + this.getRelativeFile(file) + "' with context '" + context + "'");
         this._appXml.addWebModule(file, context);
      } catch (IOException var5) {
         throw new BuildException("Error updating application.xml in ear");
      }
   }

   protected void addEjbModule(File file) throws BuildException {
      try {
         if (!this._appXml.isEjbModule(file)) {
            this.log("Adding ebj module '" + this.getRelativeFile(file) + "'");
            this._appXml.addEjbModule(file);
         }

      } catch (IOException var3) {
         throw new BuildException("Error updating application.xml in ear");
      }
   }

   private void expand() throws BuildException {
      Expand ex = new Expand();
      ex.setSrc(this._earFile);
      ex.setDest(this._earDir);
      ex.setProject(this.getProject());
      ex.setTaskName(this.getTaskName());
      ex.execute();
   }

   private void zip() throws BuildException {
      Zip ex = new Zip();
      ex.setFile(this._earFile);
      ex.setBasedir(this._earDir);
      ex.setProject(this.getProject());
      ex.setTaskName(this.getTaskName());
      ex.execute();
   }

   private String getRelativeFile(File ejbFile) throws IOException {
      String relFile = ejbFile.getCanonicalFile().getAbsolutePath().substring(this._earDir.getCanonicalFile().getAbsolutePath().length() + 1).replace('\\', '/');
      return relFile;
   }

   private static class EarFile {
      private File earDir = null;
      private boolean appBeanDirty = false;
      private ApplicationBean appBean = null;

      public EarFile(File earDir) {
         this.earDir = earDir;
      }

      public boolean isWebModule(File warFile) throws IOException {
         ApplicationBean bean = this.getApplicationBean(true);
         String relFile = this.getRelativeFilePath(warFile);
         ModuleBean[] modules = bean.getModules();

         for(int i = 0; i < modules.length; ++i) {
            WebBean webBean = modules[i].getWeb();
            if (webBean != null && webBean.getWebUri().equals(relFile)) {
               return true;
            }
         }

         return false;
      }

      public void addWebModule(File warFile, String contextRoot) throws IOException {
         ApplicationBean bean = this.getApplicationBean(true);
         String relFile = this.getRelativeFilePath(warFile);
         ModuleBean[] modules = bean.getModules();
         boolean found = false;

         WebBean webBean;
         for(int i = 0; i < modules.length && !found; ++i) {
            webBean = modules[i].getWeb();
            if (webBean != null && webBean.getWebUri().equals(relFile)) {
               found = true;
               if (!webBean.getContextRoot().equals(contextRoot)) {
                  webBean.setContextRoot(contextRoot);
                  this.appBeanDirty = true;
               }
            }
         }

         if (!found) {
            ModuleBean module = bean.createModule();
            webBean = module.createWeb();
            webBean.setWebUri(relFile);
            webBean.setContextRoot(contextRoot);
            this.appBeanDirty = true;
         }

      }

      public boolean isEjbModule(File ejbFile) throws IOException {
         ApplicationBean bean = this.getApplicationBean(true);
         String relFile = this.getRelativeFilePath(ejbFile);
         ModuleBean[] modules = bean.getModules();

         for(int i = 0; i < modules.length; ++i) {
            String ejb = modules[i].getEjb();
            if (ejb != null && relFile.equals(modules[i].getEjb())) {
               return true;
            }
         }

         return false;
      }

      public void addEjbModule(File ejbFile) throws IOException {
         ApplicationBean bean = this.getApplicationBean(true);
         String relFile = this.getRelativeFilePath(ejbFile);
         ModuleBean[] modules = bean.getModules();

         for(int j = 0; j < modules.length; ++j) {
            if (relFile.equals(modules[j].getEjb())) {
               return;
            }
         }

         ModuleBean module = bean.createModule();
         module.setEjb(relFile);
         this.appBeanDirty = true;
      }

      private String getRelativeFilePath(File ejbFile) throws IOException {
         String relFile = ejbFile.getCanonicalFile().getAbsolutePath().substring(this.earDir.getCanonicalFile().getAbsolutePath().length() + 1).replace('\\', '/');
         return relFile;
      }

      private synchronized ApplicationBean getApplicationBean(boolean create) throws IOException {
         if (this.appBean == null) {
            File f = new File(this.earDir, "META-INF/application.xml");
            if (f.exists()) {
               try {
                  ApplicationDescriptor desc = new ApplicationDescriptor(f, (File)null, (File)null, (File)null, (DeploymentPlanBean)null, (String)null);
                  desc.setValidateSchema(false);
                  this.appBean = desc.getApplicationDescriptor();
               } catch (DescriptorException var4) {
                  if (!create) {
                     throw new IOException("The existing application.xml at " + f.getAbsolutePath() + " is corrupted.\n" + var4.getMessage());
                  }

                  System.out.println("[AddEarModules] The existing application.xml at " + f.getAbsolutePath() + " is corrupted. " + var4.getMessage());
                  System.out.println("[AddEarModules] Recreating a new application.xml");
                  this.createApplicationBean();
               } catch (Exception var5) {
                  throw new IOException("The existing application.xml at " + f.getAbsolutePath() + " is corrupted.\n" + var5.getMessage());
               }
            }

            if (!f.exists() && create) {
               this.createApplicationBean();
            }
         }

         return this.appBean;
      }

      private void createApplicationBean() {
         this.appBean = (ApplicationBean)(new EditableDescriptorManager()).createDescriptorRoot(ApplicationBean.class).getRootBean();
         int index = this.earDir.getAbsolutePath().lastIndexOf("/");
         if (index == -1) {
            index = this.earDir.getAbsolutePath().lastIndexOf("\\");
         }

         String sAppName = this.earDir.getAbsolutePath().substring(index + 1);
         this.appBean.setDisplayNames(new String[]{sAppName});
      }

      public void write() throws IOException {
         if (this.appBeanDirty) {
            FileOutputStream out = null;

            try {
               File dir = new File(this.earDir, "META-INF");
               dir.mkdirs();
               File f = new File(dir, "application.xml");
               out = new FileOutputStream(f);
               (new EditableDescriptorManager()).writeDescriptorAsXML(((DescriptorBean)this.appBean).getDescriptor(), out);
            } finally {
               if (out != null) {
                  try {
                     out.close();
                  } catch (IOException var9) {
                  }
               }

            }
         }

      }
   }

   public static class Web extends Ejb {
      private String _context;

      public void setContext(String context) {
         this._context = context;
      }

      public String getContext() {
         return this._context;
      }
   }

   public static class Ejb {
      private String _src;
      private String _uri;
      private Vector _srcs = new Vector();

      public void addFileSet(FileSet set) {
         this._srcs.add(set);
      }

      public void setUri(String uri) {
         this._uri = uri;
      }

      public void setSrc(String src) {
         this._src = src;
      }

      public String getUri() {
         return this._uri;
      }

      public String getSrc() {
         return this._src;
      }

      public Enumeration getFileSets() {
         return this._srcs.elements();
      }
   }
}
