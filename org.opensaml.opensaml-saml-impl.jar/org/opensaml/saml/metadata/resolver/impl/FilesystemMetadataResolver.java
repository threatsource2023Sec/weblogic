package org.opensaml.saml.metadata.resolver.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesystemMetadataResolver extends AbstractReloadingMetadataResolver {
   private final Logger log = LoggerFactory.getLogger(FilesystemMetadataResolver.class);
   @Nonnull
   private File metadataFile;

   public FilesystemMetadataResolver(@Nonnull File metadata) throws ResolverException {
      this.setMetadataFile(metadata);
   }

   public FilesystemMetadataResolver(@Nullable Timer backgroundTaskTimer, @Nonnull File metadata) throws ResolverException {
      super(backgroundTaskTimer);
      this.setMetadataFile(metadata);
   }

   protected void setMetadataFile(@Nonnull File file) throws ResolverException {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.metadataFile = (File)Constraint.isNotNull(file, "Metadata file cannot be null");
   }

   protected void doDestroy() {
      this.metadataFile = null;
      super.doDestroy();
   }

   protected String getMetadataIdentifier() {
      return this.metadataFile.getAbsolutePath();
   }

   protected byte[] fetchMetadata() throws ResolverException {
      try {
         this.validateMetadataFile(this.metadataFile);
         DateTime metadataUpdateTime = new DateTime(this.metadataFile.lastModified(), ISOChronology.getInstanceUTC());
         return this.getLastRefresh() != null && this.getLastUpdate() != null && !metadataUpdateTime.isAfter(this.getLastUpdate()) ? null : this.inputstreamToByteArray(new FileInputStream(this.metadataFile));
      } catch (IOException var3) {
         String errMsg = "Unable to read metadata file " + this.metadataFile.getAbsolutePath();
         this.log.error("{} " + errMsg, this.getLogPrefix(), var3);
         throw new ResolverException(errMsg, var3);
      }
   }

   protected void validateMetadataFile(@Nonnull File file) throws ResolverException {
      if (!file.exists()) {
         throw new ResolverException("Metadata file '" + file.getAbsolutePath() + "' does not exist");
      } else if (!file.isFile()) {
         throw new ResolverException("Metadata file '" + file.getAbsolutePath() + "' is not a file");
      } else if (!file.canRead()) {
         throw new ResolverException("Metadata file '" + file.getAbsolutePath() + "' is not readable");
      }
   }
}
