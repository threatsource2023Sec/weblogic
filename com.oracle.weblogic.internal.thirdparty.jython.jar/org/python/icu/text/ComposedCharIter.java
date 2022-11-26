package org.python.icu.text;

import org.python.icu.impl.Norm2AllModes;
import org.python.icu.impl.Normalizer2Impl;

/** @deprecated */
@Deprecated
public final class ComposedCharIter {
   /** @deprecated */
   @Deprecated
   public static final char DONE = '\uffff';
   private final Normalizer2Impl n2impl;
   private String decompBuf;
   private int curChar;
   private int nextChar;

   /** @deprecated */
   @Deprecated
   public ComposedCharIter() {
      this(false, 0);
   }

   /** @deprecated */
   @Deprecated
   public ComposedCharIter(boolean compat, int options) {
      this.curChar = 0;
      this.nextChar = -1;
      if (compat) {
         this.n2impl = Norm2AllModes.getNFKCInstance().impl;
      } else {
         this.n2impl = Norm2AllModes.getNFCInstance().impl;
      }

   }

   /** @deprecated */
   @Deprecated
   public boolean hasNext() {
      if (this.nextChar == -1) {
         this.findNextChar();
      }

      return this.nextChar != -1;
   }

   /** @deprecated */
   @Deprecated
   public char next() {
      if (this.nextChar == -1) {
         this.findNextChar();
      }

      this.curChar = this.nextChar;
      this.nextChar = -1;
      return (char)this.curChar;
   }

   /** @deprecated */
   @Deprecated
   public String decomposition() {
      return this.decompBuf != null ? this.decompBuf : "";
   }

   private void findNextChar() {
      int c = this.curChar + 1;
      this.decompBuf = null;

      while(true) {
         if (c >= 65535) {
            c = -1;
            break;
         }

         this.decompBuf = this.n2impl.getDecomposition(c);
         if (this.decompBuf != null) {
            break;
         }

         ++c;
      }

      this.nextChar = c;
   }
}
