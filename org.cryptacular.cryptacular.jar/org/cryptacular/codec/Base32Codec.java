package org.cryptacular.codec;

public class Base32Codec implements Codec {
   private final Encoder encoder;
   private final Decoder decoder;
   private final String customAlphabet;
   private final boolean padding;

   public Base32Codec() {
      this.encoder = new Base32Encoder();
      this.decoder = new Base32Decoder();
      this.customAlphabet = null;
      this.padding = true;
   }

   public Base32Codec(String alphabet) {
      this(alphabet, true);
   }

   public Base32Codec(String alphabet, boolean inputOutputPadding) {
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
      Base32Encoder encoder;
      if (this.customAlphabet != null) {
         encoder = new Base32Encoder(this.customAlphabet);
      } else {
         encoder = new Base32Encoder();
      }

      encoder.setPaddedOutput(this.padding);
      return encoder;
   }

   public Decoder newDecoder() {
      Base32Decoder decoder;
      if (this.customAlphabet != null) {
         decoder = new Base32Decoder(this.customAlphabet);
      } else {
         decoder = new Base32Decoder();
      }

      decoder.setPaddedInput(this.padding);
      return decoder;
   }
}
