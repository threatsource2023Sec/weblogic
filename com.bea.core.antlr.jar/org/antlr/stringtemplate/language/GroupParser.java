package org.antlr.stringtemplate.language;

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
import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class GroupParser extends LLkParser implements GroupParserTokenTypes {
   protected StringTemplateGroup group;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"group\"", "ID", "COLON", "\"implements\"", "COMMA", "SEMI", "AT", "DOT", "LPAREN", "RPAREN", "DEFINED_TO_BE", "STRING", "BIGSTRING", "ASSIGN", "ANONYMOUS_TEMPLATE", "LBRACK", "RBRACK", "\"default\"", "STAR", "PLUS", "OPTIONAL", "SL_COMMENT", "ML_COMMENT", "WS"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());

   public void reportError(RecognitionException e) {
      if (this.group != null) {
         this.group.error("template group parse error", e);
      } else {
         System.err.println("template group parse error: " + e);
         e.printStackTrace(System.err);
      }

   }

   protected GroupParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public GroupParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 3);
   }

   protected GroupParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public GroupParser(TokenStream lexer) {
      this((TokenStream)lexer, 3);
   }

   public GroupParser(ParserSharedInputState state) {
      super((ParserSharedInputState)state, 3);
      this.tokenNames = _tokenNames;
   }

   public final void group(StringTemplateGroup g) throws RecognitionException, TokenStreamException {
      Token name = null;
      Token s = null;
      Token i = null;
      Token i2 = null;
      this.group = g;

      try {
         this.match(4);
         name = this.LT(1);
         this.match(5);
         g.setName(name.getText());
         switch (this.LA(1)) {
            case 6:
               this.match(6);
               s = this.LT(1);
               this.match(5);
               g.setSuperGroup(s.getText());
            case 7:
            case 9:
               break;
            case 8:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         switch (this.LA(1)) {
            case 7:
               this.match(7);
               i = this.LT(1);
               this.match(5);
               g.implementInterface(i.getText());

               while(this.LA(1) == 8) {
                  this.match(8);
                  i2 = this.LT(1);
                  this.match(5);
                  g.implementInterface(i2.getText());
               }
            case 9:
               this.match(9);
               int _cnt7 = 0;

               while(true) {
                  if ((this.LA(1) == 5 || this.LA(1) == 10) && (this.LA(2) == 5 || this.LA(2) == 12 || this.LA(2) == 14) && (this.LA(3) == 5 || this.LA(3) == 11 || this.LA(3) == 13)) {
                     this.template(g);
                  } else {
                     if (this.LA(1) != 5 || this.LA(2) != 14 || this.LA(3) != 19) {
                        if (_cnt7 < 1) {
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                        }

                        return;
                     }

                     this.mapdef(g);
                  }

                  ++_cnt7;
               }
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(var7, _tokenSet_0);
      }
   }

   public final void template(StringTemplateGroup g) throws RecognitionException, TokenStreamException {
      Token scope = null;
      Token region = null;
      Token name = null;
      Token t = null;
      Token bt = null;
      Token alias = null;
      Token target = null;
      Map formalArgs = null;
      StringTemplate st = null;
      boolean ignore = false;
      String templateName = null;
      int line = this.LT(1).getLine();

      try {
         if ((this.LA(1) == 5 || this.LA(1) == 10) && (this.LA(2) == 5 || this.LA(2) == 12)) {
            switch (this.LA(1)) {
               case 5:
                  name = this.LT(1);
                  this.match(5);
                  templateName = name.getText();
                  if (g.isDefinedInThisGroup(templateName)) {
                     g.error("redefinition of template: " + templateName);
                     st = new StringTemplate();
                  } else {
                     st = g.defineTemplate(templateName, (String)null);
                  }
                  break;
               case 10:
                  this.match(10);
                  scope = this.LT(1);
                  this.match(5);
                  this.match(11);
                  region = this.LT(1);
                  this.match(5);
                  templateName = g.getMangledRegionName(scope.getText(), region.getText());
                  if (g.isDefinedInThisGroup(templateName)) {
                     g.error("group " + g.getName() + " line " + line + ": redefinition of template region: @" + scope.getText() + "." + region.getText());
                     st = new StringTemplate();
                  } else {
                     boolean err = false;
                     StringTemplate scopeST = g.lookupTemplate(scope.getText());
                     if (scopeST == null) {
                        g.error("group " + g.getName() + " line " + line + ": reference to region within undefined template: " + scope.getText());
                        err = true;
                     }

                     if (!scopeST.containsRegionName(region.getText())) {
                        g.error("group " + g.getName() + " line " + line + ": template " + scope.getText() + " has no region called " + region.getText());
                        err = true;
                     }

                     if (err) {
                        st = new StringTemplate();
                     } else {
                        st = g.defineRegionTemplate((String)scope.getText(), region.getText(), (String)null, 3);
                     }
                  }
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            if (st != null) {
               st.setGroupFileLine(line);
            }

            this.match(12);
            switch (this.LA(1)) {
               case 5:
                  this.args(st);
                  break;
               case 13:
                  st.defineEmptyFormalArgumentList();
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            this.match(13);
            this.match(14);
            switch (this.LA(1)) {
               case 15:
                  t = this.LT(1);
                  this.match(15);
                  st.setTemplate(t.getText());
                  break;
               case 16:
                  bt = this.LT(1);
                  this.match(16);
                  st.setTemplate(bt.getText());
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         } else {
            if (this.LA(1) != 5 || this.LA(2) != 14) {
               throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            alias = this.LT(1);
            this.match(5);
            this.match(14);
            target = this.LT(1);
            this.match(5);
            g.defineTemplateAlias(alias.getText(), target.getText());
         }
      } catch (RecognitionException var16) {
         this.reportError(var16);
         this.recover(var16, _tokenSet_1);
      }

   }

   public final void mapdef(StringTemplateGroup g) throws RecognitionException, TokenStreamException {
      Token name = null;
      Map m = null;

      try {
         name = this.LT(1);
         this.match(5);
         this.match(14);
         m = this.map();
         if (g.getMap(name.getText()) != null) {
            g.error("redefinition of map: " + name.getText());
         } else if (g.isDefinedInThisGroup(name.getText())) {
            g.error("redefinition of template as map: " + name.getText());
         } else {
            g.defineMap(name.getText(), m);
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_1);
      }

   }

   public final void args(StringTemplate st) throws RecognitionException, TokenStreamException {
      try {
         this.arg(st);

         while(this.LA(1) == 8) {
            this.match(8);
            this.arg(st);
         }
      } catch (RecognitionException var3) {
         this.reportError(var3);
         this.recover(var3, _tokenSet_2);
      }

   }

   public final void arg(StringTemplate st) throws RecognitionException, TokenStreamException {
      Token name = null;
      Token s = null;
      Token bs = null;
      StringTemplate defaultValue = null;

      try {
         name = this.LT(1);
         this.match(5);
         if (this.LA(1) == 17 && this.LA(2) == 15) {
            this.match(17);
            s = this.LT(1);
            this.match(15);
            defaultValue = new StringTemplate("$_val_$");
            defaultValue.setAttribute("_val_", (Object)s.getText());
            defaultValue.defineFormalArgument("_val_");
            defaultValue.setName("<" + st.getName() + "'s arg " + name.getText() + " default value subtemplate>");
         } else if (this.LA(1) == 17 && this.LA(2) == 18) {
            this.match(17);
            bs = this.LT(1);
            this.match(18);
            defaultValue = new StringTemplate(st.getGroup(), bs.getText());
            defaultValue.setName("<" + st.getName() + "'s arg " + name.getText() + " default value subtemplate>");
         } else if (this.LA(1) != 8 && this.LA(1) != 13) {
            throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         st.defineFormalArgument(name.getText(), defaultValue);
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(var7, _tokenSet_3);
      }

   }

   public final Map map() throws RecognitionException, TokenStreamException {
      Map mapping = new HashMap();

      try {
         this.match(19);
         this.mapPairs(mapping);
         this.match(20);
      } catch (RecognitionException var3) {
         this.reportError(var3);
         this.recover(var3, _tokenSet_1);
      }

      return mapping;
   }

   public final void mapPairs(Map mapping) throws RecognitionException, TokenStreamException {
      try {
         switch (this.LA(1)) {
            case 15:
               this.keyValuePair(mapping);

               while(this.LA(1) == 8 && this.LA(2) == 15) {
                  this.match(8);
                  this.keyValuePair(mapping);
               }

               switch (this.LA(1)) {
                  case 8:
                     this.match(8);
                     this.defaultValuePair(mapping);
                     return;
                  case 20:
                     return;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 21:
               this.defaultValuePair(mapping);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var3) {
         this.reportError(var3);
         this.recover(var3, _tokenSet_4);
      }

   }

   public final void keyValuePair(Map mapping) throws RecognitionException, TokenStreamException {
      Token key = null;
      StringTemplate v = null;

      try {
         key = this.LT(1);
         this.match(15);
         this.match(6);
         v = this.keyValue();
         mapping.put(key.getText(), v);
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_5);
      }

   }

   public final void defaultValuePair(Map mapping) throws RecognitionException, TokenStreamException {
      StringTemplate v = null;

      try {
         this.match(21);
         this.match(6);
         v = this.keyValue();
         mapping.put("_default_", v);
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.recover(var4, _tokenSet_4);
      }

   }

   public final StringTemplate keyValue() throws RecognitionException, TokenStreamException {
      StringTemplate value = null;
      Token s1 = null;
      Token s2 = null;
      Token k = null;

      try {
         switch (this.LA(1)) {
            case 5:
               k = this.LT(1);
               this.match(5);
               if (!k.getText().equals("key")) {
                  throw new SemanticException("k.getText().equals(\"key\")");
               }

               value = ASTExpr.MAP_KEY_VALUE;
               break;
            case 8:
            case 20:
               value = null;
               break;
            case 15:
               s2 = this.LT(1);
               this.match(15);
               value = new StringTemplate(this.group, s2.getText());
               break;
            case 16:
               s1 = this.LT(1);
               this.match(16);
               value = new StringTemplate(this.group, s1.getText());
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(var6, _tokenSet_5);
      }

      return value;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{1058L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{8192L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[]{8448L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[]{1048576L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[]{1048832L, 0L};
      return data;
   }
}
