package antlr.preprocessor;

import antlr.ANTLRHashString;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.collections.impl.BitSet;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;

public class PreprocessorLexer extends CharScanner implements PreprocessorTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());

   public PreprocessorLexer(InputStream var1) {
      this((InputBuffer)(new ByteBuffer(var1)));
   }

   public PreprocessorLexer(Reader var1) {
      this((InputBuffer)(new CharBuffer(var1)));
   }

   public PreprocessorLexer(InputBuffer var1) {
      this(new LexerSharedInputState(var1));
   }

   public PreprocessorLexer(LexerSharedInputState var1) {
      super(var1);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("public", this), new Integer(18));
      this.literals.put(new ANTLRHashString("class", this), new Integer(8));
      this.literals.put(new ANTLRHashString("throws", this), new Integer(23));
      this.literals.put(new ANTLRHashString("catch", this), new Integer(26));
      this.literals.put(new ANTLRHashString("private", this), new Integer(17));
      this.literals.put(new ANTLRHashString("extends", this), new Integer(10));
      this.literals.put(new ANTLRHashString("protected", this), new Integer(16));
      this.literals.put(new ANTLRHashString("returns", this), new Integer(21));
      this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
      this.literals.put(new ANTLRHashString("exception", this), new Integer(25));
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
                  case '#':
                  case '$':
                  case '%':
                  case '&':
                  case '(':
                  case '*':
                  case '+':
                  case '-':
                  case '.':
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
                  case '<':
                  case '>':
                  case '?':
                  case '@':
                  case '\\':
                  case ']':
                  case '^':
                  case '`':
                  case '|':
                  default:
                     if (this.LA(1) == '(' && _tokenSet_0.member(this.LA(2))) {
                        this.mSUBRULE_BLOCK(true);
                        var1 = this._returnToken;
                     } else if (this.LA(1) == '(') {
                        this.mLPAREN(true);
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
                  case '\'':
                     this.mCHAR_LITERAL(true);
                     var1 = this._returnToken;
                     break;
                  case ')':
                     this.mRPAREN(true);
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
                  case ':':
                     this.mRULE_BLOCK(true);
                     var1 = this._returnToken;
                     break;
                  case ';':
                     this.mSEMI(true);
                     var1 = this._returnToken;
                     break;
                  case '=':
                     this.mASSIGN_RHS(true);
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
                  case '_':
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
                     this.mID_OR_KEYWORD(true);
                     var1 = this._returnToken;
                     break;
                  case '[':
                     this.mARG_ACTION(true);
                     var1 = this._returnToken;
                     break;
                  case '{':
                     this.mACTION(true);
                     var1 = this._returnToken;
                     break;
                  case '}':
                     this.mRCURLY(true);
                     var1 = this._returnToken;
               }

               if (this._returnToken != null) {
                  int var7 = this._returnToken.getType();
                  var7 = this.testLiteralsTable(var7);
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

   public final void mRULE_BLOCK(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 22;
      this.match(':');
      int var5;
      if (_tokenSet_1.member(this.LA(1)) && _tokenSet_2.member(this.LA(2))) {
         var5 = this.text.length();
         this.mWS(false);
         this.text.setLength(var5);
      } else if (!_tokenSet_2.member(this.LA(1))) {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      this.mALT(false);
      switch (this.LA(1)) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            var5 = this.text.length();
            this.mWS(false);
            this.text.setLength(var5);
         case ';':
         case '|':
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      while(this.LA(1) == '|') {
         this.match('|');
         if (_tokenSet_1.member(this.LA(1)) && _tokenSet_2.member(this.LA(2))) {
            var5 = this.text.length();
            this.mWS(false);
            this.text.setLength(var5);
         } else if (!_tokenSet_2.member(this.LA(1))) {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.mALT(false);
         switch (this.LA(1)) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               var5 = this.text.length();
               this.mWS(false);
               this.text.setLength(var5);
            case ';':
            case '|':
               break;
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }
      }

      this.match(';');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mWS(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      int var6 = 0;

      while(true) {
         if (this.LA(1) == ' ') {
            this.match(' ');
         } else if (this.LA(1) == '\t') {
            this.match('\t');
         } else {
            if (this.LA(1) != '\n' && this.LA(1) != '\r') {
               if (var6 >= 1) {
                  byte var7 = -1;
                  if (var1 && var3 == null && var7 != -1) {
                     var3 = this.makeToken(var7);
                     var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
                  }

                  this._returnToken = var3;
                  return;
               }

               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.mNEWLINE(false);
         }

         ++var6;
      }
   }

   protected final void mALT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 27;

      while(_tokenSet_3.member(this.LA(1)) && this.LA(2) >= 3 && this.LA(2) <= 255) {
         this.mELEMENT(false);
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSUBRULE_BLOCK(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 6;
      this.match('(');
      if (_tokenSet_1.member(this.LA(1)) && _tokenSet_0.member(this.LA(2))) {
         this.mWS(false);
      } else if (!_tokenSet_0.member(this.LA(1))) {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      this.mALT(false);

      for(; _tokenSet_4.member(this.LA(1)) && _tokenSet_0.member(this.LA(2)); this.mALT(false)) {
         switch (this.LA(1)) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               this.mWS(false);
            case '|':
               break;
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.match('|');
         if (_tokenSet_1.member(this.LA(1)) && _tokenSet_0.member(this.LA(2))) {
            this.mWS(false);
         } else if (!_tokenSet_0.member(this.LA(1))) {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }
      }

      switch (this.LA(1)) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            this.mWS(false);
         case ')':
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      this.match(')');
      if (this.LA(1) == '=' && this.LA(2) == '>') {
         this.match("=>");
      } else if (this.LA(1) == '*') {
         this.match('*');
      } else if (this.LA(1) == '+') {
         this.match('+');
      } else if (this.LA(1) == '?') {
         this.match('?');
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mELEMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 28;
      switch (this.LA(1)) {
         case '\n':
         case '\r':
            this.mNEWLINE(false);
            break;
         case '"':
            this.mSTRING_LITERAL(false);
            break;
         case '\'':
            this.mCHAR_LITERAL(false);
            break;
         case '(':
            this.mSUBRULE_BLOCK(false);
            break;
         case '/':
            this.mCOMMENT(false);
            break;
         case '{':
            this.mACTION(false);
            break;
         default:
            if (!_tokenSet_5.member(this.LA(1))) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.match(_tokenSet_5);
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCOMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      if (this.LA(1) == '/' && this.LA(2) == '/') {
         this.mSL_COMMENT(false);
      } else {
         if (this.LA(1) != '/' || this.LA(2) != '*') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.mML_COMMENT(false);
      }

      byte var6 = -1;
      if (var1 && var3 == null && var6 != -1) {
         var3 = this.makeToken(var6);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mACTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 7;
      this.match('{');

      while(this.LA(1) != '}') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mNEWLINE(false);
         } else if (this.LA(1) == '{' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mACTION(false);
         } else if (this.LA(1) == '\'' && _tokenSet_6.member(this.LA(2))) {
            this.mCHAR_LITERAL(false);
         } else if (this.LA(1) != '/' || this.LA(2) != '*' && this.LA(2) != '/') {
            if (this.LA(1) == '"' && this.LA(2) >= 3 && this.LA(2) <= 255) {
               this.mSTRING_LITERAL(false);
            } else {
               if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
                  break;
               }

               this.matchNot('\uffff');
            }
         } else {
            this.mCOMMENT(false);
         }
      }

      this.match('}');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSTRING_LITERAL(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 39;
      this.match('"');

      while(true) {
         while(this.LA(1) != '\\') {
            if (!_tokenSet_7.member(this.LA(1))) {
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

   public final void mCHAR_LITERAL(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 38;
      this.match('\'');
      if (this.LA(1) == '\\') {
         this.mESC(false);
      } else {
         if (!_tokenSet_8.member(this.LA(1))) {
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

   protected final void mNEWLINE(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 34;
      if (this.LA(1) == '\r' && this.LA(2) == '\n') {
         this.match('\r');
         this.match('\n');
         this.newline();
      } else if (this.LA(1) == '\r') {
         this.match('\r');
         this.newline();
      } else {
         if (this.LA(1) != '\n') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.match('\n');
         this.newline();
      }

      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mBANG(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 19;
      this.match('!');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mSEMI(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 11;
      this.match(';');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mCOMMA(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 24;
      this.match(',');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mRCURLY(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 15;
      this.match('}');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mLPAREN(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 29;
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
      byte var2 = 30;
      this.match(')');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mID_OR_KEYWORD(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      boolean var2 = true;
      Token var6 = null;
      this.mID(true);
      var6 = this._returnToken;
      int var7 = var6.getType();
      if (_tokenSet_9.member(this.LA(1)) && this.LA(2) >= 3 && this.LA(2) <= 255 && var6.getText().equals("header")) {
         if (_tokenSet_1.member(this.LA(1)) && _tokenSet_9.member(this.LA(2))) {
            this.mWS(false);
         } else if (!_tokenSet_9.member(this.LA(1)) || this.LA(2) < 3 || this.LA(2) > 255) {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         switch (this.LA(1)) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
            case '/':
            case '{':
               break;
            case '"':
               this.mSTRING_LITERAL(false);
               break;
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         label84:
         while(true) {
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
                     this.mACTION(false);
                     var7 = 5;
                     break label84;
               }
            }
         }
      } else if (_tokenSet_10.member(this.LA(1)) && this.LA(2) >= 3 && this.LA(2) <= 255 && var6.getText().equals("tokens")) {
         label68:
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
                  this.mCURLY_BLOCK_SCARF(false);
                  var7 = 12;
                  break label68;
            }
         }
      } else if (_tokenSet_10.member(this.LA(1)) && var6.getText().equals("options")) {
         label59:
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
                  this.match('{');
                  var7 = 13;
                  break label59;
            }
         }
      }

      if (var1 && var3 == null && var7 != -1) {
         var3 = this.makeToken(var7);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mID(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      int var2 = 9;
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
         case '`':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
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
               var2 = this.testLiteralsTable(new String(this.text.getBuffer(), var4, this.text.length() - var4), var2);
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

   protected final void mCURLY_BLOCK_SCARF(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 32;
      this.match('{');

      while(this.LA(1) != '}') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mNEWLINE(false);
         } else if (this.LA(1) == '"' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mSTRING_LITERAL(false);
         } else if (this.LA(1) == '\'' && _tokenSet_6.member(this.LA(2))) {
            this.mCHAR_LITERAL(false);
         } else if (this.LA(1) != '/' || this.LA(2) != '*' && this.LA(2) != '/') {
            if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
               break;
            }

            this.matchNot('\uffff');
         } else {
            this.mCOMMENT(false);
         }
      }

      this.match('}');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   public final void mASSIGN_RHS(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 14;
      int var5 = this.text.length();
      this.match('=');
      this.text.setLength(var5);

      while(this.LA(1) != ';') {
         if (this.LA(1) == '"' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mSTRING_LITERAL(false);
         } else if (this.LA(1) == '\'' && _tokenSet_6.member(this.LA(2))) {
            this.mCHAR_LITERAL(false);
         } else if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mNEWLINE(false);
         } else {
            if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      this.match(';');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mSL_COMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 36;
      this.match("//");

      while(this.LA(1) != '\n' && this.LA(1) != '\r' && this.LA(1) >= 3 && this.LA(1) <= 255 && this.LA(2) >= 3 && this.LA(2) <= 255) {
         this.matchNot('\uffff');
      }

      this.mNEWLINE(false);
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mML_COMMENT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 37;
      this.match("/*");

      while(this.LA(1) != '*' || this.LA(2) != '/') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mNEWLINE(false);
         } else {
            if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      this.match("*/");
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   protected final void mESC(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 40;
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

   protected final void mDIGIT(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 41;
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
      byte var2 = 42;
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

   public final void mARG_ACTION(boolean var1) throws RecognitionException, CharStreamException, TokenStreamException {
      Token var3 = null;
      int var4 = this.text.length();
      byte var2 = 20;
      this.match('[');

      while(this.LA(1) != ']') {
         if (this.LA(1) == '[' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mARG_ACTION(false);
         } else if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mNEWLINE(false);
         } else if (this.LA(1) == '\'' && _tokenSet_6.member(this.LA(2))) {
            this.mCHAR_LITERAL(false);
         } else if (this.LA(1) == '"' && this.LA(2) >= 3 && this.LA(2) <= 255) {
            this.mSTRING_LITERAL(false);
         } else {
            if (this.LA(1) < 3 || this.LA(1) > 255 || this.LA(2) < 3 || this.LA(2) > 255) {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      this.match(']');
      if (var1 && var3 == null && var2 != -1) {
         var3 = this.makeToken(var2);
         var3.setText(new String(this.text.getBuffer(), var4, this.text.length() - var4));
      }

      this._returnToken = var3;
   }

   private static final long[] mk_tokenSet_0() {
      long[] var0 = new long[8];
      var0[0] = -576460752303423496L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_1() {
      long[] var0 = new long[]{4294977024L, 0L, 0L, 0L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_2() {
      long[] var0 = new long[8];
      var0[0] = -2199023255560L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_3() {
      long[] var0 = new long[8];
      var0[0] = -576462951326679048L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_4() {
      long[] var0 = new long[]{4294977024L, 1152921504606846976L, 0L, 0L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_5() {
      long[] var0 = new long[8];
      var0[0] = -576605355262354440L;
      var0[1] = -576460752303423489L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_6() {
      long[] var0 = new long[8];
      var0[0] = -549755813896L;

      for(int var1 = 1; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_7() {
      long[] var0 = new long[8];
      var0[0] = -17179869192L;
      var0[1] = -268435457L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_8() {
      long[] var0 = new long[8];
      var0[0] = -549755813896L;
      var0[1] = -268435457L;

      for(int var1 = 2; var1 <= 3; ++var1) {
         var0[var1] = -1L;
      }

      return var0;
   }

   private static final long[] mk_tokenSet_9() {
      long[] var0 = new long[]{140758963201536L, 576460752303423488L, 0L, 0L, 0L};
      return var0;
   }

   private static final long[] mk_tokenSet_10() {
      long[] var0 = new long[]{140741783332352L, 576460752303423488L, 0L, 0L, 0L};
      return var0;
   }
}
