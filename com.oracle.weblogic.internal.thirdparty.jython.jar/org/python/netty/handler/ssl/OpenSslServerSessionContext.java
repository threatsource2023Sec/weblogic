package org.python.netty.handler.ssl;

import org.python.netty.internal.tcnative.SSL;
import org.python.netty.internal.tcnative.SSLContext;

public final class OpenSslServerSessionContext extends OpenSslSessionContext {
   OpenSslServerSessionContext(ReferenceCountedOpenSslContext context) {
      super(context);
   }

   public void setSessionTimeout(int seconds) {
      if (seconds < 0) {
         throw new IllegalArgumentException();
      } else {
         SSLContext.setSessionCacheTimeout(this.context.ctx, (long)seconds);
      }
   }

   public int getSessionTimeout() {
      return (int)SSLContext.getSessionCacheTimeout(this.context.ctx);
   }

   public void setSessionCacheSize(int size) {
      if (size < 0) {
         throw new IllegalArgumentException();
      } else {
         SSLContext.setSessionCacheSize(this.context.ctx, (long)size);
      }
   }

   public int getSessionCacheSize() {
      return (int)SSLContext.getSessionCacheSize(this.context.ctx);
   }

   public void setSessionCacheEnabled(boolean enabled) {
      long mode = enabled ? SSL.SSL_SESS_CACHE_SERVER : SSL.SSL_SESS_CACHE_OFF;
      SSLContext.setSessionCacheMode(this.context.ctx, mode);
   }

   public boolean isSessionCacheEnabled() {
      return SSLContext.getSessionCacheMode(this.context.ctx) == SSL.SSL_SESS_CACHE_SERVER;
   }

   public boolean setSessionIdContext(byte[] sidCtx) {
      return SSLContext.setSessionIdContext(this.context.ctx, sidCtx);
   }
}
