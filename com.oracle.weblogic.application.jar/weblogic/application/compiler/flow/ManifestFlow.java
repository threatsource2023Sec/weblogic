package weblogic.application.compiler.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.jar.Manifest;
import weblogic.application.compiler.CompilerCtx;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.utils.FileUtils;
import weblogic.utils.compiler.ToolFailureException;

public class ManifestFlow extends CompilerFlow {
   public ManifestFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      File manifestFile = this.ctx.getManifestFile();
      if (manifestFile != null) {
         try {
            if (!manifestFile.exists() || !manifestFile.isFile()) {
               throw new FileNotFoundException();
            }

            Manifest manifest = new Manifest();
            manifest.read(new FileInputStream(manifestFile));
            File targetFile = new File(new File(this.ctx.getOutputDir(), "META-INF"), "MANIFEST.MF");
            FileUtils.copyPreservePermissions(manifestFile, targetFile);
         } catch (Exception var4) {
            Loggable l = J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(this.ctx.getSourceFile().getAbsolutePath(), var4.toString());
            throw new ToolFailureException(l.getMessage(), var4);
         }
      }

   }

   public void cleanup() throws ToolFailureException {
   }
}
