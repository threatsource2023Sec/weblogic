package weblogic.jms.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class GZIPOutputStream extends DeflaterOutputStream {
   protected CRC32 crc = new CRC32();
   private static final int GZIP_MAGIC = 35615;
   private Deflater deflater = null;
   private static final int TRAILER_SIZE = 8;
   private static final byte[] header = new byte[]{31, -117, 8, 0, 0, 0, 0, 0, 0, 0};

   public GZIPOutputStream(OutputStream out, Deflater deflater) throws IOException {
      super(out, deflater, 512);
      this.writeHeader();
      this.crc.reset();
      this.deflater = deflater;
   }

   public void close() throws IOException {
      super.close();
      this.deflater.reset();
   }

   public synchronized void write(byte[] buf, int off, int len) throws IOException {
      super.write(buf, off, len);
      this.crc.update(buf, off, len);
   }

   public void finish() throws IOException {
      if (!this.def.finished()) {
         this.def.finish();

         while(!this.def.finished()) {
            int len = this.def.deflate(this.buf, 0, this.buf.length);
            if (this.def.finished() && len <= this.buf.length - 8) {
               this.writeTrailer(this.buf, len);
               len += 8;
               this.out.write(this.buf, 0, len);
               return;
            }

            if (len > 0) {
               this.out.write(this.buf, 0, len);
            }
         }

         byte[] trailer = new byte[8];
         this.writeTrailer(trailer, 0);
         this.out.write(trailer);
      }

   }

   private void writeHeader() throws IOException {
      this.out.write(header);
   }

   private void writeTrailer(byte[] buf, int offset) throws IOException {
      this.writeInt((int)this.crc.getValue(), buf, offset);
      this.writeInt(this.def.getTotalIn(), buf, offset + 4);
   }

   private void writeInt(int i, byte[] buf, int offset) throws IOException {
      this.writeShort(i & '\uffff', buf, offset);
      this.writeShort(i >> 16 & '\uffff', buf, offset + 2);
   }

   private void writeShort(int s, byte[] buf, int offset) throws IOException {
      buf[offset] = (byte)(s & 255);
      buf[offset + 1] = (byte)(s >> 8 & 255);
   }
}
