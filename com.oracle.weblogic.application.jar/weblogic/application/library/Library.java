package weblogic.application.library;

import java.io.File;
import weblogic.management.runtime.LibraryRuntimeMBean;

public interface Library {
   String getName();

   String getSpecificationVersion();

   String getImplementationVersion();

   File getLocation();

   LibraryRuntimeMBean getRuntime();

   LibraryReference[] getLibraryReferences();

   LibraryConstants.AutoReferrer[] getAutoRef();
}
