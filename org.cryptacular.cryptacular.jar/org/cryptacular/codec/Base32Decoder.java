package org.cryptacular.codec;

public class Base32Decoder extends AbstractBaseNDecoder {
   private static final byte[] DECODING_TABLE = decodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", 32);

   public Base32Decoder() {
      super(DECODING_TABLE);
   }

   public Base32Decoder(String alphabet) {
      super(decodingTable(alphabet, 32));
   }

   protected int getBlockLength() {
      return 40;
   }

   protected int getBitsPerChar() {
      return 5;
   }
}
