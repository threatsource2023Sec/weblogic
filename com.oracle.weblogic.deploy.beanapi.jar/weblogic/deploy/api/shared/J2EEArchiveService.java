package weblogic.deploy.api.shared;

import java.io.File;
import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.utils.jars.VirtualJarFile;

@Contract
public interface J2EEArchiveService {
   boolean isEJB(VirtualJarFile var1) throws IOException;

   boolean isRar(File var1);
}
