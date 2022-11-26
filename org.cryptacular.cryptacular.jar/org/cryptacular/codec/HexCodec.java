package org.cryptacular.codec;

public class HexCodec implements Codec {
   private final Encoder encoder;
   private final Decoder decoder;
   private final boolean uppercase;

   public HexCodec() {
      this(false);
   }

   public HexCodec(boolean uppercaseOutput) {
      this.decoder = new HexDecoder();
      this.uppercase = uppercaseOutput;
      this.encoder = new HexEncoder(false, this.uppercase);
   }

   public Encoder getEncoder() {
      return this.encoder;
   }

   public Decoder getDecoder() {
      return this.decoder;
   }

   public Encoder newEncoder() {
      return new HexEncoder(false, this.uppercase);
   }

   public Decoder newDecoder() {
      return new HexDecoder();
   }
}
