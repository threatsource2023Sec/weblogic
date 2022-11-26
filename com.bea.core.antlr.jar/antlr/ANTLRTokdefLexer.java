package antlr;

import antlr.collections.impl.BitSet;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;

public class ANTLRTokdefLexer extends CharScanner implements ANTLRTokdefParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());

   public ANTLRTokdefLexer(InputStream var1) {
      this((InputBuffer)(new ByteBuffer(var1)));
   }

   public ANTLRTokdefLexer(Reader var1) {
      this((InputBuffer)(new CharBuffer(var1)));
   }

   public ANTLRTokdefLexer(InputBuffer var1) {
      this(new LexerSharedInputState(var1));
   }

   public ANTLRTokdefLexer(LexerSharedInputState var1) {
      super(var1);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
   }

   public Token nextToken() throws TokenStreamException {
      Token var1 = null;

      while(true) {
         Object var2 = null;
         boolean var3 = false;
         this.resetText();

         try {
            try {
               switch (this.LA(1)) {
                  case '\t':
                  case '\n':
                  case '\r':
                  case ' ':
                     this.mWS(true);
                     var1 = this._returnToken;
                     break;
                  case '\u000b':
                  case '\f':
                  case '\u000e':
                  case '\u000f':
                  case '\u0010':
                  case '\u0011':
                  case '\u0012':
                  case '\u0013':
                  case '\u0014':
                  case '\u0015':
                  case '\u0016':
                  case '\u0017':
                  case '\u0018':
                  case '\u0019':
                  case '\u001a':
                  case '\u001b':
                  case '\u001c':
                  case '\u001d':
                  case '\u001e':
                  case '\u001f':
                  case '!':
                  case '#':
                  case '$':
                  case '%':
                  case '&':
                  case '\'':
                  case '*':
                  case '+':
                  case ',':
                  case '-':
                  case '.':
                  case '/':
                  case ':':
                  case ';':
                  case '<':
                  case '>':
                  case '?':
                  case '@':
                  case '[':
                  case '\\':
                  case ']':
                  case '^':
                  case '_':
                  case '`':
                  default:
                     if (this.LA(1) == '/' && this.LA(2) == '/') {
                        this.mSL_COMMENT(true);
                        var1 = this._returnToken;
                     } else if (this.LA(1) == '/' && this.LA(2) == '*') {
                        this.mML_COMMENT(true);
                        var1 = this._returnToken;
                     } else {
                        if (this.LA(1) != '\uffff') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.uponEOF();
                        this._returnToken = this.makeToken(1);
                     }
                     break;
                  case '"':
                     this.mSTRING(true);
                     var1 = this._returnToken;
                     break;
                  case '(':
                     this.mLPAREN(true);
                     var1 = this._returnToken;
                     break;
                  case ')':
                     this.mRPAREN(true);
                     var1 = this._returnToken;
                     break;
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                  case '8':
                  case '9':
                     this.mINT(true);
                     var1 = this._returnToken;
                     break;
                  case '=':
                     this.mASSIGN(true);
                     var1 = this._returnToken;
                     break;
                  case 'A':
                  case 'B':
                  case 'C':
                  case 'D':
                  case 'E':
                  case 'F':
                  case 'G':
                  case 'H':
                  case 'I':
                  case 'J':
                  case 'K':
                  case 'L':
                  case 'M':
                  case 'N':
                  case 'O':
                  case 'P':
                  case 'Q':
                  case 'R':
                  case 'S':
                  case 'T':
                  case 'U':
                  case 'V':
                  case 'W':
                  case 'X':
                  case 'Y':
                  case 'Z':
                  case 'a':
                  case 'b':
                  case 'c':
                  case 'd':
                  case 'e':
                  case 'f':
                  case 'g':
                  case 'h':
                  case 'i':
                  case 'j':
                  case 'k':
                  case 'l':
                  case 'm':
                  case 'n':
                  case 'o':
                  case 'p':
                  case 'q':
                  case 'r':
                  case 's':
                  case 't':
                  case 'u':
                  case 'v':
                  case 'w':
                  case 'x':
                  case 'y':
                  case 'z':
                     this.mID(true);
                     var1 = this._returnToken;
               }

               if (this._returnToken != null) {
                  int var7 = this._returnToken.getType();
                  this._returnToken.setType(var7);
                  return this._returnToken;
               }
            } catch (RecognitionException var5) {
               throw new TokenStreamRecognitionException(var5);
            }
         } catch (CharStreamException var6) {
            if (var6 instanceof CharStreamIOException) {
               throw new TokenStreamIOException(((CharStreamIOException)var6).io);
            }

            throw new TokenStreamException(var6.getMessage());
         }
      }
   }

   public final void mWS(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      switch (this.LA(1)) {
         case '\t':
            this.match('\t');
            break;
         case '\n':
            this.match('\n');
            this.newline();
            break;
         case '\r':
            this.match('\r');
            if (this.LA(1) == '\n') {
               this.match('\n');
            }

            this.newline();
            break;
         case ' ':
            this.match(' ');
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      byte var6 = -1;
      if (var1 && var3 == null && var6 != -1) {
         var3 = this.makeToken(var6);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSL_COMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      this.match("//");

      while(_tokenSet_0.member(this.LA(1))) {
         this.match(_tokenSet_0);
      }

      switch (this.LA(1)) {
         case '\n':
            this.match('\n');
            break;
         case '\r':
            this.match('\r');
            if (this.LA(1) == '\n') {
               this.match('\n');
            }
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      byte var6 = -1;
      this.newline();
      if (var1 && var3 == null && var6 != -1) {
         var3 = this.makeToken(var6);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mML_COMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      this.match("/*");

      while(true) {
         while(this.LA(1) != '*' || !_tokenSet_1.member(this.LA(2))) {
            if (this.LA(1) == '\n') {
               this.match('\n');
               this.newline();
            } else {
               if (!_tokenSet_2.member(this.LA(1))) {
                  this.match("*/");
                  byte var6 = -1;
                  if (var1 && var3 == null && var6 != -1) {
                     var3 = this.makeToken(var6);
                     var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
                  }

                  this._returnToken = var3;
                  return;
               }

               this.matchNot('*');
            }
         }

         this.match('*');
         this.matchNot('/');
      }
   }

   public final void mLPAREN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 7;
      this.match('(');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mRPAREN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 8;
      this.match(')');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mASSIGN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 6;
      this.match('=');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSTRING(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 5;
      this.match('"');

      while(true) {
         while(this.LA(1) != '\\') {
            if (!_tokenSet_3.member(this.LA(1))) {
               this.match('"');
               if (var1 && var3 == null && var2 != -1) {
                  var3 = this.makeToken(var2);
                  var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
               }

               this._returnToken = var3;
               return;
            }

            this.matchNot('"');
         }

         this.mESC(false);
      }
   }

   protected final void mESC(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 13;
      this.match('\\');
      switch (this.LA(1)) {
         case '"':
            this.match('"');
            break;
         case '\'':
            this.match('\'');
            break;
         case '0':
         case '1':
         case '2':
         case '3':
            this.matchRange('0', '3');
            if (this.LA(1) >= '0' && this.LA(1) <= '9' && this.LA(2) >= 3 && this.LA(2) <= 255) {
               this.mDIGIT(false);
               if (this.LA(1) >= '0' && this.LA(1) <= '9' && this.LA(2) >= 3 && this.LA(2) <= 255) {
                  this.mDIGIT(false);
                  break;
               } else {
                  if (this.LA(1) >= 3 && this.LA(1) <= 255) {
                     break;
                  }

                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }
            } else {
               if (this.LA(1) >= 3 && this.LA(1) <= 255) {
                  break;
               }

               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         case '4':
         case '5':
         case '6':
         case '7':
            this.matchRange('4', '7');
            if (this.LA(1) >= '0' && this.LA(1) <= '9' && this.LA(2) >= 3 && this.LA(2) <= 255) {
               this.mDIGIT(false);
               break;
            } else {
               if (this.LA(1) >= 3 && this.LA(1) <= 255) {
                  break;
               }

               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         case '\\':
            this.match('\\');
            break;
         case 'b':
            this.match('b');
            break;
         case 'f':
            this.match('f');
            break;
         case 'n':
            this.match('n');
            break;
         case 'r':
            this.match('r');
            break;
         case 't':
            this.match('t');
            break;
         case 'u':
            this.match('u');
            this.mXDIGIT(false);
            this.mXDIGIT(false);
            this.mXDIGIT(false);
            this.mXDIGIT(false);
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mDIGIT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 14;
      this.matchRange('0', '9');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mXDIGIT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 15;
      switch (this.LA(1)) {
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
            this.matchRange('0', '9');
            break;
         case ':':
         case ';':
         case '<':
         case '=':
         case '>':
         case '?':
         case '@':
         case 'G':
         case 'H':
         case 'I':
         case 'J':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         case 'Z':
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         case 'A':
         case 'B':
         case 'C':
         case 'D':
         case 'E':
         case 'F':
            this.matchRange('A', 'F');
            break;
         case 'a':
         case 'b':
         case 'c':
         case 'd':
         case 'e':
         case 'f':
            this.matchRange('a', 'f');
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mID(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 4;
      switch (this.LA(1)) {
         case 'A':
         case 'B':
         case 'C':
         case 'D':
         case 'E':
         case 'F':
         case 'G':
         case 'H':
         case 'I':
         case 'J':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         case 'Z':
            this.matchRange('A', 'Z');
            break;
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         case 'a':
         case 'b':
         case 'c':
         case 'd':
         case 'e':
         case 'f':
         case 'g':
         case 'h':
         case 'i':
         case 'j':
         case 'k':
         case 'l':
         case 'm':
         case 'n':
         case 'o':
         case 'p':
         case 'q':
         case 'r':
         case 's':
         case 't':
         case 'u':
         case 'v':
         case 'w':
         case 'x':
         case 'y':
         case 'z':
            this.matchRange('a', 'z');
      }

      while(true) {
         switch (this.LA(1)) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
               this.matchRange('0', '9');
               break;
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '`':
            default:
               if (var1 && var3 == null && var2 != -1) {
                  var3 = this.makeToken(var2);
                  var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
               }

               this._returnToken = var3;
               return;
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
               this.matchRange('A', 'Z');
               break;
            case '_':
               this.match('_');
               break;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
               this.matchRange('a', 'z');
         }
      }
   }

   public final void mINT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 9;

      int var6;
      for(var6 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++var6) {
         this.mDIGIT(false);
      }

      if (var6 >= 1) {
         if (var1 && var3 == null && var2 != -1) {
            var3 = this.makeToken(var2);
            var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
         }

         this._returnToken = var3;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] var0 = new long[8];
      var0[0] = -9224L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_1() {
      long[] var0 = new long[8];
      var0[0] = -140737488355336L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_2() {
      long[] var0 = new long[8];
      var0[0] = -4398046512136L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_3() {
      long[] var0 = new long[8];
      var0[0] = -17179869192L;
      var0[1] = -268435457L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }
}
