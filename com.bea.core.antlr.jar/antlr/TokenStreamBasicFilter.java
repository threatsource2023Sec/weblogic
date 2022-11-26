package antlr;

import antlr.ASdebug.ASDebugStream;
import antlr.ASdebug.IASDebugStream;
import antlr.ASdebug.TokenOffsetInfo;
import antlr.collections.impl.BitSet;

public class TokenStreamBasicFilter implements TokenStream, IASDebugStream {
   protected BitSet discardMask;
   protected TokenStream input;

   public TokenStreamBasicFilter(TokenStream var1) {
      this.input = var1;
      this.discardMask = new BitSet();
   }

   public void discard(int var1) {
      this.discardMask.add(var1);
   }

   public void discard(BitSet var1) {
      this.discardMask = var1;
   }

   public Token nextToken() throws TokenStreamException {
      Token var1;
      for(var1 = this.input.nextToken(); var1 != null && this.discardMask.member(var1.getType()); var1 = this.input.nextToken()) {
      }

      return var1;
   }

   public String getEntireText() {
      return ASDebugStream.getEntireText(this.input);
   }

   public TokenOffsetInfo getOffsetInfo(Token var1) {
      return ASDebugStream.getOffsetInfo(this.input, var1);
   }
}
