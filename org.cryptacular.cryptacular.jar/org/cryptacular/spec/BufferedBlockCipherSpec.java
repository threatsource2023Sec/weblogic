package org.cryptacular.spec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.bouncycastle.crypto.paddings.X923Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;

public class BufferedBlockCipherSpec implements Spec {
   public static final Pattern FORMAT = Pattern.compile("(?<alg>[A-Za-z0-9_-]+)/(?<mode>\\w+)/(?<padding>\\w+)");
   private final String algorithm;
   private final String mode;
   private final String padding;

   public BufferedBlockCipherSpec(String algName) {
      this(algName, (String)null, (String)null);
   }

   public BufferedBlockCipherSpec(String algName, String cipherMode) {
      this(algName, cipherMode, (String)null);
   }

   public BufferedBlockCipherSpec(String algName, String cipherMode, String cipherPadding) {
      this.algorithm = algName;
      this.mode = cipherMode;
      this.padding = cipherPadding;
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public String getMode() {
      return this.mode;
   }

   public String getPadding() {
      return this.padding;
   }

   public BlockCipherSpec getBlockCipherSpec() {
      return new BlockCipherSpec(this.algorithm);
   }

   public BufferedBlockCipher newInstance() {
      BlockCipher cipher = this.getBlockCipherSpec().newInstance();
      switch (this.mode) {
         case "CBC":
            cipher = new CBCBlockCipher((BlockCipher)cipher);
            break;
         case "OFB":
            cipher = new OFBBlockCipher((BlockCipher)cipher, ((BlockCipher)cipher).getBlockSize());
            break;
         case "CFB":
            cipher = new CFBBlockCipher((BlockCipher)cipher, ((BlockCipher)cipher).getBlockSize());
      }

      return (BufferedBlockCipher)(this.padding != null ? new PaddedBufferedBlockCipher((BlockCipher)cipher, getPadding(this.padding)) : new BufferedBlockCipher((BlockCipher)cipher));
   }

   public String toString() {
      return this.algorithm + '/' + this.mode + '/' + this.padding;
   }

   public static BufferedBlockCipherSpec parse(String specification) {
      Matcher m = FORMAT.matcher(specification);
      if (!m.matches()) {
         throw new IllegalArgumentException("Invalid specification " + specification);
      } else {
         return new BufferedBlockCipherSpec(m.group("alg"), m.group("mode"), m.group("padding"));
      }
   }

   private static BlockCipherPadding getPadding(String padding) {
      int pIndex = padding.indexOf("Padding");
      String name;
      if (pIndex > -1) {
         name = padding.substring(0, pIndex);
      } else {
         name = padding;
      }

      Object blockCipherPadding;
      if ("ISO7816d4".equalsIgnoreCase(name) | "ISO7816".equalsIgnoreCase(name)) {
         blockCipherPadding = new ISO7816d4Padding();
      } else if (!"ISO10126".equalsIgnoreCase(name) && !"ISO10126-2".equalsIgnoreCase(name)) {
         if (!"PKCS7".equalsIgnoreCase(name) && !"PKCS5".equalsIgnoreCase(name)) {
            if ("TBC".equalsIgnoreCase(name)) {
               blockCipherPadding = new TBCPadding();
            } else if ("X923".equalsIgnoreCase(name)) {
               blockCipherPadding = new X923Padding();
            } else {
               if (!"NULL".equalsIgnoreCase(name) && !"Zero".equalsIgnoreCase(name) && !"None".equalsIgnoreCase(name)) {
                  throw new IllegalArgumentException("Invalid padding " + padding);
               }

               blockCipherPadding = new ZeroBytePadding();
            }
         } else {
            blockCipherPadding = new PKCS7Padding();
         }
      } else {
         blockCipherPadding = new ISO10126d2Padding();
      }

      return (BlockCipherPadding)blockCipherPadding;
   }
}
