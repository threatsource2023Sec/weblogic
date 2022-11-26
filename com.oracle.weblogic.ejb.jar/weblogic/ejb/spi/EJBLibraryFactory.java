package weblogic.ejb.spi;

import java.io.File;
import java.io.IOException;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryFactory;
import weblogic.application.library.LibraryProcessingException;
import weblogic.j2ee.J2EEUtils;

public final class EJBLibraryFactory implements LibraryFactory {
   public LibraryDefinition createLibrary(LibraryData data, File tmpDir) throws LibraryProcessingException {
      try {
         return J2EEUtils.isEJB(data.getLocation()) ? new EJBLibraryDefinition(data) : null;
      } catch (IOException var4) {
         throw new LibraryProcessingException(var4);
      }
   }
}
