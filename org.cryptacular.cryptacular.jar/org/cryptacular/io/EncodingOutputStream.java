package org.cryptacular.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.codec.Base64Encoder;
import org.cryptacular.codec.Encoder;
import org.cryptacular.codec.HexEncoder;

public class EncodingOutputStream extends FilterOutputStream {
   private final Encoder encoder;
   private final OutputStreamWriter writer;
   private CharBuffer output;

   public EncodingOutputStream(OutputStream out, Encoder e) {
      super(out);
      if (e == null) {
         throw new IllegalArgumentException("Encoder cannot be null.");
      } else {
         this.encoder = e;
         this.writer = new OutputStreamWriter(out);
      }
   }

   public void write(int b) throws IOException {
      this.write(new byte[]{(byte)b});
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      ByteBuffer input = ByteBuffer.wrap(b, off, len);
      int required = this.encoder.outputSize(len - off);
      if (this.output != null && this.output.capacity() >= required) {
         this.output.clear();
      } else {
         this.output = CharBuffer.allocate(required);
      }

      this.encoder.encode(input, this.output);
      this.output.flip();
      this.writer.write(this.output.toString());
      this.writer.flush();
   }

   public void flush() throws IOException {
      this.writer.flush();
   }

   public void close() throws IOException {
      if (this.output == null) {
         this.output = CharBuffer.allocate(8);
      } else {
         this.output.clear();
      }

      this.encoder.finalize(this.output);
      this.output.flip();
      this.writer.write(this.output.toString());
      this.writer.flush();
      this.writer.close();
   }

   public static EncodingOutputStream base64(OutputStream out) {
      return base64(out, -1);
   }

   public static EncodingOutputStream base64(OutputStream out, int lineLength) {
      return new EncodingOutputStream(out, new Base64Encoder(lineLength));
   }

   public static EncodingOutputStream hex(OutputStream out) {
      return new EncodingOutputStream(out, new HexEncoder());
   }
}
