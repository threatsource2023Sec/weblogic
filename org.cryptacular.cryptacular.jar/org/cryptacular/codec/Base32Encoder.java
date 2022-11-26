package org.cryptacular.codec;

public class Base32Encoder extends AbstractBaseNEncoder {
   private static final char[] ENCODING_TABLE = encodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", 32);

   public Base32Encoder() {
      this(-1);
   }

   public Base32Encoder(int charactersPerLine) {
      super(ENCODING_TABLE, charactersPerLine);
   }

   public Base32Encoder(String alphabet) {
      this(alphabet, -1);
   }

   public Base32Encoder(String alphabet, int charactersPerLine) {
      super(encodingTable(alphabet, 32), charactersPerLine);
   }

   protected int getBlockLength() {
      return 40;
   }

   protected int getBitsPerChar() {
      return 5;
   }
}
