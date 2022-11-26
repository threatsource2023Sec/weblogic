package org.python.netty.handler.codec;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelOutboundHandlerAdapter;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.TypeParameterMatcher;

public abstract class MessageToByteEncoder extends ChannelOutboundHandlerAdapter {
   private final TypeParameterMatcher matcher;
   private final boolean preferDirect;

   protected MessageToByteEncoder() {
      this(true);
   }

   protected MessageToByteEncoder(Class outboundMessageType) {
      this(outboundMessageType, true);
   }

   protected MessageToByteEncoder(boolean preferDirect) {
      this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
      this.preferDirect = preferDirect;
   }

   protected MessageToByteEncoder(Class outboundMessageType, boolean preferDirect) {
      this.matcher = TypeParameterMatcher.get(outboundMessageType);
      this.preferDirect = preferDirect;
   }

   public boolean acceptOutboundMessage(Object msg) throws Exception {
      return this.matcher.match(msg);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      ByteBuf buf = null;

      try {
         if (this.acceptOutboundMessage(msg)) {
            Object cast = msg;
            buf = this.allocateBuffer(ctx, msg, this.preferDirect);

            try {
               this.encode(ctx, cast, buf);
            } finally {
               ReferenceCountUtil.release(msg);
            }

            if (buf.isReadable()) {
               ctx.write(buf, promise);
            } else {
               buf.release();
               ctx.write(Unpooled.EMPTY_BUFFER, promise);
            }

            buf = null;
         } else {
            ctx.write(msg, promise);
         }
      } catch (EncoderException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new EncoderException(var18);
      } finally {
         if (buf != null) {
            buf.release();
         }

      }

   }

   protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, Object msg, boolean preferDirect) throws Exception {
      return preferDirect ? ctx.alloc().ioBuffer() : ctx.alloc().heapBuffer();
   }

   protected abstract void encode(ChannelHandlerContext var1, Object var2, ByteBuf var3) throws Exception;

   protected boolean isPreferDirect() {
      return this.preferDirect;
   }
}
