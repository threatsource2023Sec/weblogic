package org.antlr.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BufferedTokenStream implements TokenStream {
   protected TokenSource tokenSource;
   protected List tokens = new ArrayList(100);
   protected int lastMarker;
   protected int p = -1;
   protected int range = -1;

   public BufferedTokenStream() {
   }

   public BufferedTokenStream(TokenSource tokenSource) {
      this.tokenSource = tokenSource;
   }

   public TokenSource getTokenSource() {
      return this.tokenSource;
   }

   public int index() {
      return this.p;
   }

   public int range() {
      return this.range;
   }

   public int mark() {
      if (this.p == -1) {
         this.setup();
      }

      this.lastMarker = this.index();
      return this.lastMarker;
   }

   public void release(int marker) {
   }

   public void rewind(int marker) {
      this.seek(marker);
   }

   public void rewind() {
      this.seek(this.lastMarker);
   }

   public void reset() {
      this.p = 0;
      this.lastMarker = 0;
   }

   public void seek(int index) {
      this.p = index;
   }

   public int size() {
      return this.tokens.size();
   }

   public void consume() {
      if (this.p == -1) {
         this.setup();
      }

      ++this.p;
      this.sync(this.p);
   }

   protected void sync(int i) {
      int n = i - this.tokens.size() + 1;
      if (n > 0) {
         this.fetch(n);
      }

   }

   protected void fetch(int n) {
      for(int i = 1; i <= n; ++i) {
         Token t = this.tokenSource.nextToken();
         t.setTokenIndex(this.tokens.size());
         this.tokens.add(t);
         if (t.getType() == -1) {
            break;
         }
      }

   }

   public Token get(int i) {
      if (i >= 0 && i < this.tokens.size()) {
         return (Token)this.tokens.get(i);
      } else {
         throw new NoSuchElementException("token index " + i + " out of range 0.." + (this.tokens.size() - 1));
      }
   }

   public List get(int start, int stop) {
      if (start >= 0 && stop >= 0) {
         if (this.p == -1) {
            this.setup();
         }

         List subset = new ArrayList();
         if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
         }

         for(int i = start; i <= stop; ++i) {
            Token t = (Token)this.tokens.get(i);
            if (t.getType() == -1) {
               break;
            }

            subset.add(t);
         }

         return subset;
      } else {
         return null;
      }
   }

   public int LA(int i) {
      return this.LT(i).getType();
   }

   protected Token LB(int k) {
      return this.p - k < 0 ? null : (Token)this.tokens.get(this.p - k);
   }

   public Token LT(int k) {
      if (this.p == -1) {
         this.setup();
      }

      if (k == 0) {
         return null;
      } else if (k < 0) {
         return this.LB(-k);
      } else {
         int i = this.p + k - 1;
         this.sync(i);
         if (i >= this.tokens.size()) {
            return (Token)this.tokens.get(this.tokens.size() - 1);
         } else {
            if (i > this.range) {
               this.range = i;
            }

            return (Token)this.tokens.get(i);
         }
      }
   }

   protected void setup() {
      this.sync(0);
      this.p = 0;
   }

   public void setTokenSource(TokenSource tokenSource) {
      this.tokenSource = tokenSource;
      this.tokens.clear();
      this.p = -1;
   }

   public List getTokens() {
      return this.tokens;
   }

   public List getTokens(int start, int stop) {
      return this.getTokens(start, stop, (BitSet)null);
   }

   public List getTokens(int start, int stop, BitSet types) {
      if (this.p == -1) {
         this.setup();
      }

      if (stop >= this.tokens.size()) {
         stop = this.tokens.size() - 1;
      }

      if (start < 0) {
         start = 0;
      }

      if (start > stop) {
         return null;
      } else {
         List filteredTokens = new ArrayList();

         for(int i = start; i <= stop; ++i) {
            Token t = (Token)this.tokens.get(i);
            if (types == null || types.member(t.getType())) {
               filteredTokens.add(t);
            }
         }

         if (filteredTokens.isEmpty()) {
            filteredTokens = null;
         }

         return filteredTokens;
      }
   }

   public List getTokens(int start, int stop, List types) {
      return this.getTokens(start, stop, new BitSet(types));
   }

   public List getTokens(int start, int stop, int ttype) {
      return this.getTokens(start, stop, BitSet.of(ttype));
   }

   public String getSourceName() {
      return this.tokenSource.getSourceName();
   }

   public String toString() {
      if (this.p == -1) {
         this.setup();
      }

      this.fill();
      return this.toString(0, this.tokens.size() - 1);
   }

   public String toString(int start, int stop) {
      if (start >= 0 && stop >= 0) {
         if (this.p == -1) {
            this.setup();
         }

         if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
         }

         StringBuilder buf = new StringBuilder();

         for(int i = start; i <= stop; ++i) {
            Token t = (Token)this.tokens.get(i);
            if (t.getType() == -1) {
               break;
            }

            buf.append(t.getText());
         }

         return buf.toString();
      } else {
         return null;
      }
   }

   public String toString(Token start, Token stop) {
      return start != null && stop != null ? this.toString(start.getTokenIndex(), stop.getTokenIndex()) : null;
   }

   public void fill() {
      if (this.p == -1) {
         this.setup();
      }

      if (((Token)this.tokens.get(this.p)).getType() != -1) {
         int i = this.p + 1;
         this.sync(i);

         while(((Token)this.tokens.get(i)).getType() != -1) {
            ++i;
            this.sync(i);
         }

      }
   }
}
