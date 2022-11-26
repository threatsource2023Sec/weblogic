package weblogic.ant.taskdefs.build.module;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import weblogic.ant.taskdefs.build.BuildCtx;

public final class RARModule extends Module {
   private final String srcDirPath;

   public RARModule(BuildCtx ctx, File srcDir, File destDir) throws BuildException {
      super(ctx, srcDir, destDir);

      try {
         this.srcDirPath = srcDir.getCanonicalPath();
      } catch (IOException var5) {
         throw new BuildException(var5);
      }
   }

   public void addToClasspath(Path classpath) {
      this.addToClasspath(classpath, this.destDir);
   }

   public void build(Path classpath) throws BuildException {
      this.log("Compiling module: " + this.getClass().getName() + ": " + this.srcDir);
      this.destDir.mkdir();
      this.javac(classpath, this.srcDir, this.destDir);
   }
}
