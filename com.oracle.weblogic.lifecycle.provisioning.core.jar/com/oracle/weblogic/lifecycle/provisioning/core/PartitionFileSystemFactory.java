package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ApplicationServer;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Partition;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.UseProxy;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class PartitionFileSystemFactory implements Factory {
   private final TemporaryPartitionFilesystem tpfs;

   @Inject
   public PartitionFileSystemFactory(@ApplicationServer @Partition TemporaryPartitionFilesystem tpfs) {
      Objects.requireNonNull(tpfs);
      this.tpfs = tpfs;
   }

   @ApplicationServer
   @Partition
   @ProvisioningOperationScoped
   @UseProxy(false)
   public final File provide() {
      String className = this.getClass().getName();
      String methodName = "provide";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provide");
      }

      assert this.tpfs != null;

      File returnValue = new File(this.tpfs.getConfigDir());

      assert returnValue.isDirectory();

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "provide", returnValue);
      }

      return returnValue;
   }

   public final void dispose(File file) {
      String className = this.getClass().getName();
      String methodName = "provide";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provide", file);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "provide");
      }

   }
}
