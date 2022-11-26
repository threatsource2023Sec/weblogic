package org.python.bouncycastle.crypto.tls;

import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Strings;

public class BasicTlsPSKIdentity implements TlsPSKIdentity {
   protected byte[] identity;
   protected byte[] psk;

   public BasicTlsPSKIdentity(byte[] var1, byte[] var2) {
      this.identity = Arrays.clone(var1);
      this.psk = Arrays.clone(var2);
   }

   public BasicTlsPSKIdentity(String var1, byte[] var2) {
      this.identity = Strings.toUTF8ByteArray(var1);
      this.psk = Arrays.clone(var2);
   }

   public void skipIdentityHint() {
   }

   public void notifyIdentityHint(byte[] var1) {
   }

   public byte[] getPSKIdentity() {
      return this.identity;
   }

   public byte[] getPSK() {
      return this.psk;
   }
}
