package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.core.annotations.MiddlewareHomeDirectory;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.UseProxy;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;

@Service
@Singleton
public final class MiddlewareHomeDirectoryPathFactory implements Factory {
   private final RuntimeAccess runtimeAccess;

   @Inject
   public MiddlewareHomeDirectoryPathFactory(@Optional RuntimeAccess runtimeAccess) {
      this.runtimeAccess = runtimeAccess;
   }

   @Singleton
   @UseProxy(false)
   @MiddlewareHomeDirectory
   public final Path provide() {
      Path returnValue = null;
      RuntimeAccess runtimeAccess = this.runtimeAccess;
      String directoryAsString = null;
      if (runtimeAccess != null) {
         ServerRuntimeMBean serverRuntimeMBean = runtimeAccess.getServerRuntime();

         assert serverRuntimeMBean != null;

         directoryAsString = serverRuntimeMBean.getMiddlewareHome();
      }

      if (directoryAsString == null) {
         directoryAsString = System.getProperty("MIDDLEWARE_HOME", System.getenv("MIDDLEWARE_HOME"));
      }

      if (directoryAsString != null) {
         returnValue = Paths.get(directoryAsString).toAbsolutePath();
      }

      return returnValue;
   }

   public final void dispose(Path path) {
   }
}
