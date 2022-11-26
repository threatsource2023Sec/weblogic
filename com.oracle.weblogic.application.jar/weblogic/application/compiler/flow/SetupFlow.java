package weblogic.application.compiler.flow;

import java.io.File;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializerManager;
import weblogic.utils.FileUtils;
import weblogic.utils.compiler.ToolFailureException;

public class SetupFlow extends CompilerFlow {
   private static final boolean KEEP_TEMP_FILES = Boolean.getBoolean("weblogic.application.compiler.KeepTempFilesOnExit");
   private String tempDirName;

   public SetupFlow(CompilerCtx ctx, String tempDirName) {
      super(ctx);
      this.tempDirName = tempDirName;
   }

   public void cleanup() throws ToolFailureException {
      if (!KEEP_TEMP_FILES) {
         if (this.ctx.getOutputDir() != null && this.ctx.getTempDir() != null && this.ctx.getOutputDir().getName().startsWith(this.ctx.getTempDir().getName() + "_")) {
            FileUtils.remove(this.ctx.getOutputDir());
            this.ctx.setOutputDir((File)null);
         }

         FileUtils.remove(this.ctx.getTempDir());
         this.ctx.setTempDir((File)null);
      }
   }

   public void compile() throws ToolFailureException {
      File baseDir = new File(ToolsFactoryManager.getToolsEnvironment().getTemporaryDirectory(), this.tempDirName);
      if (baseDir.exists() && !baseDir.isDirectory()) {
         baseDir.delete();
      }

      baseDir.mkdirs();
      this.ctx.setTempDir(baseDir);
      ToolsInitializerManager.init();
   }
}
