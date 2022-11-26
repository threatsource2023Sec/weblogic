package org.python.netty.handler.ssl;

import java.security.PrivateKey;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.Unpooled;
import org.python.netty.util.AbstractReferenceCounted;
import org.python.netty.util.CharsetUtil;
import org.python.netty.util.IllegalReferenceCountException;
import org.python.netty.util.internal.ObjectUtil;

public final class PemPrivateKey extends AbstractReferenceCounted implements PrivateKey, PemEncoded {
   private static final byte[] BEGIN_PRIVATE_KEY;
   private static final byte[] END_PRIVATE_KEY;
   private static final String PKCS8_FORMAT = "PKCS#8";
   private final ByteBuf content;

   static PemEncoded toPEM(ByteBufAllocator allocator, boolean useDirect, PrivateKey key) {
      if (key instanceof PemEncoded) {
         return ((PemEncoded)key).retain();
      } else {
         ByteBuf encoded = Unpooled.wrappedBuffer(key.getEncoded());

         PemValue var9;
         try {
            ByteBuf base64 = SslUtils.toBase64(allocator, encoded);

            try {
               int size = BEGIN_PRIVATE_KEY.length + base64.readableBytes() + END_PRIVATE_KEY.length;
               boolean success = false;
               ByteBuf pem = useDirect ? allocator.directBuffer(size) : allocator.buffer(size);

               try {
                  pem.writeBytes(BEGIN_PRIVATE_KEY);
                  pem.writeBytes(base64);
                  pem.writeBytes(END_PRIVATE_KEY);
                  PemValue value = new PemValue(pem, true);
                  success = true;
                  var9 = value;
               } finally {
                  if (!success) {
                     SslUtils.zerooutAndRelease(pem);
                  }

               }
            } finally {
               SslUtils.zerooutAndRelease(base64);
            }
         } finally {
            SslUtils.zerooutAndRelease(encoded);
         }

         return var9;
      }
   }

   public static PemPrivateKey valueOf(byte[] key) {
      return valueOf(Unpooled.wrappedBuffer(key));
   }

   public static PemPrivateKey valueOf(ByteBuf key) {
      return new PemPrivateKey(key);
   }

   private PemPrivateKey(ByteBuf content) {
      this.content = (ByteBuf)ObjectUtil.checkNotNull(content, "content");
   }

   public boolean isSensitive() {
      return true;
   }

   public ByteBuf content() {
      int count = this.refCnt();
      if (count <= 0) {
         throw new IllegalReferenceCountException(count);
      } else {
         return this.content;
      }
   }

   public PemPrivateKey copy() {
      return this.replace(this.content.copy());
   }

   public PemPrivateKey duplicate() {
      return this.replace(this.content.duplicate());
   }

   public PemPrivateKey retainedDuplicate() {
      return this.replace(this.content.retainedDuplicate());
   }

   public PemPrivateKey replace(ByteBuf content) {
      return new PemPrivateKey(content);
   }

   public PemPrivateKey touch() {
      this.content.touch();
      return this;
   }

   public PemPrivateKey touch(Object hint) {
      this.content.touch(hint);
      return this;
   }

   public PemPrivateKey retain() {
      return (PemPrivateKey)super.retain();
   }

   public PemPrivateKey retain(int increment) {
      return (PemPrivateKey)super.retain(increment);
   }

   protected void deallocate() {
      SslUtils.zerooutAndRelease(this.content);
   }

   public byte[] getEncoded() {
      throw new UnsupportedOperationException();
   }

   public String getAlgorithm() {
      throw new UnsupportedOperationException();
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public void destroy() {
      this.release(this.refCnt());
   }

   public boolean isDestroyed() {
      return this.refCnt() == 0;
   }

   static {
      BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
      END_PRIVATE_KEY = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
   }
}
