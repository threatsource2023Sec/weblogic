package weblogic.application.compiler.flow;

import java.io.File;
import java.util.List;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.utils.annotation.AnnotationMappingsImpl;
import weblogic.utils.compiler.ToolFailureException;

public class ParseAnnotationsFlow extends CompilerFlow {
   private final boolean useAndSetAnnotationCache;
   private AnnotationMappingsImpl mappings;

   public ParseAnnotationsFlow(CompilerCtx ctx, boolean useAndSetAnnotationCache) {
      super(ctx);
      this.useAndSetAnnotationCache = useAndSetAnnotationCache;
   }

   public void compile() throws ToolFailureException {
      if (this.useAndSetAnnotationCache) {
         this.mappings = new AnnotationMappingsImpl(this.ctx.getAppClassLoader(), true, this.ctx.getAppAnnotationScanningClassPath(), this.ctx.getOutputDir(), this.ctx.getCacheDir(), this.ctx.getLibraryClassInfoFinders(), true);
         this.mappings.getClassInfoFinder((Class[])null);
      } else {
         this.mappings = new AnnotationMappingsImpl(this.ctx.getAppClassLoader(), false, (String)null, (File)null, (File)null, (List)null, true);
      }

      this.ctx.setAnnotationMappings(this.mappings);
   }

   public void cleanup() throws ToolFailureException {
   }
}
