package org.python.netty.buffer;

import org.python.netty.util.IllegalReferenceCountException;
import org.python.netty.util.internal.StringUtil;

public class DefaultByteBufHolder implements ByteBufHolder {
   private final ByteBuf data;

   public DefaultByteBufHolder(ByteBuf data) {
      if (data == null) {
         throw new NullPointerException("data");
      } else {
         this.data = data;
      }
   }

   public ByteBuf content() {
      if (this.data.refCnt() <= 0) {
         throw new IllegalReferenceCountException(this.data.refCnt());
      } else {
         return this.data;
      }
   }

   public ByteBufHolder copy() {
      return this.replace(this.data.copy());
   }

   public ByteBufHolder duplicate() {
      return this.replace(this.data.duplicate());
   }

   public ByteBufHolder retainedDuplicate() {
      return this.replace(this.data.retainedDuplicate());
   }

   public ByteBufHolder replace(ByteBuf content) {
      return new DefaultByteBufHolder(content);
   }

   public int refCnt() {
      return this.data.refCnt();
   }

   public ByteBufHolder retain() {
      this.data.retain();
      return this;
   }

   public ByteBufHolder retain(int increment) {
      this.data.retain(increment);
      return this;
   }

   public ByteBufHolder touch() {
      this.data.touch();
      return this;
   }

   public ByteBufHolder touch(Object hint) {
      this.data.touch(hint);
      return this;
   }

   public boolean release() {
      return this.data.release();
   }

   public boolean release(int decrement) {
      return this.data.release(decrement);
   }

   protected final String contentToString() {
      return this.data.toString();
   }

   public String toString() {
      return StringUtil.simpleClassName((Object)this) + '(' + this.contentToString() + ')';
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return o instanceof ByteBufHolder ? this.data.equals(((ByteBufHolder)o).content()) : false;
      }
   }

   public int hashCode() {
      return this.data.hashCode();
   }
}
