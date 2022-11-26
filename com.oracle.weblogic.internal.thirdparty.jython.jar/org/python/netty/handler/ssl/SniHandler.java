package org.python.netty.handler.ssl;

import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.DecoderException;
import org.python.netty.util.AsyncMapping;
import org.python.netty.util.DomainNameMapping;
import org.python.netty.util.Mapping;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

public class SniHandler extends AbstractSniHandler {
   private static final Selection EMPTY_SELECTION = new Selection((SslContext)null, (String)null);
   protected final AsyncMapping mapping;
   private volatile Selection selection;

   public SniHandler(Mapping mapping) {
      this((AsyncMapping)(new AsyncMappingAdapter(mapping)));
   }

   public SniHandler(DomainNameMapping mapping) {
      this((Mapping)mapping);
   }

   public SniHandler(AsyncMapping mapping) {
      this.selection = EMPTY_SELECTION;
      this.mapping = (AsyncMapping)ObjectUtil.checkNotNull(mapping, "mapping");
   }

   public String hostname() {
      return this.selection.hostname;
   }

   public SslContext sslContext() {
      return this.selection.context;
   }

   protected Future lookup(ChannelHandlerContext ctx, String hostname) throws Exception {
      return this.mapping.map(hostname, ctx.executor().newPromise());
   }

   protected final void onLookupComplete(ChannelHandlerContext ctx, String hostname, Future future) throws Exception {
      if (!future.isSuccess()) {
         throw new DecoderException("failed to get the SslContext for " + hostname, future.cause());
      } else {
         SslContext sslContext = (SslContext)future.getNow();
         this.selection = new Selection(sslContext, hostname);

         try {
            this.replaceHandler(ctx, hostname, sslContext);
         } catch (Throwable var6) {
            this.selection = EMPTY_SELECTION;
            PlatformDependent.throwException(var6);
         }

      }
   }

   protected void replaceHandler(ChannelHandlerContext ctx, String hostname, SslContext sslContext) throws Exception {
      SslHandler sslHandler = null;

      try {
         sslHandler = sslContext.newHandler(ctx.alloc());
         ctx.pipeline().replace((ChannelHandler)this, SslHandler.class.getName(), sslHandler);
         sslHandler = null;
      } finally {
         if (sslHandler != null) {
            ReferenceCountUtil.safeRelease(sslHandler.engine());
         }

      }

   }

   private static final class Selection {
      final SslContext context;
      final String hostname;

      Selection(SslContext context, String hostname) {
         this.context = context;
         this.hostname = hostname;
      }
   }

   private static final class AsyncMappingAdapter implements AsyncMapping {
      private final Mapping mapping;

      private AsyncMappingAdapter(Mapping mapping) {
         this.mapping = (Mapping)ObjectUtil.checkNotNull(mapping, "mapping");
      }

      public Future map(String input, Promise promise) {
         SslContext context;
         try {
            context = (SslContext)this.mapping.map(input);
         } catch (Throwable var5) {
            return promise.setFailure(var5);
         }

         return promise.setSuccess(context);
      }

      // $FF: synthetic method
      AsyncMappingAdapter(Mapping x0, Object x1) {
         this(x0);
      }
   }
}
