package org.python.netty.handler.ssl;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.util.AbstractReferenceCounted;
import org.python.netty.util.IllegalReferenceCountException;
import org.python.netty.util.internal.ObjectUtil;

class PemValue extends AbstractReferenceCounted implements PemEncoded {
   private final ByteBuf content;
   private final boolean sensitive;

   public PemValue(ByteBuf content, boolean sensitive) {
      this.content = (ByteBuf)ObjectUtil.checkNotNull(content, "content");
      this.sensitive = sensitive;
   }

   public boolean isSensitive() {
      return this.sensitive;
   }

   public ByteBuf content() {
      int count = this.refCnt();
      if (count <= 0) {
         throw new IllegalReferenceCountException(count);
      } else {
         return this.content;
      }
   }

   public PemValue copy() {
      return this.replace(this.content.copy());
   }

   public PemValue duplicate() {
      return this.replace(this.content.duplicate());
   }

   public PemValue retainedDuplicate() {
      return this.replace(this.content.retainedDuplicate());
   }

   public PemValue replace(ByteBuf content) {
      return new PemValue(content, this.sensitive);
   }

   public PemValue touch() {
      return (PemValue)super.touch();
   }

   public PemValue touch(Object hint) {
      this.content.touch(hint);
      return this;
   }

   public PemValue retain() {
      return (PemValue)super.retain();
   }

   public PemValue retain(int increment) {
      return (PemValue)super.retain(increment);
   }

   protected void deallocate() {
      if (this.sensitive) {
         SslUtils.zeroout(this.content);
      }

      this.content.release();
   }
}
