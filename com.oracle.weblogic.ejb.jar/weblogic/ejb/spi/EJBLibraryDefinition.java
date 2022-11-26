package weblogic.ejb.spi;

import java.io.IOException;
import weblogic.application.Type;
import weblogic.application.library.ApplicationLibrary;
import weblogic.application.library.J2EELibraryReference;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryConstants;
import weblogic.application.library.LibraryContext;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryConstants.AutoReferrer;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.utils.classloaders.MultiClassFinder;

public class EJBLibraryDefinition extends LibraryDefinition implements Library, ApplicationLibrary {
   EJBLibraryDefinition(LibraryData d) {
      super(d, Type.EJB);
   }

   public void importLibrary(J2EELibraryReference libRef, LibraryContext ctx, MultiClassFinder libraryClassFinder, MultiClassFinder instanceAppLibClassFinder, MultiClassFinder shaerdAppLibClassFinder) throws LibraryProcessingException {
      LibraryLoggingUtils.checkNoContextRootSet(libRef, Type.EJB);
      this.addEJB(ctx, this.getLocation().getName());

      try {
         ctx.registerLink(this.getLocation());
      } catch (IOException var7) {
         throw new LibraryProcessingException(var7);
      }
   }

   private void addEJB(LibraryContext ctx, String uri) throws LibraryProcessingException {
      ApplicationBean appDD = ctx.getApplicationDD();
      appDD.createModule().setEjb(uri);
      LibraryLoggingUtils.updateDescriptor(ctx.getApplicationDescriptor(), appDD);
   }

   public void init() throws LibraryProcessingException {
      LibraryConstants.AutoReferrer[] autoRefs = this.getAutoRef();
      if (autoRefs.length > 0) {
         LibraryConstants.AutoReferrer[] var2 = autoRefs;
         int var3 = autoRefs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            LibraryConstants.AutoReferrer autoRef = var2[var4];
            if (autoRef != AutoReferrer.EJBApp) {
               throw new LibraryProcessingException("Unsupported Auto-Ref value: " + autoRef);
            }
         }
      }

   }
}
