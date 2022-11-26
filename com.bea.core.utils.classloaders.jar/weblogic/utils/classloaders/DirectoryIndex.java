package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import weblogic.utils.io.FilenameEncoder;

public class DirectoryIndex {
   private Set fileSet = new HashSet();

   public DirectoryIndex(String path) throws IOException {
      try {
         File f = FilenameEncoder.getSafeFile(path, "." + File.separator).getParentFile();
         if (!f.isDirectory()) {
            throw new IOException(f.getCanonicalPath() + " is not a directory");
         } else {
            this.listFiles(f, (String)null);
         }
      } catch (FilenameEncoder.UnsafeFilenameException var3) {
         throw new IOException(var3.getMessage());
      }
   }

   public boolean contains(String fileName) {
      return this.fileSet.contains(fileName);
   }

   private void listFiles(File parent, String prefix) throws IOException {
      File[] files = parent.listFiles();
      File[] var4 = files;
      int var5 = files.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File file = var4[var6];
         String key;
         if (file.isDirectory()) {
            key = prefix != null ? prefix + File.separator + file.getName() : file.getName();
            this.listFiles(file, key);
         }

         key = prefix != null ? prefix + File.separator + file.getName() : file.getName();
         this.fileSet.add(key);
      }

   }

   public static void main(String[] args) throws IOException {
      new DirectoryIndex(args[0]);
   }
}
