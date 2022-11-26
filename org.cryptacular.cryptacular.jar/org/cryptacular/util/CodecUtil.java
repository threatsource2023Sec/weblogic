package org.cryptacular.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;
import org.cryptacular.codec.Base32Decoder;
import org.cryptacular.codec.Base32Encoder;
import org.cryptacular.codec.Base64Decoder;
import org.cryptacular.codec.Base64Encoder;
import org.cryptacular.codec.Decoder;
import org.cryptacular.codec.Encoder;
import org.cryptacular.codec.HexDecoder;
import org.cryptacular.codec.HexEncoder;

public final class CodecUtil {
   private CodecUtil() {
   }

   public static String hex(byte[] raw) throws EncodingException {
      return encode(new HexEncoder(), raw);
   }

   public static String hex(byte[] raw, boolean delimit) throws EncodingException {
      return encode(new HexEncoder(delimit), raw);
   }

   public static byte[] hex(CharSequence encoded) throws EncodingException {
      return decode(new HexDecoder(), encoded);
   }

   public static String b64(byte[] raw) throws EncodingException {
      return encode(new Base64Encoder(), raw);
   }

   public static byte[] b64(CharSequence encoded) throws EncodingException {
      return decode(new Base64Decoder(), encoded);
   }

   public static String b64(byte[] raw, int lineLength) throws EncodingException {
      return encode(new Base64Encoder(lineLength), raw);
   }

   public static String b32(byte[] raw) throws EncodingException {
      return encode(new Base32Encoder(), raw);
   }

   public static byte[] b32(CharSequence encoded) throws EncodingException {
      return decode(new Base32Decoder(), encoded);
   }

   public static String b32(byte[] raw, int lineLength) throws EncodingException {
      return encode(new Base32Encoder(lineLength), raw);
   }

   public static String encode(Encoder encoder, byte[] raw) throws EncodingException {
      CharBuffer output = CharBuffer.allocate(encoder.outputSize(raw.length));
      encoder.encode(ByteBuffer.wrap(raw), output);
      encoder.finalize(output);
      return output.flip().toString();
   }

   public static byte[] decode(Decoder decoder, CharSequence encoded) throws EncodingException {
      ByteBuffer output = ByteBuffer.allocate(decoder.outputSize(encoded.length()));
      decoder.decode(CharBuffer.wrap(encoded), output);
      decoder.finalize(output);
      output.flip();
      return ByteUtil.toArray(output);
   }
}
