package weblogic.jms.common;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class GZIPInputStream extends InflaterInputStream {
   protected CRC32 crc = new CRC32();
   protected boolean eos;
   private boolean closed = false;
   private Inflater inflater = null;
   public static final int GZIP_MAGIC = 35615;
   private static final int FTEXT = 1;
   private static final int FHCRC = 2;
   private static final int FEXTRA = 4;
   private static final int FNAME = 8;
   private static final int FCOMMENT = 16;
   private byte[] tmpbuf = new byte[128];

   private void ensureOpen() throws IOException {
      if (this.closed) {
         throw new IOException("Stream closed");
      }
   }

   public GZIPInputStream(InputStream in, int size, Inflater inflater) throws IOException {
      super(in, inflater, size);
      this.readHeader(in);
      this.inflater = inflater;
   }

   public int read(byte[] buf, int off, int len) throws IOException {
      this.ensureOpen();
      if (this.eos) {
         return -1;
      } else {
         int n = super.read(buf, off, len);
         if (n == -1) {
            if (!this.readTrailer()) {
               return this.read(buf, off, len);
            }

            this.eos = true;
         } else {
            this.crc.update(buf, off, n);
         }

         return n;
      }
   }

   public void close() throws IOException {
      if (!this.closed) {
         super.close();
         this.eos = true;
         this.closed = true;
      }

      this.inflater.reset();
   }

   private int readHeader(InputStream this_in) throws IOException {
      CheckedInputStream in = new CheckedInputStream(this_in, this.crc);
      this.crc.reset();
      if (this.readUShort(in) != 35615) {
         throw new IOException("Not in GZIP format");
      } else if (this.readUByte(in) != 8) {
         throw new IOException("Unsupported compression method");
      } else {
         int flg = this.readUByte(in);
         this.skipBytes(in, 6);
         int n = 10;
         int v;
         if ((flg & 4) == 4) {
            this.skipBytes(in, this.readUShort(in));
            v = this.readUShort(in);
            this.skipBytes(in, v);
            n += v + 2;
         }

         if ((flg & 8) == 8) {
            do {
               ++n;
            } while(this.readUByte(in) != 0);
         }

         if ((flg & 16) == 16) {
            do {
               ++n;
            } while(this.readUByte(in) != 0);
         }

         if ((flg & 2) == 2) {
            v = (int)this.crc.getValue() & '\uffff';
            if (this.readUShort(in) != v) {
               throw new IOException("Corrupt GZIP header");
            }

            n += 2;
         }

         this.crc.reset();
         return n;
      }
   }

   private boolean readTrailer() throws IOException {
      InputStream in = this.in;
      int n = this.inf.getRemaining();
      if (n > 0) {
         in = new SequenceInputStream(new ByteArrayInputStream(this.buf, this.len - n, n), (InputStream)in);
      }

      if (this.readUInt((InputStream)in) == this.crc.getValue() && this.readUInt((InputStream)in) == (this.inf.getBytesWritten() & 4294967295L)) {
         if (this.in.available() <= 0 && n <= 26) {
            return true;
         } else {
            int m = 8;

            try {
               m += this.readHeader((InputStream)in);
            } catch (IOException var5) {
               return true;
            }

            this.inf.reset();
            if (n > m) {
               this.inf.setInput(this.buf, this.len - n + m, n - m);
            }

            return false;
         }
      } else {
         throw new IOException("Corrupt GZIP trailer");
      }
   }

   private long readUInt(InputStream in) throws IOException {
      long s = (long)this.readUShort(in);
      return (long)this.readUShort(in) << 16 | s;
   }

   private int readUShort(InputStream in) throws IOException {
      int b = this.readUByte(in);
      return this.readUByte(in) << 8 | b;
   }

   private int readUByte(InputStream in) throws IOException {
      int b = in.read();
      if (b == -1) {
         throw new EOFException();
      } else if (b >= -1 && b <= 255) {
         return b;
      } else {
         throw new IOException(this.in.getClass().getName() + ".read() returned value out of range -1..255: " + b);
      }
   }

   private void skipBytes(InputStream in, int n) throws IOException {
      while(n > 0) {
         int len = in.read(this.tmpbuf, 0, n < this.tmpbuf.length ? n : this.tmpbuf.length);
         if (len == -1) {
            throw new EOFException();
         }

         n -= len;
      }

   }
}
