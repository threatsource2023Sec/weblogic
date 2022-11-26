package weblogic.deploy.api.internal.utils;

import java.io.File;
import weblogic.application.internal.library.util.DeweyDecimal;

public interface SimpleLibraryData {
   DeweyDecimal getSpecificationVersion();

   String getName();

   String getImplementationVersion();

   File getLocation();
}
