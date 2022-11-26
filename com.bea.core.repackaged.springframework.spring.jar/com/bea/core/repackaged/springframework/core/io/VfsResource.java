package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.core.NestedIOException;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class VfsResource extends AbstractResource {
   private final Object resource;

   public VfsResource(Object resource) {
      Assert.notNull(resource, "VirtualFile must not be null");
      this.resource = resource;
   }

   public InputStream getInputStream() throws IOException {
      return VfsUtils.getInputStream(this.resource);
   }

   public boolean exists() {
      return VfsUtils.exists(this.resource);
   }

   public boolean isReadable() {
      return VfsUtils.isReadable(this.resource);
   }

   public URL getURL() throws IOException {
      try {
         return VfsUtils.getURL(this.resource);
      } catch (Exception var2) {
         throw new NestedIOException("Failed to obtain URL for file " + this.resource, var2);
      }
   }

   public URI getURI() throws IOException {
      try {
         return VfsUtils.getURI(this.resource);
      } catch (Exception var2) {
         throw new NestedIOException("Failed to obtain URI for " + this.resource, var2);
      }
   }

   public File getFile() throws IOException {
      return VfsUtils.getFile(this.resource);
   }

   public long contentLength() throws IOException {
      return VfsUtils.getSize(this.resource);
   }

   public long lastModified() throws IOException {
      return VfsUtils.getLastModified(this.resource);
   }

   public Resource createRelative(String relativePath) throws IOException {
      if (!relativePath.startsWith(".") && relativePath.contains("/")) {
         try {
            return new VfsResource(VfsUtils.getChild(this.resource, relativePath));
         } catch (IOException var3) {
         }
      }

      return new VfsResource(VfsUtils.getRelative(new URL(this.getURL(), relativePath)));
   }

   public String getFilename() {
      return VfsUtils.getName(this.resource);
   }

   public String getDescription() {
      return "VFS resource [" + this.resource + "]";
   }

   public boolean equals(Object other) {
      return this == other || other instanceof VfsResource && this.resource.equals(((VfsResource)other).resource);
   }

   public int hashCode() {
      return this.resource.hashCode();
   }
}
