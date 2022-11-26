package weblogic.ant.taskdefs.build.module;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import weblogic.ant.taskdefs.build.BuildCtx;

public final class EJBModule extends Module {
   public EJBModule(BuildCtx ctx, File srcDir, File destDir) throws BuildException {
      super(ctx, srcDir, destDir);
   }

   public void addToClasspath(Path classpath) {
      this.addToClasspath(classpath, this.destDir);
   }

   public void build(Path classpath) throws BuildException {
      this.log("Compiling module: " + this.getClass().getName() + ": " + this.srcDir);
      this.javac(classpath, this.srcDir, this.destDir);
   }
}
