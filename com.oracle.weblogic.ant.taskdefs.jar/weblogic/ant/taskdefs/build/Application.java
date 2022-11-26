package weblogic.ant.taskdefs.build;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;
import weblogic.ant.taskdefs.build.module.Module;
import weblogic.ant.taskdefs.build.module.ModuleFactory;
import weblogic.application.SplitDirectoryConstants;
import weblogic.utils.FileUtils;

public class Application implements SplitDirectoryConstants {
   private final BuildCtx ctx;
   private final Project project;
   private final File srcDir;
   private final File destDir;
   private static final String APPINF_CLASSES;
   private static final String APPINF_LIB;

   public Application(BuildCtx ctx) {
      this.ctx = ctx;
      this.project = ctx.getProject();
      this.srcDir = ctx.getSrcDir();
      this.destDir = ctx.getDestDir();
   }

   public void build(Path classpath, Set includeDirs) throws BuildException {
      List[] modules = ModuleFactory.createModules(this.ctx, this.srcDir.listFiles(FileUtils.DIR));

      for(int i = 0; i < modules.length; ++i) {
         if (modules[i] != null) {
            this.compileModules(modules[i], classpath, includeDirs);
         }
      }

   }

   protected void addToClasspath(Path classpath, File f) {
      if (f.exists()) {
         Path.PathElement pe = classpath.createPathElement();
         pe.setLocation(f);
      }

   }

   private void addAppInfLib(Path classpath, File dir) {
      FileFilter jarFilter = FileUtils.makeExtensionFilter(".jar");
      File[] libs = (new File(dir, APPINF_LIB)).listFiles(jarFilter);
      if (libs != null) {
         for(int i = 0; i < libs.length; ++i) {
            this.addToClasspath(classpath, libs[i]);
         }
      }

   }

   private void compileModules(List modules, Path classpath, Set includeDirs) throws BuildException {
      this.addToClasspath(classpath, new File(this.srcDir, APPINF_CLASSES));
      this.addToClasspath(classpath, new File(this.destDir, APPINF_CLASSES));
      this.addAppInfLib(classpath, this.srcDir);
      this.addAppInfLib(classpath, this.destDir);
      Iterator it = modules.iterator();

      Module m;
      while(it.hasNext()) {
         m = (Module)it.next();
         m.addToClasspath(classpath);
      }

      it = modules.iterator();

      while(it.hasNext()) {
         m = (Module)it.next();
         if (includeDirs.contains(m.getSrcdir())) {
            m.build(classpath);
         }
      }

   }

   static {
      APPINF_CLASSES = File.separatorChar + "APP-INF" + File.separatorChar + "classes";
      APPINF_LIB = File.separatorChar + "APP-INF" + File.separatorChar + "lib";
   }
}
