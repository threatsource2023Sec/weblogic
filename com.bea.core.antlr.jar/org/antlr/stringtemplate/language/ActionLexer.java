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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ActionLexer extends CharScanner implements ActionParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());

   public ActionLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public ActionLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public ActionLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public ActionLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("super", this), new Integer(32));
      this.literals.put(new ANTLRHashString("if", this), new Integer(8));
      this.literals.put(new ANTLRHashString("first", this), new Integer(26));
      this.literals.put(new ANTLRHashString("last", this), new Integer(28));
      this.literals.put(new ANTLRHashString("rest", this), new Integer(27));
      this.literals.put(new ANTLRHashString("trunc", this), new Integer(31));
      this.literals.put(new ANTLRHashString("strip", this), new Integer(30));
      this.literals.put(new ANTLRHashString("length", this), new Integer(29));
      this.literals.put(new ANTLRHashString("elseif", this), new Integer(18));
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
                  case '$':
                  case '%':
                  case '&':
                  case '\'':
                  case '*':
                  case '-':
                  case '.':
                  case '/':
                  case '<':
                  case '>':
                  case '?':
                  case '@':
                  case '\\':
                  case '^':
                  case '`':
                  default:
                     if (this.LA(1) == '.' && this.LA(2) == '.') {
                        this.mDOTDOTDOT(true);
                        theRetToken = this._returnToken;
                     } else if (this.LA(1) == '.') {
                        this.mDOT(true);
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
                  case '+':
                     this.mPLUS(true);
                     theRetToken = this._returnToken;
                     break;
                  case ',':
                     this.mCOMMA(true);
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
                     this.mINT(true);
                     theRetToken = this._returnToken;
                     break;
                  case ':':
                     this.mCOLON(true);
                     theRetToken = this._returnToken;
                     break;
                  case ';':
                     this.mSEMI(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mASSIGN(true);
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
      int _ttype = 20;
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

   public final void mINT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 35;

      int _cnt63;
      for(_cnt63 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt63) {
         this.matchRange('0', '9');
      }

      if (_cnt63 >= 1) {
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 34;
      int _saveIndex = this.text.length();
      this.match('"');
      this.text.setLength(_saveIndex);

      while(true) {
         while(this.LA(1) != '\\') {
            if (!_tokenSet_0.member(this.LA(1))) {
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

         this.mESC_CHAR(false, true);
      }
   }

   protected final void mESC_CHAR(boolean _createToken, boolean doEscape) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 41;
      char c = false;
      this.match('\\');
      if (this.LA(1) == 'n' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
         this.match('n');
         if (this.inputState.guessing == 0 && doEscape) {
            this.text.setLength(_begin);
            this.text.append("\n");
         }
      } else if (this.LA(1) == 'r' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
         this.match('r');
         if (this.inputState.guessing == 0 && doEscape) {
            this.text.setLength(_begin);
            this.text.append("\r");
         }
      } else if (this.LA(1) == 't' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
         this.match('t');
         if (this.inputState.guessing == 0 && doEscape) {
            this.text.setLength(_begin);
            this.text.append("\t");
         }
      } else if (this.LA(1) == 'b' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
         this.match('b');
         if (this.inputState.guessing == 0 && doEscape) {
            this.text.setLength(_begin);
            this.text.append("\b");
         }
      } else if (this.LA(1) == 'f' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
         this.match('f');
         if (this.inputState.guessing == 0 && doEscape) {
            this.text.setLength(_begin);
            this.text.append("\f");
         }
      } else {
         if (this.LA(1) < 3 || this.LA(1) > '\ufffe' || this.LA(2) < 3 || this.LA(2) > '\ufffe') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         char c = this.LA(1);
         this.matchNot('\uffff');
         if (this.inputState.guessing == 0 && doEscape) {
            this.text.setLength(_begin);
            this.text.append(String.valueOf(c));
         }
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mANONYMOUS_TEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 33;
      List args = null;
      StringTemplateToken t = null;
      int _saveIndex = this.text.length();
      this.match('{');
      this.text.setLength(_saveIndex);
      boolean synPredMatched70 = false;
      if (_tokenSet_1.member(this.LA(1)) && _tokenSet_2.member(this.LA(2))) {
         int _m70 = this.mark();
         synPredMatched70 = true;
         ++this.inputState.guessing;

         try {
            this.mTEMPLATE_ARGS(false);
         } catch (RecognitionException var11) {
            synPredMatched70 = false;
         }

         this.rewind(_m70);
         --this.inputState.guessing;
      }

      if (!synPredMatched70) {
         if (this.LA(1) < 3 || this.LA(1) > '\ufffe') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }
      } else {
         args = this.mTEMPLATE_ARGS(false);
         if (_tokenSet_3.member(this.LA(1)) && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
            _saveIndex = this.text.length();
            this.mWS_CHAR(false);
            this.text.setLength(_saveIndex);
         } else if (this.LA(1) < 3 || this.LA(1) > '\ufffe') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         if (this.inputState.guessing == 0) {
            t = new StringTemplateToken(33, new String(this.text.getBuffer(), _begin, this.text.length() - _begin), args);
            _token = t;
         }
      }

      while(true) {
         while(this.LA(1) != '\\' || this.LA(2) != '{') {
            if (this.LA(1) == '\\' && this.LA(2) == '}') {
               _saveIndex = this.text.length();
               this.match('\\');
               this.text.setLength(_saveIndex);
               this.match('}');
            } else if (this.LA(1) == '\\' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
               this.mESC_CHAR(false, false);
            } else if (this.LA(1) == '{') {
               this.mNESTED_ANONYMOUS_TEMPLATE(false);
            } else {
               if (!_tokenSet_4.member(this.LA(1))) {
                  if (this.inputState.guessing == 0 && t != null) {
                     t.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  _saveIndex = this.text.length();
                  this.match('}');
                  this.text.setLength(_saveIndex);
                  if (_createToken && _token == null && _ttype != -1) {
                     _token = this.makeToken(_ttype);
                     ((Token)_token).setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  this._returnToken = (Token)_token;
                  return;
               }

               this.matchNot('}');
            }
         }

         _saveIndex = this.text.length();
         this.match('\\');
         this.text.setLength(_saveIndex);
         this.match('{');
      }
   }

   protected final List mTEMPLATE_ARGS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      List args = new ArrayList();
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 39;
      Token a = null;
      Token a2 = null;
      int _saveIndex;
      switch (this.LA(1)) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            _saveIndex = this.text.length();
            this.mWS_CHAR(false);
            this.text.setLength(_saveIndex);
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
            _saveIndex = this.text.length();
            this.mID(true);
            this.text.setLength(_saveIndex);
            a = this._returnToken;
            if (this.inputState.guessing == 0) {
               args.add(a.getText());
            }
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
      }

      while(_tokenSet_5.member(this.LA(1)) && _tokenSet_6.member(this.LA(2))) {
         switch (this.LA(1)) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
               _saveIndex = this.text.length();
               this.mWS_CHAR(false);
               this.text.setLength(_saveIndex);
            case ',':
               _saveIndex = this.text.length();
               this.match(',');
               this.text.setLength(_saveIndex);
               switch (this.LA(1)) {
                  case '\t':
                  case '\n':
                  case '\r':
                  case ' ':
                     _saveIndex = this.text.length();
                     this.mWS_CHAR(false);
                     this.text.setLength(_saveIndex);
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
                     _saveIndex = this.text.length();
                     this.mID(true);
                     this.text.setLength(_saveIndex);
                     a2 = this._returnToken;
                     if (this.inputState.guessing == 0) {
                        args.add(a2.getText());
                     }
                     continue;
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
               }
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }
      }

      switch (this.LA(1)) {
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            _saveIndex = this.text.length();
            this.mWS_CHAR(false);
            this.text.setLength(_saveIndex);
         case '|':
            _saveIndex = this.text.length();
            this.match('|');
            this.text.setLength(_saveIndex);
            if (_createToken && _token == null && _ttype != -1) {
               _token = this.makeToken(_ttype);
               _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
            }

            this._returnToken = _token;
            return args;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final void mWS_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 43;
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

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mNESTED_ANONYMOUS_TEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 40;
      this.match('{');

      while(true) {
         int _saveIndex;
         while(this.LA(1) != '\\' || this.LA(2) != '{') {
            if (this.LA(1) == '\\' && this.LA(2) == '}') {
               _saveIndex = this.text.length();
               this.match('\\');
               this.text.setLength(_saveIndex);
               this.match('}');
            } else if (this.LA(1) == '\\' && this.LA(2) >= 3 && this.LA(2) <= '\ufffe') {
               this.mESC_CHAR(false, false);
            } else if (this.LA(1) == '{') {
               this.mNESTED_ANONYMOUS_TEMPLATE(false);
            } else {
               if (!_tokenSet_4.member(this.LA(1))) {
                  this.match('}');
                  if (_createToken && _token == null && _ttype != -1) {
                     _token = this.makeToken(_ttype);
                     _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  this._returnToken = _token;
                  return;
               }

               this.matchNot('}');
            }
         }

         _saveIndex = this.text.length();
         this.match('\\');
         this.text.setLength(_saveIndex);
         this.match('{');
      }
   }

   public final void mLBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 36;
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
      int _ttype = 37;
      this.match(']');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 16;
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
      int _ttype = 17;
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
      int _ttype = 19;
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
      int _ttype = 25;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      this.match('=');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      this.match(':');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 24;
      this.match('+');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 15;
      this.match(';');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match('!');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mDOTDOTDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 38;
      this.match("...");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 42;
      int _cnt100 = 0;

      while(true) {
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
               if (_cnt100 >= 1) {
                  if (this.inputState.guessing == 0) {
                     _ttype = -1;
                  }

                  if (_createToken && _token == null && _ttype != -1) {
                     _token = this.makeToken(_ttype);
                     _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  this._returnToken = _token;
                  return;
               }

               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         ++_cnt100;
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[2048];
      data[0] = -17179869192L;
      data[1] = -268435457L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[1025];
      data[0] = 4294977024L;
      data[1] = 576460745995190270L;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[1025];
      data[0] = 288107235144377856L;
      data[1] = 1729382250602037246L;
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[1025];
      data[0] = 4294977024L;
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[2048];
      data[0] = -8L;
      data[1] = -2882303761785552897L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[1025];
      data[0] = 17596481021440L;
      return data;
   }

   private static final long[] mk_tokenSet_6() {
      long[] data = new long[1025];
      data[0] = 17596481021440L;
      data[1] = 576460745995190270L;
      return data;
   }
}
