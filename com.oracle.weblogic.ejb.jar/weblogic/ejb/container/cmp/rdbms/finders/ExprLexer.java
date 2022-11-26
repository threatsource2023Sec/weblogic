package weblogic.ejb.container.cmp.rdbms.finders;

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

public class ExprLexer extends CharScanner implements ExprParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());

   public ExprLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public ExprLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public ExprLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public ExprLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = false;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("all", this), new Integer(16));
      this.literals.put(new ANTLRHashString("sqrt", this), new Integer(65));
      this.literals.put(new ANTLRHashString("count", this), new Integer(15));
      this.literals.put(new ANTLRHashString("sum", this), new Integer(14));
      this.literals.put(new ANTLRHashString("for", this), new Integer(21));
      this.literals.put(new ANTLRHashString("orderby", this), new Integer(23));
      this.literals.put(new ANTLRHashString("min", this), new Integer(11));
      this.literals.put(new ANTLRHashString("lower", this), new Integer(63));
      this.literals.put(new ANTLRHashString("upper", this), new Integer(62));
      this.literals.put(new ANTLRHashString("false", this), new Integer(56));
      this.literals.put(new ANTLRHashString("abs", this), new Integer(64));
      this.literals.put(new ANTLRHashString("true", this), new Integer(55));
      this.literals.put(new ANTLRHashString("substring", this), new Integer(59));
      this.literals.put(new ANTLRHashString("and", this), new Integer(30));
      this.literals.put(new ANTLRHashString("concat", this), new Integer(58));
      this.literals.put(new ANTLRHashString("asc", this), new Integer(45));
      this.literals.put(new ANTLRHashString("desc", this), new Integer(46));
      this.literals.put(new ANTLRHashString("select", this), new Integer(4));
      this.literals.put(new ANTLRHashString("member", this), new Integer(47));
      this.literals.put(new ANTLRHashString("exists", this), new Integer(32));
      this.literals.put(new ANTLRHashString("distinct", this), new Integer(5));
      this.literals.put(new ANTLRHashString("group", this), new Integer(26));
      this.literals.put(new ANTLRHashString("where", this), new Integer(22));
      this.literals.put(new ANTLRHashString("avg", this), new Integer(13));
      this.literals.put(new ANTLRHashString("order", this), new Integer(24));
      this.literals.put(new ANTLRHashString("mod", this), new Integer(66));
      this.literals.put(new ANTLRHashString("in", this), new Integer(19));
      this.literals.put(new ANTLRHashString("null", this), new Integer(40));
      this.literals.put(new ANTLRHashString("locate", this), new Integer(61));
      this.literals.put(new ANTLRHashString("empty", this), new Integer(41));
      this.literals.put(new ANTLRHashString("escape", this), new Integer(44));
      this.literals.put(new ANTLRHashString("length", this), new Integer(60));
      this.literals.put(new ANTLRHashString("having", this), new Integer(27));
      this.literals.put(new ANTLRHashString("of", this), new Integer(48));
      this.literals.put(new ANTLRHashString("or", this), new Integer(29));
      this.literals.put(new ANTLRHashString("between", this), new Integer(42));
      this.literals.put(new ANTLRHashString("max", this), new Integer(12));
      this.literals.put(new ANTLRHashString("from", this), new Integer(18));
      this.literals.put(new ANTLRHashString("is", this), new Integer(39));
      this.literals.put(new ANTLRHashString("like", this), new Integer(43));
      this.literals.put(new ANTLRHashString("any", this), new Integer(52));
      this.literals.put(new ANTLRHashString("select_hint", this), new Integer(28));
      this.literals.put(new ANTLRHashString("object", this), new Integer(7));
      this.literals.put(new ANTLRHashString("not", this), new Integer(31));
      this.literals.put(new ANTLRHashString("by", this), new Integer(25));
      this.literals.put(new ANTLRHashString("as", this), new Integer(20));
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
                  case '!':
                  case '"':
                  case '#':
                  case '$':
                  case '%':
                  case '&':
                  case '.':
                  case ':':
                  case ';':
                  case '<':
                  case '>':
                  default:
                     if (this.LA(1) == '<' && this.LA(2) == '>') {
                        this.mNTEQ(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '<' && this.LA(2) == '=') {
                        this.mLTEQ(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>' && this.LA(2) == '=') {
                        this.mGTEQ(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '<') {
                        this.mLT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>') {
                        this.mGT(true);
                        theRetToken = this._returnToken;
                     } else if (_tokenSet_0.member(this.LA(1))) {
                        this.mID(true);
                        theRetToken = this._returnToken;
                     } else {
                        if (this.LA(1) != '\uffff') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.uponEOF();
                        this._returnToken = this.makeToken(1);
                     }
                     break;
                  case '\'':
                     this.mSTRING(true);
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
                     this.mTIMES(true);
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
                  case '-':
                     this.mMINUS(true);
                     theRetToken = this._returnToken;
                     break;
                  case '/':
                     this.mDIV(true);
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
                     this.mEQ(true);
                     theRetToken = this._returnToken;
                     break;
                  case '?':
                     this.mVARIABLE(true);
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

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
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
      int _ttype = 10;
      this.match(')');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 34;
      this.match('=');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 35;
      this.match('<');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mGT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 36;
      this.match('>');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNTEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 33;
      this.match("<>");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLTEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 37;
      this.match("<=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mGTEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 38;
      this.match(">=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDIV(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 51;
      this.match('/');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mTIMES(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 17;
      this.match('*');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 50;
      this.match('-');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 49;
      this.match('+');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      this.match(',');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 67;
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
      int _ttype = 68;
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
      int _ttype = 69;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 70;
      this.match('@');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 71;
      this.match('-');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mUNICODE_RANGE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 72;
      this.matchRange('\u0081', '\ufffe');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mVARIABLE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 53;
      int _saveIndex = this.text.length();
      this.match('?');
      this.text.setLength(_saveIndex);
      this.mINT(false);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mINT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 73;

      int _cnt219;
      for(_cnt219 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt219) {
         this.mDIGIT(false);
      }

      if (_cnt219 >= 1) {
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
      switch (this.LA(1)) {
         case '$':
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
         case '*':
         case '+':
         case ',':
         case '-':
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
         case '[':
         case '\\':
         case ']':
         case '^':
         case '`':
         default:
            if (this.LA(1) < 129 || this.LA(1) > '\ufffe') {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.mUNICODE_RANGE(false);
            break;
         case '@':
            this.mAT(false);
      }

      while(true) {
         while(true) {
            switch (this.LA(1)) {
               case '$':
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
               case '*':
               case '+':
               case ',':
               case '/':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '[':
               case '\\':
               case ']':
               case '^':
               case '`':
               default:
                  if (this.LA(1) < 129 || this.LA(1) > '\ufffe') {
                     if (_createToken && _token == null && _ttype != -1) {
                        _token = this.makeToken(_ttype);
                        _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                     }

                     this._returnToken = _token;
                     return;
                  }

                  this.mUNICODE_RANGE(false);
                  break;
               case '-':
                  this.mDASH(false);
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
                  break;
               case '@':
                  this.mAT(false);
            }
         }
      }
   }

   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 54;
      this.match('\'');

      while(true) {
         while(this.LA(1) != '\'' || this.LA(2) != '\'') {
            if (!_tokenSet_1.member(this.LA(1))) {
               this.match('\'');
               if (_createToken && _token == null && _ttype != -1) {
                  _token = this.makeToken(_ttype);
                  _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
               }

               this._returnToken = _token;
               return;
            }

            this.matchNot('\'');
         }

         this.match('\'');
         int _saveIndex = this.text.length();
         this.match('\'');
         this.text.setLength(_saveIndex);
      }
   }

   public final void mNUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 57;
      boolean synPredMatched216 = false;
      if (this.LA(1) >= '0' && this.LA(1) <= '9' && _tokenSet_2.member(this.LA(2))) {
         int _m216 = this.mark();
         synPredMatched216 = true;
         ++this.inputState.guessing;

         try {
            int _cnt214;
            for(_cnt214 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt214) {
               this.mDIGIT(false);
            }

            if (_cnt214 < 1) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            switch (this.LA(1)) {
               case '.':
                  this.mDOT(false);
                  break;
               case 'E':
               case 'e':
                  this.mE(false);
                  break;
               default:
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         } catch (RecognitionException var9) {
            synPredMatched216 = false;
         }

         this.rewind(_m216);
         --this.inputState.guessing;
      }

      if (synPredMatched216) {
         this.mREAL(false);
      } else {
         if (this.LA(1) < '0' || this.LA(1) > '9') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.mINT(false);
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 75;
      switch (this.LA(1)) {
         case 'E':
            this.match('E');
            break;
         case 'e':
            this.match('e');
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

   protected final void mREAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 74;

      int _cnt229;
      for(_cnt229 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt229) {
         this.mDIGIT(false);
      }

      if (_cnt229 < 1) {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      } else {
         if (this.LA(1) == '.' && this.LA(2) >= '0' && this.LA(2) <= '9') {
            this.mDOT(false);

            for(_cnt229 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt229) {
               this.mDIGIT(false);
            }

            if (_cnt229 < 1) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            if (this.LA(1) == 'E' || this.LA(1) == 'e') {
               this.mE(false);
               switch (this.LA(1)) {
                  case '-':
                     this.mMINUS(false);
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
                     for(_cnt229 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt229) {
                        this.mDIGIT(false);
                     }

                     if (_cnt229 < 1) {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }
                     break;
                  case '.':
                  case '/':
                  default:
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }
            }
         } else if (this.LA(1) != 'E' && this.LA(1) != 'e') {
            if (this.LA(1) != '.') {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.mDOT(false);
         } else {
            this.mE(false);
            switch (this.LA(1)) {
               case '-':
                  this.mMINUS(false);
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
                  for(_cnt229 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt229) {
                     this.mDIGIT(false);
                  }

                  if (_cnt229 < 1) {
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }
                  break;
               case '.':
               case '/':
               default:
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

   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 76;
      switch (this.LA(1)) {
         case '\t':
            this.match('\t');
            break;
         case '\n':
            this.match('\n');
            if (this.inputState.guessing == 0) {
               this.newline();
            }
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

      if (this.inputState.guessing == 0) {
         _ttype = -1;
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[3072];
      data[0] = 68719476736L;
      data[1] = 576460745995190271L;
      data[2] = -2L;

      for(int i = 3; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[2048];
      data[0] = -549755813889L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[1025];
      data[0] = 288019269919178752L;
      data[1] = 137438953504L;
      return data;
   }
}
