package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationProperty;
import com.oracle.weblogic.lifecycle.provisioning.core.annotations.WebLogicDomainRootDirectory;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.UseProxy;
import org.jvnet.hk2.annotations.Optional;

@Singleton
public final class OfflineWebLogicDomainRootDirectoryPathFactory implements Factory {
   public static final String WEBLOGIC_ROOT_DIRECTORY_PROPERTY_NAME = "weblogic.RootDirectory";
   private final String weblogicRootDirectory;

   @Inject
   public OfflineWebLogicDomainRootDirectoryPathFactory(@Optional @ProvisioningOperationProperty("weblogicRootDirectory") String weblogicRootDirectory) {
      this.weblogicRootDirectory = weblogicRootDirectory;
   }

   @Singleton
   @UseProxy(false)
   @WebLogicDomainRootDirectory
   public final Path provide() {
      Path returnValue = null;
      if (this.weblogicRootDirectory == null) {
         returnValue = Paths.get(System.getProperty("weblogic.RootDirectory", ".")).toAbsolutePath();
      } else {
         returnValue = Paths.get(this.weblogicRootDirectory).toAbsolutePath();
      }

      return returnValue;
   }

   public final void dispose(Path path) {
   }
}
