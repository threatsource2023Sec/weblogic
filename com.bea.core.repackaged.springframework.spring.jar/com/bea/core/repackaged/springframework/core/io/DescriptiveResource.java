package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DescriptiveResource extends AbstractResource {
   private final String description;

   public DescriptiveResource(@Nullable String description) {
      this.description = description != null ? description : "";
   }

   public boolean exists() {
      return false;
   }

   public boolean isReadable() {
      return false;
   }

   public InputStream getInputStream() throws IOException {
      throw new FileNotFoundException(this.getDescription() + " cannot be opened because it does not point to a readable resource");
   }

   public String getDescription() {
      return this.description;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof DescriptiveResource && ((DescriptiveResource)other).description.equals(this.description);
   }

   public int hashCode() {
      return this.description.hashCode();
   }
}
