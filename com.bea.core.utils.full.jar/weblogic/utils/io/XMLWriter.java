package weblogic.utils.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public final class XMLWriter extends PrintWriter {
   private int space = 2;
   private int indent = 0;
   private boolean needIndent = false;

   public XMLWriter(OutputStream o) throws UnsupportedEncodingException {
      super(new BufferedWriter(new OutputStreamWriter(o, "UTF8")));
   }

   public XMLWriter(OutputStream o, String encoding) throws UnsupportedEncodingException {
      super(new BufferedWriter(new OutputStreamWriter(o, encoding)));
   }

   public XMLWriter(Writer o) {
      super(o);
   }

   public int getIndent() {
      return this.indent;
   }

   public void setIndent(int i) {
      if (i < 0) {
         throw new IllegalArgumentException();
      } else {
         this.indent = i;
      }
   }

   public int getSpace() {
      return this.space;
   }

   public void setSpace(int i) {
      if (i < 0) {
         throw new IllegalArgumentException();
      } else {
         this.space = i;
      }
   }

   public void incrIndent() {
      this.indent += this.space;
   }

   public void decrIndent() {
      this.indent -= this.space;
      if (this.indent < 0) {
         this.indent = 0;
      }

   }

   private void writeIndent() {
      for(int i = 0; i < this.indent; ++i) {
         super.write(32);
      }

   }

   public final void printNoIndent(String s) {
      try {
         this.out.write(s);
      } catch (Exception var3) {
      }

   }

   public final void print(String s) {
      if (s != null) {
         int len = s.length();
         if (len != 0) {
            if (this.needIndent) {
               this.writeIndent();
            }

            char c = 0;
            boolean isCDATA = s.indexOf("<![CDATA[") > -1;

            for(int i = 0; i < len; ++i) {
               c = s.charAt(i);
               if (!isCDATA && c == '&') {
                  this.write(38);
                  this.write(97);
                  this.write(109);
                  this.write(112);
                  this.write(59);
               } else {
                  this.write(c);
               }

               if (c == '\n') {
                  this.writeIndent();
               }
            }

            if (c != '\n') {
               this.needIndent = true;
            }

         }
      }
   }

   public final void println(String s) {
      this.print(s);
      this.println();
      this.needIndent = true;
   }
}
