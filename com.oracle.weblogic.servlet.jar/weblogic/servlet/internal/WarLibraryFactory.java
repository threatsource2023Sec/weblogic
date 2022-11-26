package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryFactory;
import weblogic.application.library.LibraryProcessingException;
import weblogic.servlet.utils.WarUtils;

public final class WarLibraryFactory implements LibraryFactory {
   public LibraryDefinition createLibrary(LibraryData data, File tmpDir) throws LibraryProcessingException {
      try {
         return WarUtils.isWar(data.getLocation()) ? new WarLibraryDefinition(data, tmpDir) : null;
      } catch (IOException var4) {
         throw new LibraryProcessingException(var4);
      }
   }

   public static class Noop implements LibraryFactory {
      public LibraryDefinition createLibrary(LibraryData data, File tmpDir) throws LibraryProcessingException {
         try {
            return WarUtils.isWar(data.getLocation()) ? new WarLibraryDefinition.Noop(data, tmpDir) : null;
         } catch (IOException var4) {
            throw new LibraryProcessingException(var4);
         }
      }
   }
}
