package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ByteArrayResource extends AbstractResource {
   private final byte[] byteArray;
   private final String description;

   public ByteArrayResource(byte[] byteArray) {
      this(byteArray, "resource loaded from byte array");
   }

   public ByteArrayResource(byte[] byteArray, @Nullable String description) {
      Assert.notNull(byteArray, (String)"Byte array must not be null");
      this.byteArray = byteArray;
      this.description = description != null ? description : "";
   }

   public final byte[] getByteArray() {
      return this.byteArray;
   }

   public boolean exists() {
      return true;
   }

   public long contentLength() {
      return (long)this.byteArray.length;
   }

   public InputStream getInputStream() throws IOException {
      return new ByteArrayInputStream(this.byteArray);
   }

   public String getDescription() {
      return "Byte array resource [" + this.description + "]";
   }

   public boolean equals(Object other) {
      return this == other || other instanceof ByteArrayResource && Arrays.equals(((ByteArrayResource)other).byteArray, this.byteArray);
   }

   public int hashCode() {
      return byte[].class.hashCode() * 29 * this.byteArray.length;
   }
}
