package org.python.apache.commons.compress.compressors.snappy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.python.apache.commons.compress.compressors.CompressorOutputStream;
import org.python.apache.commons.compress.compressors.lz77support.Parameters;
import org.python.apache.commons.compress.utils.ByteUtils;

public class FramedSnappyCompressorOutputStream extends CompressorOutputStream {
   private static final int MAX_COMPRESSED_BUFFER_SIZE = 65536;
   private final OutputStream out;
   private final Parameters params;
   private final PureJavaCrc32C checksum;
   private final byte[] oneByte;
   private final byte[] buffer;
   private int currentIndex;
   private final ByteUtils.ByteConsumer consumer;

   public FramedSnappyCompressorOutputStream(OutputStream out) throws IOException {
      this(out, SnappyCompressorOutputStream.createParameterBuilder(32768).build());
   }

   public FramedSnappyCompressorOutputStream(OutputStream out, Parameters params) throws IOException {
      this.checksum = new PureJavaCrc32C();
      this.oneByte = new byte[1];
      this.buffer = new byte[65536];
      this.currentIndex = 0;
      this.out = out;
      this.params = params;
      this.consumer = new ByteUtils.OutputStreamByteConsumer(out);
      out.write(FramedSnappyCompressorInputStream.SZ_SIGNATURE);
   }

   public void write(int b) throws IOException {
      this.oneByte[0] = (byte)(b & 255);
      this.write(this.oneByte);
   }

   public void write(byte[] data, int off, int len) throws IOException {
      if (this.currentIndex + len > 65536) {
         this.flushBuffer();

         while(len > 65536) {
            System.arraycopy(data, off, this.buffer, 0, 65536);
            off += 65536;
            len -= 65536;
            this.currentIndex = 65536;
            this.flushBuffer();
         }
      }

      System.arraycopy(data, off, this.buffer, this.currentIndex, len);
      this.currentIndex += len;
   }

   public void close() throws IOException {
      this.finish();
      this.out.close();
   }

   public void finish() throws IOException {
      if (this.currentIndex > 0) {
         this.flushBuffer();
      }

   }

   private void flushBuffer() throws IOException {
      this.out.write(0);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OutputStream o = new SnappyCompressorOutputStream(baos, (long)this.currentIndex, this.params);
      Throwable var3 = null;

      try {
         o.write(this.buffer, 0, this.currentIndex);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (o != null) {
            if (var3 != null) {
               try {
                  o.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               o.close();
            }
         }

      }

      byte[] b = baos.toByteArray();
      this.writeLittleEndian(3, (long)b.length + 4L);
      this.writeCrc();
      this.out.write(b);
      this.currentIndex = 0;
   }

   private void writeLittleEndian(int numBytes, long num) throws IOException {
      ByteUtils.toLittleEndian(this.consumer, num, numBytes);
   }

   private void writeCrc() throws IOException {
      this.checksum.update(this.buffer, 0, this.currentIndex);
      this.writeLittleEndian(4, mask(this.checksum.getValue()));
      this.checksum.reset();
   }

   static long mask(long x) {
      x = x >> 15 | x << 17;
      x += 2726488792L;
      x &= 4294967295L;
      return x;
   }
}
