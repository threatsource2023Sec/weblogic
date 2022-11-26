package org.cryptacular.codec;

public class Base64Codec implements Codec {
   private final Encoder encoder;
   private final Decoder decoder;
   private final String customAlphabet;
   private final boolean padding;

   public Base64Codec() {
      this.encoder = new Base64Encoder();
      this.decoder = new Base64Decoder();
      this.customAlphabet = null;
      this.padding = true;
   }

   public Base64Codec(String alphabet) {
      this(alphabet, true);
   }

   public Base64Codec(String alphabet, boolean inputOutputPadding) {
      this.customAlphabet = alphabet;
      this.padding = inputOutputPadding;
      this.encoder = this.newEncoder();
      this.decoder = this.newDecoder();
   }

   public Encoder getEncoder() {
      return this.encoder;
   }

   public Decoder getDecoder() {
      return this.decoder;
   }

   public Encoder newEncoder() {
      Base64Encoder encoder;
      if (this.customAlphabet != null) {
         encoder = new Base64Encoder(this.customAlphabet);
      } else {
         encoder = new Base64Encoder();
      }

      encoder.setPaddedOutput(this.padding);
      return encoder;
   }

   public Decoder newDecoder() {
      Base64Decoder decoder;
      if (this.customAlphabet != null) {
         decoder = new Base64Decoder(this.customAlphabet);
      } else {
         decoder = new Base64Decoder();
      }

      decoder.setPaddedInput(this.padding);
      return decoder;
   }
}
