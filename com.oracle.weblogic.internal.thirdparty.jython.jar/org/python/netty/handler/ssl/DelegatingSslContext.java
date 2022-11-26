package org.python.netty.handler.ssl;

import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.internal.ObjectUtil;

public abstract class DelegatingSslContext extends SslContext {
   private final SslContext ctx;

   protected DelegatingSslContext(SslContext ctx) {
      this.ctx = (SslContext)ObjectUtil.checkNotNull(ctx, "ctx");
   }

   public final boolean isClient() {
      return this.ctx.isClient();
   }

   public final List cipherSuites() {
      return this.ctx.cipherSuites();
   }

   public final long sessionCacheSize() {
      return this.ctx.sessionCacheSize();
   }

   public final long sessionTimeout() {
      return this.ctx.sessionTimeout();
   }

   public final ApplicationProtocolNegotiator applicationProtocolNegotiator() {
      return this.ctx.applicationProtocolNegotiator();
   }

   public final SSLEngine newEngine(ByteBufAllocator alloc) {
      SSLEngine engine = this.ctx.newEngine(alloc);
      this.initEngine(engine);
      return engine;
   }

   public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
      SSLEngine engine = this.ctx.newEngine(alloc, peerHost, peerPort);
      this.initEngine(engine);
      return engine;
   }

   public final SSLSessionContext sessionContext() {
      return this.ctx.sessionContext();
   }

   protected abstract void initEngine(SSLEngine var1);
}
