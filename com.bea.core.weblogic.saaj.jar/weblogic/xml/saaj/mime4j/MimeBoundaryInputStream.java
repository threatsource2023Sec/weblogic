package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class MimeBoundaryInputStream extends InputStream {
   private PushbackInputStream s = null;
   private byte[] boundary = null;
   private boolean first = true;
   private boolean eof = false;
   private boolean parenteof = false;
   private boolean moreParts = true;

   public MimeBoundaryInputStream(InputStream s, String boundary) throws IOException {
      this.s = new PushbackInputStream(s, boundary.length() + 4);
      boundary = "--" + boundary;
      this.boundary = new byte[boundary.length()];

      int b;
      for(b = 0; b < this.boundary.length; ++b) {
         this.boundary[b] = (byte)boundary.charAt(b);
      }

      b = this.read();
      if (b != -1) {
         this.s.unread(b);
      }

   }

   public void close() throws IOException {
      this.s.close();
   }

   public boolean hasMoreParts() {
      return this.moreParts;
   }

   public boolean parentEOF() {
      return this.parenteof;
   }

   public void consume() throws IOException {
      while(this.read() != -1) {
      }

   }

   public int read() throws IOException {
      if (this.eof) {
         return -1;
      } else {
         if (this.first) {
            this.first = false;
            if (this.matchBoundary()) {
               return -1;
            }
         }

         int b1 = this.s.read();
         int b2 = this.s.read();
         if (b1 == 13 && b2 == 10 && this.matchBoundary()) {
            return -1;
         } else {
            if (b2 != -1) {
               this.s.unread(b2);
            }

            this.parenteof = b1 == -1;
            this.eof = this.parenteof;
            return b1;
         }
      }
   }

   private boolean matchBoundary() throws IOException {
      int i;
      int b;
      for(i = 0; i < this.boundary.length; ++i) {
         b = this.s.read();
         if (b != this.boundary[i]) {
            if (b != -1) {
               this.s.unread(b);
            }

            for(int j = i - 1; j >= 0; --j) {
               this.s.unread(this.boundary[j]);
            }

            return false;
         }
      }

      i = this.s.read();
      b = this.s.read();
      this.moreParts = i != 45 || b != 45;

      while(b != 10 || i != 13) {
         i = b;
         if ((b = this.s.read()) == -1) {
            break;
         }
      }

      if (b == -1) {
         this.moreParts = false;
         this.parenteof = true;
      }

      this.eof = true;
      return true;
   }
}
