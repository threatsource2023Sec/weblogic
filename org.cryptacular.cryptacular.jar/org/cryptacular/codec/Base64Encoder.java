package org.cryptacular.codec;

public class Base64Encoder extends AbstractBaseNEncoder {
   private static final char[] DEFAULT_ENCODING_TABLE = encodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", 64);
   private static final char[] URLSAFE_ENCODING_TABLE = encodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", 64);

   public Base64Encoder() {
      this(-1);
   }

   public Base64Encoder(boolean urlSafe) {
      this(urlSafe, -1);
   }

   public Base64Encoder(int charactersPerLine) {
      this(false, charactersPerLine);
   }

   public Base64Encoder(boolean urlSafe, int charactersPerLine) {
      super(urlSafe ? URLSAFE_ENCODING_TABLE : DEFAULT_ENCODING_TABLE, charactersPerLine);
   }

   public Base64Encoder(String alphabet) {
      this(alphabet, -1);
   }

   public Base64Encoder(String alphabet, int charactersPerLine) {
      super(encodingTable(alphabet, 64), charactersPerLine);
   }

   protected int getBlockLength() {
      return 24;
   }

   protected int getBitsPerChar() {
      return 6;
   }

   public static class Builder {
      private boolean urlSafe;
      private String alphabet;
      private boolean padding;
      private int charactersPerLine = -1;

      public Builder setUrlSafe(boolean safe) {
         this.urlSafe = safe;
         return this;
      }

      public Builder setAlphabet(String alpha) {
         this.alphabet = alpha;
         return this;
      }

      public Builder setPadding(boolean pad) {
         this.padding = pad;
         return this;
      }

      public Builder setCharactersPerLine(int lineLength) {
         this.charactersPerLine = lineLength;
         return this;
      }

      public Base64Encoder build() {
         Base64Encoder decoder;
         if (this.alphabet != null) {
            decoder = new Base64Encoder(this.alphabet, this.charactersPerLine);
         } else {
            decoder = new Base64Encoder(this.urlSafe, this.charactersPerLine);
         }

         decoder.setPaddedOutput(this.padding);
         return decoder;
      }
   }
}
