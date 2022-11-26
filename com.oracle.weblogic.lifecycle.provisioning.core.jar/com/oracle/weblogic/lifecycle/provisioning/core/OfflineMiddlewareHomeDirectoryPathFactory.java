package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationProperty;
import com.oracle.weblogic.lifecycle.provisioning.core.annotations.MiddlewareHomeDirectory;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.UseProxy;
import org.jvnet.hk2.annotations.Optional;

@Singleton
public final class OfflineMiddlewareHomeDirectoryPathFactory implements Factory {
   public static final String MIDDLEWARE_HOME_DIRECTORY_PROPERTY_NAME = "middlewareHome";
   public static final String MIDDLEWARE_HOME_DIRECTORY_ENVIRONMENT_VARIABLE_NAME = "MIDDLEWARE_HOME";
   private final String middlewareHomeDirectory;

   @Inject
   public OfflineMiddlewareHomeDirectoryPathFactory(@Optional @ProvisioningOperationProperty("middlewareHome") String middlewareHomeDirectory) {
      this.middlewareHomeDirectory = middlewareHomeDirectory;
   }

   @MiddlewareHomeDirectory
   @Singleton
   @UseProxy(false)
   public final Path provide() {
      Path returnValue = null;
      if (this.middlewareHomeDirectory == null) {
         String s = System.getProperty("middlewareHome", System.getenv("MIDDLEWARE_HOME"));
         if (s != null) {
            returnValue = Paths.get(s).toAbsolutePath();
         }
      } else {
         returnValue = Paths.get(this.middlewareHomeDirectory).toAbsolutePath();
      }

      return returnValue;
   }

   public final void dispose(Path path) {
   }
}
