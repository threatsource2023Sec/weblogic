package weblogic.ant.taskdefs.build.module;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;
import weblogic.ant.taskdefs.build.BuildCtx;

public abstract class Module {
   protected BuildCtx ctx;
   protected Project project;
   protected final File srcDir;
   protected final File destDir;

   public Module(BuildCtx ctx, File srcDir, File destDir) {
      this.ctx = ctx;
      this.project = ctx.getProject();
      this.srcDir = srcDir;
      this.destDir = destDir;
   }

   public File getSrcdir() {
      return this.srcDir;
   }

   public File getDestdir() {
      return this.destDir;
   }

   public abstract void addToClasspath(Path var1);

   public abstract void build(Path var1) throws BuildException;

   protected void addToClasspath(Path classpath, File f) {
      Path.PathElement pe = classpath.createPathElement();
      pe.setLocation(f);
   }

   protected void log(String s) {
      this.project.log(s, 3);
   }

   protected void javac(Path classpath, File javacDir, File outDir) {
      this.javac(classpath, javacDir, outDir, (File)null);
   }

   protected void javac(Path classpath, File javacDir, File outDir, File sourcePath) {
      this.log("javac javacDir: " + javacDir + " outDir: " + outDir);
      Javac userJavacTask = this.ctx.getJavacTask();
      Javac javacTask = (Javac)this.project.createTask("javac");
      if (userJavacTask != null) {
         JavacCloner.getJavaCloner().copy(userJavacTask, javacTask);
      }

      javacTask.setSrcdir(new Path(this.project, javacDir.getAbsolutePath()));
      outDir.mkdirs();
      javacTask.setDestdir(outDir);
      javacTask.setClasspath(classpath);
      if (sourcePath != null) {
         javacTask.setSourcepath(new Path(this.project, sourcePath.getAbsolutePath()));
      }

      javacTask.execute();
   }
}
