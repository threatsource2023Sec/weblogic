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
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class TemplateParser extends LLkParser implements TemplateParserTokenTypes {
   protected StringTemplate self;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "LITERAL", "NEWLINE", "ACTION", "IF", "ELSEIF", "ELSE", "ENDIF", "REGION_REF", "REGION_DEF", "EXPR", "TEMPLATE", "IF_EXPR", "ESC_CHAR", "ESC", "HEX", "SUBTEMPLATE", "NESTED_PARENS", "INDENT", "COMMENT", "LINE_BREAK"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());

   public void reportError(RecognitionException e) {
      StringTemplateGroup group = this.self.getGroup();
      if (group == StringTemplate.defaultGroup) {
         this.self.error("template parse error; template context is " + this.self.getEnclosingInstanceStackString(), e);
      } else {
         this.self.error("template parse error in group " + this.self.getGroup().getName() + " line " + this.self.getGroupFileLine() + "; template context is " + this.self.getEnclosingInstanceStackString(), e);
      }

   }

   protected TemplateParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public TemplateParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected TemplateParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public TemplateParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public TemplateParser(ParserSharedInputState state) {
      super((ParserSharedInputState)state, 1);
      this.tokenNames = _tokenNames;
   }

   public final void template(StringTemplate self) throws RecognitionException, TokenStreamException {
      Token s = null;
      Token nl = null;
      this.self = self;

      try {
         while(true) {
            switch (this.LA(1)) {
               case 4:
                  s = this.LT(1);
                  this.match(4);
                  self.addChunk(new StringRef(self, s.getText()));
                  break;
               case 5:
                  nl = this.LT(1);
                  this.match(5);
                  if (this.LA(1) != 9 && this.LA(1) != 10) {
                     self.addChunk(new NewlineRef(self, nl.getText()));
                  }
                  break;
               case 6:
               case 7:
               case 11:
               case 12:
                  this.action(self);
                  break;
               case 8:
               case 9:
               case 10:
               default:
                  return;
            }
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_0);
      }
   }

   public final void action(StringTemplate self) throws RecognitionException, TokenStreamException {
      Token a = null;
      Token i = null;
      Token ei = null;
      Token rr = null;
      Token rd = null;

      try {
         String combinedNameTemplateStr;
         String regionRef;
         StringTemplate elseIfSubtemplate;
         switch (this.LA(1)) {
            case 6:
               a = this.LT(1);
               this.match(6);
               combinedNameTemplateStr = ((ChunkToken)a).getIndentation();
               ASTExpr c = self.parseAction(a.getText());
               c.setIndentation(combinedNameTemplateStr);
               self.addChunk(c);
               break;
            case 7:
               i = this.LT(1);
               this.match(7);
               ConditionalExpr c = (ConditionalExpr)self.parseAction(i.getText());
               StringTemplate subtemplate = new StringTemplate(self.getGroup(), (String)null);
               subtemplate.setEnclosingInstance(self);
               subtemplate.setName(i.getText() + "_subtemplate");
               self.addChunk(c);
               this.template(subtemplate);
               if (c != null) {
                  c.setSubtemplate(subtemplate);
               }

               while(this.LA(1) == 8) {
                  ei = this.LT(1);
                  this.match(8);
                  ASTExpr ec = self.parseAction(ei.getText());
                  elseIfSubtemplate = new StringTemplate(self.getGroup(), (String)null);
                  elseIfSubtemplate.setEnclosingInstance(self);
                  elseIfSubtemplate.setName(ei.getText() + "_subtemplate");
                  this.template(elseIfSubtemplate);
                  if (c != null) {
                     c.addElseIfSubtemplate(ec, elseIfSubtemplate);
                  }
               }

               switch (this.LA(1)) {
                  case 9:
                     this.match(9);
                     StringTemplate elseSubtemplate = new StringTemplate(self.getGroup(), (String)null);
                     elseSubtemplate.setEnclosingInstance(self);
                     elseSubtemplate.setName("else_subtemplate");
                     this.template(elseSubtemplate);
                     if (c != null) {
                        c.setElseSubtemplate(elseSubtemplate);
                     }
                  case 10:
                     this.match(10);
                     return;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 8:
            case 9:
            case 10:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 11:
               rr = this.LT(1);
               this.match(11);
               combinedNameTemplateStr = rr.getText();
               String mangledRef = null;
               boolean err = false;
               if (combinedNameTemplateStr.startsWith("super.")) {
                  regionRef = combinedNameTemplateStr.substring("super.".length(), combinedNameTemplateStr.length());
                  String templateScope = self.getGroup().getUnMangledTemplateName(self.getName());
                  StringTemplate scopeST = self.getGroup().lookupTemplate(templateScope);
                  if (scopeST == null) {
                     self.getGroup().error("reference to region within undefined template: " + templateScope);
                     err = true;
                  }

                  if (!scopeST.containsRegionName(regionRef)) {
                     self.getGroup().error("template " + templateScope + " has no region called " + regionRef);
                     err = true;
                  } else {
                     mangledRef = self.getGroup().getMangledRegionName(templateScope, regionRef);
                     mangledRef = "super." + mangledRef;
                  }
               } else {
                  elseIfSubtemplate = self.getGroup().defineImplicitRegionTemplate(self, combinedNameTemplateStr);
                  mangledRef = elseIfSubtemplate.getName();
               }

               if (!err) {
                  regionRef = ((ChunkToken)rr).getIndentation();
                  ASTExpr c = self.parseAction(mangledRef + "()");
                  c.setIndentation(regionRef);
                  self.addChunk(c);
               }
               break;
            case 12:
               rd = this.LT(1);
               this.match(12);
               combinedNameTemplateStr = rd.getText();
               int indexOfDefSymbol = combinedNameTemplateStr.indexOf("::=");
               if (indexOfDefSymbol >= 1) {
                  String regionName = combinedNameTemplateStr.substring(0, indexOfDefSymbol);
                  regionRef = combinedNameTemplateStr.substring(indexOfDefSymbol + 3, combinedNameTemplateStr.length());
                  StringTemplate regionST = self.getGroup().defineRegionTemplate((StringTemplate)self, regionName, regionRef, 2);
                  String indent = ((ChunkToken)rd).getIndentation();
                  ASTExpr c = self.parseAction(regionST.getName() + "()");
                  c.setIndentation(indent);
                  self.addChunk(c);
               } else {
                  self.error("embedded region definition screwed up");
               }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         this.recover(var14, _tokenSet_1);
      }

   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{1792L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{8176L, 0L};
      return data;
   }
}
