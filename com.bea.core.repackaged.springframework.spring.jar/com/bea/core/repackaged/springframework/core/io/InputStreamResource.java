package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamResource extends AbstractResource {
   private final InputStream inputStream;
   private final String description;
   private boolean read;

   public InputStreamResource(InputStream inputStream) {
      this(inputStream, "resource loaded through InputStream");
   }

   public InputStreamResource(InputStream inputStream, @Nullable String description) {
      this.read = false;
      Assert.notNull(inputStream, (String)"InputStream must not be null");
      this.inputStream = inputStream;
      this.description = description != null ? description : "";
   }

   public boolean exists() {
      return true;
   }

   public boolean isOpen() {
      return true;
   }

   public InputStream getInputStream() throws IOException, IllegalStateException {
      if (this.read) {
         throw new IllegalStateException("InputStream has already been read - do not use InputStreamResource if a stream needs to be read multiple times");
      } else {
         this.read = true;
         return this.inputStream;
      }
   }

   public String getDescription() {
      return "InputStream resource [" + this.description + "]";
   }

   public boolean equals(Object other) {
      return this == other || other instanceof InputStreamResource && ((InputStreamResource)other).inputStream.equals(this.inputStream);
   }

   public int hashCode() {
      return this.inputStream.hashCode();
   }
}
