package org.stringtemplate.v4;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AutoIndentWriter implements STWriter {
   public List indents;
   public int[] anchors;
   public int anchors_sp;
   public String newline;
   public Writer out;
   public boolean atStartOfLine;
   public int charPosition;
   public int charIndex;
   public int lineWidth;

   public AutoIndentWriter(Writer out, String newline) {
      this.indents = new ArrayList();
      this.anchors = new int[10];
      this.anchors_sp = -1;
      this.out = null;
      this.atStartOfLine = true;
      this.charPosition = 0;
      this.charIndex = 0;
      this.lineWidth = -1;
      this.out = out;
      this.indents.add((Object)null);
      this.newline = newline;
   }

   public AutoIndentWriter(Writer out) {
      this(out, System.getProperty("line.separator"));
   }

   public void setLineWidth(int lineWidth) {
      this.lineWidth = lineWidth;
   }

   public void pushIndentation(String indent) {
      this.indents.add(indent);
   }

   public String popIndentation() {
      return (String)this.indents.remove(this.indents.size() - 1);
   }

   public void pushAnchorPoint() {
      if (this.anchors_sp + 1 >= this.anchors.length) {
         int[] a = new int[this.anchors.length * 2];
         System.arraycopy(this.anchors, 0, a, 0, this.anchors.length - 1);
         this.anchors = a;
      }

      ++this.anchors_sp;
      this.anchors[this.anchors_sp] = this.charPosition;
   }

   public void popAnchorPoint() {
      --this.anchors_sp;
   }

   public int index() {
      return this.charIndex;
   }

   public int write(String str) throws IOException {
      int n = 0;
      int nll = this.newline.length();
      int sl = str.length();

      for(int i = 0; i < sl; ++i) {
         char c = str.charAt(i);
         if (c != '\r') {
            if (c == '\n') {
               this.atStartOfLine = true;
               this.charPosition = -nll;
               this.out.write(this.newline);
               n += nll;
               this.charIndex += nll;
               this.charPosition += n;
            } else {
               if (this.atStartOfLine) {
                  n += this.indent();
                  this.atStartOfLine = false;
               }

               ++n;
               this.out.write(c);
               ++this.charPosition;
               ++this.charIndex;
            }
         }
      }

      return n;
   }

   public int writeSeparator(String str) throws IOException {
      return this.write(str);
   }

   public int write(String str, String wrap) throws IOException {
      int n = this.writeWrap(wrap);
      return n + this.write(str);
   }

   public int writeWrap(String wrap) throws IOException {
      int n = 0;
      if (this.lineWidth != -1 && wrap != null && !this.atStartOfLine && this.charPosition >= this.lineWidth) {
         for(int i = 0; i < wrap.length(); ++i) {
            char c = wrap.charAt(i);
            if (c != '\r') {
               if (c == '\n') {
                  this.out.write(this.newline);
                  n += this.newline.length();
                  this.charPosition = 0;
                  this.charIndex += this.newline.length();
                  n += this.indent();
               } else {
                  ++n;
                  this.out.write(c);
                  ++this.charPosition;
                  ++this.charIndex;
               }
            }
         }
      }

      return n;
   }

   public int indent() throws IOException {
      int n = 0;
      Iterator var2 = this.indents.iterator();

      while(var2.hasNext()) {
         String ind = (String)var2.next();
         if (ind != null) {
            n += ind.length();
            this.out.write(ind);
         }
      }

      if (this.anchors_sp >= 0 && this.anchors[this.anchors_sp] > n) {
         int remainder = this.anchors[this.anchors_sp] - n;

         for(int i = 1; i <= remainder; ++i) {
            this.out.write(32);
         }

         n += remainder;
      }

      this.charPosition += n;
      this.charIndex += n;
      return n;
   }
}
