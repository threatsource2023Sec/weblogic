package weblogic.xml.saaj.mime4j.decoder;

import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream extends InputStream {
   private final InputStream s;
   private final ByteQueue byteq = new ByteQueue(3);
   private static byte[] TRANSLATION = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

   public Base64InputStream(InputStream s) {
      this.s = s;
   }

   public void close() throws IOException {
      this.s.close();
   }

   public int read() throws IOException {
      if (this.byteq.count() == 0) {
         this.fillBuffer();
         if (this.byteq.count() == 0) {
            return -1;
         }
      }

      byte val = this.byteq.dequeue();
      return val >= 0 ? val : val & 255;
   }

   public int read(byte[] buf, int off, int len) throws IOException {
      if (buf == null) {
         throw new NullPointerException();
      } else if (off >= 0 && off <= buf.length && len >= 0 && off + len <= buf.length && off + len >= 0) {
         if (len == 0) {
            return 0;
         } else {
            int i;
            for(i = 0; i < len; ++i) {
               int c = this.read();
               if (c == -1) {
                  if (i == 0) {
                     i = -1;
                  }
                  break;
               }

               buf[off + i] = (byte)c;
            }

            return i;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   private void fillBuffer() throws IOException {
      byte[] data = new byte[4];
      int pos = 0;

      while(true) {
         int i;
         switch (i = this.s.read()) {
            case -1:
               if (pos == 0) {
                  return;
               }

               throw new IOException("Failed to decode stream");
            case 61:
               this.decodeAndEnqueue(data, pos);
               return;
         }

         byte sX = TRANSLATION[i];
         if (sX >= 0) {
            data[pos++] = sX;
            if (pos == data.length) {
               this.decodeAndEnqueue(data, pos);
               return;
            }
         }
      }
   }

   private void decodeAndEnqueue(byte[] data, int len) {
      int accum = 0;
      accum |= data[0] << 18;
      accum |= data[1] << 12;
      accum |= data[2] << 6;
      accum |= data[3];
      byte b1 = (byte)(accum >>> 16);
      this.byteq.enqueue(b1);
      if (len > 2) {
         byte b2 = (byte)(accum >>> 8 & 255);
         this.byteq.enqueue(b2);
         if (len > 3) {
            byte b3 = (byte)(accum & 255);
            this.byteq.enqueue(b3);
         }
      }

   }
}
