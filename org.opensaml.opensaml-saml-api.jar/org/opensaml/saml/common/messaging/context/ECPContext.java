package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;

public class ECPContext extends BaseContext {
   private boolean requestAuthenticated;
   @Nullable
   private byte[] sessionKey;

   public boolean isRequestAuthenticated() {
      return this.requestAuthenticated;
   }

   @Nonnull
   public ECPContext setRequestAuthenticated(boolean flag) {
      this.requestAuthenticated = flag;
      return this;
   }

   @Nullable
   public byte[] getSessionKey() {
      return this.sessionKey;
   }

   @Nonnull
   public ECPContext setSessionKey(byte[] key) {
      this.sessionKey = key;
      return this;
   }
}
