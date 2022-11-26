package weblogic.application;

import java.io.File;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.io.AA;
import weblogic.application.utils.CachedApplicationArchiveFactory;
import weblogic.utils.jars.VirtualJarFile;

public abstract class ApplicationFileManager implements SplitDirectoryConstants {
   public static ApplicationFileManager newInstance(String appPath) throws IOException {
      return newInstance(new File(appPath));
   }

   public static ApplicationFileManager newInstance(File f) throws IOException {
      return (ApplicationFileManager)(AA.useApplicationArchive(f) ? new ApplicationArchiveFileManager(CachedApplicationArchiveFactory.instance.create(f)) : new ApplicationFileManagerImpl(f));
   }

   public static ApplicationFileManager newInstance(SplitDirectoryInfo info) throws IOException {
      return new ApplicationFileManagerImpl(info);
   }

   public static ApplicationFileManager newInstance(ApplicationArchive app) {
      return new EditableApplicationArchiveFileManager(app);
   }

   public abstract boolean isSplitDirectory();

   public abstract void registerLink(String var1, String var2) throws IOException;

   public abstract VirtualJarFile getVirtualJarFile() throws IOException;

   public abstract VirtualJarFile getVirtualJarFile(String var1) throws IOException;

   public abstract String getClasspath(String var1);

   public abstract String getClasspath(String var1, String var2);

   public abstract File getSourcePath();

   public abstract File getSourcePath(String var1);

   public abstract File getOutputPath();

   public abstract File getOutputPath(String var1);

   public boolean isOutputPathExtractDir(String uri) throws IOException {
      return true;
   }
}
