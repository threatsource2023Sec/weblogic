package org.cryptacular.x509;

import java.util.BitSet;
import org.bouncycastle.asn1.x509.KeyUsage;

public enum KeyUsageBits {
   DigitalSignature(7),
   NonRepudiation(6),
   KeyEncipherment(5),
   DataEncipherment(4),
   KeyAgreement(3),
   KeyCertSign(2),
   CRLSign(1),
   EncipherOnly(0),
   DecipherOnly(15);

   private final int offset;

   private KeyUsageBits(int offset) {
      this.offset = offset;
   }

   public int getMask() {
      return 1 << this.offset;
   }

   public boolean isSet(KeyUsage keyUsage) {
      return this.isSet(keyUsage.getBytes());
   }

   public boolean isSet(byte[] bitString) {
      return BitSet.valueOf(bitString).get(this.offset);
   }

   public boolean isSet(int bitString) {
      return (bitString & this.getMask()) >> this.offset == 1;
   }

   public static int usage(KeyUsageBits... bits) {
      int usage = 0;
      KeyUsageBits[] var2 = bits;
      int var3 = bits.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         KeyUsageBits bit = var2[var4];
         usage |= bit.getMask();
      }

      return usage;
   }
}
