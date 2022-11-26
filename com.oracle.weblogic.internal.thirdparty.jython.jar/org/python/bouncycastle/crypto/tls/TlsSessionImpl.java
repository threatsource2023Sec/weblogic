package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.util.Arrays;

class TlsSessionImpl implements TlsSession {
   final byte[] sessionID;
   SessionParameters sessionParameters;

   TlsSessionImpl(byte[] var1, SessionParameters var2) {
      if (var1 == null) {
         throw new IllegalArgumentException("'sessionID' cannot be null");
      } else if (var1.length >= 1 && var1.length <= 32) {
         this.sessionID = Arrays.clone(var1);
         this.sessionParameters = var2;
      } else {
         throw new IllegalArgumentException("'sessionID' must have length between 1 and 32 bytes, inclusive");
      }
   }

   public synchronized SessionParameters exportSessionParameters() {
      return this.sessionParameters == null ? null : this.sessionParameters.copy();
   }

   public synchronized byte[] getSessionID() {
      return this.sessionID;
   }

   public synchronized void invalidate() {
      if (this.sessionParameters != null) {
         this.sessionParameters.clear();
         this.sessionParameters = null;
      }

   }

   public synchronized boolean isResumable() {
      return this.sessionParameters != null;
   }
}
