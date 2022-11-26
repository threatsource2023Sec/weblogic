package com.oracle.weblogic.lifecycle.provisioning.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.management.PartitionDir;
import weblogic.utils.FileUtils;

public class TemporaryPartitionFilesystem extends PartitionDir {
   private final String partitionName;
   private final String configurationDirectory;
   private boolean deleted;

   /** @deprecated */
   @Deprecated
   public TemporaryPartitionFilesystem() throws IOException {
      super((String)null, "");
      this.partitionName = null;
      this.configurationDirectory = null;
   }

   public TemporaryPartitionFilesystem(String partitionName) throws IOException {
      super(partitionName == null ? null : createTemporaryPartitionDirectory(partitionName, "provisioning-partition-staging-"), partitionName == null ? "" : partitionName);
      Objects.requireNonNull(partitionName);
      this.partitionName = partitionName;
      this.configurationDirectory = createTemporaryPartitionDirectory(partitionName, "provisioning-partition-config-staging-");

      assert this.configurationDirectory != null;

   }

   public String getConfigDir() {
      String returnValue = this.configurationDirectory;
      if (returnValue == null) {
         throw new IllegalStateException("this.getConfigDir() == null");
      } else {
         return returnValue;
      }
   }

   public String dumpDirectories() {
      String oldValue = super.dumpDirectories();
      return oldValue != null ? oldValue.replaceFirst("\n(\\s+)configDir=.+", "\n\\1configDir=" + this.getConfigDir()) : null;
   }

   public String getDiagnosticsDir() {
      return this.getConfigDir() + File.separator + "diagnostics";
   }

   public String getJDBCDir() {
      return this.getConfigDir() + File.separator + "jdbc";
   }

   public String getJMSDir() {
      return this.getConfigDir() + File.separator + "jms";
   }

   public String getConfigSecurityDir() {
      return this.getConfigDir() + File.separator + "security";
   }

   public String getConfigStartupDir() {
      return this.getConfigDir() + File.separator + "startup";
   }

   public String getDeploymentsDir() {
      return this.getConfigDir() + File.separator + "deployments";
   }

   public String getLibModulesDir() {
      return this.getDeploymentsDir() + File.separator + "lib_modules";
   }

   public String getFMWConfigDir() {
      return this.getConfigDir() + File.separator + "fmwconfig";
   }

   public String getFMWServersConfigDir() {
      return this.getFMWConfigDir() + File.separator + "servers";
   }

   public String getCAMConfigDir() {
      return this.getFMWConfigDir() + File.separator + "components";
   }

   public String getRootDir() {
      String rootDir = super.getRootDir();
      if (rootDir == null) {
         throw new IllegalStateException("this.getRootDir() == null");
      } else {
         return rootDir;
      }
   }

   public String getPartitionHome() {
      return this.getRootDir();
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public boolean isDeleted() {
      return this.deleted;
   }

   public void overlay() throws IOException {
      String className = TemporaryPartitionFilesystem.class.getName();
      String methodName = "overlay";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "overlay");
      }

