package weblogic.diagnostics.instrumentation.action;

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

public class MethodDescriptorLexer extends CharScanner implements MethodDescriptorLexerTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public MethodDescriptorLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public MethodDescriptorLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public MethodDescriptorLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public MethodDescriptorLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
   }

   public Token nextToken() throws TokenStreamException {
      Token theRetToken = null;

      while(true) {
         Token _token = null;
         int _ttype = false;
         this.resetText();

         try {
            try {
               switch (this.LA(1)) {
                  case '\t':
                  case '\n':
                  case '\r':
                  case ' ':
                     this.mWS(true);
                     theRetToken = this._returnToken;
                     break;
                  case '(':
                     this.mLPAREN(true);
                     theRetToken = this._returnToken;
                     break;
                  case ')':
                     this.mRPAREN(true);
                     theRetToken = this._returnToken;
                     break;
                  case ';':
                     this.mSEMI_COLON(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'B':
                     this.mBYTE(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'C':
                     this.mCHAR(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'D':
                     this.mDOUBLE(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'F':
                     this.mFLOAT(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'I':
                     this.mINT(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'J':
                     this.mLONG(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'S':
                     this.mSHORT(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'V':
                     this.mVOID(true);
                     theRetToken = this._returnToken;
                     break;
                  case 'Z':
                     this.mBOOLEAN(true);
                     theRetToken = this._returnToken;
                     break;
                  case '[':
                     this.mARRAY_PREFIX(true);
                     theRetToken = this._returnToken;
                     break;
                  default:
                     if (this.LA(1) == 'L' && _tokenSet_0.member(this.LA(2))) {
                        this.mCLASS_NAME(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == 'L') {
                        this.mREFERENCE_TYPE_PREFIX(true);
                        theRetToken = this._returnToken;
                     } else {
                        if (this.LA(1) != '\uffff') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.uponEOF();
                        this._returnToken = this.makeToken(1);
                     }
               }

               if (this._returnToken != null) {
                  int _ttype = this._returnToken.getType();
                  this._returnToken.setType(_ttype);
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

   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = true;
      switch (this.LA(1)) {
         case '\t':
            this.match('\t');
            break;
         case '\n':
            this.match('\n');
            break;
         case '\r':
            this.match('\r');
            this.match('\n');
            break;
         case ' ':
            this.match(' ');
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      int _ttype = -1;
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      this.match('(');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      this.match(')');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mBYTE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 7;
      this.match('B');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mBOOLEAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
      this.match('Z');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
      this.match('C');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOUBLE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 10;
      this.match('D');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mFLOAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 11;
      this.match('F');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mINT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 12;
      this.match('I');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLONG(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 13;
      this.match('J');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSHORT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 14;
      this.match('S');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mVOID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 15;
      this.match('V');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mARRAY_PREFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 16;
      this.match('[');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mREFERENCE_TYPE_PREFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 17;
      this.match('L');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSEMI_COLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 18;
      this.match(';');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCLASS_NAME(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
      int _saveIndex = this.text.length();
      this.mREFERENCE_TYPE_PREFIX(false);
      this.text.setLength(_saveIndex);
      int _cnt19 = 0;

      while(true) {
         switch (this.LA(1)) {
            case '$':
               this.match('$');
               break;
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
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
               if (this.LA(1) < 128 || this.LA(1) > '\ufffe') {
                  if (_cnt19 >= 1) {
                     _saveIndex = this.text.length();
                     this.mSEMI_COLON(false);
                     this.text.setLength(_saveIndex);
                     if (_createToken && _token == null && _ttype != -1) {
                        _token = this.makeToken(_ttype);
                        _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                     }

                     this._returnToken = _token;
                     return;
                  } else {
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }
               }

               this.matchRange('\u0080', '\ufffe');
               break;
            case '/':
               this.match('/');
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
               this.matchRange('0', '9');
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

         ++_cnt19;
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[3072];
      data[0] = 288089707382833152L;
      data[1] = 576460745995190270L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }
}
