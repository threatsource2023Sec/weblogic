package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import weblogic.application.io.ClasspathInfo;
import weblogic.application.io.DescriptorFinder;
import weblogic.application.io.ExplodedJar;
import weblogic.application.io.JarCopyFilter;
import weblogic.utils.classloaders.ClassFinder;

class CaseAwareExplodedJar extends ExplodedJar {
   public CaseAwareExplodedJar(String uri, File extractDir, File jarFile, ClasspathInfo info) throws IOException {
      super(uri, extractDir, jarFile, info);
   }

   public CaseAwareExplodedJar(String uri, File extractDir, File[] userDirs, ClasspathInfo info, JarCopyFilter jarCopyFilter) {
      super(uri, extractDir, userDirs, info, jarCopyFilter);
   }

   protected ClassFinder buildDescriptorFinder() {
      StringBuffer cp = new StringBuffer();

      for(int i = 0; i < this.dirs.length; ++i) {
         this.addClasspath(cp, this.dirs[i]);
      }

      return new DescriptorFinder(this.uri, new CaseAwareClasspathClassFinder(cp.toString()));
   }
}
