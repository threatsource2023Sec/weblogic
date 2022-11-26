package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.library.LibraryManager;
import weblogic.application.utils.LibraryUtils;

public final class OptionalPackageReferencerFlow extends BaseFlow {
   public OptionalPackageReferencerFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() {
      this.appCtx.getLibraryManagerAggregate().setOptionalPackagesManager(new LibraryManager(LibraryUtils.initOptPackReferencer(this.appCtx), this.appCtx.getPartitionName()));
   }
}
