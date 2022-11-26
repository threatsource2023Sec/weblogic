package weblogic.ant.taskdefs.build.module;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import weblogic.ant.taskdefs.build.BuildCtx;
import weblogic.servlet.utils.WarUtils;

public final class WebModuleFactory extends ModuleFactory {
   Module claim(BuildCtx ctx, File srcDir, File destDir) throws BuildException {
      try {
         if ((new File(srcDir, "WEB-INF")).exists() || WarUtils.isWar(srcDir)) {
            return new WebModule(ctx, srcDir, destDir);
         }
      } catch (IOException var5) {
      }

      return null;
   }
}
