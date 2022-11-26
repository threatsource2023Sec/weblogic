package org.cryptacular.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.codec.Base64Decoder;
import org.cryptacular.codec.Decoder;
import org.cryptacular.codec.HexDecoder;

public class DecodingInputStream extends FilterInputStream {
   private final Decoder decoder;
   private final InputStreamReader reader;
   private CharBuffer input;
   private ByteBuffer output;

   public DecodingInputStream(InputStream in, Decoder d) {
      super(in);
      if (d == null) {
         throw new IllegalArgumentException("Decoder cannot be null.");
      } else {
         this.decoder = d;
         this.reader = new InputStreamReader(in);
      }
   }

   public int read() throws IOException {
      return this.read(new byte[1]);
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      this.prepareInputBuffer(len - off);
      this.prepareOutputBuffer();
      if (this.reader.read(this.input) < 0) {
         this.decoder.finalize(this.output);
         if (this.output.position() == 0) {
            return -1;
         }
      } else {
         this.input.flip();
         this.decoder.decode(this.input, this.output);
      }

      this.output.flip();
      this.output.get(b, off, this.output.limit());
      return this.output.position();
   }

   public static DecodingInputStream base64(InputStream in) {
      return new DecodingInputStream(in, new Base64Decoder());
   }

   public static DecodingInputStream hex(InputStream in) {
      return new DecodingInputStream(in, new HexDecoder());
   }

   private void prepareInputBuffer(int required) {
      if (this.input != null && this.input.capacity() >= required) {
         this.input.clear();
      } else {
         this.input = CharBuffer.allocate(required);
      }

   }

   private void prepareOutputBuffer() {
      int required = this.decoder.outputSize(this.input.capacity());
      if (this.output != null && this.output.capacity() >= required) {
         this.output.clear();
      } else {
         this.output = ByteBuffer.allocate(required);
      }

   }
}
