package org.opensaml.saml.metadata.resolver.impl;

import java.io.IOException;
import java.util.Timer;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resource.Resource;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceBackedMetadataResolver extends AbstractReloadingMetadataResolver {
   private final Logger log = LoggerFactory.getLogger(ResourceBackedMetadataResolver.class);
   private Resource metadataResource;

   public ResourceBackedMetadataResolver(Timer timer, Resource resource) throws IOException {
      super(timer);
      if (!resource.exists()) {
         throw new IOException("Resource " + resource.getDescription() + " does not exist.");
      } else {
         this.metadataResource = resource;
      }
   }

   public ResourceBackedMetadataResolver(Resource resource) throws IOException {
      if (!resource.exists()) {
         throw new IOException("Resource " + resource.getDescription() + " does not exist.");
      } else {
         this.metadataResource = resource;
      }
   }

   protected void doDestroy() {
      this.metadataResource = null;
      super.doDestroy();
   }

   protected String getMetadataIdentifier() {
      return this.metadataResource.getDescription();
   }

   protected byte[] fetchMetadata() throws ResolverException {
      try {
         DateTime metadataUpdateTime = new DateTime(this.metadataResource.lastModified());
         this.log.debug("{} Resource {} was last modified {}", new Object[]{this.getLogPrefix(), this.metadataResource.getDescription(), metadataUpdateTime});
         return this.getLastRefresh() != null && !metadataUpdateTime.isAfter(this.getLastRefresh()) ? null : this.inputstreamToByteArray(this.metadataResource.getInputStream());
      } catch (IOException var3) {
         String errorMsg = "Unable to read metadata file";
         this.log.error("{} Unable to read metadata file", this.getLogPrefix(), var3);
         throw new ResolverException("Unable to read metadata file", var3);
      }
   }
}
