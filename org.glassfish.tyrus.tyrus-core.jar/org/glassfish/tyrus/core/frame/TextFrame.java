package org.glassfish.tyrus.core.frame;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import org.glassfish.tyrus.core.StrictUtf8;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.Utf8DecodingException;

public class TextFrame extends TyrusFrame {
   private final Charset utf8 = new StrictUtf8();
   private final CharsetDecoder currentDecoder;
   private final String textPayload;
   private final boolean continuation;
   private ByteBuffer remainder;

   public TextFrame(Frame frame, ByteBuffer remainder) {
      super(frame, TyrusFrame.FrameType.TEXT);
      this.currentDecoder = this.utf8.newDecoder();
      this.textPayload = this.utf8Decode(this.isFin(), this.getPayloadData(), remainder);
      this.continuation = false;
   }

   public TextFrame(Frame frame, ByteBuffer remainder, boolean continuation) {
      super(frame, continuation ? TyrusFrame.FrameType.TEXT_CONTINUATION : TyrusFrame.FrameType.TEXT);
      this.currentDecoder = this.utf8.newDecoder();
      this.textPayload = this.utf8Decode(this.isFin(), this.getPayloadData(), remainder);
      this.continuation = continuation;
   }

   public TextFrame(String message, boolean continuation, boolean fin) {
      super(Frame.builder().payloadData(encode(new StrictUtf8(), message)).opcode((byte)(continuation ? 0 : 1)).fin(fin).build(), continuation ? TyrusFrame.FrameType.TEXT_CONTINUATION : TyrusFrame.FrameType.TEXT);
      this.currentDecoder = this.utf8.newDecoder();
      this.continuation = continuation;
      this.textPayload = message;
   }

   public String getTextPayload() {
      return this.textPayload;
   }

   public ByteBuffer getRemainder() {
      return this.remainder;
   }

   public void respond(TyrusWebSocket socket) {
      if (this.continuation) {
         socket.onFragment(this, this.isFin());
      } else if (this.isFin()) {
         socket.onMessage(this);
      } else {
         socket.onFragment(this, false);
      }

   }

   private String utf8Decode(boolean finalFragment, byte[] data, ByteBuffer remainder) {
      ByteBuffer b = this.getByteBuffer(data, remainder);
      int n = (int)((float)b.remaining() * this.currentDecoder.averageCharsPerByte());
      CharBuffer cb = CharBuffer.allocate(n);

      CoderResult result;
      do {
         while(true) {
            result = this.currentDecoder.decode(b, cb, finalFragment);
            if (result.isUnderflow()) {
               if (finalFragment) {
                  this.currentDecoder.flush(cb);
                  if (b.hasRemaining()) {
                     throw new IllegalStateException("Final UTF-8 fragment received, but not all bytes consumed by decode process");
                  }

                  this.currentDecoder.reset();
               } else if (b.hasRemaining()) {
                  this.remainder = b;
               }

               cb.flip();
               String res = cb.toString();
               return res;
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

      throw new Utf8DecodingException();
   }

   private ByteBuffer getByteBuffer(byte[] data, ByteBuffer remainder) {
      if (remainder == null) {
         return ByteBuffer.wrap(data);
      } else {
         int rem = remainder.remaining();
         byte[] orig = remainder.array();
         byte[] b = new byte[rem + data.length];
         System.arraycopy(orig, orig.length - rem, b, 0, rem);
         System.arraycopy(data, 0, b, rem, data.length);
         return ByteBuffer.wrap(b);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(super.toString());
      sb.append(", textPayload='").append(this.textPayload).append('\'');
      return sb.toString();
   }

   private static byte[] encode(Charset charset, String string) {
      if (string != null && !string.isEmpty()) {
         CharsetEncoder ce = charset.newEncoder();
         int en = scale(string.length(), ce.maxBytesPerChar());
         byte[] ba = new byte[en];
         if (string.length() == 0) {
            return ba;
         } else {
            ce.reset();
            ByteBuffer bb = ByteBuffer.wrap(ba);
            CharBuffer cb = CharBuffer.wrap(string);

            try {
               CoderResult cr = ce.encode(cb, bb, true);
               if (!cr.isUnderflow()) {
                  cr.throwException();
               }

               cr = ce.flush(bb);
               if (!cr.isUnderflow()) {
                  cr.throwException();
               }
            } catch (CharacterCodingException var8) {
               throw new Error(var8);
            }

            return safeTrim(ba, bb.position());
         }
      } else {
         return new byte[0];
      }
   }

   private static int scale(int len, float expansionFactor) {
      return (int)((double)len * (double)expansionFactor);
   }

   private static byte[] safeTrim(byte[] ba, int len) {
      return len == ba.length && System.getSecurityManager() == null ? ba : copyOf(ba, len);
   }

   private static byte[] copyOf(byte[] original, int newLength) {
      byte[] copy = new byte[newLength];
      System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
      return copy;
   }
}
