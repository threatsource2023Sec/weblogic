package weblogic.application.internal.library;

import java.io.File;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryFactory;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.EarUtils;
import weblogic.j2ee.J2EELogger;

public class EarLibraryFactory implements LibraryFactory {
   public LibraryDefinition createLibrary(LibraryData data, File tmpDir) throws LibraryProcessingException {
      if (EarUtils.isEar(data.getLocation())) {
         if (EarUtils.isSplitDir(data.getLocation())) {
            throw new LoggableLibraryProcessingException(J2EELogger.logSplitDirNotSupportedForLibrariesLoggable(data.getLocation().getAbsolutePath()));
         } else {
            return new EarLibraryDefinition(data, tmpDir);
         }
      } else {
         return null;
      }
   }
}
