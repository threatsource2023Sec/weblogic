package org.stringtemplate.v4.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.Misc;

public class STLexer implements TokenSource {
   public static final char EOF = '\uffff';
   public static final int EOF_TYPE = -1;
   public static final Token SKIP = new STToken(-1, "<skip>");
   public static final int RBRACK = 17;
   public static final int LBRACK = 16;
   public static final int ELSE = 5;
   public static final int ELLIPSIS = 11;
   public static final int LCURLY = 20;
   public static final int BANG = 10;
   public static final int EQUALS = 12;
   public static final int TEXT = 22;
   public static final int ID = 25;
   public static final int SEMI = 9;
   public static final int LPAREN = 14;
   public static final int IF = 4;
   public static final int ELSEIF = 6;
   public static final int COLON = 13;
   public static final int RPAREN = 15;
   public static final int COMMA = 18;
   public static final int RCURLY = 21;
   public static final int ENDIF = 7;
   public static final int RDELIM = 24;
   public static final int SUPER = 8;
   public static final int DOT = 19;
   public static final int LDELIM = 23;
   public static final int STRING = 26;
   public static final int PIPE = 28;
   public static final int OR = 29;
   public static final int AND = 30;
   public static final int INDENT = 31;
   public static final int NEWLINE = 32;
   public static final int AT = 33;
   public static final int REGION_END = 34;
   public static final int TRUE = 35;
   public static final int FALSE = 36;
   public static final int COMMENT = 37;
   char delimiterStartChar;
   char delimiterStopChar;
   boolean scanningInsideExpr;
   public int subtemplateDepth;
   ErrorManager errMgr;
   Token templateToken;
   CharStream input;
   char c;
   int startCharIndex;
   int startLine;
   int startCharPositionInLine;
   List tokens;

   public STLexer(CharStream input) {
      this(STGroup.DEFAULT_ERR_MGR, input, (Token)null, '<', '>');
   }

   public STLexer(ErrorManager errMgr, CharStream input, Token templateToken) {
      this(errMgr, input, templateToken, '<', '>');
   }

   public STLexer(ErrorManager errMgr, CharStream input, Token templateToken, char delimiterStartChar, char delimiterStopChar) {
      this.delimiterStartChar = '<';
      this.delimiterStopChar = '>';
      this.scanningInsideExpr = false;
      this.subtemplateDepth = 0;
      this.tokens = new ArrayList();
      this.errMgr = errMgr;
      this.input = input;
      this.c = (char)input.LA(1);
      this.templateToken = templateToken;
      this.delimiterStartChar = delimiterStartChar;
      this.delimiterStopChar = delimiterStopChar;
   }

   public Token nextToken() {
      Token t;
      if (this.tokens.size() > 0) {
         t = (Token)this.tokens.remove(0);
      } else {
         t = this._nextToken();
      }

      return t;
   }

   public void match(char x) {
      if (this.c != x) {
         NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
         this.errMgr.lexerError(this.input.getSourceName(), "expecting '" + x + "', found '" + str(this.c) + "'", this.templateToken, e);
      }

      this.consume();
   }

   protected void consume() {
      this.input.consume();
      this.c = (char)this.input.LA(1);
   }

   public void emit(Token token) {
      this.tokens.add(token);
   }

   public Token _nextToken() {
      Token t;
      do {
         this.startCharIndex = this.input.index();
         this.startLine = this.input.getLine();
         this.startCharPositionInLine = this.input.getCharPositionInLine();
         if (this.c == '\uffff') {
            return this.newToken(-1);
         }

         if (this.scanningInsideExpr) {
            t = this.inside();
         } else {
            t = this.outside();
         }
      } while(t == SKIP);

      return t;
   }

   protected Token outside() {
      if (this.input.getCharPositionInLine() == 0 && (this.c == ' ' || this.c == '\t')) {
         while(this.c == ' ' || this.c == '\t') {
            this.consume();
         }

         if (this.c != '\uffff') {
            return this.newToken(31);
         } else {
            return this.newToken(22);
         }
      } else if (this.c == this.delimiterStartChar) {
         this.consume();
         if (this.c == '!') {
            return this.COMMENT();
         } else if (this.c == '\\') {
            return this.ESCAPE();
         } else {
            this.scanningInsideExpr = true;
            return this.newToken(23);
         }
      } else if (this.c == '\r') {
         this.consume();
         this.consume();
         return this.newToken(32);
      } else if (this.c == '\n') {
         this.consume();
         return this.newToken(32);
      } else if (this.c == '}' && this.subtemplateDepth > 0) {
         this.scanningInsideExpr = true;
         --this.subtemplateDepth;
         this.consume();
         return this.newTokenFromPreviousChar(21);
      } else {
         return this.mTEXT();
      }
   }

