package com.bea.core.repackaged.springframework.scripting.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.support.EncodedResource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.FileCopyUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.Reader;

public class ResourceScriptSource implements ScriptSource {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private EncodedResource resource;
   private long lastModified = -1L;
   private final Object lastModifiedMonitor = new Object();

   public ResourceScriptSource(EncodedResource resource) {
      Assert.notNull(resource, (String)"Resource must not be null");
      this.resource = resource;
   }

   public ResourceScriptSource(Resource resource) {
      Assert.notNull(resource, (String)"Resource must not be null");
      this.resource = new EncodedResource(resource, "UTF-8");
   }

   public final Resource getResource() {
      return this.resource.getResource();
   }

   public void setEncoding(@Nullable String encoding) {
      this.resource = new EncodedResource(this.resource.getResource(), encoding);
   }

   public String getScriptAsString() throws IOException {
      synchronized(this.lastModifiedMonitor) {
         this.lastModified = this.retrieveLastModifiedTime();
      }

      Reader reader = this.resource.getReader();
      return FileCopyUtils.copyToString(reader);
   }

   public boolean isModified() {
      synchronized(this.lastModifiedMonitor) {
         return this.lastModified < 0L || this.retrieveLastModifiedTime() > this.lastModified;
      }
   }

   protected long retrieveLastModifiedTime() {
      try {
         return this.getResource().lastModified();
      } catch (IOException var2) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getResource() + " could not be resolved in the file system - current timestamp not available for script modification check", var2);
         }

         return 0L;
      }
   }

   @Nullable
   public String suggestedClassName() {
      String filename = this.getResource().getFilename();
      return filename != null ? StringUtils.stripFilenameExtension(filename) : null;
   }

   public String toString() {
      return this.resource.toString();
   }
}
