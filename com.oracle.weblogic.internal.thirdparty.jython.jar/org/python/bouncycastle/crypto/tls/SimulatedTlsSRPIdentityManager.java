package org.python.bouncycastle.crypto.tls;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.agreement.srp.SRP6VerifierGenerator;
import org.python.bouncycastle.crypto.macs.HMac;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.SRP6GroupParameters;
import org.python.bouncycastle.util.Strings;

public class SimulatedTlsSRPIdentityManager implements TlsSRPIdentityManager {
   private static final byte[] PREFIX_PASSWORD = Strings.toByteArray("password");
   private static final byte[] PREFIX_SALT = Strings.toByteArray("salt");
   protected SRP6GroupParameters group;
   protected SRP6VerifierGenerator verifierGenerator;
   protected Mac mac;

   public static SimulatedTlsSRPIdentityManager getRFC5054Default(SRP6GroupParameters var0, byte[] var1) {
      SRP6VerifierGenerator var2 = new SRP6VerifierGenerator();
      var2.init(var0, TlsUtils.createHash((short)2));
      HMac var3 = new HMac(TlsUtils.createHash((short)2));
      var3.init(new KeyParameter(var1));
      return new SimulatedTlsSRPIdentityManager(var0, var2, var3);
   }

   public SimulatedTlsSRPIdentityManager(SRP6GroupParameters var1, SRP6VerifierGenerator var2, Mac var3) {
      this.group = var1;
      this.verifierGenerator = var2;
      this.mac = var3;
   }

   public TlsSRPLoginParameters getLoginParameters(byte[] var1) {
      this.mac.update(PREFIX_SALT, 0, PREFIX_SALT.length);
      this.mac.update(var1, 0, var1.length);
      byte[] var2 = new byte[this.mac.getMacSize()];
      this.mac.doFinal(var2, 0);
      this.mac.update(PREFIX_PASSWORD, 0, PREFIX_PASSWORD.length);
      this.mac.update(var1, 0, var1.length);
      byte[] var3 = new byte[this.mac.getMacSize()];
      this.mac.doFinal(var3, 0);
      BigInteger var4 = this.verifierGenerator.generateVerifier(var2, var1, var3);
      return new TlsSRPLoginParameters(this.group, var4, var2);
   }
}
