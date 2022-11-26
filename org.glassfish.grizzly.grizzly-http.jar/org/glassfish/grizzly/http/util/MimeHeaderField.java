package org.glassfish.grizzly.http.util;

final class MimeHeaderField {
   protected final DataChunk nameB = DataChunk.newInstance();
   protected final DataChunk valueB = DataChunk.newInstance();
   private boolean isSerialized;

   public MimeHeaderField() {
   }

   public void recycle() {
      this.isSerialized = false;
      this.nameB.recycle();
      this.valueB.recycle();
   }

   public DataChunk getName() {
      return this.nameB;
   }

   public DataChunk getValue() {
      return this.valueB;
   }

   public boolean isSerialized() {
      return this.isSerialized;
   }

   public void setSerialized(boolean isSerialized) {
      this.isSerialized = isSerialized;
   }
}
