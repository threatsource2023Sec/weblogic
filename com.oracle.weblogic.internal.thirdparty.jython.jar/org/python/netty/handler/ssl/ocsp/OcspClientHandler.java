package org.python.netty.handler.ssl.ocsp;

import javax.net.ssl.SSLHandshakeException;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelInboundHandlerAdapter;
import org.python.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import org.python.netty.handler.ssl.SslHandshakeCompletionEvent;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.ThrowableUtil;

public abstract class OcspClientHandler extends ChannelInboundHandlerAdapter {
   private static final SSLHandshakeException OCSP_VERIFICATION_EXCEPTION = (SSLHandshakeException)ThrowableUtil.unknownStackTrace(new SSLHandshakeException("Bad OCSP response"), OcspClientHandler.class, "verify(...)");
   private final ReferenceCountedOpenSslEngine engine;

   protected OcspClientHandler(ReferenceCountedOpenSslEngine engine) {
      this.engine = (ReferenceCountedOpenSslEngine)ObjectUtil.checkNotNull(engine, "engine");
   }

   protected abstract boolean verify(ChannelHandlerContext var1, ReferenceCountedOpenSslEngine var2) throws Exception;

   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      if (evt instanceof SslHandshakeCompletionEvent) {
         ctx.pipeline().remove((ChannelHandler)this);
         SslHandshakeCompletionEvent event = (SslHandshakeCompletionEvent)evt;
         if (event.isSuccess() && !this.verify(ctx, this.engine)) {
            throw OCSP_VERIFICATION_EXCEPTION;
         }
      }

      ctx.fireUserEventTriggered(evt);
   }
}
