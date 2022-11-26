package weblogic.application.compiler;

import java.io.File;
import java.util.jar.Attributes;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.utils.OptionalPackageProvider;

public class BuildtimeOptionalPackageProviderImpl extends OptionalPackageProvider {
   private CompilerCtx ctx;

   public BuildtimeOptionalPackageProviderImpl(CompilerCtx ctx) {
      this.ctx = ctx;
   }

   public File[] getOptionalPackages(String src, Attributes attrs) {
      LibraryReference[] refs = LibraryReferenceFactory.getOptPackReference(src, attrs);
      return refs == null ? null : this.ctx.getLibraryManagerAggregate().getOptionalPackagesManager().getOptionalPackages(refs);
   }
}
