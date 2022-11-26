package org.apache.velocity.runtime.resource;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

public abstract class Resource {
   protected RuntimeServices rsvc = null;
   protected ResourceLoader resourceLoader;
   protected static final long MILLIS_PER_SECOND = 1000L;
   protected long modificationCheckInterval = 0L;
   protected long lastModified = 0L;
   protected long nextCheck = 0L;
   protected String name;
   protected String encoding = "ISO-8859-1";
   protected Object data = null;

   public void setRuntimeServices(RuntimeServices rs) {
      this.rsvc = rs;
   }

   public abstract boolean process() throws ResourceNotFoundException, ParseErrorException, Exception;

   public boolean isSourceModified() {
      return this.resourceLoader.isSourceModified(this);
   }

   public void setModificationCheckInterval(long modificationCheckInterval) {
      this.modificationCheckInterval = modificationCheckInterval;
   }

   public boolean requiresChecking() {
      if (this.modificationCheckInterval <= 0L) {
         return false;
      } else {
         return System.currentTimeMillis() >= this.nextCheck;
      }
   }

   public void touch() {
      this.nextCheck = System.currentTimeMillis() + 1000L * this.modificationCheckInterval;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public long getLastModified() {
      return this.lastModified;
   }

   public void setLastModified(long lastModified) {
      this.lastModified = lastModified;
   }

   public ResourceLoader getResourceLoader() {
      return this.resourceLoader;
   }

   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
   }

   public void setData(Object data) {
      this.data = data;
   }

   public Object getData() {
      return this.data;
   }
}