   protected Token inside() {
      while(true) {
         switch (this.c) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               this.consume();
               return SKIP;
            case '!':
               this.consume();
               return this.newToken(10);
            case '"':
               return this.mSTRING();
            case '&':
               this.consume();
               this.match('&');
               return this.newToken(30);
            case '(':
               this.consume();
               return this.newToken(14);
            case ')':
               this.consume();
               return this.newToken(15);
            case ',':
               this.consume();
               return this.newToken(18);
            case '.':
               this.consume();
               if (this.input.LA(1) == 46 && this.input.LA(2) == 46) {
                  this.consume();
                  this.match('.');
                  return this.newToken(11);
               }

               return this.newToken(19);
            case ':':
               this.consume();
               return this.newToken(13);
            case ';':
               this.consume();
               return this.newToken(9);
            case '=':
               this.consume();
               return this.newToken(12);
            case '@':
               this.consume();
               if (this.c == 'e' && this.input.LA(2) == 110 && this.input.LA(3) == 100) {
                  this.consume();
                  this.consume();
                  this.consume();
                  return this.newToken(34);
               }

               return this.newToken(33);
            case '[':
               this.consume();
               return this.newToken(16);
            case ']':
               this.consume();
               return this.newToken(17);
            case '{':
               return this.subTemplate();
            case '|':
               this.consume();
               this.match('|');
               return this.newToken(29);
            default:
               if (this.c == this.delimiterStopChar) {
                  this.consume();
                  this.scanningInsideExpr = false;
                  return this.newToken(24);
               }

               if (isIDStartLetter(this.c)) {
                  Token id = this.mID();
                  String name = id.getText();
                  if (name.equals("if")) {
                     return this.newToken(4);
                  }

                  if (name.equals("endif")) {
                     return this.newToken(7);
                  }

                  if (name.equals("else")) {
                     return this.newToken(5);
                  }

                  if (name.equals("elseif")) {
                     return this.newToken(6);
                  }

                  if (name.equals("super")) {
                     return this.newToken(8);
                  }

                  if (name.equals("true")) {
                     return this.newToken(35);
                  }

                  if (name.equals("false")) {
                     return this.newToken(36);
                  }

                  return id;
               }

               RecognitionException re = new NoViableAltException("", 0, 0, this.input);
               re.line = this.startLine;
               re.charPositionInLine = this.startCharPositionInLine;
               this.errMgr.lexerError(this.input.getSourceName(), "invalid character '" + str(this.c) + "'", this.templateToken, re);
               if (this.c == '\uffff') {
                  return this.newToken(-1);
               }

               this.consume();
         }
      }
   }

   Token subTemplate() {
      ++this.subtemplateDepth;
      int m = this.input.mark();
      int curlyStartChar = this.startCharIndex;
      int curlyLine = this.startLine;
      int curlyPos = this.startCharPositionInLine;
      List argTokens = new ArrayList();
      this.consume();
      Token curly = this.newTokenFromPreviousChar(20);
      this.WS();
      argTokens.add(this.mID());
      this.WS();

      while(this.c == ',') {
         this.consume();
         argTokens.add(this.newTokenFromPreviousChar(18));
         this.WS();
         argTokens.add(this.mID());
         this.WS();
      }

      this.WS();
      if (this.c != '|') {
         this.input.rewind(m);
         this.startCharIndex = curlyStartChar;
         this.startLine = curlyLine;
         this.startCharPositionInLine = curlyPos;
         this.consume();
         this.scanningInsideExpr = false;
         return curly;
      } else {
         this.consume();
         argTokens.add(this.newTokenFromPreviousChar(28));
         if (isWS(this.c)) {
            this.consume();
         }

         Iterator var7 = argTokens.iterator();

         while(var7.hasNext()) {
            Token t = (Token)var7.next();
            this.emit(t);
         }

         this.input.release(m);
         this.scanningInsideExpr = false;
         this.startCharIndex = curlyStartChar;
         this.startLine = curlyLine;
         this.startCharPositionInLine = curlyPos;
         return curly;
      }
   }

   Token ESCAPE() {
      this.startCharIndex = this.input.index();
      this.startCharPositionInLine = this.input.getCharPositionInLine();
      this.consume();
      if (this.c == 'u') {
         return this.UNICODE();
      } else {
         String text = null;
         switch (this.c) {
            case ' ':
               text = " ";
               break;
            case '\\':
               this.LINEBREAK();
               return SKIP;
            case 'n':
               text = "\n";
               break;
            case 't':
               text = "\t";
               break;
            default:
               NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
               this.errMgr.lexerError(this.input.getSourceName(), "invalid escaped char: '" + str(this.c) + "'", this.templateToken, e);
               this.consume();
               this.match(this.delimiterStopChar);
               return SKIP;
         }

         this.consume();
         Token t = this.newToken(22, text, this.input.getCharPositionInLine() - 2);
         this.match(this.delimiterStopChar);
         return t;
      }
   }

   Token UNICODE() {
      this.consume();
      char[] chars = new char[4];
      NoViableAltException e;
      if (!isUnicodeLetter(this.c)) {
         e = new NoViableAltException("", 0, 0, this.input);
         this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
      }

      chars[0] = this.c;
      this.consume();
      if (!isUnicodeLetter(this.c)) {
         e = new NoViableAltException("", 0, 0, this.input);
         this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
      }

      chars[1] = this.c;
      this.consume();
      if (!isUnicodeLetter(this.c)) {
         e = new NoViableAltException("", 0, 0, this.input);
         this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
      }

      chars[2] = this.c;
      this.consume();
      if (!isUnicodeLetter(this.c)) {
         e = new NoViableAltException("", 0, 0, this.input);
         this.errMgr.lexerError(this.input.getSourceName(), "invalid unicode char: '" + str(this.c) + "'", this.templateToken, e);
      }

      chars[3] = this.c;
      char uc = (char)Integer.parseInt(new String(chars), 16);
      Token t = this.newToken(22, String.valueOf(uc), this.input.getCharPositionInLine() - 6);
      this.consume();
      this.match(this.delimiterStopChar);
      return t;
   }

   Token mTEXT() {
      boolean modifiedText = false;
      StringBuilder buf = new StringBuilder();

      while(this.c != '\uffff' && this.c != this.delimiterStartChar && this.c != '\r' && this.c != '\n' && (this.c != '}' || this.subtemplateDepth <= 0)) {
         if (this.c == '\\') {
            if (this.input.LA(2) == 92) {
               this.consume();
               this.consume();
               buf.append('\\');
               modifiedText = true;
            } else if (this.input.LA(2) != this.delimiterStartChar && this.input.LA(2) != 125) {
               buf.append(this.c);
               this.consume();
            } else {
               modifiedText = true;
               this.consume();
               buf.append(this.c);
               this.consume();
            }
         } else {
            buf.append(this.c);
            this.consume();
         }
      }

      return modifiedText ? this.newToken(22, buf.toString()) : this.newToken(22);
   }

   Token mID() {
      this.startCharIndex = this.input.index();
      this.startLine = this.input.getLine();
      this.startCharPositionInLine = this.input.getCharPositionInLine();
      this.consume();

      while(isIDLetter(this.c)) {
         this.consume();
      }

      return this.newToken(25);
   }

   Token mSTRING() {
      boolean sawEscape = false;
      StringBuilder buf = new StringBuilder();
      buf.append(this.c);
      this.consume();

      while(this.c != '"') {
         if (this.c == '\\') {
            sawEscape = true;
            this.consume();
            switch (this.c) {
               case 'n':
                  buf.append('\n');
                  break;
               case 'r':
                  buf.append('\r');
                  break;
               case 't':
                  buf.append('\t');
                  break;
               default:
                  buf.append(this.c);
            }

            this.consume();
         } else {
            buf.append(this.c);
            this.consume();
            if (this.c == '\uffff') {
               RecognitionException re = new MismatchedTokenException(34, this.input);
               re.line = this.input.getLine();
               re.charPositionInLine = this.input.getCharPositionInLine();
               this.errMgr.lexerError(this.input.getSourceName(), "EOF in string", this.templateToken, re);
               break;
            }
         }
      }

      buf.append(this.c);
      this.consume();
      return sawEscape ? this.newToken(26, buf.toString()) : this.newToken(26);
   }

   void WS() {
      while(this.c == ' ' || this.c == '\t' || this.c == '\n' || this.c == '\r') {
         this.consume();
      }

   }

   Token COMMENT() {
      this.match('!');

      while(this.c != '!' || this.input.LA(2) != this.delimiterStopChar) {
         if (this.c == '\uffff') {
            RecognitionException re = new MismatchedTokenException(33, this.input);
            re.line = this.input.getLine();
            re.charPositionInLine = this.input.getCharPositionInLine();
            this.errMgr.lexerError(this.input.getSourceName(), "Nonterminated comment starting at " + this.startLine + ":" + this.startCharPositionInLine + ": '!" + this.delimiterStopChar + "' missing", this.templateToken, re);
            break;
         }

         this.consume();
      }

      this.consume();
      this.consume();
      return this.newToken(37);
   }

   void LINEBREAK() {
      this.match('\\');
      this.match(this.delimiterStopChar);

      while(this.c == ' ' || this.c == '\t') {
         this.consume();
      }

      if (this.c == '\uffff') {
         RecognitionException re = new RecognitionException(this.input);
         re.line = this.input.getLine();
         re.charPositionInLine = this.input.getCharPositionInLine();
         this.errMgr.lexerError(this.input.getSourceName(), "Missing newline after newline escape <\\\\>", this.templateToken, re);
      } else {
         if (this.c == '\r') {
            this.consume();
         }

         this.match('\n');

         while(this.c == ' ' || this.c == '\t') {
            this.consume();
         }

      }
   }

   public static boolean isIDStartLetter(char c) {
      return isIDLetter(c);
   }

   public static boolean isIDLetter(char c) {
      return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9' || c == '_' || c == '/';
   }

   public static boolean isWS(char c) {
      return c == ' ' || c == '\t' || c == '\n' || c == '\r';
   }

   public static boolean isUnicodeLetter(char c) {
      return c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c >= '0' && c <= '9';
   }

   public Token newToken(int ttype) {
      STToken t = new STToken(this.input, ttype, this.startCharIndex, this.input.index() - 1);
      t.setLine(this.startLine);
      t.setCharPositionInLine(this.startCharPositionInLine);
      return t;
   }

   public Token newTokenFromPreviousChar(int ttype) {
      STToken t = new STToken(this.input, ttype, this.input.index() - 1, this.input.index() - 1);
      t.setLine(this.input.getLine());
      t.setCharPositionInLine(this.input.getCharPositionInLine() - 1);
      return t;
   }

   public Token newToken(int ttype, String text, int pos) {
      STToken t = new STToken(ttype, text);
      t.setStartIndex(this.startCharIndex);
      t.setStopIndex(this.input.index() - 1);
      t.setLine(this.input.getLine());
      t.setCharPositionInLine(pos);
      return t;
   }

   public Token newToken(int ttype, String text) {
      STToken t = new STToken(ttype, text);
      t.setStartIndex(this.startCharIndex);
      t.setStopIndex(this.input.index() - 1);
      t.setLine(this.startLine);
      t.setCharPositionInLine(this.startCharPositionInLine);
      return t;
   }

   public String getSourceName() {
      return "no idea";
   }

   public static String str(int c) {
      return c == 65535 ? "<EOF>" : String.valueOf((char)c);
   }

   public static class STToken extends CommonToken {
      public STToken(CharStream input, int type, int start, int stop) {
         super(input, type, 0, start, stop);
      }

      public STToken(int type, String text) {
         super(type, text);
      }

      public String toString() {
         String channelStr = "";
         if (this.channel > 0) {
            channelStr = ",channel=" + this.channel;
         }

         String txt = this.getText();
         if (txt != null) {
            txt = Misc.replaceEscapes(txt);
         } else {
            txt = "<no text>";
         }

         String tokenName = null;
         if (this.type == -1) {
            tokenName = "EOF";
         } else {
            tokenName = STParser.tokenNames[this.type];
         }

         return "[@" + this.getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + tokenName + ">" + channelStr + "," + this.line + ":" + this.getCharPositionInLine() + "]";
      }
   }
}
