package weblogic.ant.taskdefs.build.module;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import weblogic.ant.taskdefs.build.BuildCtx;

public final class JavaModule extends Module {
   private static final String APPINF_CLASSES;

   public JavaModule(BuildCtx ctx, File srcDir, File destDir) {
      super(ctx, srcDir, destDir);
   }

   public void addToClasspath(Path classpath) {
      this.addToClasspath(classpath, new File(this.destDir, APPINF_CLASSES));
   }

   public void build(Path classpath) throws BuildException {
      this.log("Compiling module: " + this.getClass().getName() + ": " + this.srcDir);
      File dir = this.destDir.getParentFile();
      if (dir == null) {
         dir = this.destDir;
      }

      this.javac(classpath, this.srcDir, new File(dir, APPINF_CLASSES));
   }

   static {
      APPINF_CLASSES = File.separatorChar + "APP-INF" + File.separatorChar + "classes";
   }
}
