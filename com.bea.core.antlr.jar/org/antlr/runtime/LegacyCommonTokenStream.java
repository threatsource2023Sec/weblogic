package org.antlr.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LegacyCommonTokenStream implements TokenStream {
   protected TokenSource tokenSource;
   protected List tokens;
   protected Map channelOverrideMap;
   protected Set discardSet;
   protected int channel;
   protected boolean discardOffChannelTokens;
   protected int lastMarker;
   protected int range;
   protected int p;

   public LegacyCommonTokenStream() {
      this.channel = 0;
      this.discardOffChannelTokens = false;
      this.range = -1;
      this.p = -1;
      this.tokens = new ArrayList(500);
   }

   public LegacyCommonTokenStream(TokenSource tokenSource) {
      this();
      this.tokenSource = tokenSource;
   }

   public LegacyCommonTokenStream(TokenSource tokenSource, int channel) {
      this(tokenSource);
      this.channel = channel;
   }

   public void setTokenSource(TokenSource tokenSource) {
      this.tokenSource = tokenSource;
      this.tokens.clear();
      this.p = -1;
      this.channel = 0;
   }

   protected void fillBuffer() {
      int index = 0;

      for(Token t = this.tokenSource.nextToken(); t != null && t.getType() != -1; t = this.tokenSource.nextToken()) {
         boolean discard = false;
         if (this.channelOverrideMap != null) {
            Integer channelI = (Integer)this.channelOverrideMap.get(t.getType());
            if (channelI != null) {
               t.setChannel(channelI);
            }
         }

         if (this.discardSet != null && this.discardSet.contains(new Integer(t.getType()))) {
            discard = true;
         } else if (this.discardOffChannelTokens && t.getChannel() != this.channel) {
            discard = true;
         }

         if (!discard) {
            t.setTokenIndex(index);
            this.tokens.add(t);
            ++index;
         }
      }

      this.p = 0;
      this.p = this.skipOffTokenChannels(this.p);
   }

   public void consume() {
      if (this.p < this.tokens.size()) {
         ++this.p;
         this.p = this.skipOffTokenChannels(this.p);
      }

   }

   protected int skipOffTokenChannels(int i) {
      for(int n = this.tokens.size(); i < n && ((Token)this.tokens.get(i)).getChannel() != this.channel; ++i) {
      }

      return i;
   }

   protected int skipOffTokenChannelsReverse(int i) {
      while(i >= 0 && ((Token)this.tokens.get(i)).getChannel() != this.channel) {
         --i;
      }

      return i;
   }

   public void setTokenTypeChannel(int ttype, int channel) {
      if (this.channelOverrideMap == null) {
         this.channelOverrideMap = new HashMap();
      }

      this.channelOverrideMap.put(ttype, channel);
   }

   public void discardTokenType(int ttype) {
      if (this.discardSet == null) {
         this.discardSet = new HashSet();
      }

      this.discardSet.add(ttype);
   }

   public void discardOffChannelTokens(boolean discardOffChannelTokens) {
      this.discardOffChannelTokens = discardOffChannelTokens;
   }

   public List getTokens() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      return this.tokens;
   }

   public List getTokens(int start, int stop) {
      return this.getTokens(start, stop, (BitSet)null);
   }

   public List getTokens(int start, int stop, BitSet types) {
      if (this.p == -1) {
         this.fillBuffer();
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

   public Token LT(int k) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      if (k == 0) {
         return null;
      } else if (k < 0) {
         return this.LB(-k);
      } else if (this.p + k - 1 >= this.tokens.size()) {
         return (Token)this.tokens.get(this.tokens.size() - 1);
      } else {
         int i = this.p;

         for(int n = 1; n < k; ++n) {
            i = this.skipOffTokenChannels(i + 1);
         }

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

   protected Token LB(int k) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      if (k == 0) {
         return null;
      } else if (this.p - k < 0) {
         return null;
      } else {
         int i = this.p;

         for(int n = 1; n <= k; ++n) {
            i = this.skipOffTokenChannelsReverse(i - 1);
         }

         return i < 0 ? null : (Token)this.tokens.get(i);
      }
   }

   public Token get(int i) {
      return (Token)this.tokens.get(i);
   }

   public List get(int start, int stop) {
      if (this.p == -1) {
         this.fillBuffer();
      }

      return start >= 0 && stop >= 0 ? this.tokens.subList(start, stop) : null;
   }

   public int LA(int i) {
      return this.LT(i).getType();
   }

   public int mark() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      this.lastMarker = this.index();
      return this.lastMarker;
   }

   public void release(int marker) {
   }

   public int size() {
      return this.tokens.size();
   }

   public int index() {
      return this.p;
   }

   public int range() {
      return this.range;
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

   public TokenSource getTokenSource() {
      return this.tokenSource;
   }

   public String getSourceName() {
      return this.getTokenSource().getSourceName();
   }

   public String toString() {
      if (this.p == -1) {
         this.fillBuffer();
      }

      return this.toString(0, this.tokens.size() - 1);
   }

   public String toString(int start, int stop) {
      if (start >= 0 && stop >= 0) {
         if (this.p == -1) {
            this.fillBuffer();
         }

         if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
         }

         StringBuilder buf = new StringBuilder();

         for(int i = start; i <= stop; ++i) {
            Token t = (Token)this.tokens.get(i);
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
}
