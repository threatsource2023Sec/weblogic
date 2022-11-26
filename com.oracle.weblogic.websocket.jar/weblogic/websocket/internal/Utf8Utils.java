package weblogic.websocket.internal;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

class Utf8Utils {
   public static String decodePayload(byte[] payload) {
      DecodingContext context = new DecodingContext();
      return decodePayload(payload, context, true);
   }

   public static String decodePayload(byte[] payload, DecodingContext context, boolean isFinalFragment) {
      ByteBuffer bb = context.getByteBuffer(payload);
      CharsetDecoder decoder = context.getDecoder();
      int n = (int)((float)bb.remaining() * decoder.averageCharsPerByte());
      CharBuffer cb = CharBuffer.allocate(n);

      CoderResult result;
      do {
         while(true) {
            result = decoder.decode(bb, cb, isFinalFragment);
            if (result.isUnderflow()) {
               if (isFinalFragment) {
                  decoder.flush(cb);
                  if (bb.hasRemaining()) {
                     throw new WebSocketMessageParsingException(1007, "Not all bytes decoded.");
                  }

                  context.reset();
               } else if (bb.hasRemaining()) {
                  context.setRemainder(bb);
               }

               cb.flip();
               return cb.toString();
            }

            if (!result.isOverflow()) {
               break;
            }

            CharBuffer tmp = CharBuffer.allocate(2 * n + 1);
            cb.flip();
            tmp.put(cb);
            cb = tmp;
         }
      } while(!result.isError() && !result.isMalformed());

      throw new WebSocketMessageParsingException(1007, "Illegal UTF-8 Sequence");
   }

   public static class DecodingContext {
      private CharsetDecoder decoder = (new StrictUtf8()).newDecoder();
      private ByteBuffer remainder;

      public CharsetDecoder getDecoder() {
         return this.decoder;
      }

      public ByteBuffer getByteBuffer() {
         return this.remainder;
      }

      public ByteBuffer getByteBuffer(byte[] data) {
         if (this.remainder == null) {
            return ByteBuffer.wrap(data);
         } else {
            int rem = this.remainder.remaining();
            byte[] orig = this.remainder.array();
            byte[] b = new byte[rem + data.length];
            System.arraycopy(orig, orig.length - rem, b, 0, rem);
            System.arraycopy(data, 0, b, rem, data.length);
            this.remainder = null;
            return ByteBuffer.wrap(b);
         }
      }

      public void setRemainder(ByteBuffer remainder) {
         this.remainder = remainder;
      }

      public void reset() {
         this.decoder.reset();
         this.remainder = null;
      }
   }
}
