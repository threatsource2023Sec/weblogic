package org.apache.openjpa.lib.xml;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class XMLWriter extends FilterWriter {
   private static String _endl = J2DoPrivHelper.getLineSeparator();
   private int _lastChar = 32;
   private int _lastChar2 = 32;
   private int _lastChar3 = 32;
   private int _depth = 0;

   public XMLWriter(Writer out) {
      super(out);
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      for(int i = 0; i < len; ++i) {
         this.write(cbuf[off + i]);
      }

   }

   public void write(String str, int off, int len) throws IOException {
      for(int i = 0; i < len; ++i) {
         this.write(str.charAt(off + i));
      }

   }

   public void write(int c) throws IOException {
      if (this._lastChar == 60) {
         if (c != 63 && c != 33) {
            if (c == 47) {
               --this._depth;
            }

            this.out.write(_endl);
            this.writeSpaces();
            if (c != 47) {
               ++this._depth;
            }
         }

         if (c != 33) {
            this.out.write(60);
            this.out.write(c);
         }
      } else if (c == 62) {
         if (this._lastChar == 47) {
            --this._depth;
         }

         if (this._lastChar2 == 60 && this._lastChar == 33) {
            this.out.write("<!");
         } else if (this._lastChar3 == 60 && this._lastChar2 == 33 && this._lastChar == 45) {
            this.out.write("<!-");
         }

         this.out.write(62);
      } else if (c != 60) {
         if (this._lastChar3 == 60 && this._lastChar2 == 33 && this._lastChar == 45) {
            if (c == 45) {
               this.out.write(_endl);
               this.writeSpaces();
               this.out.write("<!--");
            } else {
               this.out.write("<!-");
               this.out.write(c);
            }
         } else if (this._lastChar2 != 60 || this._lastChar != 33 || c != 45) {
            if (this._lastChar == 62 && this._lastChar2 != 63 && this._lastChar2 != 33) {
               this.out.write(_endl);
               this.writeSpaces();
            }

            this.out.write(c);
         }
      }

      this._lastChar3 = this._lastChar2;
      this._lastChar2 = this._lastChar;
      this._lastChar = c;
   }

   private void writeSpaces() throws IOException {
      for(int i = 0; i < this._depth; ++i) {
         this.out.write("    ");
      }

   }
}
