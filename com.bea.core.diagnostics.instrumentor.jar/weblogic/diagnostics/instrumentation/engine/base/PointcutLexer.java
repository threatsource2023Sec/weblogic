package weblogic.diagnostics.instrumentation.engine.base;

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

public class PointcutLexer extends CharScanner implements PointcutParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public PointcutLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public PointcutLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public PointcutLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public PointcutLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("public", this), new Integer(18));
      this.literals.put(new ANTLRHashString("static", this), new Integer(21));
      this.literals.put(new ANTLRHashString("short", this), new Integer(33));
      this.literals.put(new ANTLRHashString("boolean", this), new Integer(32));
      this.literals.put(new ANTLRHashString("call", this), new Integer(16));
      this.literals.put(new ANTLRHashString("float", this), new Integer(35));
      this.literals.put(new ANTLRHashString("char", this), new Integer(31));
      this.literals.put(new ANTLRHashString("newcall", this), new Integer(17));
      this.literals.put(new ANTLRHashString("catch", this), new Integer(15));
      this.literals.put(new ANTLRHashString("private", this), new Integer(19));
      this.literals.put(new ANTLRHashString("double", this), new Integer(37));
      this.literals.put(new ANTLRHashString("protected", this), new Integer(20));
      this.literals.put(new ANTLRHashString("byte", this), new Integer(30));
      this.literals.put(new ANTLRHashString("void", this), new Integer(38));
      this.literals.put(new ANTLRHashString("OR", this), new Integer(7));
      this.literals.put(new ANTLRHashString("within", this), new Integer(14));
      this.literals.put(new ANTLRHashString("execution", this), new Integer(12));
      this.literals.put(new ANTLRHashString("AND", this), new Integer(8));
      this.literals.put(new ANTLRHashString("NOT", this), new Integer(9));
      this.literals.put(new ANTLRHashString("long", this), new Integer(36));
      this.literals.put(new ANTLRHashString("int", this), new Integer(34));
      this.literals.put(new ANTLRHashString("construction", this), new Integer(13));
      this.literals.put(new ANTLRHashString("*", this), new Integer(22));
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
                  case '"':
                  case '\'':
                  case '/':
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
                  case ':':
                  case '>':
                  case '?':
                  case '\\':
                  case '^':
                  case '`':
                  case '{':
                  case '}':
                  default:
                     if (this.LA(1) != '\uffff') {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }

                     this.uponEOF();
                     this._returnToken = this.makeToken(1);
                     break;
                  case '!':
                     this.mNOT(true);
                     theRetToken = this._returnToken;
                     break;
                  case '#':
                     this.mPOUND(true);
                     theRetToken = this._returnToken;
                     break;
                  case '$':
                  case '*':
                  case '-':
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
                     this.mIDENTIFIER(true);
                     theRetToken = this._returnToken;
                     break;
                  case '%':
                     this.mPERCENT(true);
                     theRetToken = this._returnToken;
                     break;
                  case '&':
                     this.mAND(true);
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
                  case '+':
                     this.mPLUS(true);
                     theRetToken = this._returnToken;
                     break;
                  case ',':
                     this.mCOMMA(true);
                     theRetToken = this._returnToken;
                     break;
                  case '.':
                     this.mELIPSES(true);
                     theRetToken = this._returnToken;
                     break;
                  case ';':
                     this.mSEMI(true);
                     theRetToken = this._returnToken;
                     break;
                  case '<':
                     this.mCOMMENT(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mEQ(true);
                     theRetToken = this._returnToken;
                     break;
                  case '@':
                     this.mAT(true);
                     theRetToken = this._returnToken;
                     break;
                  case '[':
                     this.mLBRACKET(true);
                     theRetToken = this._returnToken;
                     break;
                  case ']':
                     this.mRBRACKET(true);
                     theRetToken = this._returnToken;
                     break;
                  case '|':
                     this.mOR(true);
                     theRetToken = this._returnToken;
                     break;
                  case '~':
                     this.mTILDA(true);
                     theRetToken = this._returnToken;
               }

               if (this._returnToken != null) {
                  int _ttype = this._returnToken.getType();
                  _ttype = this.testLiteralsTable(_ttype);
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

   protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 41;
      this.matchRange('0', '9');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mLETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 42;
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
         case '.':
         case '/':
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
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         case '-':
            this.match('-');
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

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 43;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match('@');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 44;
      this.match('|');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 45;
      this.match('&');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 46;
      this.match('!');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      this.match('=');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      this.match(';');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 24;
      this.match(',');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 10;
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
      int _ttype = 11;
      this.match(')');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 39;
      this.match('[');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mRBRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 40;
      this.match(']');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 27;
      this.match('+');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPOUND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 29;
      this.match('#');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mTILDA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 28;
      this.match('~');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPERCENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 26;
      this.match('%');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mELIPSES(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 25;
      this.match('.');
      this.match('.');
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
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
            this.newline();
            break;
         case '\r':
            this.match('\r');
            this.match('\n');
            this.newline();
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

   protected final void mCOMMENT_DATA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 48;

      while(true) {
         while(true) {
            while(true) {
               while(true) {
                  switch (this.LA(1)) {
                     case '\u0001':
                     case '\u0002':
                     case '\u0003':
                     case '\u0004':
                     case '\u0005':
                     case '\u0006':
                     case '\u0007':
                     case '\b':
                     case '\t':
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
                     case ' ':
                     case '!':
                     case '"':
                     case '#':
                     case '$':
                     case '%':
                     case '&':
                     case '\'':
                     case '(':
                     case ')':
                     case '*':
                     case '+':
                     case ',':
                     case '.':
                     case '/':
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
                     case ':':
                     case ';':
                     case '<':
                     case '=':
                     case '>':
                     case '?':
                     case '@':
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
                     case '[':
                     case '\\':
                     case ']':
                     case '^':
                     case '_':
                     case '`':
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
                     case '{':
                     case '|':
                     case '}':
                     case '~':
                     case '\u007f':
                        this.match(_tokenSet_0);
                        break;
                     case '\n':
                        this.match('\n');
                        this.newline();
                        break;
                     case '\r':
                     case '-':
                     default:
                        if (this.LA(1) != '-' || this.LA(2) < 1 || this.LA(2) > 127 || this.LA(2) == '-' && this.LA(3) == '>') {
                           if (this.LA(1) != '\r' || this.LA(2) != '\n') {
                              if (this.LA(1) != '\r' || this.LA(2) < 1 || this.LA(2) > 127) {
                                 if (_createToken && _token == null && _ttype != -1) {
                                    _token = this.makeToken(_ttype);
                                    _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                                 }

                                 this._returnToken = _token;
                                 return;
                              }

                              this.match('\r');
                              this.newline();
                           } else {
                              this.match('\r');
                              this.match('\n');
                              this.newline();
                           }
                        } else {
                           this.match('-');
                        }
                  }
               }
            }
         }
      }
   }

   public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = true;
      Token c = null;
      this.match("<!--");
      this.mCOMMENT_DATA(true);
      c = this._returnToken;
      this.match("-->");
      int _ttype = -1;
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mIDENTIFIER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 4;
      switch (this.LA(1)) {
         case '$':
         case '-':
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
            this.mLETTER(false);
            break;
         case '%':
         case '&':
         case '\'':
         case '(':
         case ')':
         case '+':
         case ',':
         case '.':
         case '/':
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
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         case '*':
            this.match('*');
      }

      while(true) {
         switch (this.LA(1)) {
            case '$':
            case '-':
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
               this.mLETTER(false);
               break;
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '+':
            case ',':
            case '/':
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
               if (_createToken && _token == null && _ttype != -1) {
                  _token = this.makeToken(_ttype);
                  _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
               }

               this._returnToken = _token;
               return;
            case '*':
               this.match('*');
               break;
            case '.':
               this.mDOT(false);
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
               this.mDIGIT(false);
         }
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{-35184372098050L, -1L, 0L, 0L};
      return data;
   }
}
