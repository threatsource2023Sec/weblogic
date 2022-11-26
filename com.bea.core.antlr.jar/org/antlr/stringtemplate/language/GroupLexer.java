package org.antlr.stringtemplate.language;

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
import java.util.List;

public class GroupLexer extends CharScanner implements GroupParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());

   public GroupLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public GroupLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public GroupLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public GroupLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("default", this), new Integer(21));
      this.literals.put(new ANTLRHashString("group", this), new Integer(4));
      this.literals.put(new ANTLRHashString("implements", this), new Integer(7));
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
                  case '\f':
                  case '\r':
                  case ' ':
                     this.mWS(true);
                     theRetToken = this._returnToken;
                     break;
                  case '\u000b':
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
                  case '-':
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
                  case '\\':
                  case '^':
                  case '`':
                  default:
                     if (this.LA(1) == ':' && this.LA(2) == ':') {
                        this.mDEFINED_TO_BE(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '/' && this.LA(2) == '/') {
                        this.mSL_COMMENT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '/' && this.LA(2) == '*') {
                        this.mML_COMMENT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == ':') {
                        this.mCOLON(true);
                        theRetToken = this._returnToken;
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
                     this.mSTAR(true);
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
                     this.mDOT(true);
                     theRetToken = this._returnToken;
                     break;
                  case ';':
                     this.mSEMI(true);
                     theRetToken = this._returnToken;
                     break;
                  case '<':
                     this.mBIGSTRING(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mASSIGN(true);
                     theRetToken = this._returnToken;
                     break;
                  case '?':
                     this.mOPTIONAL(true);
                     theRetToken = this._returnToken;
                     break;
                  case '@':
                     this.mAT(true);
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
                     this.mID(true);
                     theRetToken = this._returnToken;
                     break;
                  case '[':
                     this.mLBRACK(true);
                     theRetToken = this._returnToken;
                     break;
                  case ']':
                     this.mRBRACK(true);
                     theRetToken = this._returnToken;
                     break;
                  case '{':
                     this.mANONYMOUS_TEMPLATE(true);
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

   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
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
            case '-':
               this.match('-');
               break;
            case '.':
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
      }
   }

   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 15;
      int _saveIndex = this.text.length();
      this.match('"');
      this.text.setLength(_saveIndex);

      while(true) {
         while(this.LA(1) != '\\' || this.LA(2) != '"') {
            if (this.LA(1) == '\\' && _tokenSet_0.member(this.LA(2))) {
               this.match('\\');
               this.matchNot('"');
            } else {
               if (!_tokenSet_1.member(this.LA(1))) {
                  _saveIndex = this.text.length();
                  this.match('"');
                  this.text.setLength(_saveIndex);
                  if (_createToken && _token == null && _ttype != -1) {
                     _token = this.makeToken(_ttype);
                     _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  this._returnToken = _token;
                  return;
               }

               this.matchNot('"');
            }
         }

         _saveIndex = this.text.length();
         this.match('\\');
         this.text.setLength(_saveIndex);
         this.match('"');
      }
   }

   public final void mBIGSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 16;
      int _saveIndex = this.text.length();
      this.match("<<");
      this.text.setLength(_saveIndex);
      if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 0 && this.LA(2) <= '\ufffe') {
         switch (this.LA(1)) {
            case '\r':
               _saveIndex = this.text.length();
               this.match('\r');
               this.text.setLength(_saveIndex);
            case '\n':
               _saveIndex = this.text.length();
               this.match('\n');
               this.text.setLength(_saveIndex);
               this.newline();
               break;
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }
      } else if (this.LA(1) < 0 || this.LA(1) > '\ufffe' || this.LA(2) < 0 || this.LA(2) > '\ufffe') {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      while(this.LA(1) != '>' || this.LA(2) != '>') {
         if (this.LA(1) == '\r' && this.LA(2) == '\n' && this.LA(3) == '>' && this.LA(4) == '>') {
            _saveIndex = this.text.length();
            this.match('\r');
            this.text.setLength(_saveIndex);
            _saveIndex = this.text.length();
            this.match('\n');
            this.text.setLength(_saveIndex);
            this.newline();
         } else if (this.LA(1) == '\n' && this.LA(2) >= 0 && this.LA(2) <= '\ufffe' && this.LA(2) == '>' && this.LA(3) == '>') {
            _saveIndex = this.text.length();
            this.match('\n');
            this.text.setLength(_saveIndex);
            this.newline();
         } else if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 0 && this.LA(2) <= '\ufffe') {
            switch (this.LA(1)) {
               case '\r':
                  this.match('\r');
               case '\n':
                  this.match('\n');
                  this.newline();
                  break;
               default:
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         } else if (this.LA(1) == '\\' && this.LA(2) == '>') {
            _saveIndex = this.text.length();
            this.match('\\');
            this.text.setLength(_saveIndex);
            this.match('>');
         } else {
            if (this.LA(1) < 0 || this.LA(1) > '\ufffe' || this.LA(2) < 0 || this.LA(2) > '\ufffe') {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      _saveIndex = this.text.length();
      this.match(">>");
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mANONYMOUS_TEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 18;
      List args = null;
      StringTemplateToken t = null;
      int _saveIndex = this.text.length();
      this.match('{');
      this.text.setLength(_saveIndex);

      while(this.LA(1) != '}') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 0 && this.LA(2) <= '\ufffe') {
            switch (this.LA(1)) {
               case '\r':
                  this.match('\r');
               case '\n':
                  this.match('\n');
                  this.newline();
                  break;
               default:
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         } else if (this.LA(1) == '\\' && this.LA(2) == '}') {
            _saveIndex = this.text.length();
            this.match('\\');
            this.text.setLength(_saveIndex);
            this.match('}');
         } else {
            if (this.LA(1) < 0 || this.LA(1) > '\ufffe' || this.LA(2) < 0 || this.LA(2) > '\ufffe') {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      _saveIndex = this.text.length();
      this.match('}');
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 10;
      this.match('@');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 12;
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
      int _ttype = 13;
      this.match(')');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
      this.match('[');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mRBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 20;
      this.match(']');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
      this.match(',');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 11;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDEFINED_TO_BE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 14;
      this.match("::=");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
      this.match(';');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      this.match(':');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSTAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      this.match('*');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match('+');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 17;
      this.match('=');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mOPTIONAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 24;
      this.match('?');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = true;
      this.match("//");

      while(_tokenSet_2.member(this.LA(1))) {
         this.match(_tokenSet_2);
      }

      if (this.LA(1) == '\n' || this.LA(1) == '\r') {
         switch (this.LA(1)) {
            case '\r':
               this.match('\r');
            case '\n':
               this.match('\n');
               break;
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }
      }

      int _ttype = -1;
      this.newline();
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mML_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = true;
      this.match("/*");

      while(this.LA(1) != '*' || this.LA(2) != '/') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 0 && this.LA(2) <= '\ufffe') {
            switch (this.LA(1)) {
               case '\r':
                  this.match('\r');
               case '\n':
                  this.match('\n');
                  this.newline();
                  break;
               default:
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }
         } else {
            if (this.LA(1) < 0 || this.LA(1) > '\ufffe' || this.LA(2) < 0 || this.LA(2) > '\ufffe') {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      this.match("*/");
      int _ttype = -1;
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
      int _cnt70 = 0;

      while(true) {
         label30:
         switch (this.LA(1)) {
            case '\t':
               this.match('\t');
               break;
            case '\n':
            case '\r':
               switch (this.LA(1)) {
                  case '\r':
                     this.match('\r');
                  case '\n':
                     this.match('\n');
                     this.newline();
                     break label30;
                  default:
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }
            case '\f':
               this.match('\f');
               break;
            case ' ':
               this.match(' ');
               break;
            default:
               if (_cnt70 >= 1) {
                  int _ttype = -1;
                  if (_createToken && _token == null && _ttype != -1) {
                     _token = this.makeToken(_ttype);
                     _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  this._returnToken = _token;
                  return;
               }

               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         ++_cnt70;
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[2048];
      data[0] = -17179869185L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[2048];
      data[0] = -17179869185L;
      data[1] = -268435457L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[2048];
      data[0] = -9217L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }
}
