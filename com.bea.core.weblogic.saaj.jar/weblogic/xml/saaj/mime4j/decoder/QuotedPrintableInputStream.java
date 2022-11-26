package weblogic.xml.saaj.mime4j.decoder;

import java.io.IOException;
import java.io.InputStream;

public class QuotedPrintableInputStream extends InputStream {
   private InputStream stream;
   ByteQueue byteq = new ByteQueue();
   ByteQueue pushbackq = new ByteQueue();
   private byte state = 0;

   public QuotedPrintableInputStream(InputStream stream) {
      this.stream = stream;
   }

   public void close() throws IOException {
      this.stream.close();
   }

   public int read() throws IOException {
      this.fillBuffer();
      if (this.byteq.count() == 0) {
         return -1;
      } else {
         byte val = this.byteq.dequeue();
         return val >= 0 ? val : val & 255;
      }
   }

   private void populatePushbackQueue() throws IOException {
      if (this.pushbackq.count() == 0) {
         while(true) {
            int i = this.stream.read();
            switch (i) {
               case -1:
                  this.pushbackq.clear();
                  return;
               case 9:
               case 32:
                  this.pushbackq.enqueue((byte)i);
                  break;
               case 10:
               case 13:
                  this.pushbackq.clear();
                  this.pushbackq.enqueue((byte)i);
                  return;
               default:
                  this.pushbackq.enqueue((byte)i);
                  return;
            }
         }
      }
   }

   private void fillBuffer() throws IOException {
      byte msdChar = 0;

      while(true) {
         while(true) {
            while(this.byteq.count() == 0) {
               if (this.pushbackq.count() == 0) {
                  this.populatePushbackQueue();
                  if (this.pushbackq.count() == 0) {
                     return;
                  }
               }

               byte b = this.pushbackq.dequeue();
               switch (this.state) {
                  case 0:
                     if (b != 61) {
                        this.byteq.enqueue(b);
                     } else {
                        this.state = 1;
                     }
                     break;
                  case 1:
                     if (b == 13) {
                        this.state = 2;
                     } else {
                        if ((b < 48 || b > 57) && (b < 65 || b > 70) && (b < 97 || b > 102)) {
                           if (b == 61) {
                              this.byteq.enqueue((byte)61);
                           } else {
                              this.state = 0;
                              this.byteq.enqueue((byte)61);
                              this.byteq.enqueue(b);
                           }
                           continue;
                        }

                        this.state = 3;
                        msdChar = b;
                     }
                     break;
                  case 2:
                     if (b == 10) {
                        this.state = 0;
                     } else {
                        this.state = 0;
                        this.byteq.enqueue((byte)61);
                        this.byteq.enqueue((byte)13);
                        this.byteq.enqueue(b);
                     }
                     break;
                  case 3:
                     if (b >= 48 && b <= 57 || b >= 65 && b <= 70 || b >= 97 && b <= 102) {
                        byte msd = this.asciiCharToNumericValue(msdChar);
                        byte low = this.asciiCharToNumericValue(b);
                        this.state = 0;
                        this.byteq.enqueue((byte)(msd << 4 | low));
                        break;
                     }

                     this.state = 0;
                     this.byteq.enqueue((byte)61);
                     this.byteq.enqueue(msdChar);
                     this.byteq.enqueue(b);
                     break;
                  default:
                     this.state = 0;
                     this.byteq.enqueue(b);
               }
            }

            return;
         }
      }
   }

   private byte asciiCharToNumericValue(byte c) {
      if (c >= 48 && c <= 57) {
         return (byte)(c - 48);
      } else if (c >= 65 && c <= 90) {
         return (byte)(10 + (c - 65));
      } else if (c >= 97 && c <= 122) {
         return (byte)(10 + (c - 97));
      } else {
         throw new IllegalArgumentException((char)c + " is not a hexadecimal digit");
      }
   }
}
