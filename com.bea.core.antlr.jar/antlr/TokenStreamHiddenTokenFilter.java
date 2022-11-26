package antlr;

import antlr.collections.impl.BitSet;

public class TokenStreamHiddenTokenFilter extends TokenStreamBasicFilter implements TokenStream {
   protected BitSet hideMask = new BitSet();
   protected CommonHiddenStreamToken nextMonitoredToken;
   protected CommonHiddenStreamToken lastHiddenToken;
   protected CommonHiddenStreamToken firstHidden = null;

   public TokenStreamHiddenTokenFilter(TokenStream var1) {
      super(var1);
   }

   protected void consume() throws TokenStreamException {
      this.nextMonitoredToken = (CommonHiddenStreamToken)this.input.nextToken();
   }

   private void consumeFirst() throws TokenStreamException {
      this.consume();

      for(CommonHiddenStreamToken var1 = null; this.hideMask.member(this.LA(1).getType()) || this.discardMask.member(this.LA(1).getType()); this.consume()) {
         if (this.hideMask.member(this.LA(1).getType())) {
            if (var1 == null) {
               var1 = this.LA(1);
            } else {
               var1.setHiddenAfter(this.LA(1));
               this.LA(1).setHiddenBefore(var1);
               var1 = this.LA(1);
            }

            this.lastHiddenToken = var1;
            if (this.firstHidden == null) {
               this.firstHidden = var1;
            }
         }
      }

   }

   public BitSet getDiscardMask() {
      return this.discardMask;
   }

   public CommonHiddenStreamToken getHiddenAfter(CommonHiddenStreamToken var1) {
      return var1.getHiddenAfter();
   }

   public CommonHiddenStreamToken getHiddenBefore(CommonHiddenStreamToken var1) {
      return var1.getHiddenBefore();
   }

   public BitSet getHideMask() {
      return this.hideMask;
   }

   public CommonHiddenStreamToken getInitialHiddenToken() {
      return this.firstHidden;
   }

   public void hide(int var1) {
      this.hideMask.add(var1);
   }

   public void hide(BitSet var1) {
      this.hideMask = var1;
   }

   protected CommonHiddenStreamToken LA(int var1) {
      return this.nextMonitoredToken;
   }

   public Token nextToken() throws TokenStreamException {
      if (this.LA(1) == null) {
         this.consumeFirst();
      }

      CommonHiddenStreamToken var1 = this.LA(1);
      var1.setHiddenBefore(this.lastHiddenToken);
      this.lastHiddenToken = null;
      this.consume();

      for(CommonHiddenStreamToken var2 = var1; this.hideMask.member(this.LA(1).getType()) || this.discardMask.member(this.LA(1).getType()); this.consume()) {
         if (this.hideMask.member(this.LA(1).getType())) {
            var2.setHiddenAfter(this.LA(1));
            if (var2 != var1) {
               this.LA(1).setHiddenBefore(var2);
            }

            var2 = this.lastHiddenToken = this.LA(1);
         }
      }

      return var1;
   }
}
