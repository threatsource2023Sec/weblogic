package weblogic.ant.taskdefs.build.module;

import java.io.File;
import java.io.FileFilter;
import org.apache.tools.ant.BuildException;
import weblogic.ant.taskdefs.build.BuildCtx;
import weblogic.utils.FileUtils;

public final class JavaModuleFactory extends ModuleFactory {
   private static FileFilter JAVA_FILTER = new FileFilter() {
      public boolean accept(File f) {
         return !f.isDirectory() && f.getName().endsWith(".java");
      }
   };

   Module claim(BuildCtx ctx, File srcDir, File destDir) throws BuildException {
      return this.hasJavaFiles(srcDir) ? new JavaModule(ctx, srcDir, destDir) : null;
   }

   private boolean hasJavaFiles(File dir) {
      if (dir == null) {
         return false;
      } else {
         File[] f = dir.listFiles(JAVA_FILTER);
         if (f != null && f.length > 0) {
            return true;
         } else {
            File[] dirs = dir.listFiles(FileUtils.DIR);
            if (dirs != null) {
               for(int i = 0; i < dirs.length; ++i) {
                  if (this.hasJavaFiles(dirs[i])) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }
}
