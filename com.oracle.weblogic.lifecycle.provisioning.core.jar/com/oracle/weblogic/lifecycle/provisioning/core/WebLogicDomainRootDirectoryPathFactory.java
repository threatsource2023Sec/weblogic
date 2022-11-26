package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.core.annotations.WebLogicDomainRootDirectory;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.UseProxy;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.RuntimeAccess;

@Service
@Singleton
public final class WebLogicDomainRootDirectoryPathFactory implements Factory {
   public static final String WEBLOGIC_ROOT_DIRECTORY_PROPERTY_NAME = "weblogic.RootDirectory";
   private final RuntimeAccess runtimeAccess;

   @Inject
   public WebLogicDomainRootDirectoryPathFactory(@Optional RuntimeAccess runtimeAccess) {
      this.runtimeAccess = runtimeAccess;
   }

   @Singleton
   @UseProxy(false)
   @WebLogicDomainRootDirectory
   public final Path provide() {
      Path returnValue = null;
      String directoryAsString = System.getProperty("weblogic.RootDirectory");
      if (directoryAsString == null) {
         RuntimeAccess runtimeAccess = this.runtimeAccess;
         if (runtimeAccess != null) {
            DomainMBean domainMBean = runtimeAccess.getDomain();

            assert domainMBean != null;

            directoryAsString = domainMBean.getRootDirectory();
         }
      }

      if (directoryAsString != null) {
         returnValue = Paths.get(directoryAsString).toAbsolutePath();
      }

      return returnValue;
   }

   public final void dispose(Path path) {
   }
}
