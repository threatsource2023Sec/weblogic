package weblogic.application.compiler.flow;

import weblogic.application.compiler.BuildtimeOptionalPackageProviderImpl;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.library.LibraryManager;
import weblogic.application.utils.LibraryUtils;
import weblogic.utils.OptionalPackageProvider;
import weblogic.utils.compiler.ToolFailureException;

public class OptionalPackageReferencerFlow extends CompilerFlow {
   private boolean buildtimeProvider = false;

   public OptionalPackageReferencerFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void cleanup() throws ToolFailureException {
      if (this.buildtimeProvider) {
         OptionalPackageProvider.set((OptionalPackageProvider)null);
         this.buildtimeProvider = false;
      }

   }

   public void compile() throws ToolFailureException {
      if (OptionalPackageProvider.get() == null) {
         OptionalPackageProvider.set(new BuildtimeOptionalPackageProviderImpl(this.ctx));
         this.buildtimeProvider = true;
      }

      this.ctx.getLibraryManagerAggregate().setOptionalPackagesManager(new LibraryManager(LibraryUtils.initOptPackReferencer(), "DOMAIN"));
   }
}
