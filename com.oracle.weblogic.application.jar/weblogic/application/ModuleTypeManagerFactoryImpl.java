package weblogic.application;

import java.io.File;
import java.io.IOException;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.shared.ModuleTypeManager;
import weblogic.deploy.api.shared.ModuleTypeManagerFactory;
import weblogic.utils.jars.VirtualJarFile;

@Service
public class ModuleTypeManagerFactoryImpl implements ModuleTypeManagerFactory {
   public ModuleTypeManager create(File path) throws IOException {
      return new ModuleTypeManagerImpl(path);
   }

   private class ModuleTypeManagerImpl implements ModuleTypeManager {
      private File path;
      ApplicationFileManager applicationFileManager;

      private ModuleTypeManagerImpl(File path) throws IOException {
         this.path = path;
         this.applicationFileManager = ApplicationFileManager.newInstance(path);
      }

      public boolean isSplitDirectory() {
         return this.applicationFileManager.isSplitDirectory();
      }

      public VirtualJarFile createVirtualJarFile() throws IOException {
         return this.applicationFileManager.getVirtualJarFile();
      }

      // $FF: synthetic method
      ModuleTypeManagerImpl(File x1, Object x2) throws IOException {
         this(x1);
      }
   }
}
