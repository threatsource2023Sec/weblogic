package weblogic.coherence.container.server;

import java.io.File;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryFactory;
import weblogic.application.library.LibraryProcessingException;

public class GarLibraryFactory implements LibraryFactory {
   public LibraryDefinition createLibrary(LibraryData data, File tmpDir) throws LibraryProcessingException {
      return data != null && CoherenceDeploymentFactory.isGAR(data.getLocation()) ? new GarLibraryDefinition(data) : null;
   }
}
