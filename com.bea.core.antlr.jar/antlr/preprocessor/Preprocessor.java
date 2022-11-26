package antlr.preprocessor;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.IndexedVector;

public class Preprocessor extends LLkParser implements PreprocessorTokenTypes {
   private antlr.Tool antlrTool;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "HEADER_ACTION", "SUBRULE_BLOCK", "ACTION", "\"class\"", "ID", "\"extends\"", "SEMI", "TOKENS_SPEC", "OPTIONS_START", "ASSIGN_RHS", "RCURLY", "\"protected\"", "\"private\"", "\"public\"", "BANG", "ARG_ACTION", "\"returns\"", "RULE_BLOCK", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "ALT", "ELEMENT", "LPAREN", "RPAREN", "ID_OR_KEYWORD", "CURLY_BLOCK_SCARF", "WS", "NEWLINE", "COMMENT", "SL_COMMENT", "ML_COMMENT", "CHAR_LITERAL", "STRING_LITERAL", "ESC", "DIGIT", "XDIGIT"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());

   public void setTool(antlr.Tool var1) {
      if (this.antlrTool == null) {
         this.antlrTool = var1;
      } else {
         throw new IllegalStateException("antlr.Tool already registered");
      }
   }

   protected antlr.Tool getTool() {
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

   protected Preprocessor(TokenBuffer var1, int var2) {
      super(var1, var2);
      this.tokenNames = _tokenNames;
   }

   public Preprocessor(TokenBuffer var1) {
      this((TokenBuffer)var1, 1);
   }

   protected Preprocessor(TokenStream var1, int var2) {
      super(var1, var2);
      this.tokenNames = _tokenNames;
   }

   public Preprocessor(TokenStream var1) {
      this((TokenStream)var1, 1);
   }

   public Preprocessor(ParserSharedInputState var1) {
      super((ParserSharedInputState)var1, 1);
      this.tokenNames = _tokenNames;
   }

   public final void grammarFile(Hierarchy var1, String var2) throws RecognitionException, TokenStreamException {
      Token var3 = null;
      IndexedVector var5 = null;

      try {
         while(this.LA(1) == 5) {
            var3 = this.LT(1);
            this.match(5);
            var1.getFile(var2).addHeaderAction(var3.getText());
         }

         switch (this.LA(1)) {
            case 1:
            case 7:
            case 8:
               break;
            case 13:
               var5 = this.optionSpec((Grammar)null);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         while(this.LA(1) == 7 || this.LA(1) == 8) {
            Grammar var4 = this.class_def(var2, var1);
            if (var4 != null && var5 != null) {
               var1.getFile(var2).setOptions(var5);
            }

            if (var4 != null) {
               var4.setFileName(var2);
               var1.addGrammar(var4);
            }
         }

         this.match(1);
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.consume();
         this.consumeUntil(_tokenSet_0);
      }

   }

   public final IndexedVector optionSpec(Grammar var1) throws RecognitionException, TokenStreamException {
      Token var3 = null;
      Token var4 = null;
      IndexedVector var2 = new IndexedVector();

      try {
         this.match(13);

         while(true) {
            while(this.LA(1) == 9) {
               var3 = this.LT(1);
               this.match(9);
               var4 = this.LT(1);
               this.match(14);
               Option var5 = new Option(var3.getText(), var4.getText(), var1);
               var2.appendElement(var5.getName(), var5);
               if (var1 != null && var3.getText().equals("importVocab")) {
                  var1.specifiedVocabulary = true;
                  var1.importVocab = var4.getText();
               } else if (var1 != null && var3.getText().equals("exportVocab")) {
                  var1.exportVocab = var4.getText().substring(0, var4.getText().length() - 1);
                  var1.exportVocab = var1.exportVocab.trim();
               }
            }

            this.match(15);
            break;
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.consume();
         this.consumeUntil(_tokenSet_1);
      }

      return var2;
   }

   public final Grammar class_def(String var1, Hierarchy var2) throws RecognitionException, TokenStreamException {
      Token var4 = null;
      Token var5 = null;
      Token var6 = null;
      Token var7 = null;
      Token var8 = null;
      Grammar var3 = null;
      IndexedVector var9 = new IndexedVector(100);
      IndexedVector var10 = null;
      String var11 = null;

      try {
         switch (this.LA(1)) {
            case 7:
               var4 = this.LT(1);
               this.match(7);
            case 8:
               this.match(8);
               var5 = this.LT(1);
               this.match(9);
               this.match(10);
               var6 = this.LT(1);
               this.match(9);
               switch (this.LA(1)) {
                  case 6:
                     var11 = this.superClass();
                  case 11:
                     this.match(11);
                     var3 = var2.getGrammar(var5.getText());
                     if (var3 != null) {
                        var3 = null;
                        throw new SemanticException("redefinition of grammar " + var5.getText(), var1, var5.getLine(), var5.getColumn());
                     } else {
                        var3 = new Grammar(var2.getTool(), var5.getText(), var6.getText(), var9);
                        var3.superClass = var11;
                        if (var4 != null) {
                           var3.setPreambleAction(var4.getText());
                        }

                        switch (this.LA(1)) {
                           case 8:
                           case 10:
                           case 11:
                           case 14:
                           case 15:
                           default:
                              throw new NoViableAltException(this.LT(1), this.getFilename());
                           case 13:
                              var10 = this.optionSpec(var3);
                           case 7:
                           case 9:
                           case 12:
                           case 16:
                           case 17:
                           case 18:
                              if (var3 != null) {
                                 var3.setOptions(var10);
                              }

                              switch (this.LA(1)) {
                                 case 8:
                                 case 10:
                                 case 11:
                                 case 13:
                                 case 14:
                                 case 15:
                                 default:
                                    throw new NoViableAltException(this.LT(1), this.getFilename());
                                 case 12:
                                    var7 = this.LT(1);
                                    this.match(12);
                                    var3.setTokenSection(var7.getText());
                                 case 7:
                                 case 9:
                                 case 16:
                                 case 17:
                                 case 18:
                                    switch (this.LA(1)) {
                                       case 7:
                                          var8 = this.LT(1);
                                          this.match(7);
                                          var3.setMemberAction(var8.getText());
                                       case 9:
                                       case 16:
                                       case 17:
                                       case 18:
                                          int var12;
                                          for(var12 = 0; _tokenSet_2.member(this.LA(1)); ++var12) {
                                             this.rule(var3);
                                          }

                                          if (var12 < 1) {
                                             throw new NoViableAltException(this.LT(1), this.getFilename());
                                          }

                                          return var3;
                                       case 8:
                                       case 10:
                                       case 11:
                                       case 12:
                                       case 13:
                                       case 14:
                                       case 15:
                                       default:
                                          throw new NoViableAltException(this.LT(1), this.getFilename());
                                    }
                              }
                        }
                     }
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.consume();
         this.consumeUntil(_tokenSet_3);
         return var3;
      }
   }

   public final String superClass() throws RecognitionException, TokenStreamException {
      String var1 = this.LT(1).getText();

      try {
         this.match(6);
      } catch (RecognitionException var3) {
         this.reportError(var3);
         this.consume();
         this.consumeUntil(_tokenSet_4);
      }

      return var1;
   }

   public final void rule(Grammar var1) throws RecognitionException, TokenStreamException {
      Token var2 = null;
      Token var3 = null;
      Token var4 = null;
      Token var5 = null;
      Token var6 = null;
      IndexedVector var7 = null;
      String var8 = null;
      boolean var9 = false;
      String var10 = null;
      String var11 = "";

      try {
         switch (this.LA(1)) {
            case 9:
               break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 16:
               this.match(16);
               var8 = "protected";
               break;
            case 17:
               this.match(17);
               var8 = "private";
               break;
            case 18:
               this.match(18);
               var8 = "public";
         }

         var2 = this.LT(1);
         this.match(9);
         switch (this.LA(1)) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 19:
               this.match(19);
               var9 = true;
            case 7:
            case 13:
            case 20:
            case 21:
            case 22:
            case 23:
               switch (this.LA(1)) {
                  case 8:
                  case 9:
                  case 10:
                  case 11:
                  case 12:
                  case 14:
                  case 15:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
                  case 20:
                     var3 = this.LT(1);
                     this.match(20);
                  case 7:
                  case 13:
                  case 21:
                  case 22:
                  case 23:
                     switch (this.LA(1)) {
                        case 21:
                           this.match(21);
                           var4 = this.LT(1);
                           this.match(20);
                        case 7:
                        case 13:
                        case 22:
                        case 23:
                           switch (this.LA(1)) {
                              case 23:
                                 var11 = this.throwsSpec();
                              case 7:
                              case 13:
                              case 22:
                                 switch (this.LA(1)) {
                                    case 13:
                                       var7 = this.optionSpec((Grammar)null);
                                    case 7:
                                    case 22:
                                       switch (this.LA(1)) {
                                          case 7:
                                             var5 = this.LT(1);
                                             this.match(7);
                                          case 22:
                                             var6 = this.LT(1);
                                             this.match(22);
                                             var10 = this.exceptionGroup();
                                             String var12 = var6.getText() + var10;
                                             Rule var13 = new Rule(var2.getText(), var12, var7, var1);
                                             var13.setThrowsSpec(var11);
                                             if (var3 != null) {
                                                var13.setArgs(var3.getText());
                                             }

                                             if (var4 != null) {
                                                var13.setReturnValue(var4.getText());
                                             }

                                             if (var5 != null) {
                                                var13.setInitAction(var5.getText());
                                             }

                                             if (var9) {
                                                var13.setBang();
                                             }

                                             var13.setVisibility(var8);
                                             if (var1 != null) {
                                                var1.addRule(var13);
                                             }

                                             return;
                                          default:
                                             throw new NoViableAltException(this.LT(1), this.getFilename());
                                       }
                                    default:
                                       throw new NoViableAltException(this.LT(1), this.getFilename());
                                 }
                              default:
                                 throw new NoViableAltException(this.LT(1), this.getFilename());
                           }
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }
               }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         this.consume();
         this.consumeUntil(_tokenSet_5);
      }
   }

   public final String throwsSpec() throws RecognitionException, TokenStreamException {
      Token var2 = null;
      Token var3 = null;
      String var1 = "throws ";

      try {
         this.match(23);
         var2 = this.LT(1);
         this.match(9);

         for(var1 = var1 + var2.getText(); this.LA(1) == 24; var1 = var1 + "," + var3.getText()) {
            this.match(24);
            var3 = this.LT(1);
            this.match(9);
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.consume();
         this.consumeUntil(_tokenSet_6);
      }

      return var1;
   }

   public final String exceptionGroup() throws RecognitionException, TokenStreamException {
      String var2 = null;
      String var1 = "";

      try {
         while(this.LA(1) == 25) {
            var2 = this.exceptionSpec();
            var1 = var1 + var2;
         }
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.consume();
         this.consumeUntil(_tokenSet_5);
      }

      return var1;
   }

   public final String exceptionSpec() throws RecognitionException, TokenStreamException {
      Token var2 = null;
      String var3 = null;
      String var1 = System.getProperty("line.separator") + "exception ";

      try {
         this.match(25);
         switch (this.LA(1)) {
            case 1:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 18:
            case 25:
            case 26:
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 19:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 20:
               var2 = this.LT(1);
               this.match(20);
               var1 = var1 + var2.getText();
         }

         while(this.LA(1) == 26) {
            var3 = this.exceptionHandler();
            var1 = var1 + var3;
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.consume();
         this.consumeUntil(_tokenSet_7);
      }

      return var1;
   }

   public final String exceptionHandler() throws RecognitionException, TokenStreamException {
      Token var2 = null;
      Token var3 = null;
      String var1 = null;

      try {
         this.match(26);
         var2 = this.LT(1);
         this.match(20);
         var3 = this.LT(1);
         this.match(7);
         var1 = System.getProperty("line.separator") + "catch " + var2.getText() + " " + var3.getText();
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.consume();
         this.consumeUntil(_tokenSet_8);
      }

      return var1;
   }

   private static final long[] mk_tokenSet_0() {
      long[] var0 = new long[]{2L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_1() {
      long[] var0 = new long[]{4658050L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_2() {
      long[] var0 = new long[]{459264L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_3() {
      long[] var0 = new long[]{386L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_4() {
      long[] var0 = new long[]{2048L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_5() {
      long[] var0 = new long[]{459650L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_6() {
      long[] var0 = new long[]{4202624L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_7() {
      long[] var0 = new long[]{34014082L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_8() {
      long[] var0 = new long[]{101122946L, 0L};
      return var0;
   }
}
