package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.library.LibraryManager;

public final class ImportOptionalPackagesFlow extends BaseFlow {
   public ImportOptionalPackagesFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() {
      LibraryManager mgr = this.appCtx.getLibraryManagerAggregate().getOptionalPackagesManager();
      mgr.addReferences();
      if (this.appCtx.getRuntime() != null) {
         this.appCtx.getRuntime().setOptionalPackageRuntimes(mgr.getReferencedLibraryRuntimes());
      }

   }

   public void unprepare() {
      this.appCtx.getLibraryManagerAggregate().getOptionalPackagesManager().removeReferences();
   }
}
