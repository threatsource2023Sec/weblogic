package weblogic.application.internal.library;

import java.io.File;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryFactory;

public class JarLibraryFactory implements LibraryFactory {
   public LibraryDefinition createLibrary(LibraryData data, File tmpDir) {
      return new JarLibraryDefinition(data, tmpDir);
   }
}
