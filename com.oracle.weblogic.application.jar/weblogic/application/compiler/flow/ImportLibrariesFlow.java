package weblogic.application.compiler.flow;

import weblogic.application.compiler.CompilerCtx;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.utils.compiler.ToolFailureException;

public final class ImportLibrariesFlow extends CompilerFlow {
   private LibraryManager libraryManager = null;

   public ImportLibrariesFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.libraryManager = new LibraryManager(LibraryUtils.initAppReferencer(this.ctx.getSourceName()), "DOMAIN");
      this.processLibraries();
   }

   private void processLibraries() throws ToolFailureException {
      if (this.ctx.getWLApplicationDD() != null) {
         LibraryRefBean[] libRefsDD = this.ctx.getWLApplicationDD().getLibraryRefs();
         if (libRefsDD != null && libRefsDD.length != 0) {
            this.initAppLibManager(libRefsDD);
            if (!this.ctx.verifyLibraryReferences() || !this.libraryManager.hasUnresolvedReferences()) {
               this.importLibraries();
            }
         }
      }
   }

   private void importLibraries() throws ToolFailureException {
      try {
         LibraryUtils.importAppLibraries(this.libraryManager, this.ctx, this.ctx, this.ctx.isVerbose());
      } catch (LoggableLibraryProcessingException var2) {
         throw new ToolFailureException(var2.getLoggable().getMessage(), var2.getCause());
      }
   }

   private void initAppLibManager(LibraryRefBean[] libRefsDD) throws ToolFailureException {
      J2EELibraryReference[] ref = null;

      try {
         ref = LibraryLoggingUtils.initLibRefs(libRefsDD);
      } catch (LoggableLibraryProcessingException var4) {
         throw new ToolFailureException(var4.getLoggable().getMessage(), var4.getCause());
      }

      this.libraryManager.lookup((LibraryReference[])ref);
      this.ctx.getLibraryManagerAggregate().setAppLevelLibraryManager(this.libraryManager);
   }

   public void cleanup() {
   }
}
