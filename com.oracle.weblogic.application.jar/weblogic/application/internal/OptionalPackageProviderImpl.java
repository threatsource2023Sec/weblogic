package weblogic.application.internal;

import java.io.File;
import java.util.jar.Attributes;
import weblogic.application.ApplicationAccess;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.utils.LibraryUtils;
import weblogic.utils.OptionalPackageProvider;

public class OptionalPackageProviderImpl extends OptionalPackageProvider {
   private final ApplicationAccess appAccess = ApplicationAccess.getApplicationAccess();

   protected FlowContext getflowContext() {
      return (FlowContext)this.appAccess.getCurrentApplicationContext();
   }

   protected String getModuleName() {
      return this.appAccess.getCurrentModuleName();
   }

   public File[] getOptionalPackages(String src, Attributes attrs) {
      FlowContext appCtx = this.getflowContext();
      String moduleName = this.getModuleName();
      if (moduleName != null) {
         src = moduleName + " at " + src;
      }

      LibraryReference[] refs = LibraryReferenceFactory.getOptPackReference(src, attrs);
      if (refs == null) {
         return null;
      } else {
         return appCtx != null ? appCtx.getLibraryManagerAggregate().getOptionalPackagesManager().getOptionalPackages(refs) : (new LibraryManager(LibraryUtils.initOptPackReferencer(), "DOMAIN")).getOptionalPackages(refs);
      }
   }
}
