package org.python.netty.handler.ssl;

import org.python.netty.buffer.ByteBufAllocator;

public final class OpenSslEngine extends ReferenceCountedOpenSslEngine {
   OpenSslEngine(OpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort) {
      super(context, alloc, peerHost, peerPort, false);
   }

   protected void finalize() throws Throwable {
      super.finalize();
      OpenSsl.releaseIfNeeded(this);
   }
}
