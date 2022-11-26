package weblogic.application.io.mvf.internal;

import java.io.File;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import weblogic.application.internal.library.util.DeweyDecimal;
import weblogic.application.io.mvf.MultiVersionFile;
import weblogic.application.io.mvf.VersionFile;
import weblogic.j2ee.J2EELogger;

public class MultiVersionFileImpl implements MultiVersionFile {
   private final File sourceFile;
   private SortedMap versions = new TreeMap();
   private VersionFileImpl latest = null;

   public MultiVersionFileImpl(File sourceFile) throws IOException {
      this.sourceFile = sourceFile;
      this.scan();
   }

   public VersionFile getLatest() {
      return this.latest;
   }

   private void scan() throws IOException {
      File[] contents = this.sourceFile.listFiles();
      File[] var2 = contents;
      int var3 = contents.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File content = var2[var4];
         if (content.isDirectory()) {
            DeweyDecimal version = null;

            try {
               version = new DeweyDecimal(content.getName());
               if (!this.versions.containsKey(version)) {
                  this.versions.put(version, content.getAbsoluteFile());
               }
            } catch (RuntimeException var8) {
               J2EELogger.logDotDecimalNameExpected(content.getAbsolutePath(), this.sourceFile.getAbsolutePath());
            }
         }
      }

      if (this.versions.isEmpty()) {
         throw new IOException(J2EELogger.logNoValidVersionFound(this.sourceFile.getAbsolutePath()));
      } else {
         DeweyDecimal latestVersion = (DeweyDecimal)this.versions.lastKey();
         if (latestVersion != null) {
            this.latest = new VersionFileImpl(latestVersion.toString(), (File)this.versions.get(latestVersion), latestVersion);
         }

      }
   }

   private static class VersionFileImpl implements VersionFile {
      private final String versionString;
      private final File sourceFile;
      private final DeweyDecimal subDirVersion;

      private VersionFileImpl(String versionString, File sourceFile, DeweyDecimal subDirVersion) {
         this.versionString = versionString;
         this.sourceFile = sourceFile;
         this.subDirVersion = subDirVersion;
      }

      public String getVersion() {
         return this.versionString;
      }

      public File getFile() {
         return this.sourceFile;
      }

      // $FF: synthetic method
      VersionFileImpl(String x0, File x1, DeweyDecimal x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
