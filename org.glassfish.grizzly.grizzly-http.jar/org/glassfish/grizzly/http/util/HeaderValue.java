package org.glassfish.grizzly.http.util;

public final class HeaderValue {
   public static final HeaderValue IDENTITY = newHeaderValue("identity").prepare();
   private final String value;
   private byte[] preparedByteArray;

   public static HeaderValue newHeaderValue(String value) {
      return new HeaderValue(value);
   }

   private HeaderValue(String value) {
      this.value = value;
   }

   public HeaderValue prepare() {
      if (this.preparedByteArray == null) {
         this.getByteArray();
      }

      return this;
   }

   public boolean isSet() {
      return this.value != null;
   }

   public String get() {
      return this.value;
   }

   public byte[] getByteArray() {
      if (this.preparedByteArray != null) {
         return this.preparedByteArray;
      } else if (this.value == null) {
         return HttpCodecUtils.EMPTY_ARRAY;
      } else {
         this.preparedByteArray = HttpCodecUtils.toCheckedByteArray(this.value);
         return this.preparedByteArray;
      }
   }

   public String toString() {
      return this.value;
   }

   public void serializeToDataChunk(DataChunk dc) {
      if (this.preparedByteArray != null) {
         dc.setBytes(this.preparedByteArray);
      } else {
         dc.setString(this.value);
      }

   }
}
