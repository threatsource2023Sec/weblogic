package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.python.bouncycastle.util.Arrays;

public class ServerSRPParams {
   protected BigInteger N;
   protected BigInteger g;
   protected BigInteger B;
   protected byte[] s;

   public ServerSRPParams(BigInteger var1, BigInteger var2, byte[] var3, BigInteger var4) {
      this.N = var1;
      this.g = var2;
      this.s = Arrays.clone(var3);
      this.B = var4;
   }

   public BigInteger getB() {
      return this.B;
   }

   public BigInteger getG() {
      return this.g;
   }

   public BigInteger getN() {
      return this.N;
   }

   public byte[] getS() {
      return this.s;
   }

   public void encode(OutputStream var1) throws IOException {
      TlsSRPUtils.writeSRPParameter(this.N, var1);
      TlsSRPUtils.writeSRPParameter(this.g, var1);
      TlsUtils.writeOpaque8(this.s, var1);
      TlsSRPUtils.writeSRPParameter(this.B, var1);
   }

   public static ServerSRPParams parse(InputStream var0) throws IOException {
      BigInteger var1 = TlsSRPUtils.readSRPParameter(var0);
      BigInteger var2 = TlsSRPUtils.readSRPParameter(var0);
      byte[] var3 = TlsUtils.readOpaque8(var0);
      BigInteger var4 = TlsSRPUtils.readSRPParameter(var0);
      return new ServerSRPParams(var1, var2, var3, var4);
   }
}
