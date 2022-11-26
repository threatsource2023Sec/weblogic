package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ApplicationServer;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Partition;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.RuntimeDir;

@Service
@Singleton
public class TemporaryPartitionFilesystemConfigDirPathFactory implements Factory {
   private final Provider provider;

   @Inject
   public TemporaryPartitionFilesystemConfigDirPathFactory(@ApplicationServer @Partition Provider provider) {
      Objects.requireNonNull(provider);
      this.provider = provider;
   }

   @ApplicationServer
   @Partition
   @ProvisioningOperationScoped
   public Path provide() {
      assert this.provider != null;

      RuntimeDir partitionFilesystem = (RuntimeDir)this.provider.get();

      assert partitionFilesystem != null;

      String configDir = partitionFilesystem.getConfigDir();

      assert configDir != null;

      return Paths.get(configDir);
   }

   public void dispose(Path path) {
   }
}
