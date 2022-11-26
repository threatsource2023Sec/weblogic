package weblogic.application.library;

import java.io.File;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.deploy.api.internal.utils.LibraryDataFactory;
import weblogic.deploy.api.internal.utils.SimpleLibraryData;

@Service
public class LibraryDataFactoryImpl implements LibraryDataFactory {
   public SimpleLibraryData create(String name, String specVersion, String implVersion, File location) throws LoggableLibraryProcessingException {
      return LibraryLoggingUtils.initLibraryData(name, specVersion, implVersion, location);
   }
}
