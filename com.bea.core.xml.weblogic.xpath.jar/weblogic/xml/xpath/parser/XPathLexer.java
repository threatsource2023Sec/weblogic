package weblogic.xml.xpath.parser;

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

public class XPathLexer extends CharScanner implements XPathParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());

   public XPathLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public XPathLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public XPathLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public XPathLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("node", this), new Integer(18));
      this.literals.put(new ANTLRHashString("text", this), new Integer(17));
      this.literals.put(new ANTLRHashString("processing-instruction", this), new Integer(12));
      this.literals.put(new ANTLRHashString("comment", this), new Integer(16));
      this.literals.put(new ANTLRHashString("or", this), new Integer(26));
      this.literals.put(new ANTLRHashString("mod", this), new Integer(37));
      this.literals.put(new ANTLRHashString("div", this), new Integer(36));
      this.literals.put(new ANTLRHashString("and", this), new Integer(27));
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
                  case '!':
                     this.mNOT_EQUAL(true);
                     theRetToken = this._returnToken;
                     break;
                  case '"':
                  case '\'':
                     this.mLITERAL(true);
                     theRetToken = this._returnToken;
                     break;
                  case '#':
                  case '%':
                  case '&':
                  case '.':
                  case '/':
                  case ':':
                  case ';':
                  case '<':
                  case '>':
                  case '?':
                  case '\\':
                  case '^':
                  case '`':
                  case '{':
                  default:
                     if (this.LA(1) == '.' && this.LA(2) == '.') {
                        this.mDOUBLE_DOT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == ':' && this.LA(2) == ':') {
                        this.mDOUBLE_COLON(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '/' && this.LA(2) == '/') {
                        this.mDOUBLE_SLASH(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '<' && this.LA(2) == '=') {
                        this.mLESS_EQUAL(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>' && this.LA(2) == '=') {
                        this.mGREATER_EQUAL(true);
                        theRetToken = this._returnToken;
                     } else if (_tokenSet_0.member(this.LA(1)) && _tokenSet_1.member(this.LA(2))) {
                        this.mSUBTRACTION(true);
                        theRetToken = this._returnToken;
                     } else if (_tokenSet_0.member(this.LA(1))) {
                        this.mWS(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '.') {
                        this.mDOT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == ':') {
                        this.mCOLON(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '/') {
                        this.mSLASH(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '<') {
                        this.mLESS_THAN(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>') {
                        this.mGREATER_THAN(true);
                        theRetToken = this._returnToken;
                     } else {
                        if (this.LA(1) != '\uffff') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.uponEOF();
                        this._returnToken = this.makeToken(1);
                     }
                     break;
                  case '$':
                     this.mDOLLAR(true);
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
                  case '*':
                     this.mASTERISK(true);
                     theRetToken = this._returnToken;
                     break;
                  case '+':
                     this.mADDITION(true);
                     theRetToken = this._returnToken;
                     break;
                  case ',':
                     this.mCOMMA(true);
                     theRetToken = this._returnToken;
                     break;
                  case '-':
                     this.mDASH(true);
                     theRetToken = this._returnToken;
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
                     this.mNUMBER(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mEQUALS(true);
                     theRetToken = this._returnToken;
                     break;
                  case '@':
                     this.mATSIGN(true);
                     theRetToken = this._returnToken;
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
                     this.mNCNAME(true);
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
                     this.mPIPE(true);
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

   public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 7;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOUBLE_DOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      this.match("..");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNCNAME(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
      this.mLETTER(false);

      while(true) {
         switch (this.LA(1)) {
            case '-':
               this.mDASH(false);
               break;
            case '.':
               this.mDOT(false);
               break;
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
               _ttype = this.testLiteralsTable(_ttype);
               if (_createToken && _token == null && _ttype != -1) {
                  _token = this.makeToken(_ttype);
                  _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
               }

               this._returnToken = _token;
               return;
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
               this.mLETTER(false);
         }
      }
   }

   protected final void mLETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 41;
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

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 40;
      this.matchRange('0', '9');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 38;
      this.match('-');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 20;

      int _cnt122;
      for(_cnt122 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt122) {
         this.mDIGIT(false);
      }

      if (_cnt122 < 1) {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      } else {
         if (this.LA(1) == '.') {
            this.mDOT(false);

            for(_cnt122 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt122) {
               this.mDIGIT(false);
            }

            if (_cnt122 < 1) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         }

         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      }
   }

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 13;
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
      int _ttype = 15;
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
      int _ttype = 24;
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
      int _ttype = 25;
      this.match(']');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOUBLE_COLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 10;
      this.match("::");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mATSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
      this.match('@');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      this.match(',');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
      this.match(":");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOLLAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      this.match('$');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPIPE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match('|');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 4;
      this.match('/');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOUBLE_SLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      this.match('/');
      this.match('/');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLESS_THAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 30;
      this.match('<');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mGREATER_THAN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 32;
      this.match('>');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mEQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 28;
      this.match('=');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNOT_EQUAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 29;
      this.match("!=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLESS_EQUAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 31;
      this.match("<=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mGREATER_EQUAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 33;
      this.match(">=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mADDITION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 34;
      this.match('+');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSUBTRACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 35;

      int _cnt148;
      for(_cnt148 = 0; _tokenSet_0.member(this.LA(1)); ++_cnt148) {
         this.mWS(false);
      }

      if (_cnt148 >= 1) {
         this.match('-');
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   public final void mASTERISK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 11;
      this.match('*');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSINGLE_QUOTE_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 42;
      int _saveIndex = this.text.length();
      this.match('\'');
      this.text.setLength(_saveIndex);

      while(_tokenSet_2.member(this.LA(1))) {
         this.match(_tokenSet_2);
      }

      _saveIndex = this.text.length();
      this.match('\'');
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDOUBLE_QUOTE_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 43;
      int _saveIndex = this.text.length();
      this.match('"');
      this.text.setLength(_saveIndex);

      while(_tokenSet_3.member(this.LA(1))) {
         this.match(_tokenSet_3);
      }

      _saveIndex = this.text.length();
      this.match('"');
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 14;
      switch (this.LA(1)) {
         case '"':
            this.mDOUBLE_QUOTE_LITERAL(false);
            break;
         case '\'':
            this.mSINGLE_QUOTE_LITERAL(false);
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{4294977024L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{35188667065856L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{-549755813889L, -1L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[]{-17179869185L, -1L, 0L, 0L};
      return data;
   }
}
