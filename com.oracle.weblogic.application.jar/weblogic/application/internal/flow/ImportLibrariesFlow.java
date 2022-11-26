package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;

public final class ImportLibrariesFlow extends BaseFlow {
   private static final ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
   private LibraryManager mgr = null;

   public ImportLibrariesFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
      if (wldd != null) {
         LibraryRefBean[] libRefsDD = wldd.getLibraryRefs();
         if (libRefsDD != null && libRefsDD.length != 0) {
            J2EELibraryReference[] ref;
            try {
               ref = LibraryLoggingUtils.initLibRefs(libRefsDD);
            } catch (LoggableLibraryProcessingException var6) {
               throw new DeploymentException(var6.getLoggable().getMessage());
            }

            this.mgr = new LibraryManager(LibraryUtils.initAppReferencer((ApplicationContextInternal)this.appCtx), this.appCtx.getPartitionName(), ref);
            this.appCtx.getLibraryManagerAggregate().setAppLevelLibraryManager(this.mgr);
            if (!this.mgr.hasUnresolvedReferences()) {
               try {
                  LibraryUtils.importAppLibraries(this.mgr, this.appCtx, this.appCtx, true);
               } catch (LoggableLibraryProcessingException var5) {
                  throw new DeploymentException(var5.getLoggable().getMessage());
               }

               this.mgr.addReferences();
               this.appCtx.getRuntime().setLibraryRuntimes(this.mgr.getReferencedLibraryRuntimes());
            }
         }
      }
   }

   public void unprepare() {
      if (this.mgr != null) {
         this.mgr.removeReferences();
      }

   }
}
