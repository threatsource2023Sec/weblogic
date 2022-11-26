package weblogic.diagnostics.query;

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
import java.io.StringReader;
import java.util.Hashtable;

public class QueryExpressionLexer extends CharScanner implements QueryExpressionParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());

   public QueryExpressionLexer(String query) {
      this((Reader)(new StringReader(terminateQuery(query))));
   }

   private static String terminateQuery(String query) {
      if (query.charAt(query.length() - 1) != ';') {
         query = query + ';';
      }

      return query;
   }

   public QueryExpressionLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public QueryExpressionLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public QueryExpressionLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public QueryExpressionLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("LIKE", this), new Integer(14));
      this.literals.put(new ANTLRHashString("OR", this), new Integer(12));
      this.literals.put(new ANTLRHashString("IN", this), new Integer(16));
      this.literals.put(new ANTLRHashString("AND", this), new Integer(11));
      this.literals.put(new ANTLRHashString("NOT", this), new Integer(13));
      this.literals.put(new ANTLRHashString("MATCHES", this), new Integer(15));
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
                  case '#':
                  case '%':
                  case '*':
                  case '.':
                  case '/':
                  case ':':
                  case '<':
                  case '>':
                  case '?':
                  case '@':
                  case '[':
                  case '\\':
                  case ']':
                  case '^':
                  case '`':
                  case '{':
                  default:
                     if (this.LA(1) == '<' && this.LA(2) == '=') {
                        this.mLE(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>' && this.LA(2) == '=') {
                        this.mGE(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '<') {
                        this.mLT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '>') {
                        this.mGT(true);
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
                     this.mNE(true);
                     theRetToken = this._returnToken;
                     break;
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
                     this.mVARIABLE_NAME(true);
                     theRetToken = this._returnToken;
                     break;
                  case '&':
                     this.mBITWISE_AND(true);
                     theRetToken = this._returnToken;
                     break;
                  case '\'':
                     this.mSTRING_LITERAL(true);
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
                  case '-':
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
                     this.mCONSTANT_NUMBER(true);
                     theRetToken = this._returnToken;
                     break;
                  case ',':
                     this.mCOMMA(true);
                     theRetToken = this._returnToken;
                     break;
                  case ';':
                     this.mEND_OF_QUERY(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mEQ(true);
                     theRetToken = this._returnToken;
                     break;
                  case '|':
                     this.mBITWISE_OR(true);
                     theRetToken = this._returnToken;
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

   public final void mBITWISE_AND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 27;
      this.match("&");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mBITWISE_OR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 26;
      this.match("|");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 18;
      this.match("<");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mGT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
      this.match(">");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 20;
      this.match("<=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mGE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      this.match(">=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mEQ(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      this.match("=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match("!=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mEND_OF_QUERY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 17;
      this.match(";");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 24;
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
      int _ttype = 25;
      this.match(')');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 28;
      this.match(',');
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
            break;
         case '\r':
            this.match('\r');
            this.match('\n');
            break;
         case ' ':
            this.mSPACE(false);
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

   protected final void mSPACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 30;
      this.match(' ');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mVARIABLE_NAME(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      int _ttype;
      Token _token;
      int _begin;
      _token = null;
      _begin = this.text.length();
      _ttype = 7;
      label70:
      switch (this.LA(1)) {
         case '$':
            int _saveIndex = this.text.length();
            this.match("${");
            this.text.setLength(_saveIndex);
            int _cnt60 = 0;

            while(true) {
               if (this.LA(1) == '\\' && this.LA(2) == '}') {
                  _saveIndex = this.text.length();
                  this.match('\\');
                  this.text.setLength(_saveIndex);
                  this.match('}');
               } else {
                  if (!_tokenSet_0.member(this.LA(1)) || !_tokenSet_1.member(this.LA(2))) {
                     if (_cnt60 < 1) {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }

                     _saveIndex = this.text.length();
                     this.match("}");
                     this.text.setLength(_saveIndex);
                     break label70;
                  }

                  if (this.LA(1) != '}') {
                     this.match(_tokenSet_0);
                  }
               }

               ++_cnt60;
            }
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
            this.mASCII_VARNAME_START(false);

            label67:
            while(true) {
               while(true) {
                  switch (this.LA(1)) {
                     case '-':
                        this.mMINUS(false);
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
                        String t = this.getText();
                        if (t.equalsIgnoreCase("true") || t.equalsIgnoreCase("false")) {
                           _ttype = 4;
                        }
                        break label67;
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
                        this.mASCII_VARNAME_START(false);
                  }
               }
            }
      }

      _ttype = this.testLiteralsTable(_ttype);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mASCII_VARNAME_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 34;
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

   protected final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 37;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 33;
      this.matchRange('0', '9');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 32;
      this.match("-");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCONSTANT_NUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      switch (this.LA(1)) {
         case '+':
            this.mPLUS(false);
            break;
         case ',':
         case '.':
         case '/':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
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
      }

      int _cnt67;
      for(_cnt67 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt67) {
         this.mDIGIT(false);
      }

      if (_cnt67 < 1) {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      } else {
         if (this.LA(1) == '.') {
            this.mDOT(false);

            for(_cnt67 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt67) {
               this.mDIGIT(false);
            }

            if (_cnt67 < 1) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         }

         if (_tokenSet_2.member(this.LA(1))) {
            this.mNUMBER_SUFFIXES(false);
         }

         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      }
   }

   protected final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 31;
      this.match("+");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mNUMBER_SUFFIXES(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 38;
      switch (this.LA(1)) {
         case 'D':
         case 'd':
            this.mDOUBLE_SUFFIX(false);
            break;
         case 'E':
         case 'e':
            this.mEXPONENT(false);
            break;
         case 'F':
         case 'f':
            this.mFLOAT_SUFFIX(false);
            break;
         case 'L':
         case 'l':
            this.mLONG_SUFFIX(false);
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

   public final void mSTRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      int _saveIndex = this.text.length();
      this.match('\'');
      this.text.setLength(_saveIndex);

      while(true) {
         while(this.LA(1) == '\\' && this.LA(2) == '\'') {
            _saveIndex = this.text.length();
            this.match('\\');
            this.text.setLength(_saveIndex);
            this.match('\'');
         }

         if (!_tokenSet_3.member(this.LA(1)) || !_tokenSet_1.member(this.LA(2))) {
            _saveIndex = this.text.length();
            this.match('\'');
            this.text.setLength(_saveIndex);
            if (_createToken && _token == null && _ttype != -1) {
               _token = this.makeToken(_ttype);
               _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
            }

            this._returnToken = _token;
            return;
         }

         if (this.LA(1) != '\'') {
            this.match(_tokenSet_3);
         }
      }
   }

   protected final void mUNICODE_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 35;
      this.matchRange('\u0080', '\ufffe');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mALIAS_DELIMITER_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 36;
      this.match('#');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDOUBLE_SUFFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 39;
      switch (this.LA(1)) {
         case 'D':
            this.match('D');
            break;
         case 'd':
            this.match('d');
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

   protected final void mFLOAT_SUFFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 40;
      switch (this.LA(1)) {
         case 'F':
            this.match('F');
            break;
         case 'f':
            this.match('f');
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

   protected final void mLONG_SUFFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 41;
      switch (this.LA(1)) {
         case 'L':
            this.match('L');
            break;
         case 'l':
            this.match('l');
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

   protected final void mEXPONENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 42;
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

      switch (this.LA(1)) {
         case '+':
            this.mPLUS(false);
            break;
         case ',':
         case '.':
         case '/':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
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
      }

      int _cnt90;
      for(_cnt90 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt90) {
         this.mDIGIT(false);
      }

      if (_cnt90 >= 1) {
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[2048];
      data[0] = -4294957568L;
      data[1] = -2305843009213693953L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[2048];
      data[0] = -4294957568L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[1025];
      data[1] = 18073222385776L;
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[2048];
      data[0] = -554050771456L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }
}
