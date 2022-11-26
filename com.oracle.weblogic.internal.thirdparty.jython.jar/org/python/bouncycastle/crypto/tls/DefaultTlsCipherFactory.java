package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import org.python.bouncycastle.crypto.BlockCipher;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.StreamCipher;
import org.python.bouncycastle.crypto.engines.AESEngine;
import org.python.bouncycastle.crypto.engines.CamelliaEngine;
import org.python.bouncycastle.crypto.engines.DESedeEngine;
import org.python.bouncycastle.crypto.engines.RC4Engine;
import org.python.bouncycastle.crypto.engines.SEEDEngine;
import org.python.bouncycastle.crypto.modes.AEADBlockCipher;
import org.python.bouncycastle.crypto.modes.CBCBlockCipher;
import org.python.bouncycastle.crypto.modes.CCMBlockCipher;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.crypto.modes.OCBBlockCipher;

public class DefaultTlsCipherFactory extends AbstractTlsCipherFactory {
   public TlsCipher createCipher(TlsContext var1, int var2, int var3) throws IOException {
      switch (var2) {
         case 0:
            return this.createNullCipher(var1, var3);
         case 2:
            return this.createRC4Cipher(var1, 16, var3);
         case 7:
            return this.createDESedeCipher(var1, var3);
         case 8:
            return this.createAESCipher(var1, 16, var3);
         case 9:
            return this.createAESCipher(var1, 32, var3);
         case 10:
            return this.createCipher_AES_GCM(var1, 16, 16);
         case 11:
            return this.createCipher_AES_GCM(var1, 32, 16);
         case 12:
            return this.createCamelliaCipher(var1, 16, var3);
         case 13:
            return this.createCamelliaCipher(var1, 32, var3);
         case 14:
            return this.createSEEDCipher(var1, var3);
         case 15:
            return this.createCipher_AES_CCM(var1, 16, 16);
         case 16:
            return this.createCipher_AES_CCM(var1, 16, 8);
         case 17:
            return this.createCipher_AES_CCM(var1, 32, 16);
         case 18:
            return this.createCipher_AES_CCM(var1, 32, 8);
         case 19:
            return this.createCipher_Camellia_GCM(var1, 16, 16);
         case 20:
            return this.createCipher_Camellia_GCM(var1, 32, 16);
         case 102:
            return this.createChaCha20Poly1305(var1);
         case 103:
            return this.createCipher_AES_OCB(var1, 16, 12);
         case 104:
            return this.createCipher_AES_OCB(var1, 32, 12);
         default:
            throw new TlsFatalAlert((short)80);
      }
   }

   protected TlsBlockCipher createAESCipher(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsBlockCipher(var1, this.createAESBlockCipher(), this.createAESBlockCipher(), this.createHMACDigest(var3), this.createHMACDigest(var3), var2);
   }

   protected TlsBlockCipher createCamelliaCipher(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsBlockCipher(var1, this.createCamelliaBlockCipher(), this.createCamelliaBlockCipher(), this.createHMACDigest(var3), this.createHMACDigest(var3), var2);
   }

   protected TlsCipher createChaCha20Poly1305(TlsContext var1) throws IOException {
      return new Chacha20Poly1305(var1);
   }

   protected TlsAEADCipher createCipher_AES_CCM(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsAEADCipher(var1, this.createAEADBlockCipher_AES_CCM(), this.createAEADBlockCipher_AES_CCM(), var2, var3);
   }

   protected TlsAEADCipher createCipher_AES_GCM(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsAEADCipher(var1, this.createAEADBlockCipher_AES_GCM(), this.createAEADBlockCipher_AES_GCM(), var2, var3);
   }

   protected TlsAEADCipher createCipher_AES_OCB(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsAEADCipher(var1, this.createAEADBlockCipher_AES_OCB(), this.createAEADBlockCipher_AES_OCB(), var2, var3, 2);
   }

   protected TlsAEADCipher createCipher_Camellia_GCM(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsAEADCipher(var1, this.createAEADBlockCipher_Camellia_GCM(), this.createAEADBlockCipher_Camellia_GCM(), var2, var3);
   }

   protected TlsBlockCipher createDESedeCipher(TlsContext var1, int var2) throws IOException {
      return new TlsBlockCipher(var1, this.createDESedeBlockCipher(), this.createDESedeBlockCipher(), this.createHMACDigest(var2), this.createHMACDigest(var2), 24);
   }

   protected TlsNullCipher createNullCipher(TlsContext var1, int var2) throws IOException {
      return new TlsNullCipher(var1, this.createHMACDigest(var2), this.createHMACDigest(var2));
   }

   protected TlsStreamCipher createRC4Cipher(TlsContext var1, int var2, int var3) throws IOException {
      return new TlsStreamCipher(var1, this.createRC4StreamCipher(), this.createRC4StreamCipher(), this.createHMACDigest(var3), this.createHMACDigest(var3), var2, false);
   }

   protected TlsBlockCipher createSEEDCipher(TlsContext var1, int var2) throws IOException {
      return new TlsBlockCipher(var1, this.createSEEDBlockCipher(), this.createSEEDBlockCipher(), this.createHMACDigest(var2), this.createHMACDigest(var2), 16);
   }

   protected BlockCipher createAESEngine() {
      return new AESEngine();
   }

   protected BlockCipher createCamelliaEngine() {
      return new CamelliaEngine();
   }

   protected BlockCipher createAESBlockCipher() {
      return new CBCBlockCipher(this.createAESEngine());
   }

   protected AEADBlockCipher createAEADBlockCipher_AES_CCM() {
      return new CCMBlockCipher(this.createAESEngine());
   }

   protected AEADBlockCipher createAEADBlockCipher_AES_GCM() {
      return new GCMBlockCipher(this.createAESEngine());
   }

   protected AEADBlockCipher createAEADBlockCipher_AES_OCB() {
      return new OCBBlockCipher(this.createAESEngine(), this.createAESEngine());
   }

   protected AEADBlockCipher createAEADBlockCipher_Camellia_GCM() {
      return new GCMBlockCipher(this.createCamelliaEngine());
   }

   protected BlockCipher createCamelliaBlockCipher() {
      return new CBCBlockCipher(this.createCamelliaEngine());
   }

   protected BlockCipher createDESedeBlockCipher() {
      return new CBCBlockCipher(new DESedeEngine());
   }

   protected StreamCipher createRC4StreamCipher() {
      return new RC4Engine();
   }

   protected BlockCipher createSEEDBlockCipher() {
      return new CBCBlockCipher(new SEEDEngine());
   }

   protected Digest createHMACDigest(int var1) throws IOException {
      switch (var1) {
         case 0:
            return null;
         case 1:
            return TlsUtils.createHash((short)1);
         case 2:
            return TlsUtils.createHash((short)2);
         case 3:
            return TlsUtils.createHash((short)4);
         case 4:
            return TlsUtils.createHash((short)5);
         case 5:
            return TlsUtils.createHash((short)6);
         default:
            throw new TlsFatalAlert((short)80);
      }
   }
}
