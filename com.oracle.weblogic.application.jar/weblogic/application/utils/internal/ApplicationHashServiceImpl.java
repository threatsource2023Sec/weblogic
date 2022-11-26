package weblogic.application.utils.internal;

import java.io.FileFilter;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.utils.ApplicationHashService;
import weblogic.application.utils.ApplicationHasher;

@Service
public class ApplicationHashServiceImpl implements ApplicationHashService {
   public ApplicationHasher createApplicationHasher() {
      return this.createApplicationHasher((String)null, (FileFilter)null);
   }

   public ApplicationHasher createApplicationHasher(String algorithm) {
      return this.createApplicationHasher(algorithm, (FileFilter)null);
   }

   public ApplicationHasher createApplicationHasher(String algorithm, FileFilter filter) {
      return new ApplicationHasherImpl(algorithm, filter);
   }

   public String toString() {
      return "ApplicationHashServiceImpl(" + System.identityHashCode(this) + ")";
   }
}
