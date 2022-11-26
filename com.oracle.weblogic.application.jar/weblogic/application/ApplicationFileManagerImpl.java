package weblogic.application;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class ApplicationFileManagerImpl extends ApplicationFileManager implements SplitDirectoryConstants {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final File appFile;
   private boolean isSplitDirectory;
   private File splitSourceDir;
   private HashMap links = new HashMap();

   protected ApplicationFileManagerImpl(File appFile) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("created with : " + appFile.getPath());
      }

      this.appFile = appFile;
      this.handleSplitDirectory();
   }

   protected ApplicationFileManagerImpl(SplitDirectoryInfo info) {
      this.appFile = info.getDestDir();
      this.splitSourceDir = info.getSrcDir();
      this.isSplitDirectory = this.splitSourceDir != null;
      this.registerLinks(info);
   }

   private void handleSplitDirectory() throws IOException {
      if (!this.appFile.isDirectory()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("is archive not split dir");
         }

      } else {
         File propsFile = new File(this.appFile, ".beabuild.txt");
         if (!propsFile.exists()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("No split directory file so it must be a \"normal\" exploded archive");
            }

            this.isSplitDirectory = false;
            this.splitSourceDir = null;
         } else {
            this.isSplitDirectory = true;
            SplitDirectoryInfo info = new SplitDirectoryInfo(this.appFile, propsFile);
            this.splitSourceDir = info.getSrcDir();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Its a split directory app and source dir is : " + this.splitSourceDir);
            }

            this.registerLinks(info);
         }
      }
   }

   public boolean isSplitDirectory() {
      return this.isSplitDirectory;
   }

   private void registerLinks(SplitDirectoryInfo info) {
      Map uriLinks = info.getUriLinks();
      Iterator it = uriLinks.keySet().iterator();

      while(it.hasNext()) {
         String uri = (String)it.next();
         List fileList = (List)uriLinks.get(uri);
         this.registerLink(uri, (File[])((File[])fileList.toArray(new File[fileList.size()])));
      }

   }

   public void registerLink(String uri, String path) throws IOException {
      File f = new File(path);
      if (!f.exists()) {
         throw new IOException("Cannot register link for uri '" + uri + "'.  " + path + " does not exist.");
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("About to register " + uri + " for location\n" + path);
         }

         this.registerLink(uri, new File[]{f});
      }
   }

   private void registerLink(String uri, File[] f) {
      synchronized(this) {
         this.links.put(uri, f);
      }
   }

   public VirtualJarFile getVirtualJarFile() throws IOException {
      if (this.isSplitDirectory) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getVirtualJarFile isSplitDirectory src=" + this.splitSourceDir.getPath() + " appFile=" + this.appFile.getPath());
         }

         return VirtualJarFactory.createVirtualJar(this.splitSourceDir, this.appFile);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getVirtualJarFile isNOTSplitDirectory src=" + this.appFile.getPath());
         }

         return VirtualJarFactory.createVirtualJar(this.appFile);
      }
   }

   public VirtualJarFile getVirtualJarFile(String compuri) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("getVirtualJarFile isSplitDirectory=" + this.isSplitDirectory);
      }

      File[] linkFile = (File[])((File[])this.links.get(compuri));
      if (linkFile != null) {
         return VirtualJarFactory.createVirtualJar(linkFile);
      } else if (this.isSplitDirectory) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getVirtualJarFile creating SplitExplodedJarFile \nsrcdir is : " + this.splitSourceDir + "/" + compuri + "\noutdir is : " + this.appFile.getPath() + "/" + compuri);
         }

         File srcFile = new File(this.splitSourceDir, compuri);
         File outFile = new File(this.appFile, compuri);
         if (srcFile.exists() && !srcFile.isDirectory()) {
            return VirtualJarFactory.createVirtualJar(srcFile);
         } else {
            return outFile.exists() && !outFile.isDirectory() ? VirtualJarFactory.createVirtualJar(outFile) : VirtualJarFactory.createVirtualJar(srcFile, outFile);
         }
      } else if (this.appFile.isDirectory()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getVirtualJarFile creating with exploded dir \nsrcdir is : " + this.appFile.getPath() + "/" + compuri);
         }

         return VirtualJarFactory.createVirtualJar(new File(this.appFile, compuri));
      } else {
         throw new AssertionError("You cannot get a sub-vjf on an archive");
      }
   }

   private String getFileCP(File dir, String name, String relPath) {
      return (new File(dir, name + File.separatorChar + relPath)).getAbsolutePath();
   }

   public String getClasspath(String compuri) {
      return this.getClasspath(compuri, "");
   }

   public String getClasspath(String compuri, String relPath) {
      File[] linkFile = (File[])((File[])this.links.get(compuri));
      if (linkFile == null) {
         return this.isSplitDirectory ? this.getFileCP(this.splitSourceDir, compuri, relPath) + File.pathSeparator + this.getFileCP(this.appFile, compuri, relPath) : this.getFileCP(this.appFile, compuri, relPath);
      } else {
         String sep = "";
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < linkFile.length; ++i) {
            sb.append(sep);
            sb.append(linkFile[i].getAbsolutePath());
            sep = File.pathSeparator;
         }

         return sb.toString();
      }
   }

   public File getSourcePath() {
      return this.isSplitDirectory ? this.splitSourceDir : this.appFile;
   }

   public File getSourcePath(String uri) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("getSourcePath uri " + uri + " isSplitDirectory : " + this.isSplitDirectory);
      }

      File f = null;
      File[] linkFile = (File[])((File[])this.links.get(uri));
      if (linkFile != null) {
         return linkFile[0];
      } else {
         return this.isSplitDirectory && (f = new File(this.splitSourceDir, uri)).exists() ? f : new File(this.appFile, uri);
      }
   }

   public File getOutputPath() {
      return this.appFile;
   }

   public File getOutputPath(String uri) {
      File[] linkFile = (File[])((File[])this.links.get(uri));
      return linkFile != null ? linkFile[0] : new File(this.appFile, uri);
   }

   public String toString() {
      return super.toString() + " isSplitDirectory : " + (new Boolean(this.isSplitDirectory)).toString() + (this.appFile != null ? this.appFile.getPath() : "null");
   }
}
