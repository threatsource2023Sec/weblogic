package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.core.NestedIOException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public abstract class AbstractResource implements Resource {
   public boolean exists() {
      try {
         return this.getFile().exists();
      } catch (IOException var4) {
         try {
            this.getInputStream().close();
            return true;
         } catch (Throwable var3) {
            return false;
         }
      }
   }

   public boolean isReadable() {
      return this.exists();
   }

   public boolean isOpen() {
      return false;
   }

   public boolean isFile() {
      return false;
   }

   public URL getURL() throws IOException {
      throw new FileNotFoundException(this.getDescription() + " cannot be resolved to URL");
   }

   public URI getURI() throws IOException {
      URL url = this.getURL();

      try {
         return ResourceUtils.toURI(url);
      } catch (URISyntaxException var3) {
         throw new NestedIOException("Invalid URI [" + url + "]", var3);
      }
   }

   public File getFile() throws IOException {
      throw new FileNotFoundException(this.getDescription() + " cannot be resolved to absolute file path");
   }

   public ReadableByteChannel readableChannel() throws IOException {
      return Channels.newChannel(this.getInputStream());
   }

   public long contentLength() throws IOException {
      InputStream is = this.getInputStream();

      try {
         long size = 0L;

         int read;
         for(byte[] buf = new byte[256]; (read = is.read(buf)) != -1; size += (long)read) {
         }

         long var6 = size;
         return var6;
      } finally {
         try {
            is.close();
         } catch (IOException var14) {
         }

      }
   }

   public long lastModified() throws IOException {
      File fileToCheck = this.getFileForLastModifiedCheck();
      long lastModified = fileToCheck.lastModified();
      if (lastModified == 0L && !fileToCheck.exists()) {
         throw new FileNotFoundException(this.getDescription() + " cannot be resolved in the file system for checking its last-modified timestamp");
      } else {
         return lastModified;
      }
   }

   protected File getFileForLastModifiedCheck() throws IOException {
      return this.getFile();
   }

   public Resource createRelative(String relativePath) throws IOException {
      throw new FileNotFoundException("Cannot create a relative resource for " + this.getDescription());
   }

   @Nullable
   public String getFilename() {
      return null;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof Resource && ((Resource)other).getDescription().equals(this.getDescription());
   }

   public int hashCode() {
      return this.getDescription().hashCode();
   }

   public String toString() {
      return this.getDescription();
   }
}
