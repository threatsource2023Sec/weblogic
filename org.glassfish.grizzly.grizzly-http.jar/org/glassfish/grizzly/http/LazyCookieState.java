package org.glassfish.grizzly.http;

import org.glassfish.grizzly.http.util.DataChunk;

public class LazyCookieState {
   private final DataChunk name = DataChunk.newInstance();
   private final DataChunk value = DataChunk.newInstance();
   private final DataChunk path = DataChunk.newInstance();
   private final DataChunk domain = DataChunk.newInstance();
   private boolean secure;
   private final DataChunk comment = DataChunk.newInstance();

   public void recycle() {
      this.path.recycle();
      this.name.recycle();
      this.value.recycle();
      this.comment.recycle();
      this.path.recycle();
      this.domain.recycle();
      this.secure = false;
   }

   public DataChunk getComment() {
      return this.comment;
   }

   public DataChunk getDomain() {
      return this.domain;
   }

   public DataChunk getPath() {
      return this.path;
   }

   public void setSecure(boolean flag) {
      this.secure = flag;
   }

   public boolean getSecure() {
      return this.secure;
   }

   public DataChunk getName() {
      return this.name;
   }

   public DataChunk getValue() {
      return this.value;
   }

   public String toString() {
      return "LazyCookieState " + this.getName() + '=' + this.getValue() + " ; " + ' ' + this.getPath() + ' ' + this.getDomain();
   }
}
