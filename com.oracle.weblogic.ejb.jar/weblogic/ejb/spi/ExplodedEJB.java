package weblogic.ejb.spi;

import java.io.File;
import java.io.IOException;
import weblogic.application.io.Archive;
import weblogic.application.io.DescriptorFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.MultiClassFinder;

public final class ExplodedEJB extends Archive {
   private final MultiClassFinder classFinder = new MultiClassFinder();

   public ExplodedEJB(String uri, File[] dirs) {
      String cp = this.flatten(dirs);
      this.classFinder.addFinder(new DescriptorFinder(uri, new ClasspathClassFinder2(cp)));
      this.classFinder.addFinder(new ClasspathClassFinder2(cp));
   }

   private String flatten(File[] files) {
      StringBuilder sb = new StringBuilder();
      String sep = "";
      File[] var4 = files;
      int var5 = files.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File file = var4[var6];
         sb.append(sep);
         sep = File.pathSeparator;
         sb.append(file.getAbsolutePath());
      }

      return sb.toString();
   }

   public ClassFinder getClassFinder() throws IOException {
      return this.classFinder;
   }

   public void remove() {
   }
}
