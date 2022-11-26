package weblogic.deploy.api.shared;

import java.io.IOException;
import weblogic.utils.jars.VirtualJarFile;

public interface ModuleTypeManager {
   boolean isSplitDirectory();

   VirtualJarFile createVirtualJarFile() throws IOException;
}
