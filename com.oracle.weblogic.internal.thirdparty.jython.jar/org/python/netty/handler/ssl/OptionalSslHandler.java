package org.python.netty.handler.ssl;

import java.util.List;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.ByteToMessageDecoder;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.internal.ObjectUtil;

public class OptionalSslHandler extends ByteToMessageDecoder {
   private final SslContext sslContext;

   public OptionalSslHandler(SslContext sslContext) {
      this.sslContext = (SslContext)ObjectUtil.checkNotNull(sslContext, "sslContext");
   }

   protected void decode(ChannelHandlerContext context, ByteBuf in, List out) throws Exception {
      if (in.readableBytes() >= 5) {
         if (SslHandler.isEncrypted(in)) {
            this.handleSsl(context);
         } else {
            this.handleNonSsl(context);
         }

      }
   }

   private void handleSsl(ChannelHandlerContext context) {
      SslHandler sslHandler = null;

      try {
         sslHandler = this.newSslHandler(context, this.sslContext);
         context.pipeline().replace((ChannelHandler)this, this.newSslHandlerName(), sslHandler);
         sslHandler = null;
      } finally {
         if (sslHandler != null) {
            ReferenceCountUtil.safeRelease(sslHandler.engine());
         }

      }

   }

   private void handleNonSsl(ChannelHandlerContext context) {
      ChannelHandler handler = this.newNonSslHandler(context);
      if (handler != null) {
         context.pipeline().replace((ChannelHandler)this, this.newNonSslHandlerName(), handler);
      } else {
         context.pipeline().remove((ChannelHandler)this);
      }

   }

   protected String newSslHandlerName() {
      return null;
   }

   protected SslHandler newSslHandler(ChannelHandlerContext context, SslContext sslContext) {
      return sslContext.newHandler(context.alloc());
   }

   protected String newNonSslHandlerName() {
      return null;
   }

   protected ChannelHandler newNonSslHandler(ChannelHandlerContext context) {
      return null;
   }
}
