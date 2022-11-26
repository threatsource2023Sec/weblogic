package weblogic.j2ee;

import java.io.File;
import java.io.IOException;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.shared.J2EEArchiveService;
import weblogic.utils.jars.VirtualJarFile;

@Service
public class J2EEArchiveServiceImpl implements J2EEArchiveService {
   public boolean isEJB(VirtualJarFile jar) throws IOException {
      return J2EEUtils.isEJB(jar);
   }

   public boolean isRar(File f) {
      return J2EEUtils.isRar(f);
   }
}
