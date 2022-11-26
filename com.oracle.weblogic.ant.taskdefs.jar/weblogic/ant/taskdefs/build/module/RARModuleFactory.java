package weblogic.ant.taskdefs.build.module;

import java.io.File;
import org.apache.tools.ant.BuildException;
import weblogic.ant.taskdefs.build.BuildCtx;
import weblogic.j2ee.J2EEUtils;

public final class RARModuleFactory extends ModuleFactory {
   Module claim(BuildCtx ctx, File srcDir, File destDir) throws BuildException {
      boolean isRAR = J2EEUtils.isRar(srcDir);
      return isRAR ? new RARModule(ctx, srcDir, destDir) : null;
   }
}
