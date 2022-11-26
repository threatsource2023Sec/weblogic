package weblogic.utils.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SearchReplaceStream extends FilterOutputStream {
   protected byte[] find;
   protected byte[] replace;
   private byte[] buf;
   private byte firstChar;
   private int idx = 0;
   private int maxIdx = 0;

   public SearchReplaceStream(OutputStream out) {
      super(out);
   }

   public SearchReplaceStream(OutputStream out, String find, String replace) {
      super(out);
      this.find = new byte[find.length()];
      find.getBytes(0, find.length(), this.find, 0);
      this.replace = new byte[replace.length()];
      replace.getBytes(0, replace.length(), this.replace, 0);
      this.buf = new byte[this.find.length];
      this.firstChar = this.find[0];
   }

   public void write(int c) throws IOException {
      this.idx = this.maxIdx;
      this.buf[this.maxIdx++] = (byte)c;

      while(true) {
         int i;
         if (this.find[this.idx] == this.buf[this.idx]) {
            if (this.idx == this.buf.length - 1) {
               for(i = 0; i < this.replace.length; ++i) {
                  super.write(this.replace[i]);
               }

               this.idx = 0;
               this.maxIdx = 0;
               break;
            }

            if (this.idx == this.maxIdx - 1) {
               break;
            }

            ++this.idx;
         } else {
            if (this.maxIdx <= 0) {
               break;
            }

            i = 0;

            do {
               super.write(this.buf[i]);
               ++i;
            } while(i < this.maxIdx && this.buf[i] != this.firstChar);

            this.maxIdx -= i;
            System.arraycopy(this.buf, i, this.buf, 0, this.maxIdx);
            this.idx = 0;
         }
      }

   }

   public void write(byte[] bytes) throws IOException {
      for(int i = 0; i < bytes.length; ++i) {
         this.write(bytes[i]);
      }

   }

   public void write(byte[] bytes, int off, int len) throws IOException {
      for(int i = off; i < len; ++i) {
         this.write(bytes[i]);
      }

   }

   public static void main(String[] argv) throws Exception {
      SearchReplaceStream srs = new SearchReplaceStream(System.out, "foo", "bar");

      int c;
      while((c = System.in.read()) != -1) {
         srs.write(c);
      }

   }
}