      if (this.isDeleted()) {
         throw new IllegalStateException("this.isDeleted()");
      } else {
         String root = getRealPartitionRoot(this.getPartitionName());
         if (root != null) {
            this.overlay(root);
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "overlay");
         }

      }
   }

   final void overlay(String root) throws IOException {
      String className = TemporaryPartitionFilesystem.class.getName();
      String methodName = "overlay";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "overlay", root);
      }

      if (this.isDeleted()) {
         throw new IllegalStateException("this.isDeleted()");
      } else {
         if (root != null) {
            PartitionDir partitionDir = new PartitionDir(root, this.partitionName);
            String realConfigurationDirectory = partitionDir.getConfigDir();
            if (realConfigurationDirectory == null) {
               throw new IllegalArgumentException("partitionDir", new IllegalStateException("partitionDir.getConfigDir() == null"));
            }

            String realRootDirectory = partitionDir.getRootDir();
            if (realRootDirectory == null) {
               throw new IllegalArgumentException("partitionDir", new IllegalStateException("partitionDir.getRootDir() == null"));
            }

            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "overlay", "Overlaying {0} on top of {1}", new Object[]{this.getConfigDir(), realConfigurationDirectory});
            }

            FileUtils.copyNoOverwritePreservePermissions(new File(this.getConfigDir()), new File(realConfigurationDirectory));
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "overlay", "Overlaying {0} on top of {1}", new Object[]{this.getRootDir(), realRootDirectory});
            }

            FileUtils.copyNoOverwritePreservePermissions(new File(this.getRootDir()), new File(realRootDirectory), Collections.singleton(new File(partitionDir.getTempDir())));
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "overlay");
         }

      }
   }

   public void delete() throws IOException {
      String className = TemporaryPartitionFilesystem.class.getName();
      String methodName = "delete";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "delete");
      }

      if (this.isDeleted()) {
         throw new IllegalStateException("this.isDeleted()");
      } else {
         String partitionName = this.getPartitionName();
         if (partitionName == null) {
            assert !(new File(this.getRootDir())).isDirectory();

            assert !(new File(this.getConfigDir())).isDirectory();
         } else {
            String rootDirectory = this.getRootDir();

            assert rootDirectory != null;

            assert rootDirectory.contains("provisioning-partition-staging-");

            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "delete", "Deleting {0}...", rootDirectory);
            }

            deleteDirectory(Paths.get(rootDirectory));
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "delete", "...Deleted {0}.", rootDirectory);
            }

            String configurationDirectory = this.getConfigDir();

            assert configurationDirectory != null;

            assert configurationDirectory.contains("provisioning-partition-config-staging-");

            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "delete", "Deleting {0}...", configurationDirectory);
            }

            deleteDirectory(Paths.get(configurationDirectory));
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "delete", "...Deleted {0}.", configurationDirectory);
            }
         }

         this.deleted = true;
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "delete");
         }

      }
   }

   private static final void deleteDirectory(Path moribundDirectory) throws IOException {
      if (moribundDirectory != null) {
         Files.walkFileTree(moribundDirectory, new SimpleFileVisitor() {
            public final FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
               FileVisitResult superResult = super.visitFile(file, attributes);
               Files.delete(file);
               return superResult;
            }

            public final FileVisitResult postVisitDirectory(Path directory, IOException directoryIterationFailure) throws IOException {
               FileVisitResult superResult = super.postVisitDirectory(directory, directoryIterationFailure);
               Files.delete(directory);
               return superResult;
            }
         });
      }

   }

   private static final String getRealPartitionRoot(String partitionName) {
      String className = TemporaryPartitionFilesystem.class.getName();
      String methodName = "getRealPartitionRoot";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getRealPartitionRoot", partitionName);
      }

      String returnValue = null;
      if (partitionName != null) {
         PartitionTable partitionTable = PartitionTable.getInstance();

         assert partitionTable != null;

         if (partitionName.equals(partitionTable.getGlobalPartitionName())) {
            throw new IllegalArgumentException("partition " + partitionName + " is the global partition");
         }

         PartitionTableEntry partitionTableEntry = null;

         try {
            partitionTableEntry = partitionTable.lookupByName(partitionName);

            assert partitionTableEntry != null;
         } catch (IllegalArgumentException var8) {
            throw var8;
         } catch (Throwable var9) {
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "getRealPartitionRoot", "Exception encountered while calling PartitionTable.lookupByName(\"" + partitionName + "\")", var9);
            }

            partitionTableEntry = null;
         }

         if (partitionTableEntry == null) {
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "getRealPartitionRoot", "No PartitionTableEntry was found for partition {0}", partitionName);
            }
         } else {
            assert partitionName.equals(partitionTableEntry.getPartitionName());

            returnValue = partitionTableEntry.getPartitionRoot();
            if (returnValue == null) {
               throw new IllegalStateException("partitionTableEntry.getPartitionRoot() == null");
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getRealPartitionRoot", returnValue);
      }

      return returnValue;
   }

   private static final String createTemporaryPartitionDirectory(String partitionName, String prefix) throws IOException {
      String className = TemporaryPartitionFilesystem.class.getName();
      String methodName = "createTemporaryPartitionDirectory";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "createTemporaryPartitionDirectory", new Object[]{partitionName, prefix});
      }

      if (prefix == null) {
         prefix = "";
      }

      Path temporaryRootPath;
      String returnValue;
      if (partitionName == null) {
         temporaryRootPath = Files.createTempDirectory(prefix);
      } else {
         String realPartitionRoot = getRealPartitionRoot(partitionName);
         if (realPartitionRoot == null) {
            temporaryRootPath = Files.createTempDirectory(prefix);
         } else {
            returnValue = (new PartitionDir(realPartitionRoot, partitionName)).getTempDir();

            assert returnValue != null;

            Path tempDirPath = Paths.get(returnValue);

            assert tempDirPath != null;

            File tempDirFile = tempDirPath.toFile();

            assert tempDirFile != null;

            tempDirFile.mkdirs();
            temporaryRootPath = Files.createTempDirectory(tempDirPath, prefix);
         }
      }

      assert temporaryRootPath != null;

      File temporaryRootFile = temporaryRootPath.toFile();

      assert temporaryRootFile != null;

      if (partitionName == null) {
         if (!temporaryRootFile.delete()) {
            throw new IOException("Could not delete temporary directory: " + temporaryRootFile);
         }
      } else {
         temporaryRootFile.deleteOnExit();
      }

      returnValue = temporaryRootPath.toString();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "createTemporaryPartitionDirectory", returnValue);
      }

      return returnValue;
   }
}
