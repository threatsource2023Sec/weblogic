package antlr;

import antlr.collections.impl.BitSet;

public class ANTLRTokdefParser extends LLkParser implements ANTLRTokdefParserTokenTypes {
   private Tool antlrTool;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "ID", "STRING", "ASSIGN", "LPAREN", "RPAREN", "INT", "WS", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());

   public void setTool(Tool var1) {
      if (this.antlrTool == null) {
         this.antlrTool = var1;
      } else {
         throw new IllegalStateException("antlr.Tool already registered");
      }
   }

   protected Tool getTool() {
      return this.antlrTool;
   }

   public void reportError(String var1) {
      if (this.getTool() != null) {
         this.getTool().error(var1, this.getFilename(), -1, -1);
      } else {
         super.reportError(var1);
      }

   }

   public void reportError(RecognitionException var1) {
      if (this.getTool() != null) {
         this.getTool().error(var1.getErrorMessage(), var1.getFilename(), var1.getLine(), var1.getColumn());
      } else {
         super.reportError(var1);
      }

   }

   public void reportWarning(String var1) {
      if (this.getTool() != null) {
         this.getTool().warning((String)var1, this.getFilename(), -1, -1);
      } else {
         super.reportWarning(var1);
      }

   }

   protected ANTLRTokdefParser(TokenBuffer var1, int var2) {
      super(var1, var2);
      this.tokenNames = _tokenNames;
   }

   public ANTLRTokdefParser(TokenBuffer var1) {
      this((TokenBuffer)var1, 3);
   }

   protected ANTLRTokdefParser(TokenStream var1, int var2) {
      super(var1, var2);
      this.tokenNames = _tokenNames;
   }

   public ANTLRTokdefParser(TokenStream var1) {
      this((TokenStream)var1, 3);
   }

   public ANTLRTokdefParser(ParserSharedInputState var1) {
      super((ParserSharedInputState)var1, 3);
      this.tokenNames = _tokenNames;
   }

   public final void file(ImportVocabTokenManager var1) throws RecognitionException, TokenStreamException {
      Token var2 = null;

      try {
         var2 = this.LT(1);
         this.match(4);

         while(this.LA(1) == 4 || this.LA(1) == 5) {
            this.line(var1);
         }
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.consume();
         this.consumeUntil(_tokenSet_0);
      }

   }

   public final void line(ImportVocabTokenManager var1) throws RecognitionException, TokenStreamException {
      Token var2 = null;
      Token var3 = null;
      Token var4 = null;
      Token var5 = null;
      Token var6 = null;
      Token var7 = null;
      Token var8 = null;
      Token var9 = null;
      Token var10 = null;

      try {
         if (this.LA(1) == 5) {
            var2 = this.LT(1);
            this.match(5);
            var10 = var2;
         } else if (this.LA(1) == 4 && this.LA(2) == 6 && this.LA(3) == 5) {
            var3 = this.LT(1);
            this.match(4);
            var9 = var3;
            this.match(6);
            var4 = this.LT(1);
            this.match(5);
            var10 = var4;
         } else if (this.LA(1) == 4 && this.LA(2) == 7) {
            var5 = this.LT(1);
            this.match(4);
            var9 = var5;
            this.match(7);
            var6 = this.LT(1);
            this.match(5);
            this.match(8);
         } else {
            if (this.LA(1) != 4 || this.LA(2) != 6 || this.LA(3) != 9) {
               throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            var7 = this.LT(1);
            this.match(4);
            var9 = var7;
         }

         this.match(6);
         var8 = this.LT(1);
         this.match(9);
         Integer var11 = Integer.valueOf(var8.getText());
         if (var10 != null) {
            var1.define(var10.getText(), var11);
            if (var9 != null) {
               StringLiteralSymbol var12 = (StringLiteralSymbol)var1.getTokenSymbol(var10.getText());
               var12.setLabel(var9.getText());
               var1.mapToTokenSymbol(var9.getText(), var12);
            }
         } else if (var9 != null) {
            var1.define(var9.getText(), var11);
            if (var6 != null) {
               TokenSymbol var14 = var1.getTokenSymbol(var9.getText());
               var14.setParaphrase(var6.getText());
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.consume();
         this.consumeUntil(_tokenSet_1);
      }

   }

   private static final long[] mk_tokenSet_0() {
      long[] var0 = new long[]{2L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_1() {
      long[] var0 = new long[]{50L, 0L};
      return var0;
   }
}
