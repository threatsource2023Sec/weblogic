package weblogic.application.io;

import java.io.File;
import java.io.IOException;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.MultiClassFinder;

public final class Jar extends Archive {
   private final File jarFile;
   private final String uri;

   public Jar(String uri, File jarFile) {
      if (debugLogger.isDebugEnabled()) {
         Debug.assertion(jarFile != null);
         Debug.assertion(jarFile.exists());
         Debug.assertion(!jarFile.isDirectory());
      }

      this.uri = uri;
      this.jarFile = jarFile;
   }

   public ClassFinder getClassFinder() throws IOException {
      MultiClassFinder mcf = new MultiClassFinder();
      mcf.addFinder(new DescriptorFinder(this.uri, new ClasspathClassFinder2(this.jarFile.getAbsolutePath())));
      mcf.addFinder(new ClasspathClassFinder2(this.jarFile.getAbsolutePath()));
      return mcf;
   }

   public void remove() {
   }
}
