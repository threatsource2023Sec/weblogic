package weblogic.ejb.container.cmp11.rdbms.finders;

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

public class WLQLLexer extends CharScanner implements WLQLParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());

   public WLQLLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public WLQLLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public WLQLLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public WLQLLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("isNull", this), new Integer(15));
      this.literals.put(new ANTLRHashString("isNotNull", this), new Integer(16));
      this.literals.put(new ANTLRHashString("orderBy", this), new Integer(17));
      this.literals.put(new ANTLRHashString("like", this), new Integer(14));
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
                  case '#':
                  case '%':
                  case '*':
                  case '+':
                  case ',':
                  case ':':
                  case '<':
                  case '>':
                  case '?':
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
                  default:
                     if (this.LA(1) == '<' && this.LA(2) == '=') {
                        this.mLTEQ(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>' && this.LA(2) == '=') {
                        this.mGTEQ(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '`' && this.LA(2) >= 0 && this.LA(2) <= '\ufffe') {
                        this.mBACKSTRING(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '<') {
                        this.mLT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>') {
                        this.mGT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '`') {
                        this.mBACKTICK(true);
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
                  case '!':
                     this.mNOT(true);
                     theRetToken = this._returnToken;
                     break;
                  case '"':
                  case '\'':
                     this.mSTRING(true);
                     theRetToken = this._returnToken;
                     break;
                  case '$':
                     this.mVARIABLE(true);
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
                     this.mNUMBER(true);
                     theRetToken = this._returnToken;
                     break;
                  case '/':
                     this.mSLASH(true);
                     theRetToken = this._returnToken;
                     break;
                  case ';':
                     this.mCOMMENT(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mEQUALS(true);
                     theRetToken = this._returnToken;
                     break;
                  case '@':
                     this.mSPECIAL(true);
                     theRetToken = this._returnToken;
                     break;
                  case '|':
                     this.mOR(true);
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
      int _ttype = 4;
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
      int _ttype = 5;
      this.match(')');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mEQUALS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
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
      int _ttype = 10;
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
      int _ttype = 11;
      this.match('>');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLTEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 12;
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
      int _ttype = 13;
      this.match(">=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSLASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 24;
      this.match('/');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
      this.match('!');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      this.match('&');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 7;
      this.match('|');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mBACKTICK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 25;
      this.match('`');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSPECIAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
      int _saveIndex = this.text.length();
      this.match('@');
      this.text.setLength(_saveIndex);
      this.mID(false);
      _saveIndex = this.text.length();
      this.match(':');
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 20;
      this.mLETTER(false);

      while(true) {
         while(true) {
            switch (this.LA(1)) {
               case '.':
                  this.mDOT(false);
                  break;
               case '/':
               default:
                  if (!_tokenSet_0.member(this.LA(1))) {
                     if (_createToken && _token == null && _ttype != -1) {
                        _token = this.makeToken(_ttype);
                        _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                     }

                     this._returnToken = _token;
                     return;
                  }

                  this.mLETTER(false);
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
   }

   public final void mVARIABLE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 18;
      int _saveIndex = this.text.length();
      this.match('$');
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
      int _ttype = 30;

      int _cnt82;
      for(_cnt82 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt82) {
         this.mDIGIT(false);
      }

      if (_cnt82 >= 1) {
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final void mLETTER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 36;
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
            if (this.LA(1) >= 129 && this.LA(1) <= '\ufffe') {
               this.mUNICODE_RANGE(false);
               break;
            }

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
      int _ttype = 35;
      this.matchRange('0', '9');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 29;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      switch (this.LA(1)) {
         case '"':
            this.mDSTRING(false);
            break;
         case '\'':
            this.mSSTRING(false);
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

   protected final void mDSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 27;
      int _saveIndex = this.text.length();
      this.match('"');
      this.text.setLength(_saveIndex);

      while(_tokenSet_1.member(this.LA(1))) {
         this.matchNot('"');
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

   protected final void mSSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 26;
      int _saveIndex = this.text.length();
      this.match('\'');
      this.text.setLength(_saveIndex);

      while(_tokenSet_2.member(this.LA(1))) {
         this.matchNot('\'');
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

   public final void mBACKSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      int _saveIndex = this.text.length();
      this.match('`');
      this.text.setLength(_saveIndex);

      while(_tokenSet_3.member(this.LA(1))) {
         this.matchNot('`');
      }

      _saveIndex = this.text.length();
      this.match('`');
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      switch (this.LA(1)) {
         case '-':
            this.mDASH(false);
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
            break;
         case '/':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      boolean synPredMatched77 = false;
      if (_tokenSet_4.member(this.LA(1)) && _tokenSet_4.member(this.LA(2))) {
         int _m77 = this.mark();
         synPredMatched77 = true;
         ++this.inputState.guessing;

         try {
            while(this.LA(1) >= '0' && this.LA(1) <= '9') {
               this.mDIGIT(false);
            }

            this.mDOT(false);
         } catch (RecognitionException var9) {
            synPredMatched77 = false;
         }

         this.rewind(_m77);
         --this.inputState.guessing;
      }

      if (synPredMatched77) {
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

   protected final void mDASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 28;
      this.match('-');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mREAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 31;

      while(this.LA(1) >= '0' && this.LA(1) <= '9') {
         this.mDIGIT(false);
      }

      this.mDOT(false);

      int _cnt87;
      for(_cnt87 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt87) {
         this.mDIGIT(false);
      }

      if (_cnt87 >= 1) {
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final void mUNICODE_RANGE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 32;
      this.matchRange('\u0081', '\ufffe');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 33;
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

   public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 34;
      this.match(';');

      while(_tokenSet_5.member(this.LA(1))) {
         this.match(_tokenSet_5);
      }

      switch (this.LA(1)) {
         case '\n':
            this.match('\n');
            if (this.inputState.guessing == 0) {
               this.newline();
            }
            break;
         case '\r':
            this.match('\r');
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
      data[1] = 576460745995190270L;
      data[2] = -2L;

      for(int i = 3; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[2048];
      data[0] = -17179869185L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[2048];
      data[0] = -549755813889L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[2048];
      data[0] = -1L;
      data[1] = -4294967297L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[1025];
      data[0] = 288019269919178752L;
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[2048];
      data[0] = -9217L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }
}
