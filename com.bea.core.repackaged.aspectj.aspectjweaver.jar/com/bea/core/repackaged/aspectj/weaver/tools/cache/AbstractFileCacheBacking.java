package com.bea.core.repackaged.aspectj.weaver.tools.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractFileCacheBacking extends AbstractCacheBacking {
   public static final String WEAVED_CLASS_CACHE_DIR = "aj.weaving.cache.dir";
   private final File cacheDirectory;

   protected AbstractFileCacheBacking(File cacheDirectory) {
      if ((this.cacheDirectory = cacheDirectory) == null) {
         throw new IllegalStateException("No cache directory specified");
      }
   }

   public File getCacheDirectory() {
      return this.cacheDirectory;
   }

   protected void writeClassBytes(String key, byte[] bytes) throws Exception {
      File dir = this.getCacheDirectory();
      File file = new File(dir, key);
      FileOutputStream out = new FileOutputStream(file);

      try {
         out.write(bytes);
      } finally {
         this.close((OutputStream)out, file);
      }

   }

   protected void delete(File file) {
      if (file.exists() && !file.delete() && this.logger != null && this.logger.isTraceEnabled()) {
         this.logger.error("Error deleting file " + file.getAbsolutePath());
      }

   }

   protected void close(OutputStream out, File file) {
      if (out != null) {
         try {
            out.close();
         } catch (IOException var4) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.error("Failed (" + var4.getClass().getSimpleName() + ")" + " to close write file " + file.getAbsolutePath() + ": " + var4.getMessage(), var4);
            }
         }
      }

   }

   protected void close(InputStream in, File file) {
      if (in != null) {
         try {
            in.close();
         } catch (IOException var4) {
            if (this.logger != null && this.logger.isTraceEnabled()) {
               this.logger.error("Failed (" + var4.getClass().getSimpleName() + ")" + " to close read file " + file.getAbsolutePath() + ": " + var4.getMessage(), var4);
            }
         }
      }

   }
}
