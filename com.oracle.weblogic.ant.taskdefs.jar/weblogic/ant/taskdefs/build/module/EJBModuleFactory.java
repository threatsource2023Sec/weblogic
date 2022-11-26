package weblogic.ant.taskdefs.build.module;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Javac;
import weblogic.ant.taskdefs.build.BuildCtx;
import weblogic.ejb.spi.EJBJarUtils;
import weblogic.j2ee.J2EEUtils;

public final class EJBModuleFactory extends ModuleFactory {
   Module claim(BuildCtx ctx, File srcDir, File destDir) throws BuildException {
      boolean isEJB = false;

      try {
         isEJB = J2EEUtils.isEJB(srcDir);
      } catch (IOException var8) {
      }

      try {
         if (!isEJB) {
            String encoding = null;
            Javac javacTask = ctx.getJavacTask();
            if (javacTask != null) {
               encoding = javacTask.getEncoding();
            }

            isEJB = EJBJarUtils.hasEJBSources(javacTask, srcDir, encoding);
         }
      } catch (IOException var7) {
      }

      if (srcDir.isDirectory() && srcDir.getName().toLowerCase().endsWith(".jar")) {
         isEJB = true;
      }

      return isEJB ? new EJBModule(ctx, srcDir, destDir) : null;
   }
}
