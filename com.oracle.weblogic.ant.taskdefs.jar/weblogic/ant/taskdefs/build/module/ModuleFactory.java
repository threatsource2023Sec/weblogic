package weblogic.ant.taskdefs.build.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import weblogic.ant.taskdefs.build.BuildCtx;

public abstract class ModuleFactory {
   private static ModuleFactory[] factories = new ModuleFactory[]{new WebModuleFactory(), new RARModuleFactory(), new EJBModuleFactory(), new JavaModuleFactory()};

   protected static void logVerbose(Project project, String str) {
      project.log(str, 3);
   }

   abstract Module claim(BuildCtx var1, File var2, File var3) throws BuildException;

   public static List[] createModules(BuildCtx ctx, File[] dirs) throws BuildException {
      Project project = ctx.getProject();
      logVerbose(project, "Creating Modules");
      List[] modules = new List[factories.length];
      File destDir = ctx.getDestDir();

      for(int i = 0; i < dirs.length; ++i) {
         Module m = null;
         ModuleFactory factory = null;

         for(int j = 0; j < factories.length; ++j) {
            m = factories[j].claim(ctx, dirs[i], new File(destDir, dirs[i].getName()));
            if (m != null) {
               logVerbose(project, "Adding module: " + m.getClass().getName() + " with srcdir: " + m.getSrcdir() + " and destdir: " + m.getDestdir());
               if (modules[j] == null) {
                  modules[j] = new ArrayList();
               }

               modules[j].add(m);
               break;
            }
         }

         if (m == null) {
            logVerbose(project, "Unable to determine module type of directory " + dirs[i].getAbsolutePath());
         }
      }

      return reverse(modules);
   }

   private static List[] reverse(List[] m) {
      int n = m.length;
      List[] r = new List[n];

      for(int i = 0; i < n; ++i) {
         r[i] = m[n - i - 1];
      }

      return r;
   }
}
