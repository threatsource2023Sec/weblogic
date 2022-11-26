package antlr;

import antlr.collections.impl.BitSet;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;

public class ANTLRLexer extends CharScanner implements ANTLRTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());

   public static int escapeCharValue(String var0) {
      if (var0.charAt(1) != '\\') {
         return 0;
      } else {
         switch (var0.charAt(2)) {
            case '"':
               return 34;
            case '\'':
               return 39;
            case '0':
            case '1':
            case '2':
            case '3':
               if (var0.length() > 5 && Character.isDigit(var0.charAt(4))) {
                  return (var0.charAt(2) - 48) * 8 * 8 + (var0.charAt(3) - 48) * 8 + (var0.charAt(4) - 48);
               } else {
                  if (var0.length() > 4 && Character.isDigit(var0.charAt(3))) {
                     return (var0.charAt(2) - 48) * 8 + (var0.charAt(3) - 48);
                  }

                  return var0.charAt(2) - 48;
               }
            case '4':
            case '5':
            case '6':
            case '7':
               if (var0.length() > 4 && Character.isDigit(var0.charAt(3))) {
                  return (var0.charAt(2) - 48) * 8 + (var0.charAt(3) - 48);
               }

               return var0.charAt(2) - 48;
            case '\\':
               return 92;
            case 'b':
               return 8;
            case 'f':
               return 12;
            case 'n':
               return 10;
            case 'r':
               return 13;
            case 't':
               return 9;
            case 'u':
               if (var0.length() != 8) {
                  return 0;
               }

               return Character.digit(var0.charAt(3), 16) * 16 * 16 * 16 + Character.digit(var0.charAt(4), 16) * 16 * 16 + Character.digit(var0.charAt(5), 16) * 16 + Character.digit(var0.charAt(6), 16);
            default:
               return 0;
         }
      }
   }

   public static int tokenTypeForCharLiteral(String var0) {
      return var0.length() > 3 ? escapeCharValue(var0) : var0.charAt(1);
   }

   public ANTLRLexer(InputStream var1) {
      this((InputBuffer)(new ByteBuffer(var1)));
   }

   public ANTLRLexer(Reader var1) {
      this((InputBuffer)(new CharBuffer(var1)));
   }

   public ANTLRLexer(InputBuffer var1) {
      this(new LexerSharedInputState(var1));
   }

   public ANTLRLexer(LexerSharedInputState var1) {
      super(var1);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("public", this), new Integer(31));
      this.literals.put(new ANTLRHashString("class", this), new Integer(10));
      this.literals.put(new ANTLRHashString("header", this), new Integer(5));
      this.literals.put(new ANTLRHashString("throws", this), new Integer(37));
      this.literals.put(new ANTLRHashString("lexclass", this), new Integer(9));
      this.literals.put(new ANTLRHashString("catch", this), new Integer(40));
      this.literals.put(new ANTLRHashString("private", this), new Integer(32));
      this.literals.put(new ANTLRHashString("options", this), new Integer(51));
      this.literals.put(new ANTLRHashString("extends", this), new Integer(11));
      this.literals.put(new ANTLRHashString("protected", this), new Integer(30));
      this.literals.put(new ANTLRHashString("TreeParser", this), new Integer(13));
      this.literals.put(new ANTLRHashString("Parser", this), new Integer(29));
      this.literals.put(new ANTLRHashString("Lexer", this), new Integer(12));
      this.literals.put(new ANTLRHashString("returns", this), new Integer(35));
      this.literals.put(new ANTLRHashString("charVocabulary", this), new Integer(18));
      this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
      this.literals.put(new ANTLRHashString("exception", this), new Integer(39));
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
                  case '$':
                  case '%':
                  case '&':
                  case '-':
                  case '.':
                  case '=':
                  case '@':
                  case '\\':
                  case ']':
                  case '_':
                  case '`':
                  default:
                     if (this.LA(1) == '=' && this.LA(2) == '>') {
                        this.mIMPLIES(true);
                        var1 = this._returnToken;
                     } else if (this.LA(1) == '.' && this.LA(2) == '.') {
                        this.mRANGE(true);
                        var1 = this._returnToken;
                     } else if (this.LA(1) == '=') {
                        this.mASSIGN(true);
                        var1 = this._returnToken;
                     } else if (this.LA(1) == '.') {
                        this.mWILDCARD(true);
                        var1 = this._returnToken;
                     } else {
                        if (this.LA(1) != '\uffff') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.uponEOF();
                        this._returnToken = this.makeToken(1);
                     }
                     break;
                  case '!':
                     this.mBANG(true);
                     var1 = this._returnToken;
                     break;
                  case '"':
                     this.mSTRING_LITERAL(true);
                     var1 = this._returnToken;
                     break;
                  case '#':
                     this.mTREE_BEGIN(true);
                     var1 = this._returnToken;
                     break;
                  case '\'':
                     this.mCHAR_LITERAL(true);
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
                  case '*':
                     this.mSTAR(true);
                     var1 = this._returnToken;
                     break;
                  case '+':
                     this.mPLUS(true);
                     var1 = this._returnToken;
                     break;
                  case ',':
                     this.mCOMMA(true);
                     var1 = this._returnToken;
                     break;
                  case '/':
                     this.mCOMMENT(true);
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
                  case ':':
                     this.mCOLON(true);
                     var1 = this._returnToken;
                     break;
                  case ';':
                     this.mSEMI(true);
                     var1 = this._returnToken;
                     break;
                  case '<':
                     this.mOPEN_ELEMENT_OPTION(true);
                     var1 = this._returnToken;
                     break;
                  case '>':
                     this.mCLOSE_ELEMENT_OPTION(true);
                     var1 = this._returnToken;
                     break;
                  case '?':
                     this.mQUESTION(true);
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
                     this.mTOKEN_REF(true);
                     var1 = this._returnToken;
                     break;
                  case '[':
                     this.mARG_ACTION(true);
                     var1 = this._returnToken;
                     break;
                  case '^':
                     this.mCARET(true);
                     var1 = this._returnToken;
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
                     this.mRULE_REF(true);
                     var1 = this._returnToken;
                     break;
                  case '{':
                     this.mACTION(true);
                     var1 = this._returnToken;
                     break;
                  case '|':
                     this.mOR(true);
                     var1 = this._returnToken;
                     break;
                  case '}':
                     this.mRCURLY(true);
                     var1 = this._returnToken;
                     break;
                  case '~':
                     this.mNOT_OP(true);
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
         case ' ':
            this.match(' ');
            break;
         default:
            if (this.LA(1) == '\r' && this.LA(2) == '\n') {
               this.match('\r');
               this.match('\n');
               this.newline();
            } else {
               if (this.LA(1) != '\r') {
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.match('\r');
               this.newline();
            }
      }

      byte var6 = -1;
      if (var1 && var3 == null && var6 != -1) {
         var3 = this.makeToken(var6);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCOMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      int var2 = 53;
      Token var6 = null;
      if (this.LA(1) == '/' && this.LA(2) == '/') {
         this.mSL_COMMENT(false);
      } else {
         if (this.LA(1) != '/' || this.LA(2) != '*') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.mML_COMMENT(true);
         var6 = this._returnToken;
         var2 = var6.getType();
      }

      if (var2 != 8) {
         var2 = -1;
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mSL_COMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 54;
      this.match("//");

      while(_tokenSet_0.member(this.LA(1))) {
         this.match(_tokenSet_0);
      }

      if (this.LA(1) == '\r' && this.LA(2) == '\n') {
         this.match('\r');
         this.match('\n');
      } else if (this.LA(1) == '\r') {
         this.match('\r');
      } else {
         if (this.LA(1) != '\n') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.match('\n');
      }

      this.newline();
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mML_COMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 55;
      this.match("/*");
      if (this.LA(1) == '*' && this.LA(2) >= 3 && this.LA(2) <= 255 && this.LA(2) != '/') {
         this.match('*');
         var2 = 8;
      } else if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      while(this.LA(1) != '*' || this.LA(2) != '/') {
         if (this.LA(1) == '\r' && this.LA(2) == '\n') {
            this.match('\r');
            this.match('\n');
            this.newline();
         } else if (this.LA(1) == '\r' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.match('\r');
            this.newline();
         } else if (_tokenSet_0.member(this.LA(1)) && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.match(_tokenSet_0);
         } else {
            if (this.LA(1) != '\n') {
               break;
            }

            this.match('\n');
            this.newline();
         }
      }

      this.match("*/");
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mOPEN_ELEMENT_OPTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 25;
      this.match('<');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCLOSE_ELEMENT_OPTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 26;
      this.match('>');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCOMMA(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 38;
      this.match(',');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mQUESTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 45;
      this.match('?');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mTREE_BEGIN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 44;
      this.match("#(");
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mLPAREN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 27;
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
      byte var2 = 28;
      this.match(')');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCOLON(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 36;
      this.match(':');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSTAR(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 46;
      this.match('*');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mPLUS(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 47;
      this.match('+');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mASSIGN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 15;
      this.match('=');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mIMPLIES(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 48;
      this.match("=>");
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSEMI(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 16;
      this.match(';');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCARET(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 49;
      this.match('^');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mBANG(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 33;
      this.match('!');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mOR(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 21;
      this.match('|');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mWILDCARD(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 50;
      this.match('.');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mRANGE(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 22;
      this.match("..");
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mNOT_OP(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 42;
      this.match('~');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mRCURLY(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 17;
      this.match('}');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCHAR_LITERAL(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 19;
      this.match('\'');
      if (this.LA(1) == '\\') {
         this.mESC(false);
      } else {
         if (!_tokenSet_1.member(this.LA(1))) {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.matchNot('\'');
      }

      this.match('\'');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mESC(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 56;
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
            if (this.LA(1) >= '0' && this.LA(1) <= '7' && this.LA(2) >= 3 && this.LA(2) <= 255) {
               this.matchRange('0', '7');
               if (this.LA(1) >= '0' && this.LA(1) <= '7' && this.LA(2) >= 3 && this.LA(2) <= 255) {
                  this.matchRange('0', '7');
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
            if (this.LA(1) >= '0' && this.LA(1) <= '7' && this.LA(2) >= 3 && this.LA(2) <= 255) {
               this.matchRange('0', '7');
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
         case 'a':
            this.match('a');
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
         case 'w':
            this.match('w');
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

   public final void mSTRING_LITERAL(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 6;
      this.match('"');

      while(true) {
         while(this.LA(1) != '\\') {
            if (!_tokenSet_2.member(this.LA(1))) {
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

   protected final void mXDIGIT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 58;
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

   protected final void mDIGIT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 57;
      this.matchRange('0', '9');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mINT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 20;

      int var6;
      for(var6 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++var6) {
         this.matchRange('0', '9');
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

   public final void mARG_ACTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 34;
      this.mNESTED_ARG_ACTION(false);
      this.setText(StringUtils.stripFrontBack(this.getText(), "[", "]"));
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mNESTED_ARG_ACTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 59;
      this.match('[');

      while(true) {
         while(true) {
            switch (this.LA(1)) {
               case '\n':
                  this.match('\n');
                  this.newline();
                  break;
               case '"':
                  this.mSTRING_LITERAL(false);
                  break;
               case '\'':
                  this.mCHAR_LITERAL(false);
                  break;
               case '[':
                  this.mNESTED_ARG_ACTION(false);
                  break;
               default:
                  if (this.LA(1) == '\r' && this.LA(2) == '\n') {
                     this.match('\r');
                     this.match('\n');
                     this.newline();
                  } else if (this.LA(1) == '\r' && this.LA(2) >= 3 && this.LA(2) <= 255) {
                     this.match('\r');
                     this.newline();
                  } else {
                     if (!_tokenSet_3.member(this.LA(1))) {
                        this.match(']');
                        if (var1 && var3 == null && var2 != -1) {
                           var3 = this.makeToken(var2);
                           var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
                        }

                        this._returnToken = var3;
                        return;
                     }

                     this.matchNot(']');
                  }
            }
         }
      }
   }

   public final void mACTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Object var3 = null;
      int var4 = this.text.length();
      byte var2 = 7;
      int var6 = this.getLine();
      int var7 = this.getColumn();
      this.mNESTED_ACTION(false);
      if (this.LA(1) == '?') {
         this.match('?');
         var2 = 43;
      }

      if (var2 == 7) {
         this.setText(StringUtils.stripFrontBack(this.getText(), "{", "}"));
      } else {
         this.setText(StringUtils.stripFrontBack(this.getText(), "{", "}?"));
      }

      CommonToken var8 = new CommonToken(var2, new String(this.text.getBuffer(), var4, this.text.length() - var4));
      var8.setLine(var6);
      var8.setColumn(var7);
      var3 = var8;
      if (var1 && var8 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         ((Token)var3).setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = (Token)var3;
   }

   protected final void mNESTED_ACTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 60;
      this.match('{');

      while(this.LA(1) != '}') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 3 && this.LA(2) <= 255) {
            if (this.LA(1) == '\r' && this.LA(2) == '\n') {
               this.match('\r');
               this.match('\n');
               this.newline();
            } else if (this.LA(1) == '\r' && this.LA(2) >= 3 && this.LA(2) <= 255) {
               this.match('\r');
               this.newline();
            } else {
               if (this.LA(1) != '\n') {
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.match('\n');
               this.newline();
            }
         } else if (this.LA(1) == '{' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mNESTED_ACTION(false);
         } else if (this.LA(1) == '\'' && _tokenSet_4.member(this.LA(2))) {
            this.mCHAR_LITERAL(false);
         } else if (this.LA(1) == '/' && (this.LA(2) == '*' || this.LA(2) == '/')) {
            this.mCOMMENT(false);
         } else if (this.LA(1) == '"' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mSTRING_LITERAL(false);
         } else {
            if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      this.match('}');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mTOKEN_REF(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      int var2 = 24;
      this.matchRange('A', 'Z');

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
               var2 = this.testLiteralsTable(var2);
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

   public final void mRULE_REF(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      boolean var6 = false;
      int var8 = this.mINTERNAL_RULE_REF(false);
      int var7 = var8;
      if (var8 == 51) {
         this.mWS_LOOP(false);
         if (this.LA(1) == '{') {
            this.match('{');
            var7 = 14;
         }
      } else if (var8 == 4) {
         this.mWS_LOOP(false);
         if (this.LA(1) == '{') {
            this.match('{');
            var7 = 23;
         }
      }

      if (var1 && var3 == null && var7 != -1) {
         var3 = this.makeToken(var7);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final int mINTERNAL_RULE_REF(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var4 = null;
      int var5 = this.text.length();
      byte var3 = 62;
      int var2 = 41;
      this.matchRange('a', 'z');

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
               var2 = this.testLiteralsTable(var2);
               if (var1 && var4 == null && var3 != -1) {
                  var4 = this.makeToken(var3);
                  var4.setText(new String(this.text.getBuffer(), var5, this.text.length() - var5));
               }

               this._returnToken = var4;
               return var2;
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

   protected final void mWS_LOOP(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 61;

      while(true) {
         switch (this.LA(1)) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               this.mWS(false);
               break;
            case '/':
               this.mCOMMENT(false);
               break;
            default:
               if (var1 && var3 == null && var2 != -1) {
                  var3 = this.makeToken(var2);
                  var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
               }

               this._returnToken = var3;
               return;
         }
      }
   }

   protected final void mWS_OPT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 63;
      if (_tokenSet_5.member(this.LA(1))) {
         this.mWS(false);
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
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
      var0[0] = -549755813896L;
      var0[1] = -268435457L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_2() {
      long[] var0 = new long[8];
      var0[0] = -17179869192L;
      var0[1] = -268435457L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_3() {
      long[] var0 = new long[8];
      var0[0] = -566935692296L;
      var0[1] = -671088641L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_4() {
      long[] var0 = new long[8];
      var0[0] = -549755813896L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_5() {
      long[] var0 = new long[]{4294977024L, 0L, 0L, 0L, 0L};
      return var0;
   }
}
