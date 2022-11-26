package weblogic.application.compiler.flow;

import java.util.Arrays;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.library.Library;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.compiler.ToolFailureException;

public final class CheckUnusedLibrariesFlow extends CompilerFlow {
   public CheckUnusedLibrariesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      Library[] lib = this.ctx.getLibraryManagerAggregate().getUnreferencedLibraries();
      if (lib.length != 0) {
         Arrays.sort(lib);
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < lib.length; ++i) {
            sb.append(lib[i].toString());
            if (i < lib.length - 1) {
               sb.append(", ");
            }
         }

         J2EELogger.logAppcUnreferencedLibraries(sb.toString());
      }
   }

   public void cleanup() {
   }
}
