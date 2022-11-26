package org.cryptacular.spec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.OCBBlockCipher;

public class AEADBlockCipherSpec implements Spec {
   public static final Pattern FORMAT = Pattern.compile("(?<alg>[A-Za-z0-9_-]+)/(?<mode>\\w+)");
   private final String algorithm;
   private final String mode;

   public AEADBlockCipherSpec(String algName, String cipherMode) {
      this.algorithm = algName;
      this.mode = cipherMode;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getMode() {
      return this.mode;
   }

   public AEADBlockCipher newInstance() {
      BlockCipher blockCipher = (new BlockCipherSpec(this.algorithm)).newInstance();
      Object aeadBlockCipher;
      switch (this.mode) {
         case "GCM":
            aeadBlockCipher = new GCMBlockCipher(blockCipher);
            break;
         case "CCM":
            aeadBlockCipher = new CCMBlockCipher(blockCipher);
            break;
         case "OCB":
            aeadBlockCipher = new OCBBlockCipher(blockCipher, (new BlockCipherSpec(this.algorithm)).newInstance());
            break;
         case "EAX":
            aeadBlockCipher = new EAXBlockCipher(blockCipher);
            break;
         default:
            throw new IllegalStateException("Unsupported mode " + this.mode);
      }

      return (AEADBlockCipher)aeadBlockCipher;
   }

   public String toString() {
      return this.algorithm + '/' + this.mode;
   }

   public static AEADBlockCipherSpec parse(String specification) {
      Matcher m = FORMAT.matcher(specification);
      if (!m.matches()) {
         throw new IllegalArgumentException("Invalid specification " + specification);
      } else {
         return new AEADBlockCipherSpec(m.group("alg"), m.group("mode"));
      }
   }
}
