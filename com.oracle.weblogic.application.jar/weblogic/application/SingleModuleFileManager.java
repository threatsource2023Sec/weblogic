package weblogic.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class SingleModuleFileManager extends ApplicationFileManager {
   private final File f;
   private final File extractDir;
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");

   public SingleModuleFileManager(File f) {
      this.f = f;
      this.extractDir = null;
   }

   public SingleModuleFileManager(File f, File extractDir) {
      this.f = f;
      this.extractDir = extractDir;
   }

   public boolean isSplitDirectory() {
      return false;
   }

   public void registerLink(String uri, String path) throws IOException {
      throw new AssertionError("Not supported");
   }

   public VirtualJarFile getVirtualJarFile() throws IOException {
      return VirtualJarFactory.createVirtualJar(this.f);
   }

   public VirtualJarFile getVirtualJarFile(String compuri) throws IOException {
      return this.getVirtualJarFile();
   }

   public String getClasspath(String compuri) {
      return this.getClasspath(compuri, "");
   }

   public String getClasspath(String compuri, String relPath) {
      return this.f.getAbsolutePath();
   }

   public File getSourcePath() {
      return this.f;
   }

   public File getSourcePath(String uri) {
      return this.getSourcePath();
   }

   public File getOutputPath() {
      return this.extractDir != null ? this.extractDir : this.f;
   }

   public File getOutputPath(String uri) {
      return this.getOutputPath();
   }

   public boolean isOutputPathExtractDir(String uri) throws IOException {
      File outputPath = this.getOutputPath(uri);
      String foundEntry = this.findNonInternalFiles(outputPath.toPath());
      if (debugger.isDebugEnabled()) {
         debugger.debug("SingleModuleFileManager for " + this.f + " and extract dir " + this.extractDir + " checked for isOutputPathExtractDir. Found entry: " + foundEntry);
      }

      return foundEntry != null;
   }

   private String findNonInternalFiles(Path directoryPath) throws IOException {
      DirectoryStream newDirectoryStream = Files.newDirectoryStream(directoryPath);
      Throwable var3 = null;

      try {
         Iterator var4 = newDirectoryStream.iterator();

         while(var4.hasNext()) {
            Path path = (Path)var4.next();
            String foundPath;
            if (Files.isDirectory(path, new LinkOption[0])) {
               foundPath = this.findNonInternalFiles(path);
               if (foundPath != null) {
                  String var7 = foundPath;
                  return var7;
               }
            } else if (!path.endsWith(".cache.ser")) {
               foundPath = path.toAbsolutePath().toString();
               return foundPath;
            }
         }
      } catch (Throwable var18) {
         var3 = var18;
         throw var18;
      } finally {
         if (newDirectoryStream != null) {
            if (var3 != null) {
               try {
                  newDirectoryStream.close();
               } catch (Throwable var17) {
                  var3.addSuppressed(var17);
               }
            } else {
               newDirectoryStream.close();
            }
         }

      }

      return null;
   }
}
