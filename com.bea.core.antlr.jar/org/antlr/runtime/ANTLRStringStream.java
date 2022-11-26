package org.antlr.runtime;

import java.util.ArrayList;
import java.util.List;

public class ANTLRStringStream implements CharStream {
   protected char[] data;
   protected int n;
   protected int p;
   protected int line;
   protected int charPositionInLine;
   protected int markDepth;
   protected List markers;
   protected int lastMarker;
   public String name;

   public ANTLRStringStream() {
      this.p = 0;
      this.line = 1;
      this.charPositionInLine = 0;
      this.markDepth = 0;
   }

   public ANTLRStringStream(String input) {
      this();
      this.data = input.toCharArray();
      this.n = input.length();
   }

   public ANTLRStringStream(char[] data, int numberOfActualCharsInArray) {
      this();
      this.data = data;
      this.n = numberOfActualCharsInArray;
   }

   public void reset() {
      this.p = 0;
      this.line = 1;
      this.charPositionInLine = 0;
      this.markDepth = 0;
   }

   public void consume() {
      if (this.p < this.n) {
         ++this.charPositionInLine;
         if (this.data[this.p] == '\n') {
            ++this.line;
            this.charPositionInLine = 0;
         }

         ++this.p;
      }

   }

   public int LA(int i) {
      if (i == 0) {
         return 0;
      } else {
         if (i < 0) {
            ++i;
            if (this.p + i - 1 < 0) {
               return -1;
            }
         }

         return this.p + i - 1 >= this.n ? -1 : this.data[this.p + i - 1];
      }
   }

   public int LT(int i) {
      return this.LA(i);
   }

   public int index() {
      return this.p;
   }

   public int size() {
      return this.n;
   }

   public int mark() {
      if (this.markers == null) {
         this.markers = new ArrayList();
         this.markers.add((Object)null);
      }

      ++this.markDepth;
      CharStreamState state;
      if (this.markDepth >= this.markers.size()) {
         state = new CharStreamState();
         this.markers.add(state);
      } else {
         state = (CharStreamState)this.markers.get(this.markDepth);
      }

      state.p = this.p;
      state.line = this.line;
      state.charPositionInLine = this.charPositionInLine;
      this.lastMarker = this.markDepth;
      return this.markDepth;
   }

   public void rewind(int m) {
      CharStreamState state = (CharStreamState)this.markers.get(m);
      this.seek(state.p);
      this.line = state.line;
      this.charPositionInLine = state.charPositionInLine;
      this.release(m);
   }

   public void rewind() {
      this.rewind(this.lastMarker);
   }

   public void release(int marker) {
      this.markDepth = marker;
      --this.markDepth;
   }

   public void seek(int index) {
      if (index <= this.p) {
         this.p = index;
      } else {
         while(this.p < index) {
            this.consume();
         }

      }
   }

   public String substring(int start, int stop) {
      return new String(this.data, start, stop - start + 1);
   }

   public int getLine() {
      return this.line;
   }

   public int getCharPositionInLine() {
      return this.charPositionInLine;
   }

   public void setLine(int line) {
      this.line = line;
   }

   public void setCharPositionInLine(int pos) {
      this.charPositionInLine = pos;
   }

   public String getSourceName() {
      return this.name;
   }

   public String toString() {
      return new String(this.data);
   }
}
