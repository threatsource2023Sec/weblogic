package org.python.bouncycastle.crypto.tls;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.python.bouncycastle.crypto.prng.RandomGenerator;
import org.python.bouncycastle.util.Times;

abstract class AbstractTlsContext implements TlsContext {
   private static long counter = Times.nanoTime();
   private RandomGenerator nonceRandom;
   private SecureRandom secureRandom;
   private SecurityParameters securityParameters;
   private ProtocolVersion clientVersion = null;
   private ProtocolVersion serverVersion = null;
   private TlsSession session = null;
   private Object userObject = null;

   private static synchronized long nextCounterValue() {
      return ++counter;
   }

   AbstractTlsContext(SecureRandom var1, SecurityParameters var2) {
      Digest var3 = TlsUtils.createHash((short)4);
      byte[] var4 = new byte[var3.getDigestSize()];
      var1.nextBytes(var4);
      this.nonceRandom = new DigestRandomGenerator(var3);
      this.nonceRandom.addSeedMaterial(nextCounterValue());
      this.nonceRandom.addSeedMaterial(Times.nanoTime());
      this.nonceRandom.addSeedMaterial(var4);
      this.secureRandom = var1;
      this.securityParameters = var2;
   }

   public RandomGenerator getNonceRandomGenerator() {
      return this.nonceRandom;
   }

   public SecureRandom getSecureRandom() {
      return this.secureRandom;
   }

   public SecurityParameters getSecurityParameters() {
      return this.securityParameters;
   }

   public ProtocolVersion getClientVersion() {
      return this.clientVersion;
   }

   void setClientVersion(ProtocolVersion var1) {
      this.clientVersion = var1;
   }

   public ProtocolVersion getServerVersion() {
      return this.serverVersion;
   }

   void setServerVersion(ProtocolVersion var1) {
      this.serverVersion = var1;
   }

   public TlsSession getResumableSession() {
      return this.session;
   }

   void setResumableSession(TlsSession var1) {
      this.session = var1;
   }

   public Object getUserObject() {
      return this.userObject;
   }

   public void setUserObject(Object var1) {
      this.userObject = var1;
   }

   public byte[] exportKeyingMaterial(String var1, byte[] var2, int var3) {
      if (var2 != null && !TlsUtils.isValidUint16(var2.length)) {
         throw new IllegalArgumentException("'context_value' must have length less than 2^16 (or be null)");
      } else {
         SecurityParameters var4 = this.getSecurityParameters();
         byte[] var5 = var4.getClientRandom();
         byte[] var6 = var4.getServerRandom();
         int var7 = var5.length + var6.length;
         if (var2 != null) {
            var7 += 2 + var2.length;
         }

         byte[] var8 = new byte[var7];
         int var9 = 0;
         System.arraycopy(var5, 0, var8, var9, var5.length);
         var9 += var5.length;
         System.arraycopy(var6, 0, var8, var9, var6.length);
         var9 += var6.length;
         if (var2 != null) {
            TlsUtils.writeUint16(var2.length, var8, var9);
            var9 += 2;
            System.arraycopy(var2, 0, var8, var9, var2.length);
            var9 += var2.length;
         }

         if (var9 != var7) {
            throw new IllegalStateException("error in calculation of seed for export");
         } else {
            return TlsUtils.PRF(this, var4.getMasterSecret(), var1, var8, var3);
         }
      }
   }
}
