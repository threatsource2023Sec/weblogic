package weblogic.application.library;

import java.io.File;

public interface LibraryFactory {
   LibraryDefinition createLibrary(LibraryData var1, File var2) throws LibraryProcessingException;
}
