package org.antlr.stringtemplate.language;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.util.LinkedHashMap;
import org.antlr.stringtemplate.StringTemplateGroupInterface;

public class InterfaceParser extends LLkParser implements InterfaceParserTokenTypes {
   protected StringTemplateGroupInterface groupI;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"interface\"", "ID", "SEMI", "\"optional\"", "LPAREN", "RPAREN", "COMMA", "COLON", "SL_COMMENT", "ML_COMMENT", "WS"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());

   public void reportError(RecognitionException e) {
      if (this.groupI != null) {
         this.groupI.error("template group interface parse error", e);
      } else {
         System.err.println("template group interface parse error: " + e);
         e.printStackTrace(System.err);
      }

   }

   protected InterfaceParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public InterfaceParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 3);
   }

   protected InterfaceParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public InterfaceParser(TokenStream lexer) {
      this((TokenStream)lexer, 3);
   }

   public InterfaceParser(ParserSharedInputState state) {
      super((ParserSharedInputState)state, 3);
      this.tokenNames = _tokenNames;
   }

   public final void groupInterface(StringTemplateGroupInterface groupI) throws RecognitionException, TokenStreamException {
      Token name = null;
      this.groupI = groupI;

      try {
         this.match(4);
         name = this.LT(1);
         this.match(5);
         groupI.setName(name.getText());
         this.match(6);

         int _cnt3;
         for(_cnt3 = 0; this.LA(1) == 5 || this.LA(1) == 7; ++_cnt3) {
            this.template(groupI);
         }

         if (_cnt3 < 1) {
            throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.recover(var4, _tokenSet_0);
      }

   }

   public final void template(StringTemplateGroupInterface groupI) throws RecognitionException, TokenStreamException {
      Token opt = null;
      Token name = null;
      LinkedHashMap formalArgs = new LinkedHashMap();
      String templateName = null;

      try {
         switch (this.LA(1)) {
            case 7:
               opt = this.LT(1);
               this.match(7);
            case 5:
               name = this.LT(1);
               this.match(5);
               this.match(8);
               switch (this.LA(1)) {
                  case 5:
                     formalArgs = this.args();
                  case 9:
                     this.match(9);
                     this.match(6);
                     templateName = name.getText();
                     groupI.defineTemplate(templateName, formalArgs, opt != null);
                     return;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(var7, _tokenSet_1);
      }
   }

   public final LinkedHashMap args() throws RecognitionException, TokenStreamException {
      LinkedHashMap args = new LinkedHashMap();
      Token a = null;
      Token b = null;

      try {
         a = this.LT(1);
         this.match(5);
         args.put(a.getText(), new FormalArgument(a.getText()));

         while(this.LA(1) == 10) {
            this.match(10);
            b = this.LT(1);
            this.match(5);
            args.put(b.getText(), new FormalArgument(b.getText()));
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_2);
      }

      return args;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{162L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{512L, 0L};
      return data;
   }
}
