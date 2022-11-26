package weblogic.application;

import java.io.File;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.navigator.ApplicationArchiveImpl;
import weblogic.application.utils.CachedApplicationArchiveFactory;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ApplicationArchiveFileManager extends ApplicationFileManager {
   protected final ApplicationArchiveImpl application;

   public ApplicationArchiveFileManager(ApplicationArchive application) {
      this.application = (ApplicationArchiveImpl)application;
   }

   public boolean isSplitDirectory() {
      return false;
   }

   public void registerLink(String uri, String path) throws IOException {
      throw new UnsupportedOperationException("Links may not be registered  to readonly application file manager");
   }

   public VirtualJarFile getVirtualJarFile() throws IOException {
      return CachedApplicationArchiveFactory.instance.createVirtualJarFile(this.application);
   }

   public VirtualJarFile getVirtualJarFile(String compuri) throws IOException {
      return VirtualJarFactory.createVirtualJar(this.application.getDirectoryOrJarFile(compuri));
   }

   public String getClasspath(String compuri) {
      throw new UnsupportedOperationException();
   }

   public String getClasspath(String compuri, String relPath) {
      throw new UnsupportedOperationException();
   }

   public File getSourcePath() {
      return new File(this.application.getName());
   }

   public File getSourcePath(String uri) {
      try {
         return this.application.getDirectoryOrJarFile(uri);
      } catch (IOException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public File getOutputPath() {
      return this.application.getWriteableRootDir(false);
   }

   public File getOutputPath(String uri) {
      return new File(this.getOutputPath(), uri);
   }
}
