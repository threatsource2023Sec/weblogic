package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.Positive;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.apache.http.client.HttpClient;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class FileBackedHTTPMetadataResolver extends HTTPMetadataResolver {
   @Nonnull
   private final Logger log;
   @Nullable
   private File metadataBackupFile;
   private boolean initializing;
   private boolean initializeFromBackupFile;
   private boolean initializedFromBackupFile;
   @Duration
   @Positive
   private long backupFileInitNextRefreshDelay;

   public FileBackedHTTPMetadataResolver(HttpClient client, String metadataURL, String backupFilePath) throws ResolverException {
      this((Timer)null, client, metadataURL, backupFilePath);
   }

   public FileBackedHTTPMetadataResolver(Timer backgroundTaskTimer, HttpClient client, String metadataURL, String backupFilePath) throws ResolverException {
      super(backgroundTaskTimer, client, metadataURL);
      this.log = LoggerFactory.getLogger(FileBackedHTTPMetadataResolver.class);
      this.initializeFromBackupFile = true;
      this.backupFileInitNextRefreshDelay = 5000L;
      this.setBackupFile(backupFilePath);
   }

   public boolean isInitializedFromBackupFile() {
      return this.initializedFromBackupFile;
   }

   public boolean isInitializeFromBackupFile() {
      return this.initializeFromBackupFile;
   }

   public void setInitializeFromBackupFile(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.initializeFromBackupFile = flag;
   }

   public long getBackupFileInitNextRefreshDelay() {
      return this.backupFileInitNextRefreshDelay;
   }

   public void setBackupFileInitNextRefreshDelay(long delay) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (delay < 0L) {
         throw new IllegalArgumentException("Backup file init next refresh delay must be greater than 0");
      } else {
         this.backupFileInitNextRefreshDelay = delay;
      }
   }

   protected void doDestroy() {
      this.metadataBackupFile = null;
      super.doDestroy();
   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      try {
         this.validateBackupFile(this.metadataBackupFile);
      } catch (ResolverException var6) {
         if (this.isFailFastInitialization()) {
            this.log.error("{} Metadata backup file path was invalid, initialization is fatal", this.getLogPrefix());
            throw new ComponentInitializationException("Metadata backup file path was invalid", var6);
         }

         this.log.error("{} Metadata backup file path was invalid, continuing without known good backup file", this.getLogPrefix());
      }

      try {
         this.initializing = true;
         super.initMetadataResolver();
      } finally {
         this.initializing = false;
      }

   }

   protected void setBackupFile(String backupFilePath) throws ResolverException {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      File backingFile = new File(backupFilePath);
      this.metadataBackupFile = backingFile;
   }

   protected void validateBackupFile(File backupFile) throws ResolverException {
      if (!backupFile.exists()) {
         boolean var8 = false;

         try {
            var8 = true;
            this.log.debug("{} Testing creation of backup file", this.getLogPrefix());
            backupFile.createNewFile();
            var8 = false;
         } catch (IOException var9) {
            String msg = "Unable to create backup file " + backupFile.getAbsolutePath();
            this.log.error("{} " + msg, this.getLogPrefix(), var9);
            throw new ResolverException(msg, var9);
         } finally {
            if (var8) {
               if (backupFile.exists()) {
                  boolean deleted = backupFile.delete();
                  if (!deleted) {
                     this.log.debug("{} Deletion of test backup file failed", this.getLogPrefix());
                  }
               }

            }
         }

         if (backupFile.exists()) {
            boolean deleted = backupFile.delete();
            if (!deleted) {
               this.log.debug("{} Deletion of test backup file failed", this.getLogPrefix());
            }
         }
      }

      if (backupFile.exists()) {
         if (backupFile.isDirectory()) {
            throw new ResolverException("Filepath " + backupFile.getAbsolutePath() + " is a directory and may not be used as a backup metadata file");
         }

         if (!backupFile.canRead()) {
            throw new ResolverException("Filepath " + backupFile.getAbsolutePath() + " exists but can not be read by this user");
         }

         if (!backupFile.canWrite()) {
            throw new ResolverException("Filepath " + backupFile.getAbsolutePath() + " exists but can not be written to by this user");
         }
      }

   }

   protected byte[] fetchMetadata() throws ResolverException {
      if (this.initializing && this.initializeFromBackupFile && this.metadataBackupFile.exists()) {
         this.log.debug("{} On initialization, detected existing backup file, attempting load from that: {}", this.getLogPrefix(), this.metadataBackupFile.getAbsolutePath());

         try {
            byte[] backingData = Files.toByteArray(this.metadataBackupFile);
            this.log.debug("{} Successfully initialized from backup file: {}", this.getLogPrefix(), this.metadataBackupFile.getAbsolutePath());
            this.initializedFromBackupFile = true;
            return backingData;
         } catch (IOException var6) {
            this.log.warn("{} Error initializing from backup file, continuing with normal HTTP fetch", this.getLogPrefix(), var6);
         }
      }

      try {
         return super.fetchMetadata();
      } catch (ResolverException var5) {
         if (this.getCachedOriginalMetadata() != null) {
            this.log.warn("{} Problem reading metadata from remote source; detected existing cached metadata, skipping load of backup file", this.getLogPrefix());
            return null;
         } else if (this.metadataBackupFile.exists()) {
            this.log.warn("{} Problem reading metadata from remote source, processing existing backup file: {}", this.getLogPrefix(), this.metadataBackupFile.getAbsolutePath());

            try {
               return Files.toByteArray(this.metadataBackupFile);
            } catch (IOException var4) {
               String errMsg = "Unable to retrieve metadata from backup file " + this.metadataBackupFile.getAbsolutePath();
               this.log.error("{} " + errMsg, this.getLogPrefix(), var4);
               throw new ResolverException(errMsg, var4);
            }
         } else {
            this.log.error("{} Unable to read metadata from remote server and backup does not exist", this.getLogPrefix());
            throw new ResolverException("Unable to read metadata from remote server and backup does not exist");
         }
      }
   }

   protected long computeNextRefreshDelay(DateTime expectedExpiration) {
      if (this.initializing && this.initializedFromBackupFile) {
         this.log.debug("{} Detected initialization from backup file, scheduling next refresh from HTTP in {}ms", this.getLogPrefix(), this.getBackupFileInitNextRefreshDelay());
         return this.getBackupFileInitNextRefreshDelay();
      } else {
         return super.computeNextRefreshDelay(expectedExpiration);
      }
   }

   protected void postProcessMetadata(byte[] metadataBytes, Document metadataDom, XMLObject originalMetadata, XMLObject filteredMetadata) throws ResolverException {
      try {
         this.validateBackupFile(this.metadataBackupFile);
         FileOutputStream out = new FileOutputStream(this.metadataBackupFile);
         Throwable var6 = null;

         try {
            out.write(metadataBytes);
            out.flush();
         } catch (Throwable var26) {
            var6 = var26;
            throw var26;
         } finally {
            if (out != null) {
               if (var6 != null) {
                  try {
                     out.close();
                  } catch (Throwable var25) {
                     var6.addSuppressed(var25);
                  }
               } else {
                  out.close();
               }
            }

         }
      } catch (ResolverException var28) {
         this.log.error("{} Unable to write metadata to backup file: {}", new Object[]{this.getLogPrefix(), this.metadataBackupFile.getAbsoluteFile(), var28});
      } catch (IOException var29) {
         this.log.error("{} Unable to write metadata to backup file: {}", new Object[]{this.getLogPrefix(), this.metadataBackupFile.getAbsoluteFile(), var29});
      } finally {
         super.postProcessMetadata(metadataBytes, metadataDom, originalMetadata, filteredMetadata);
      }

   }
}
