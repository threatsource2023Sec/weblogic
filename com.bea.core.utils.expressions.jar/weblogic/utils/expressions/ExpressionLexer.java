package weblogic.utils.expressions;

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

public class ExpressionLexer extends CharScanner implements ExpressionParserTokenTypes, TokenStream {
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());

   public ExpressionLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public ExpressionLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public ExpressionLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public ExpressionLexer(LexerSharedInputState state) {
      super(state);
      this.caseSensitiveLiterals = false;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
      this.literals.put(new ANTLRHashString("between", this), new Integer(15));
      this.literals.put(new ANTLRHashString("null", this), new Integer(14));
      this.literals.put(new ANTLRHashString("like", this), new Integer(16));
      this.literals.put(new ANTLRHashString("in", this), new Integer(18));
      this.literals.put(new ANTLRHashString("or", this), new Integer(4));
      this.literals.put(new ANTLRHashString("escape", this), new Integer(17));
      this.literals.put(new ANTLRHashString("true", this), new Integer(29));
      this.literals.put(new ANTLRHashString("and", this), new Integer(5));
      this.literals.put(new ANTLRHashString("not", this), new Integer(6));
      this.literals.put(new ANTLRHashString("false", this), new Integer(30));
      this.literals.put(new ANTLRHashString("is", this), new Integer(13));
      this.literals.put(new ANTLRHashString("jms_bea_select", this), new Integer(26));
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
                  case '"':
                  case '#':
                  case '$':
                  case '%':
                  case '&':
                  case ':':
                  case ';':
                  case '<':
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
                     this.mINTEGER(true);
                     theRetToken = this._returnToken;
                     break;
                  case '/':
                     this.mDIV(true);
                     theRetToken = this._returnToken;
                     break;
                  case '=':
                     this.mEQ(true);
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

   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
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
      int _ttype = 20;
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
      int _ttype = 8;
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
      int _ttype = 9;
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
      int _ttype = 10;
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
      int _ttype = 7;
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
      int _ttype = 11;
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
      int _ttype = 12;
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
      int _ttype = 25;
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
      int _ttype = 24;
      this.match('*');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      this.match(',');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      this.match('+');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match('-');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 27;
      if (_tokenSet_1.member(this.LA(1))) {
         this.mLETTER(false);
      } else if (this.LA(1) != '$' && this.LA(1) != '_') {
         if (!_tokenSet_0.member(this.LA(1))) {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         this.mUNICODEJAVAIDSTART(false);
      } else {
         this.mSPECIAL(false);
      }

      while(true) {
         while(!_tokenSet_1.member(this.LA(1))) {
            if (this.LA(1) != '$' && this.LA(1) != '_') {
               if (this.LA(1) >= '0' && this.LA(1) <= '9') {
                  this.mDIGIT(false);
               } else {
                  if (!_tokenSet_2.member(this.LA(1))) {
                     _ttype = this.testLiteralsTable(_ttype);
                     if (_createToken && _token == null && _ttype != -1) {
                        _token = this.makeToken(_ttype);
                        _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                     }

                     this._returnToken = _token;
                     return;
                  }

                  this.mUNICODEJAVAIDPART(false);
               }
            } else {
               this.mSPECIAL(false);
            }
         }

         this.mLETTER(false);
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
         case '_':
         case '`':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
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

   protected final void mSPECIAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 39;
      switch (this.LA(1)) {
         case '$':
            this.match('$');
            break;
         case '_':
            this.match('_');
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

   protected final void mUNICODEJAVAIDSTART(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 37;
      switch (this.LA(1)) {
         case '$':
            this.matchRange('$', '$');
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
            this.matchRange('_', '_');
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
            break;
         case '¢':
         case '£':
         case '¤':
         case '¥':
            this.matchRange('¢', '¥');
            break;
         case 'ª':
            this.matchRange('ª', 'ª');
            break;
         case 'µ':
            this.matchRange('µ', 'µ');
            break;
         case 'º':
            this.matchRange('º', 'º');
            break;
         case 'À':
         case 'Á':
         case 'Â':
         case 'Ã':
         case 'Ä':
         case 'Å':
         case 'Æ':
         case 'Ç':
         case 'È':
         case 'É':
         case 'Ê':
         case 'Ë':
         case 'Ì':
         case 'Í':
         case 'Î':
         case 'Ï':
         case 'Ð':
         case 'Ñ':
         case 'Ò':
         case 'Ó':
         case 'Ô':
         case 'Õ':
         case 'Ö':
            this.matchRange('À', 'Ö');
            break;
         case 'Ø':
         case 'Ù':
         case 'Ú':
         case 'Û':
         case 'Ü':
         case 'Ý':
         case 'Þ':
         case 'ß':
         case 'à':
         case 'á':
         case 'â':
         case 'ã':
         case 'ä':
         case 'å':
         case 'æ':
         case 'ç':
         case 'è':
         case 'é':
         case 'ê':
         case 'ë':
         case 'ì':
         case 'í':
         case 'î':
         case 'ï':
         case 'ð':
         case 'ñ':
         case 'ò':
         case 'ó':
         case 'ô':
         case 'õ':
         case 'ö':
            this.matchRange('Ø', 'ö');
            break;
         case 'ˆ':
         case 'ˇ':
         case 'ˈ':
         case 'ˉ':
         case 'ˊ':
         case 'ˋ':
         case 'ˌ':
         case 'ˍ':
         case 'ˎ':
         case 'ˏ':
         case 'ː':
         case 'ˑ':
            this.matchRange('ˆ', 'ˑ');
            break;
         case 'ˠ':
         case 'ˡ':
         case 'ˢ':
         case 'ˣ':
         case 'ˤ':
            this.matchRange('ˠ', 'ˤ');
            break;
         case 'ˬ':
            this.matchRange('ˬ', 'ˬ');
            break;
         case 'ˮ':
            this.matchRange('ˮ', 'ˮ');
            break;
         case 'Ͱ':
         case 'ͱ':
         case 'Ͳ':
         case 'ͳ':
         case 'ʹ':
            this.matchRange('Ͱ', 'ʹ');
            break;
         case 'Ͷ':
         case 'ͷ':
            this.matchRange('Ͷ', 'ͷ');
            break;
         case 'ͺ':
         case 'ͻ':
         case 'ͼ':
         case 'ͽ':
            this.matchRange('ͺ', 'ͽ');
            break;
         case 'Ά':
            this.matchRange('Ά', 'Ά');
            break;
         case 'Έ':
         case 'Ή':
         case 'Ί':
            this.matchRange('Έ', 'Ί');
            break;
         case 'Ό':
            this.matchRange('Ό', 'Ό');
            break;
         case 'Ύ':
         case 'Ώ':
         case 'ΐ':
         case 'Α':
         case 'Β':
         case 'Γ':
         case 'Δ':
         case 'Ε':
         case 'Ζ':
         case 'Η':
         case 'Θ':
         case 'Ι':
         case 'Κ':
         case 'Λ':
         case 'Μ':
         case 'Ν':
         case 'Ξ':
         case 'Ο':
         case 'Π':
         case 'Ρ':
            this.matchRange('Ύ', 'Ρ');
            break;
         case 'Σ':
         case 'Τ':
         case 'Υ':
         case 'Φ':
         case 'Χ':
         case 'Ψ':
         case 'Ω':
         case 'Ϊ':
         case 'Ϋ':
         case 'ά':
         case 'έ':
         case 'ή':
         case 'ί':
         case 'ΰ':
         case 'α':
         case 'β':
         case 'γ':
         case 'δ':
         case 'ε':
         case 'ζ':
         case 'η':
         case 'θ':
         case 'ι':
         case 'κ':
         case 'λ':
         case 'μ':
         case 'ν':
         case 'ξ':
         case 'ο':
         case 'π':
         case 'ρ':
         case 'ς':
         case 'σ':
         case 'τ':
         case 'υ':
         case 'φ':
         case 'χ':
         case 'ψ':
         case 'ω':
         case 'ϊ':
         case 'ϋ':
         case 'ό':
         case 'ύ':
         case 'ώ':
         case 'Ϗ':
         case 'ϐ':
         case 'ϑ':
         case 'ϒ':
         case 'ϓ':
         case 'ϔ':
         case 'ϕ':
         case 'ϖ':
         case 'ϗ':
         case 'Ϙ':
         case 'ϙ':
         case 'Ϛ':
         case 'ϛ':
         case 'Ϝ':
         case 'ϝ':
         case 'Ϟ':
         case 'ϟ':
         case 'Ϡ':
         case 'ϡ':
         case 'Ϣ':
         case 'ϣ':
         case 'Ϥ':
         case 'ϥ':
         case 'Ϧ':
         case 'ϧ':
         case 'Ϩ':
         case 'ϩ':
         case 'Ϫ':
         case 'ϫ':
         case 'Ϭ':
         case 'ϭ':
         case 'Ϯ':
         case 'ϯ':
         case 'ϰ':
         case 'ϱ':
         case 'ϲ':
         case 'ϳ':
         case 'ϴ':
         case 'ϵ':
            this.matchRange('Σ', 'ϵ');
            break;
         case 'Ա':
         case 'Բ':
         case 'Գ':
         case 'Դ':
         case 'Ե':
         case 'Զ':
         case 'Է':
         case 'Ը':
         case 'Թ':
         case 'Ժ':
         case 'Ի':
         case 'Լ':
         case 'Խ':
         case 'Ծ':
         case 'Կ':
         case 'Հ':
         case 'Ձ':
         case 'Ղ':
         case 'Ճ':
         case 'Մ':
         case 'Յ':
         case 'Ն':
         case 'Շ':
         case 'Ո':
         case 'Չ':
         case 'Պ':
         case 'Ջ':
         case 'Ռ':
         case 'Ս':
         case 'Վ':
         case 'Տ':
         case 'Ր':
         case 'Ց':
         case 'Ւ':
         case 'Փ':
         case 'Ք':
         case 'Օ':
         case 'Ֆ':
            this.matchRange('Ա', 'Ֆ');
            break;
         case 'ՙ':
            this.matchRange('ՙ', 'ՙ');
            break;
         case 'ա':
         case 'բ':
         case 'գ':
         case 'դ':
         case 'ե':
         case 'զ':
         case 'է':
         case 'ը':
         case 'թ':
         case 'ժ':
         case 'ի':
         case 'լ':
         case 'խ':
         case 'ծ':
         case 'կ':
         case 'հ':
         case 'ձ':
         case 'ղ':
         case 'ճ':
         case 'մ':
         case 'յ':
         case 'ն':
         case 'շ':
         case 'ո':
         case 'չ':
         case 'պ':
         case 'ջ':
         case 'ռ':
         case 'ս':
         case 'վ':
         case 'տ':
         case 'ր':
         case 'ց':
         case 'ւ':
         case 'փ':
         case 'ք':
         case 'օ':
         case 'ֆ':
         case 'և':
            this.matchRange('ա', 'և');
            break;
         case 'א':
         case 'ב':
         case 'ג':
         case 'ד':
         case 'ה':
         case 'ו':
         case 'ז':
         case 'ח':
         case 'ט':
         case 'י':
         case 'ך':
         case 'כ':
         case 'ל':
         case 'ם':
         case 'מ':
         case 'ן':
         case 'נ':
         case 'ס':
         case 'ע':
         case 'ף':
         case 'פ':
         case 'ץ':
         case 'צ':
         case 'ק':
         case 'ר':
         case 'ש':
         case 'ת':
            this.matchRange('א', 'ת');
            break;
         case 'װ':
         case 'ױ':
         case 'ײ':
            this.matchRange('װ', 'ײ');
            break;
         case '؋':
            this.matchRange('؋', '؋');
            break;
         case 'ؠ':
         case 'ء':
         case 'آ':
         case 'أ':
         case 'ؤ':
         case 'إ':
         case 'ئ':
         case 'ا':
         case 'ب':
         case 'ة':
         case 'ت':
         case 'ث':
         case 'ج':
         case 'ح':
         case 'خ':
         case 'د':
         case 'ذ':
         case 'ر':
         case 'ز':
         case 'س':
         case 'ش':
         case 'ص':
         case 'ض':
         case 'ط':
         case 'ظ':
         case 'ع':
         case 'غ':
         case 'ػ':
         case 'ؼ':
         case 'ؽ':
         case 'ؾ':
         case 'ؿ':
         case 'ـ':
         case 'ف':
         case 'ق':
         case 'ك':
         case 'ل':
         case 'م':
         case 'ن':
         case 'ه':
         case 'و':
         case 'ى':
         case 'ي':
            this.matchRange('ؠ', 'ي');
            break;
         case 'ٮ':
         case 'ٯ':
            this.matchRange('ٮ', 'ٯ');
            break;
         case 'ٱ':
         case 'ٲ':
         case 'ٳ':
         case 'ٴ':
         case 'ٵ':
         case 'ٶ':
         case 'ٷ':
         case 'ٸ':
         case 'ٹ':
         case 'ٺ':
         case 'ٻ':
         case 'ټ':
         case 'ٽ':
         case 'پ':
         case 'ٿ':
         case 'ڀ':
         case 'ځ':
         case 'ڂ':
         case 'ڃ':
         case 'ڄ':
         case 'څ':
         case 'چ':
         case 'ڇ':
         case 'ڈ':
         case 'ډ':
         case 'ڊ':
         case 'ڋ':
         case 'ڌ':
         case 'ڍ':
         case 'ڎ':
         case 'ڏ':
         case 'ڐ':
         case 'ڑ':
         case 'ڒ':
         case 'ړ':
         case 'ڔ':
         case 'ڕ':
         case 'ږ':
         case 'ڗ':
         case 'ژ':
         case 'ڙ':
         case 'ښ':
         case 'ڛ':
         case 'ڜ':
         case 'ڝ':
         case 'ڞ':
         case 'ڟ':
         case 'ڠ':
         case 'ڡ':
         case 'ڢ':
         case 'ڣ':
         case 'ڤ':
         case 'ڥ':
         case 'ڦ':
         case 'ڧ':
         case 'ڨ':
         case 'ک':
         case 'ڪ':
         case 'ګ':
         case 'ڬ':
         case 'ڭ':
         case 'ڮ':
         case 'گ':
         case 'ڰ':
         case 'ڱ':
         case 'ڲ':
         case 'ڳ':
         case 'ڴ':
         case 'ڵ':
         case 'ڶ':
         case 'ڷ':
         case 'ڸ':
         case 'ڹ':
         case 'ں':
         case 'ڻ':
         case 'ڼ':
         case 'ڽ':
         case 'ھ':
         case 'ڿ':
         case 'ۀ':
         case 'ہ':
         case 'ۂ':
         case 'ۃ':
         case 'ۄ':
         case 'ۅ':
         case 'ۆ':
         case 'ۇ':
         case 'ۈ':
         case 'ۉ':
         case 'ۊ':
         case 'ۋ':
         case 'ی':
         case 'ۍ':
         case 'ێ':
         case 'ۏ':
         case 'ې':
         case 'ۑ':
         case 'ے':
         case 'ۓ':
            this.matchRange('ٱ', 'ۓ');
            break;
         case 'ە':
            this.matchRange('ە', 'ە');
            break;
         case 'ۥ':
         case 'ۦ':
            this.matchRange('ۥ', 'ۦ');
            break;
         case 'ۮ':
         case 'ۯ':
            this.matchRange('ۮ', 'ۯ');
            break;
         case 'ۺ':
         case 'ۻ':
         case 'ۼ':
            this.matchRange('ۺ', 'ۼ');
            break;
         case 'ۿ':
            this.matchRange('ۿ', 'ۿ');
            break;
         case 'ܐ':
            this.matchRange('ܐ', 'ܐ');
            break;
         case 'ܒ':
         case 'ܓ':
         case 'ܔ':
         case 'ܕ':
         case 'ܖ':
         case 'ܗ':
         case 'ܘ':
         case 'ܙ':
         case 'ܚ':
         case 'ܛ':
         case 'ܜ':
         case 'ܝ':
         case 'ܞ':
         case 'ܟ':
         case 'ܠ':
         case 'ܡ':
         case 'ܢ':
         case 'ܣ':
         case 'ܤ':
         case 'ܥ':
         case 'ܦ':
         case 'ܧ':
         case 'ܨ':
         case 'ܩ':
         case 'ܪ':
         case 'ܫ':
         case 'ܬ':
         case 'ܭ':
         case 'ܮ':
         case 'ܯ':
            this.matchRange('ܒ', 'ܯ');
            break;
         case 'ݍ':
         case 'ݎ':
         case 'ݏ':
         case 'ݐ':
         case 'ݑ':
         case 'ݒ':
         case 'ݓ':
         case 'ݔ':
         case 'ݕ':
         case 'ݖ':
         case 'ݗ':
         case 'ݘ':
         case 'ݙ':
         case 'ݚ':
         case 'ݛ':
         case 'ݜ':
         case 'ݝ':
         case 'ݞ':
         case 'ݟ':
         case 'ݠ':
         case 'ݡ':
         case 'ݢ':
         case 'ݣ':
         case 'ݤ':
         case 'ݥ':
         case 'ݦ':
         case 'ݧ':
         case 'ݨ':
         case 'ݩ':
         case 'ݪ':
         case 'ݫ':
         case 'ݬ':
         case 'ݭ':
         case 'ݮ':
         case 'ݯ':
         case 'ݰ':
         case 'ݱ':
         case 'ݲ':
         case 'ݳ':
         case 'ݴ':
         case 'ݵ':
         case 'ݶ':
         case 'ݷ':
         case 'ݸ':
         case 'ݹ':
         case 'ݺ':
         case 'ݻ':
         case 'ݼ':
         case 'ݽ':
         case 'ݾ':
         case 'ݿ':
         case 'ހ':
         case 'ށ':
         case 'ނ':
         case 'ރ':
         case 'ބ':
         case 'ޅ':
         case 'ކ':
         case 'އ':
         case 'ވ':
         case 'މ':
         case 'ފ':
         case 'ދ':
         case 'ތ':
         case 'ލ':
         case 'ގ':
         case 'ޏ':
         case 'ސ':
         case 'ޑ':
         case 'ޒ':
         case 'ޓ':
         case 'ޔ':
         case 'ޕ':
         case 'ޖ':
         case 'ޗ':
         case 'ޘ':
         case 'ޙ':
         case 'ޚ':
         case 'ޛ':
         case 'ޜ':
         case 'ޝ':
         case 'ޞ':
         case 'ޟ':
         case 'ޠ':
         case 'ޡ':
         case 'ޢ':
         case 'ޣ':
         case 'ޤ':
         case 'ޥ':
            this.matchRange('ݍ', 'ޥ');
            break;
         case 'ޱ':
            this.matchRange('ޱ', 'ޱ');
            break;
         case 'ߊ':
         case 'ߋ':
         case 'ߌ':
         case 'ߍ':
         case 'ߎ':
         case 'ߏ':
         case 'ߐ':
         case 'ߑ':
         case 'ߒ':
         case 'ߓ':
         case 'ߔ':
         case 'ߕ':
         case 'ߖ':
         case 'ߗ':
         case 'ߘ':
         case 'ߙ':
         case 'ߚ':
         case 'ߛ':
         case 'ߜ':
         case 'ߝ':
         case 'ߞ':
         case 'ߟ':
         case 'ߠ':
         case 'ߡ':
         case 'ߢ':
         case 'ߣ':
         case 'ߤ':
         case 'ߥ':
         case 'ߦ':
         case 'ߧ':
         case 'ߨ':
         case 'ߩ':
         case 'ߪ':
            this.matchRange('ߊ', 'ߪ');
            break;
         case 'ߴ':
         case 'ߵ':
            this.matchRange('ߴ', 'ߵ');
            break;
         case 'ߺ':
            this.matchRange('ߺ', 'ߺ');
            break;
         case 'ࠀ':
         case 'ࠁ':
         case 'ࠂ':
         case 'ࠃ':
         case 'ࠄ':
         case 'ࠅ':
         case 'ࠆ':
         case 'ࠇ':
         case 'ࠈ':
         case 'ࠉ':
         case 'ࠊ':
         case 'ࠋ':
         case 'ࠌ':
         case 'ࠍ':
         case 'ࠎ':
         case 'ࠏ':
         case 'ࠐ':
         case 'ࠑ':
         case 'ࠒ':
         case 'ࠓ':
         case 'ࠔ':
         case 'ࠕ':
            this.matchRange('ࠀ', 'ࠕ');
            break;
         case 'ࠚ':
            this.matchRange('ࠚ', 'ࠚ');
            break;
         case 'ࠤ':
            this.matchRange('ࠤ', 'ࠤ');
            break;
         case 'ࠨ':
            this.matchRange('ࠨ', 'ࠨ');
            break;
         case 'ࡀ':
         case 'ࡁ':
         case 'ࡂ':
         case 'ࡃ':
         case 'ࡄ':
         case 'ࡅ':
         case 'ࡆ':
         case 'ࡇ':
         case 'ࡈ':
         case 'ࡉ':
         case 'ࡊ':
         case 'ࡋ':
         case 'ࡌ':
         case 'ࡍ':
         case 'ࡎ':
         case 'ࡏ':
         case 'ࡐ':
         case 'ࡑ':
         case 'ࡒ':
         case 'ࡓ':
         case 'ࡔ':
         case 'ࡕ':
         case 'ࡖ':
         case 'ࡗ':
         case 'ࡘ':
            this.matchRange('ࡀ', 'ࡘ');
            break;
         case 'ऄ':
         case 'अ':
         case 'आ':
         case 'इ':
         case 'ई':
         case 'उ':
         case 'ऊ':
         case 'ऋ':
         case 'ऌ':
         case 'ऍ':
         case 'ऎ':
         case 'ए':
         case 'ऐ':
         case 'ऑ':
         case 'ऒ':
         case 'ओ':
         case 'औ':
         case 'क':
         case 'ख':
         case 'ग':
         case 'घ':
         case 'ङ':
         case 'च':
         case 'छ':
         case 'ज':
         case 'झ':
         case 'ञ':
         case 'ट':
         case 'ठ':
         case 'ड':
         case 'ढ':
         case 'ण':
         case 'त':
         case 'थ':
         case 'द':
         case 'ध':
         case 'न':
         case 'ऩ':
         case 'प':
         case 'फ':
         case 'ब':
         case 'भ':
         case 'म':
         case 'य':
         case 'र':
         case 'ऱ':
         case 'ल':
         case 'ळ':
         case 'ऴ':
         case 'व':
         case 'श':
         case 'ष':
         case 'स':
         case 'ह':
            this.matchRange('ऄ', 'ह');
            break;
         case 'ऽ':
            this.matchRange('ऽ', 'ऽ');
            break;
         case 'ॐ':
            this.matchRange('ॐ', 'ॐ');
            break;
         case 'क़':
         case 'ख़':
         case 'ग़':
         case 'ज़':
         case 'ड़':
         case 'ढ़':
         case 'फ़':
         case 'य़':
         case 'ॠ':
         case 'ॡ':
            this.matchRange('क़', 'ॡ');
            break;
         case 'ॱ':
         case 'ॲ':
         case 'ॳ':
         case 'ॴ':
         case 'ॵ':
         case 'ॶ':
         case 'ॷ':
            this.matchRange('ॱ', 'ॷ');
            break;
         case 'ॹ':
         case 'ॺ':
         case 'ॻ':
         case 'ॼ':
         case 'ॽ':
         case 'ॾ':
         case 'ॿ':
            this.matchRange('ॹ', 'ॿ');
            break;
         case 'অ':
         case 'আ':
         case 'ই':
         case 'ঈ':
         case 'উ':
         case 'ঊ':
         case 'ঋ':
         case 'ঌ':
            this.matchRange('অ', 'ঌ');
            break;
         case 'এ':
         case 'ঐ':
            this.matchRange('এ', 'ঐ');
            break;
         case 'ও':
         case 'ঔ':
         case 'ক':
         case 'খ':
         case 'গ':
         case 'ঘ':
         case 'ঙ':
         case 'চ':
         case 'ছ':
         case 'জ':
         case 'ঝ':
         case 'ঞ':
         case 'ট':
         case 'ঠ':
         case 'ড':
         case 'ঢ':
         case 'ণ':
         case 'ত':
         case 'থ':
         case 'দ':
         case 'ধ':
         case 'ন':
            this.matchRange('ও', 'ন');
            break;
         case 'প':
         case 'ফ':
         case 'ব':
         case 'ভ':
         case 'ম':
         case 'য':
         case 'র':
            this.matchRange('প', 'র');
            break;
         case 'ল':
            this.matchRange('ল', 'ল');
            break;
         case 'শ':
         case 'ষ':
         case 'স':
         case 'হ':
            this.matchRange('শ', 'হ');
            break;
         case 'ঽ':
            this.matchRange('ঽ', 'ঽ');
            break;
         case 'ৎ':
            this.matchRange('ৎ', 'ৎ');
            break;
         case 'ড়':
         case 'ঢ়':
            this.matchRange('ড়', 'ঢ়');
            break;
         case 'য়':
         case 'ৠ':
         case 'ৡ':
            this.matchRange('য়', 'ৡ');
            break;
         case 'ৰ':
         case 'ৱ':
         case '৲':
         case '৳':
            this.matchRange('ৰ', '৳');
            break;
         case '৻':
            this.matchRange('৻', '৻');
            break;
         case 'ਅ':
         case 'ਆ':
         case 'ਇ':
         case 'ਈ':
         case 'ਉ':
         case 'ਊ':
            this.matchRange('ਅ', 'ਊ');
            break;
         case 'ਏ':
         case 'ਐ':
            this.matchRange('ਏ', 'ਐ');
            break;
         case 'ਓ':
         case 'ਔ':
         case 'ਕ':
         case 'ਖ':
         case 'ਗ':
         case 'ਘ':
         case 'ਙ':
         case 'ਚ':
         case 'ਛ':
         case 'ਜ':
         case 'ਝ':
         case 'ਞ':
         case 'ਟ':
         case 'ਠ':
         case 'ਡ':
         case 'ਢ':
         case 'ਣ':
         case 'ਤ':
         case 'ਥ':
         case 'ਦ':
         case 'ਧ':
         case 'ਨ':
            this.matchRange('ਓ', 'ਨ');
            break;
         case 'ਪ':
         case 'ਫ':
         case 'ਬ':
         case 'ਭ':
         case 'ਮ':
         case 'ਯ':
         case 'ਰ':
            this.matchRange('ਪ', 'ਰ');
            break;
         case 'ਲ':
         case 'ਲ਼':
            this.matchRange('ਲ', 'ਲ਼');
            break;
         case 'ਵ':
         case 'ਸ਼':
            this.matchRange('ਵ', 'ਸ਼');
            break;
         case 'ਸ':
         case 'ਹ':
            this.matchRange('ਸ', 'ਹ');
            break;
         case 'ਖ਼':
         case 'ਗ਼':
         case 'ਜ਼':
         case 'ੜ':
            this.matchRange('ਖ਼', 'ੜ');
            break;
         case 'ਫ਼':
            this.matchRange('ਫ਼', 'ਫ਼');
            break;
         case 'ੲ':
         case 'ੳ':
         case 'ੴ':
            this.matchRange('ੲ', 'ੴ');
            break;
         case 'અ':
         case 'આ':
         case 'ઇ':
         case 'ઈ':
         case 'ઉ':
         case 'ઊ':
         case 'ઋ':
         case 'ઌ':
         case 'ઍ':
            this.matchRange('અ', 'ઍ');
            break;
         case 'એ':
         case 'ઐ':
         case 'ઑ':
            this.matchRange('એ', 'ઑ');
            break;
         case 'ઓ':
         case 'ઔ':
         case 'ક':
         case 'ખ':
         case 'ગ':
         case 'ઘ':
         case 'ઙ':
         case 'ચ':
         case 'છ':
         case 'જ':
         case 'ઝ':
         case 'ઞ':
         case 'ટ':
         case 'ઠ':
         case 'ડ':
         case 'ઢ':
         case 'ણ':
         case 'ત':
         case 'થ':
         case 'દ':
         case 'ધ':
         case 'ન':
            this.matchRange('ઓ', 'ન');
            break;
         case 'પ':
         case 'ફ':
         case 'બ':
         case 'ભ':
         case 'મ':
         case 'ય':
         case 'ર':
            this.matchRange('પ', 'ર');
            break;
         case 'લ':
         case 'ળ':
            this.matchRange('લ', 'ળ');
            break;
         case 'વ':
         case 'શ':
         case 'ષ':
         case 'સ':
         case 'હ':
            this.matchRange('વ', 'હ');
            break;
         case 'ઽ':
            this.matchRange('ઽ', 'ઽ');
            break;
         case 'ૐ':
            this.matchRange('ૐ', 'ૐ');
            break;
         case 'ૠ':
         case 'ૡ':
            this.matchRange('ૠ', 'ૡ');
            break;
         case '૱':
            this.matchRange('૱', '૱');
            break;
         case 'ଅ':
         case 'ଆ':
         case 'ଇ':
         case 'ଈ':
         case 'ଉ':
         case 'ଊ':
         case 'ଋ':
         case 'ଌ':
            this.matchRange('ଅ', 'ଌ');
            break;
         case 'ଏ':
         case 'ଐ':
            this.matchRange('ଏ', 'ଐ');
            break;
         case 'ଓ':
         case 'ଔ':
         case 'କ':
         case 'ଖ':
         case 'ଗ':
         case 'ଘ':
         case 'ଙ':
         case 'ଚ':
         case 'ଛ':
         case 'ଜ':
         case 'ଝ':
         case 'ଞ':
         case 'ଟ':
         case 'ଠ':
         case 'ଡ':
         case 'ଢ':
         case 'ଣ':
         case 'ତ':
         case 'ଥ':
         case 'ଦ':
         case 'ଧ':
         case 'ନ':
            this.matchRange('ଓ', 'ନ');
            break;
         case 'ପ':
         case 'ଫ':
         case 'ବ':
         case 'ଭ':
         case 'ମ':
         case 'ଯ':
         case 'ର':
            this.matchRange('ପ', 'ର');
            break;
         case 'ଲ':
         case 'ଳ':
            this.matchRange('ଲ', 'ଳ');
            break;
         case 'ଵ':
         case 'ଶ':
         case 'ଷ':
         case 'ସ':
         case 'ହ':
            this.matchRange('ଵ', 'ହ');
            break;
         case 'ଽ':
            this.matchRange('ଽ', 'ଽ');
            break;
         case 'ଡ଼':
         case 'ଢ଼':
            this.matchRange('ଡ଼', 'ଢ଼');
            break;
         case 'ୟ':
         case 'ୠ':
         case 'ୡ':
            this.matchRange('ୟ', 'ୡ');
            break;
         case 'ୱ':
            this.matchRange('ୱ', 'ୱ');
            break;
         case 'ஃ':
            this.matchRange('ஃ', 'ஃ');
            break;
         case 'அ':
         case 'ஆ':
         case 'இ':
         case 'ஈ':
         case 'உ':
         case 'ஊ':
            this.matchRange('அ', 'ஊ');
            break;
         case 'எ':
         case 'ஏ':
         case 'ஐ':
            this.matchRange('எ', 'ஐ');
            break;
         case 'ஒ':
         case 'ஓ':
         case 'ஔ':
         case 'க':
            this.matchRange('ஒ', 'க');
            break;
         case 'ங':
         case 'ச':
            this.matchRange('ங', 'ச');
            break;
         case 'ஜ':
            this.matchRange('ஜ', 'ஜ');
            break;
         case 'ஞ':
         case 'ட':
            this.matchRange('ஞ', 'ட');
            break;
         case 'ண':
         case 'த':
            this.matchRange('ண', 'த');
            break;
         case 'ந':
         case 'ன':
         case 'ப':
            this.matchRange('ந', 'ப');
            break;
         case 'ம':
         case 'ய':
         case 'ர':
         case 'ற':
         case 'ல':
         case 'ள':
         case 'ழ':
         case 'வ':
         case 'ஶ':
         case 'ஷ':
         case 'ஸ':
         case 'ஹ':
            this.matchRange('ம', 'ஹ');
            break;
         case 'ௐ':
            this.matchRange('ௐ', 'ௐ');
            break;
         case '௹':
            this.matchRange('௹', '௹');
            break;
         case 'అ':
         case 'ఆ':
         case 'ఇ':
         case 'ఈ':
         case 'ఉ':
         case 'ఊ':
         case 'ఋ':
         case 'ఌ':
            this.matchRange('అ', 'ఌ');
            break;
         case 'ఎ':
         case 'ఏ':
         case 'ఐ':
            this.matchRange('ఎ', 'ఐ');
            break;
         case 'ఒ':
         case 'ఓ':
         case 'ఔ':
         case 'క':
         case 'ఖ':
         case 'గ':
         case 'ఘ':
         case 'ఙ':
         case 'చ':
         case 'ఛ':
         case 'జ':
         case 'ఝ':
         case 'ఞ':
         case 'ట':
         case 'ఠ':
         case 'డ':
         case 'ఢ':
         case 'ణ':
         case 'త':
         case 'థ':
         case 'ద':
         case 'ధ':
         case 'న':
            this.matchRange('ఒ', 'న');
            break;
         case 'ప':
         case 'ఫ':
         case 'బ':
         case 'భ':
         case 'మ':
         case 'య':
         case 'ర':
         case 'ఱ':
         case 'ల':
         case 'ళ':
            this.matchRange('ప', 'ళ');
            break;
         case 'వ':
         case 'శ':
         case 'ష':
         case 'స':
         case 'హ':
            this.matchRange('వ', 'హ');
            break;
         case 'ఽ':
            this.matchRange('ఽ', 'ఽ');
            break;
         case 'ౘ':
         case 'ౙ':
            this.matchRange('ౘ', 'ౙ');
            break;
         case 'ౠ':
         case 'ౡ':
            this.matchRange('ౠ', 'ౡ');
            break;
         case 'ಅ':
         case 'ಆ':
         case 'ಇ':
         case 'ಈ':
         case 'ಉ':
         case 'ಊ':
         case 'ಋ':
         case 'ಌ':
            this.matchRange('ಅ', 'ಌ');
            break;
         case 'ಎ':
         case 'ಏ':
         case 'ಐ':
            this.matchRange('ಎ', 'ಐ');
            break;
         case 'ಒ':
         case 'ಓ':
         case 'ಔ':
         case 'ಕ':
         case 'ಖ':
         case 'ಗ':
         case 'ಘ':
         case 'ಙ':
         case 'ಚ':
         case 'ಛ':
         case 'ಜ':
         case 'ಝ':
         case 'ಞ':
         case 'ಟ':
         case 'ಠ':
         case 'ಡ':
         case 'ಢ':
         case 'ಣ':
         case 'ತ':
         case 'ಥ':
         case 'ದ':
         case 'ಧ':
         case 'ನ':
            this.matchRange('ಒ', 'ನ');
            break;
         case 'ಪ':
         case 'ಫ':
         case 'ಬ':
         case 'ಭ':
         case 'ಮ':
         case 'ಯ':
         case 'ರ':
         case 'ಱ':
         case 'ಲ':
         case 'ಳ':
            this.matchRange('ಪ', 'ಳ');
            break;
         case 'ವ':
         case 'ಶ':
         case 'ಷ':
         case 'ಸ':
         case 'ಹ':
            this.matchRange('ವ', 'ಹ');
            break;
         case 'ಽ':
            this.matchRange('ಽ', 'ಽ');
            break;
         case 'ೞ':
            this.matchRange('ೞ', 'ೞ');
            break;
         case 'ೠ':
         case 'ೡ':
            this.matchRange('ೠ', 'ೡ');
            break;
         case 'ೱ':
         case 'ೲ':
            this.matchRange('ೱ', 'ೲ');
            break;
         case 'അ':
         case 'ആ':
         case 'ഇ':
         case 'ഈ':
         case 'ഉ':
         case 'ഊ':
         case 'ഋ':
         case 'ഌ':
            this.matchRange('അ', 'ഌ');
            break;
         case 'എ':
         case 'ഏ':
         case 'ഐ':
            this.matchRange('എ', 'ഐ');
            break;
         case 'ഒ':
         case 'ഓ':
         case 'ഔ':
         case 'ക':
         case 'ഖ':
         case 'ഗ':
         case 'ഘ':
         case 'ങ':
         case 'ച':
         case 'ഛ':
         case 'ജ':
         case 'ഝ':
         case 'ഞ':
         case 'ട':
         case 'ഠ':
         case 'ഡ':
         case 'ഢ':
         case 'ണ':
         case 'ത':
         case 'ഥ':
         case 'ദ':
         case 'ധ':
         case 'ന':
         case 'ഩ':
         case 'പ':
         case 'ഫ':
         case 'ബ':
         case 'ഭ':
         case 'മ':
         case 'യ':
         case 'ര':
         case 'റ':
         case 'ല':
         case 'ള':
         case 'ഴ':
         case 'വ':
         case 'ശ':
         case 'ഷ':
         case 'സ':
         case 'ഹ':
         case 'ഺ':
            this.matchRange('ഒ', 'ഺ');
            break;
         case 'ഽ':
            this.matchRange('ഽ', 'ഽ');
            break;
         case 'ൎ':
            this.matchRange('ൎ', 'ൎ');
            break;
         case 'ൠ':
         case 'ൡ':
            this.matchRange('ൠ', 'ൡ');
            break;
         case 'ൺ':
         case 'ൻ':
         case 'ർ':
         case 'ൽ':
         case 'ൾ':
         case 'ൿ':
            this.matchRange('ൺ', 'ൿ');
            break;
         case 'අ':
         case 'ආ':
         case 'ඇ':
         case 'ඈ':
         case 'ඉ':
         case 'ඊ':
         case 'උ':
         case 'ඌ':
         case 'ඍ':
         case 'ඎ':
         case 'ඏ':
         case 'ඐ':
         case 'එ':
         case 'ඒ':
         case 'ඓ':
         case 'ඔ':
         case 'ඕ':
         case 'ඖ':
            this.matchRange('අ', 'ඖ');
            break;
         case 'ක':
         case 'ඛ':
         case 'ග':
         case 'ඝ':
         case 'ඞ':
         case 'ඟ':
         case 'ච':
         case 'ඡ':
         case 'ජ':
         case 'ඣ':
         case 'ඤ':
         case 'ඥ':
         case 'ඦ':
         case 'ට':
         case 'ඨ':
         case 'ඩ':
         case 'ඪ':
         case 'ණ':
         case 'ඬ':
         case 'ත':
         case 'ථ':
         case 'ද':
         case 'ධ':
         case 'න':
            this.matchRange('ක', 'න');
            break;
         case 'ඳ':
         case 'ප':
         case 'ඵ':
         case 'බ':
         case 'භ':
         case 'ම':
         case 'ඹ':
         case 'ය':
         case 'ර':
            this.matchRange('ඳ', 'ර');
            break;
         case 'ල':
            this.matchRange('ල', 'ල');
            break;
         case 'ව':
         case 'ශ':
         case 'ෂ':
         case 'ස':
         case 'හ':
         case 'ළ':
         case 'ෆ':
            this.matchRange('ව', 'ෆ');
            break;
         case 'ก':
         case 'ข':
         case 'ฃ':
         case 'ค':
         case 'ฅ':
         case 'ฆ':
         case 'ง':
         case 'จ':
         case 'ฉ':
         case 'ช':
         case 'ซ':
         case 'ฌ':
         case 'ญ':
         case 'ฎ':
         case 'ฏ':
         case 'ฐ':
         case 'ฑ':
         case 'ฒ':
         case 'ณ':
         case 'ด':
         case 'ต':
         case 'ถ':
         case 'ท':
         case 'ธ':
         case 'น':
         case 'บ':
         case 'ป':
         case 'ผ':
         case 'ฝ':
         case 'พ':
         case 'ฟ':
         case 'ภ':
         case 'ม':
         case 'ย':
         case 'ร':
         case 'ฤ':
         case 'ล':
         case 'ฦ':
         case 'ว':
         case 'ศ':
         case 'ษ':
         case 'ส':
         case 'ห':
         case 'ฬ':
         case 'อ':
         case 'ฮ':
         case 'ฯ':
         case 'ะ':
            this.matchRange('ก', 'ะ');
            break;
         case 'า':
         case 'ำ':
            this.matchRange('า', 'ำ');
            break;
         case '฿':
         case 'เ':
         case 'แ':
         case 'โ':
         case 'ใ':
         case 'ไ':
         case 'ๅ':
         case 'ๆ':
            this.matchRange('฿', 'ๆ');
            break;
         case 'ກ':
         case 'ຂ':
            this.matchRange('ກ', 'ຂ');
            break;
         case 'ຄ':
            this.matchRange('ຄ', 'ຄ');
            break;
         case 'ງ':
         case 'ຈ':
            this.matchRange('ງ', 'ຈ');
            break;
         case 'ຊ':
            this.matchRange('ຊ', 'ຊ');
            break;
         case 'ຍ':
            this.matchRange('ຍ', 'ຍ');
            break;
         case 'ດ':
         case 'ຕ':
         case 'ຖ':
         case 'ທ':
            this.matchRange('ດ', 'ທ');
            break;
         case 'ນ':
         case 'ບ':
         case 'ປ':
         case 'ຜ':
         case 'ຝ':
         case 'ພ':
         case 'ຟ':
            this.matchRange('ນ', 'ຟ');
            break;
         case 'ມ':
         case 'ຢ':
         case 'ຣ':
            this.matchRange('ມ', 'ຣ');
            break;
         case 'ລ':
            this.matchRange('ລ', 'ລ');
            break;
         case 'ວ':
            this.matchRange('ວ', 'ວ');
            break;
         case 'ສ':
         case 'ຫ':
            this.matchRange('ສ', 'ຫ');
            break;
         case 'ອ':
         case 'ຮ':
         case 'ຯ':
         case 'ະ':
            this.matchRange('ອ', 'ະ');
            break;
         case 'າ':
         case 'ຳ':
            this.matchRange('າ', 'ຳ');
            break;
         case 'ຽ':
            this.matchRange('ຽ', 'ຽ');
            break;
         case 'ເ':
         case 'ແ':
         case 'ໂ':
         case 'ໃ':
         case 'ໄ':
            this.matchRange('ເ', 'ໄ');
            break;
         case 'ໆ':
            this.matchRange('ໆ', 'ໆ');
            break;
         case 'ໜ':
         case 'ໝ':
            this.matchRange('ໜ', 'ໝ');
            break;
         case 'ༀ':
            this.matchRange('ༀ', 'ༀ');
            break;
         case 'ཀ':
         case 'ཁ':
         case 'ག':
         case 'གྷ':
         case 'ང':
         case 'ཅ':
         case 'ཆ':
         case 'ཇ':
            this.matchRange('ཀ', 'ཇ');
            break;
         case 'ཉ':
         case 'ཊ':
         case 'ཋ':
         case 'ཌ':
         case 'ཌྷ':
         case 'ཎ':
         case 'ཏ':
         case 'ཐ':
         case 'ད':
         case 'དྷ':
         case 'ན':
         case 'པ':
         case 'ཕ':
         case 'བ':
         case 'བྷ':
         case 'མ':
         case 'ཙ':
         case 'ཚ':
         case 'ཛ':
         case 'ཛྷ':
         case 'ཝ':
         case 'ཞ':
         case 'ཟ':
         case 'འ':
         case 'ཡ':
         case 'ར':
         case 'ལ':
         case 'ཤ':
         case 'ཥ':
         case 'ས':
         case 'ཧ':
         case 'ཨ':
         case 'ཀྵ':
         case 'ཪ':
         case 'ཫ':
         case 'ཬ':
            this.matchRange('ཉ', 'ཬ');
            break;
         case 'ྈ':
         case 'ྉ':
         case 'ྊ':
         case 'ྋ':
         case 'ྌ':
            this.matchRange('ྈ', 'ྌ');
            break;
         case 'က':
         case 'ခ':
         case 'ဂ':
         case 'ဃ':
         case 'င':
         case 'စ':
         case 'ဆ':
         case 'ဇ':
         case 'ဈ':
         case 'ဉ':
         case 'ည':
         case 'ဋ':
         case 'ဌ':
         case 'ဍ':
         case 'ဎ':
         case 'ဏ':
         case 'တ':
         case 'ထ':
         case 'ဒ':
         case 'ဓ':
         case 'န':
         case 'ပ':
         case 'ဖ':
         case 'ဗ':
         case 'ဘ':
         case 'မ':
         case 'ယ':
         case 'ရ':
         case 'လ':
         case 'ဝ':
         case 'သ':
         case 'ဟ':
         case 'ဠ':
         case 'အ':
         case 'ဢ':
         case 'ဣ':
         case 'ဤ':
         case 'ဥ':
         case 'ဦ':
         case 'ဧ':
         case 'ဨ':
         case 'ဩ':
         case 'ဪ':
            this.matchRange('က', 'ဪ');
            break;
         case 'ဿ':
            this.matchRange('ဿ', 'ဿ');
            break;
         case 'ၐ':
         case 'ၑ':
         case 'ၒ':
         case 'ၓ':
         case 'ၔ':
         case 'ၕ':
            this.matchRange('ၐ', 'ၕ');
            break;
         case 'ၚ':
         case 'ၛ':
         case 'ၜ':
         case 'ၝ':
            this.matchRange('ၚ', 'ၝ');
            break;
         case 'ၡ':
            this.matchRange('ၡ', 'ၡ');
            break;
         case 'ၥ':
         case 'ၦ':
            this.matchRange('ၥ', 'ၦ');
            break;
         case 'ၮ':
         case 'ၯ':
         case 'ၰ':
            this.matchRange('ၮ', 'ၰ');
            break;
         case 'ၵ':
         case 'ၶ':
         case 'ၷ':
         case 'ၸ':
         case 'ၹ':
         case 'ၺ':
         case 'ၻ':
         case 'ၼ':
         case 'ၽ':
         case 'ၾ':
         case 'ၿ':
         case 'ႀ':
         case 'ႁ':
            this.matchRange('ၵ', 'ႁ');
            break;
         case 'ႎ':
            this.matchRange('ႎ', 'ႎ');
            break;
         case 'Ⴀ':
         case 'Ⴁ':
         case 'Ⴂ':
         case 'Ⴃ':
         case 'Ⴄ':
         case 'Ⴅ':
         case 'Ⴆ':
         case 'Ⴇ':
         case 'Ⴈ':
         case 'Ⴉ':
         case 'Ⴊ':
         case 'Ⴋ':
         case 'Ⴌ':
         case 'Ⴍ':
         case 'Ⴎ':
         case 'Ⴏ':
         case 'Ⴐ':
         case 'Ⴑ':
         case 'Ⴒ':
         case 'Ⴓ':
         case 'Ⴔ':
         case 'Ⴕ':
         case 'Ⴖ':
         case 'Ⴗ':
         case 'Ⴘ':
         case 'Ⴙ':
         case 'Ⴚ':
         case 'Ⴛ':
         case 'Ⴜ':
         case 'Ⴝ':
         case 'Ⴞ':
         case 'Ⴟ':
         case 'Ⴠ':
         case 'Ⴡ':
         case 'Ⴢ':
         case 'Ⴣ':
         case 'Ⴤ':
         case 'Ⴥ':
            this.matchRange('Ⴀ', 'Ⴥ');
            break;
         case 'ა':
         case 'ბ':
         case 'გ':
         case 'დ':
         case 'ე':
         case 'ვ':
         case 'ზ':
         case 'თ':
         case 'ი':
         case 'კ':
         case 'ლ':
         case 'მ':
         case 'ნ':
         case 'ო':
         case 'პ':
         case 'ჟ':
         case 'რ':
         case 'ს':
         case 'ტ':
         case 'უ':
         case 'ფ':
         case 'ქ':
         case 'ღ':
         case 'ყ':
         case 'შ':
         case 'ჩ':
         case 'ც':
         case 'ძ':
         case 'წ':
         case 'ჭ':
         case 'ხ':
         case 'ჯ':
         case 'ჰ':
         case 'ჱ':
         case 'ჲ':
         case 'ჳ':
         case 'ჴ':
         case 'ჵ':
         case 'ჶ':
         case 'ჷ':
         case 'ჸ':
         case 'ჹ':
         case 'ჺ':
            this.matchRange('ა', 'ჺ');
            break;
         case 'ჼ':
            this.matchRange('ჼ', 'ჼ');
            break;
         case 'ቊ':
         case 'ቋ':
         case 'ቌ':
         case 'ቍ':
            this.matchRange('ቊ', 'ቍ');
            break;
         case 'ቐ':
         case 'ቑ':
         case 'ቒ':
         case 'ቓ':
         case 'ቔ':
         case 'ቕ':
         case 'ቖ':
            this.matchRange('ቐ', 'ቖ');
            break;
         case 'ቘ':
            this.matchRange('ቘ', 'ቘ');
            break;
         case 'ቚ':
         case 'ቛ':
         case 'ቜ':
         case 'ቝ':
            this.matchRange('ቚ', 'ቝ');
            break;
         case 'በ':
         case 'ቡ':
         case 'ቢ':
         case 'ባ':
         case 'ቤ':
         case 'ብ':
         case 'ቦ':
         case 'ቧ':
         case 'ቨ':
         case 'ቩ':
         case 'ቪ':
         case 'ቫ':
         case 'ቬ':
         case 'ቭ':
         case 'ቮ':
         case 'ቯ':
         case 'ተ':
         case 'ቱ':
         case 'ቲ':
         case 'ታ':
         case 'ቴ':
         case 'ት':
         case 'ቶ':
         case 'ቷ':
         case 'ቸ':
         case 'ቹ':
         case 'ቺ':
         case 'ቻ':
         case 'ቼ':
         case 'ች':
         case 'ቾ':
         case 'ቿ':
         case 'ኀ':
         case 'ኁ':
         case 'ኂ':
         case 'ኃ':
         case 'ኄ':
         case 'ኅ':
         case 'ኆ':
         case 'ኇ':
         case 'ኈ':
            this.matchRange('በ', 'ኈ');
            break;
         case 'ኊ':
         case 'ኋ':
         case 'ኌ':
         case 'ኍ':
            this.matchRange('ኊ', 'ኍ');
            break;
         case 'ነ':
         case 'ኑ':
         case 'ኒ':
         case 'ና':
         case 'ኔ':
         case 'ን':
         case 'ኖ':
         case 'ኗ':
         case 'ኘ':
         case 'ኙ':
         case 'ኚ':
         case 'ኛ':
         case 'ኜ':
         case 'ኝ':
         case 'ኞ':
         case 'ኟ':
         case 'አ':
         case 'ኡ':
         case 'ኢ':
         case 'ኣ':
         case 'ኤ':
         case 'እ':
         case 'ኦ':
         case 'ኧ':
         case 'ከ':
         case 'ኩ':
         case 'ኪ':
         case 'ካ':
         case 'ኬ':
         case 'ክ':
         case 'ኮ':
         case 'ኯ':
         case 'ኰ':
            this.matchRange('ነ', 'ኰ');
            break;
         case 'ኲ':
         case 'ኳ':
         case 'ኴ':
         case 'ኵ':
            this.matchRange('ኲ', 'ኵ');
            break;
         case 'ኸ':
         case 'ኹ':
         case 'ኺ':
         case 'ኻ':
         case 'ኼ':
         case 'ኽ':
         case 'ኾ':
            this.matchRange('ኸ', 'ኾ');
            break;
         case 'ዀ':
            this.matchRange('ዀ', 'ዀ');
            break;
         case 'ዂ':
         case 'ዃ':
         case 'ዄ':
         case 'ዅ':
            this.matchRange('ዂ', 'ዅ');
            break;
         case 'ወ':
         case 'ዉ':
         case 'ዊ':
         case 'ዋ':
         case 'ዌ':
         case 'ው':
         case 'ዎ':
         case 'ዏ':
         case 'ዐ':
         case 'ዑ':
         case 'ዒ':
         case 'ዓ':
         case 'ዔ':
         case 'ዕ':
         case 'ዖ':
            this.matchRange('ወ', 'ዖ');
            break;
         case 'ዘ':
         case 'ዙ':
         case 'ዚ':
         case 'ዛ':
         case 'ዜ':
         case 'ዝ':
         case 'ዞ':
         case 'ዟ':
         case 'ዠ':
         case 'ዡ':
         case 'ዢ':
         case 'ዣ':
         case 'ዤ':
         case 'ዥ':
         case 'ዦ':
         case 'ዧ':
         case 'የ':
         case 'ዩ':
         case 'ዪ':
         case 'ያ':
         case 'ዬ':
         case 'ይ':
         case 'ዮ':
         case 'ዯ':
         case 'ደ':
         case 'ዱ':
         case 'ዲ':
         case 'ዳ':
         case 'ዴ':
         case 'ድ':
         case 'ዶ':
         case 'ዷ':
         case 'ዸ':
         case 'ዹ':
         case 'ዺ':
         case 'ዻ':
         case 'ዼ':
         case 'ዽ':
         case 'ዾ':
         case 'ዿ':
         case 'ጀ':
         case 'ጁ':
         case 'ጂ':
         case 'ጃ':
         case 'ጄ':
         case 'ጅ':
         case 'ጆ':
         case 'ጇ':
         case 'ገ':
         case 'ጉ':
         case 'ጊ':
         case 'ጋ':
         case 'ጌ':
         case 'ግ':
         case 'ጎ':
         case 'ጏ':
         case 'ጐ':
            this.matchRange('ዘ', 'ጐ');
            break;
         case 'ጒ':
         case 'ጓ':
         case 'ጔ':
         case 'ጕ':
            this.matchRange('ጒ', 'ጕ');
            break;
         case 'ጘ':
         case 'ጙ':
         case 'ጚ':
         case 'ጛ':
         case 'ጜ':
         case 'ጝ':
         case 'ጞ':
         case 'ጟ':
         case 'ጠ':
         case 'ጡ':
         case 'ጢ':
         case 'ጣ':
         case 'ጤ':
         case 'ጥ':
         case 'ጦ':
         case 'ጧ':
         case 'ጨ':
         case 'ጩ':
         case 'ጪ':
         case 'ጫ':
         case 'ጬ':
         case 'ጭ':
         case 'ጮ':
         case 'ጯ':
         case 'ጰ':
         case 'ጱ':
         case 'ጲ':
         case 'ጳ':
         case 'ጴ':
         case 'ጵ':
         case 'ጶ':
         case 'ጷ':
         case 'ጸ':
         case 'ጹ':
         case 'ጺ':
         case 'ጻ':
         case 'ጼ':
         case 'ጽ':
         case 'ጾ':
         case 'ጿ':
         case 'ፀ':
         case 'ፁ':
         case 'ፂ':
         case 'ፃ':
         case 'ፄ':
         case 'ፅ':
         case 'ፆ':
         case 'ፇ':
         case 'ፈ':
         case 'ፉ':
         case 'ፊ':
         case 'ፋ':
         case 'ፌ':
         case 'ፍ':
         case 'ፎ':
         case 'ፏ':
         case 'ፐ':
         case 'ፑ':
         case 'ፒ':
         case 'ፓ':
         case 'ፔ':
         case 'ፕ':
         case 'ፖ':
         case 'ፗ':
         case 'ፘ':
         case 'ፙ':
         case 'ፚ':
            this.matchRange('ጘ', 'ፚ');
            break;
         case 'ᎀ':
         case 'ᎁ':
         case 'ᎂ':
         case 'ᎃ':
         case 'ᎄ':
         case 'ᎅ':
         case 'ᎆ':
         case 'ᎇ':
         case 'ᎈ':
         case 'ᎉ':
         case 'ᎊ':
         case 'ᎋ':
         case 'ᎌ':
         case 'ᎍ':
         case 'ᎎ':
         case 'ᎏ':
            this.matchRange('ᎀ', 'ᎏ');
            break;
         case 'Ꭰ':
         case 'Ꭱ':
         case 'Ꭲ':
         case 'Ꭳ':
         case 'Ꭴ':
         case 'Ꭵ':
         case 'Ꭶ':
         case 'Ꭷ':
         case 'Ꭸ':
         case 'Ꭹ':
         case 'Ꭺ':
         case 'Ꭻ':
         case 'Ꭼ':
         case 'Ꭽ':
         case 'Ꭾ':
         case 'Ꭿ':
         case 'Ꮀ':
         case 'Ꮁ':
         case 'Ꮂ':
         case 'Ꮃ':
         case 'Ꮄ':
         case 'Ꮅ':
         case 'Ꮆ':
         case 'Ꮇ':
         case 'Ꮈ':
         case 'Ꮉ':
         case 'Ꮊ':
         case 'Ꮋ':
         case 'Ꮌ':
         case 'Ꮍ':
         case 'Ꮎ':
         case 'Ꮏ':
         case 'Ꮐ':
         case 'Ꮑ':
         case 'Ꮒ':
         case 'Ꮓ':
         case 'Ꮔ':
         case 'Ꮕ':
         case 'Ꮖ':
         case 'Ꮗ':
         case 'Ꮘ':
         case 'Ꮙ':
         case 'Ꮚ':
         case 'Ꮛ':
         case 'Ꮜ':
         case 'Ꮝ':
         case 'Ꮞ':
         case 'Ꮟ':
         case 'Ꮠ':
         case 'Ꮡ':
         case 'Ꮢ':
         case 'Ꮣ':
         case 'Ꮤ':
         case 'Ꮥ':
         case 'Ꮦ':
         case 'Ꮧ':
         case 'Ꮨ':
         case 'Ꮩ':
         case 'Ꮪ':
         case 'Ꮫ':
         case 'Ꮬ':
         case 'Ꮭ':
         case 'Ꮮ':
         case 'Ꮯ':
         case 'Ꮰ':
         case 'Ꮱ':
         case 'Ꮲ':
         case 'Ꮳ':
         case 'Ꮴ':
         case 'Ꮵ':
         case 'Ꮶ':
         case 'Ꮷ':
         case 'Ꮸ':
         case 'Ꮹ':
         case 'Ꮺ':
         case 'Ꮻ':
         case 'Ꮼ':
         case 'Ꮽ':
         case 'Ꮾ':
         case 'Ꮿ':
         case 'Ᏸ':
         case 'Ᏹ':
         case 'Ᏺ':
         case 'Ᏻ':
         case 'Ᏼ':
            this.matchRange('Ꭰ', 'Ᏼ');
            break;
         case 'ᙯ':
         case 'ᙰ':
         case 'ᙱ':
         case 'ᙲ':
         case 'ᙳ':
         case 'ᙴ':
         case 'ᙵ':
         case 'ᙶ':
         case 'ᙷ':
         case 'ᙸ':
         case 'ᙹ':
         case 'ᙺ':
         case 'ᙻ':
         case 'ᙼ':
         case 'ᙽ':
         case 'ᙾ':
         case 'ᙿ':
            this.matchRange('ᙯ', 'ᙿ');
            break;
         case 'ᚁ':
         case 'ᚂ':
         case 'ᚃ':
         case 'ᚄ':
         case 'ᚅ':
         case 'ᚆ':
         case 'ᚇ':
         case 'ᚈ':
         case 'ᚉ':
         case 'ᚊ':
         case 'ᚋ':
         case 'ᚌ':
         case 'ᚍ':
         case 'ᚎ':
         case 'ᚏ':
         case 'ᚐ':
         case 'ᚑ':
         case 'ᚒ':
         case 'ᚓ':
         case 'ᚔ':
         case 'ᚕ':
         case 'ᚖ':
         case 'ᚗ':
         case 'ᚘ':
         case 'ᚙ':
         case 'ᚚ':
            this.matchRange('ᚁ', 'ᚚ');
            break;
         case 'ᚠ':
         case 'ᚡ':
         case 'ᚢ':
         case 'ᚣ':
         case 'ᚤ':
         case 'ᚥ':
         case 'ᚦ':
         case 'ᚧ':
         case 'ᚨ':
         case 'ᚩ':
         case 'ᚪ':
         case 'ᚫ':
         case 'ᚬ':
         case 'ᚭ':
         case 'ᚮ':
         case 'ᚯ':
         case 'ᚰ':
         case 'ᚱ':
         case 'ᚲ':
         case 'ᚳ':
         case 'ᚴ':
         case 'ᚵ':
         case 'ᚶ':
         case 'ᚷ':
         case 'ᚸ':
         case 'ᚹ':
         case 'ᚺ':
         case 'ᚻ':
         case 'ᚼ':
         case 'ᚽ':
         case 'ᚾ':
         case 'ᚿ':
         case 'ᛀ':
         case 'ᛁ':
         case 'ᛂ':
         case 'ᛃ':
         case 'ᛄ':
         case 'ᛅ':
         case 'ᛆ':
         case 'ᛇ':
         case 'ᛈ':
         case 'ᛉ':
         case 'ᛊ':
         case 'ᛋ':
         case 'ᛌ':
         case 'ᛍ':
         case 'ᛎ':
         case 'ᛏ':
         case 'ᛐ':
         case 'ᛑ':
         case 'ᛒ':
         case 'ᛓ':
         case 'ᛔ':
         case 'ᛕ':
         case 'ᛖ':
         case 'ᛗ':
         case 'ᛘ':
         case 'ᛙ':
         case 'ᛚ':
         case 'ᛛ':
         case 'ᛜ':
         case 'ᛝ':
         case 'ᛞ':
         case 'ᛟ':
         case 'ᛠ':
         case 'ᛡ':
         case 'ᛢ':
         case 'ᛣ':
         case 'ᛤ':
         case 'ᛥ':
         case 'ᛦ':
         case 'ᛧ':
         case 'ᛨ':
         case 'ᛩ':
         case 'ᛪ':
            this.matchRange('ᚠ', 'ᛪ');
            break;
         case 'ᛮ':
         case 'ᛯ':
         case 'ᛰ':
            this.matchRange('ᛮ', 'ᛰ');
            break;
         case 'ᜀ':
         case 'ᜁ':
         case 'ᜂ':
         case 'ᜃ':
         case 'ᜄ':
         case 'ᜅ':
         case 'ᜆ':
         case 'ᜇ':
         case 'ᜈ':
         case 'ᜉ':
         case 'ᜊ':
         case 'ᜋ':
         case 'ᜌ':
            this.matchRange('ᜀ', 'ᜌ');
            break;
         case 'ᜎ':
         case 'ᜏ':
         case 'ᜐ':
         case 'ᜑ':
            this.matchRange('ᜎ', 'ᜑ');
            break;
         case 'ᜠ':
         case 'ᜡ':
         case 'ᜢ':
         case 'ᜣ':
         case 'ᜤ':
         case 'ᜥ':
         case 'ᜦ':
         case 'ᜧ':
         case 'ᜨ':
         case 'ᜩ':
         case 'ᜪ':
         case 'ᜫ':
         case 'ᜬ':
         case 'ᜭ':
         case 'ᜮ':
         case 'ᜯ':
         case 'ᜰ':
         case 'ᜱ':
            this.matchRange('ᜠ', 'ᜱ');
            break;
         case 'ᝀ':
         case 'ᝁ':
         case 'ᝂ':
         case 'ᝃ':
         case 'ᝄ':
         case 'ᝅ':
         case 'ᝆ':
         case 'ᝇ':
         case 'ᝈ':
         case 'ᝉ':
         case 'ᝊ':
         case 'ᝋ':
         case 'ᝌ':
         case 'ᝍ':
         case 'ᝎ':
         case 'ᝏ':
         case 'ᝐ':
         case 'ᝑ':
            this.matchRange('ᝀ', 'ᝑ');
            break;
         case 'ᝠ':
         case 'ᝡ':
         case 'ᝢ':
         case 'ᝣ':
         case 'ᝤ':
         case 'ᝥ':
         case 'ᝦ':
         case 'ᝧ':
         case 'ᝨ':
         case 'ᝩ':
         case 'ᝪ':
         case 'ᝫ':
         case 'ᝬ':
            this.matchRange('ᝠ', 'ᝬ');
            break;
         case 'ᝮ':
         case 'ᝯ':
         case 'ᝰ':
            this.matchRange('ᝮ', 'ᝰ');
            break;
         case 'ក':
         case 'ខ':
         case 'គ':
         case 'ឃ':
         case 'ង':
         case 'ច':
         case 'ឆ':
         case 'ជ':
         case 'ឈ':
         case 'ញ':
         case 'ដ':
         case 'ឋ':
         case 'ឌ':
         case 'ឍ':
         case 'ណ':
         case 'ត':
         case 'ថ':
         case 'ទ':
         case 'ធ':
         case 'ន':
         case 'ប':
         case 'ផ':
         case 'ព':
         case 'ភ':
         case 'ម':
         case 'យ':
         case 'រ':
         case 'ល':
         case 'វ':
         case 'ឝ':
         case 'ឞ':
         case 'ស':
         case 'ហ':
         case 'ឡ':
         case 'អ':
         case 'ឣ':
         case 'ឤ':
         case 'ឥ':
         case 'ឦ':
         case 'ឧ':
         case 'ឨ':
         case 'ឩ':
         case 'ឪ':
         case 'ឫ':
         case 'ឬ':
         case 'ឭ':
         case 'ឮ':
         case 'ឯ':
         case 'ឰ':
         case 'ឱ':
         case 'ឲ':
         case 'ឳ':
            this.matchRange('ក', 'ឳ');
            break;
         case 'ៗ':
            this.matchRange('ៗ', 'ៗ');
            break;
         case '៛':
         case 'ៜ':
            this.matchRange('៛', 'ៜ');
            break;
         case 'ᠠ':
         case 'ᠡ':
         case 'ᠢ':
         case 'ᠣ':
         case 'ᠤ':
         case 'ᠥ':
         case 'ᠦ':
         case 'ᠧ':
         case 'ᠨ':
         case 'ᠩ':
         case 'ᠪ':
         case 'ᠫ':
         case 'ᠬ':
         case 'ᠭ':
         case 'ᠮ':
         case 'ᠯ':
         case 'ᠰ':
         case 'ᠱ':
         case 'ᠲ':
         case 'ᠳ':
         case 'ᠴ':
         case 'ᠵ':
         case 'ᠶ':
         case 'ᠷ':
         case 'ᠸ':
         case 'ᠹ':
         case 'ᠺ':
         case 'ᠻ':
         case 'ᠼ':
         case 'ᠽ':
         case 'ᠾ':
         case 'ᠿ':
         case 'ᡀ':
         case 'ᡁ':
         case 'ᡂ':
         case 'ᡃ':
         case 'ᡄ':
         case 'ᡅ':
         case 'ᡆ':
         case 'ᡇ':
         case 'ᡈ':
         case 'ᡉ':
         case 'ᡊ':
         case 'ᡋ':
         case 'ᡌ':
         case 'ᡍ':
         case 'ᡎ':
         case 'ᡏ':
         case 'ᡐ':
         case 'ᡑ':
         case 'ᡒ':
         case 'ᡓ':
         case 'ᡔ':
         case 'ᡕ':
         case 'ᡖ':
         case 'ᡗ':
         case 'ᡘ':
         case 'ᡙ':
         case 'ᡚ':
         case 'ᡛ':
         case 'ᡜ':
         case 'ᡝ':
         case 'ᡞ':
         case 'ᡟ':
         case 'ᡠ':
         case 'ᡡ':
         case 'ᡢ':
         case 'ᡣ':
         case 'ᡤ':
         case 'ᡥ':
         case 'ᡦ':
         case 'ᡧ':
         case 'ᡨ':
         case 'ᡩ':
         case 'ᡪ':
         case 'ᡫ':
         case 'ᡬ':
         case 'ᡭ':
         case 'ᡮ':
         case 'ᡯ':
         case 'ᡰ':
         case 'ᡱ':
         case 'ᡲ':
         case 'ᡳ':
         case 'ᡴ':
         case 'ᡵ':
         case 'ᡶ':
         case 'ᡷ':
            this.matchRange('ᠠ', 'ᡷ');
            break;
         case 'ᢀ':
         case 'ᢁ':
         case 'ᢂ':
         case 'ᢃ':
         case 'ᢄ':
         case 'ᢅ':
         case 'ᢆ':
         case 'ᢇ':
         case 'ᢈ':
         case 'ᢉ':
         case 'ᢊ':
         case 'ᢋ':
         case 'ᢌ':
         case 'ᢍ':
         case 'ᢎ':
         case 'ᢏ':
         case 'ᢐ':
         case 'ᢑ':
         case 'ᢒ':
         case 'ᢓ':
         case 'ᢔ':
         case 'ᢕ':
         case 'ᢖ':
         case 'ᢗ':
         case 'ᢘ':
         case 'ᢙ':
         case 'ᢚ':
         case 'ᢛ':
         case 'ᢜ':
         case 'ᢝ':
         case 'ᢞ':
         case 'ᢟ':
         case 'ᢠ':
         case 'ᢡ':
         case 'ᢢ':
         case 'ᢣ':
         case 'ᢤ':
         case 'ᢥ':
         case 'ᢦ':
         case 'ᢧ':
         case 'ᢨ':
            this.matchRange('ᢀ', 'ᢨ');
            break;
         case 'ᢪ':
            this.matchRange('ᢪ', 'ᢪ');
            break;
         case 'ᢰ':
         case 'ᢱ':
         case 'ᢲ':
         case 'ᢳ':
         case 'ᢴ':
         case 'ᢵ':
         case 'ᢶ':
         case 'ᢷ':
         case 'ᢸ':
         case 'ᢹ':
         case 'ᢺ':
         case 'ᢻ':
         case 'ᢼ':
         case 'ᢽ':
         case 'ᢾ':
         case 'ᢿ':
         case 'ᣀ':
         case 'ᣁ':
         case 'ᣂ':
         case 'ᣃ':
         case 'ᣄ':
         case 'ᣅ':
         case 'ᣆ':
         case 'ᣇ':
         case 'ᣈ':
         case 'ᣉ':
         case 'ᣊ':
         case 'ᣋ':
         case 'ᣌ':
         case 'ᣍ':
         case 'ᣎ':
         case 'ᣏ':
         case 'ᣐ':
         case 'ᣑ':
         case 'ᣒ':
         case 'ᣓ':
         case 'ᣔ':
         case 'ᣕ':
         case 'ᣖ':
         case 'ᣗ':
         case 'ᣘ':
         case 'ᣙ':
         case 'ᣚ':
         case 'ᣛ':
         case 'ᣜ':
         case 'ᣝ':
         case 'ᣞ':
         case 'ᣟ':
         case 'ᣠ':
         case 'ᣡ':
         case 'ᣢ':
         case 'ᣣ':
         case 'ᣤ':
         case 'ᣥ':
         case 'ᣦ':
         case 'ᣧ':
         case 'ᣨ':
         case 'ᣩ':
         case 'ᣪ':
         case 'ᣫ':
         case 'ᣬ':
         case 'ᣭ':
         case 'ᣮ':
         case 'ᣯ':
         case 'ᣰ':
         case 'ᣱ':
         case 'ᣲ':
         case 'ᣳ':
         case 'ᣴ':
         case 'ᣵ':
            this.matchRange('ᢰ', 'ᣵ');
            break;
         case 'ᤀ':
         case 'ᤁ':
         case 'ᤂ':
         case 'ᤃ':
         case 'ᤄ':
         case 'ᤅ':
         case 'ᤆ':
         case 'ᤇ':
         case 'ᤈ':
         case 'ᤉ':
         case 'ᤊ':
         case 'ᤋ':
         case 'ᤌ':
         case 'ᤍ':
         case 'ᤎ':
         case 'ᤏ':
         case 'ᤐ':
         case 'ᤑ':
         case 'ᤒ':
         case 'ᤓ':
         case 'ᤔ':
         case 'ᤕ':
         case 'ᤖ':
         case 'ᤗ':
         case 'ᤘ':
         case 'ᤙ':
         case 'ᤚ':
         case 'ᤛ':
         case 'ᤜ':
            this.matchRange('ᤀ', 'ᤜ');
            break;
         case 'ᥐ':
         case 'ᥑ':
         case 'ᥒ':
         case 'ᥓ':
         case 'ᥔ':
         case 'ᥕ':
         case 'ᥖ':
         case 'ᥗ':
         case 'ᥘ':
         case 'ᥙ':
         case 'ᥚ':
         case 'ᥛ':
         case 'ᥜ':
         case 'ᥝ':
         case 'ᥞ':
         case 'ᥟ':
         case 'ᥠ':
         case 'ᥡ':
         case 'ᥢ':
         case 'ᥣ':
         case 'ᥤ':
         case 'ᥥ':
         case 'ᥦ':
         case 'ᥧ':
         case 'ᥨ':
         case 'ᥩ':
         case 'ᥪ':
         case 'ᥫ':
         case 'ᥬ':
         case 'ᥭ':
            this.matchRange('ᥐ', 'ᥭ');
            break;
         case 'ᥰ':
         case 'ᥱ':
         case 'ᥲ':
         case 'ᥳ':
         case 'ᥴ':
            this.matchRange('ᥰ', 'ᥴ');
            break;
         case 'ᦀ':
         case 'ᦁ':
         case 'ᦂ':
         case 'ᦃ':
         case 'ᦄ':
         case 'ᦅ':
         case 'ᦆ':
         case 'ᦇ':
         case 'ᦈ':
         case 'ᦉ':
         case 'ᦊ':
         case 'ᦋ':
         case 'ᦌ':
         case 'ᦍ':
         case 'ᦎ':
         case 'ᦏ':
         case 'ᦐ':
         case 'ᦑ':
         case 'ᦒ':
         case 'ᦓ':
         case 'ᦔ':
         case 'ᦕ':
         case 'ᦖ':
         case 'ᦗ':
         case 'ᦘ':
         case 'ᦙ':
         case 'ᦚ':
         case 'ᦛ':
         case 'ᦜ':
         case 'ᦝ':
         case 'ᦞ':
         case 'ᦟ':
         case 'ᦠ':
         case 'ᦡ':
         case 'ᦢ':
         case 'ᦣ':
         case 'ᦤ':
         case 'ᦥ':
         case 'ᦦ':
         case 'ᦧ':
         case 'ᦨ':
         case 'ᦩ':
         case 'ᦪ':
         case 'ᦫ':
            this.matchRange('ᦀ', 'ᦫ');
            break;
         case 'ᧁ':
         case 'ᧂ':
         case 'ᧃ':
         case 'ᧄ':
         case 'ᧅ':
         case 'ᧆ':
         case 'ᧇ':
            this.matchRange('ᧁ', 'ᧇ');
            break;
         case 'ᨀ':
         case 'ᨁ':
         case 'ᨂ':
         case 'ᨃ':
         case 'ᨄ':
         case 'ᨅ':
         case 'ᨆ':
         case 'ᨇ':
         case 'ᨈ':
         case 'ᨉ':
         case 'ᨊ':
         case 'ᨋ':
         case 'ᨌ':
         case 'ᨍ':
         case 'ᨎ':
         case 'ᨏ':
         case 'ᨐ':
         case 'ᨑ':
         case 'ᨒ':
         case 'ᨓ':
         case 'ᨔ':
         case 'ᨕ':
         case 'ᨖ':
            this.matchRange('ᨀ', 'ᨖ');
            break;
         case 'ᨠ':
         case 'ᨡ':
         case 'ᨢ':
         case 'ᨣ':
         case 'ᨤ':
         case 'ᨥ':
         case 'ᨦ':
         case 'ᨧ':
         case 'ᨨ':
         case 'ᨩ':
         case 'ᨪ':
         case 'ᨫ':
         case 'ᨬ':
         case 'ᨭ':
         case 'ᨮ':
         case 'ᨯ':
         case 'ᨰ':
         case 'ᨱ':
         case 'ᨲ':
         case 'ᨳ':
         case 'ᨴ':
         case 'ᨵ':
         case 'ᨶ':
         case 'ᨷ':
         case 'ᨸ':
         case 'ᨹ':
         case 'ᨺ':
         case 'ᨻ':
         case 'ᨼ':
         case 'ᨽ':
         case 'ᨾ':
         case 'ᨿ':
         case 'ᩀ':
         case 'ᩁ':
         case 'ᩂ':
         case 'ᩃ':
         case 'ᩄ':
         case 'ᩅ':
         case 'ᩆ':
         case 'ᩇ':
         case 'ᩈ':
         case 'ᩉ':
         case 'ᩊ':
         case 'ᩋ':
         case 'ᩌ':
         case 'ᩍ':
         case 'ᩎ':
         case 'ᩏ':
         case 'ᩐ':
         case 'ᩑ':
         case 'ᩒ':
         case 'ᩓ':
         case 'ᩔ':
            this.matchRange('ᨠ', 'ᩔ');
            break;
         case 'ᪧ':
            this.matchRange('ᪧ', 'ᪧ');
            break;
         case 'ᬅ':
         case 'ᬆ':
         case 'ᬇ':
         case 'ᬈ':
         case 'ᬉ':
         case 'ᬊ':
         case 'ᬋ':
         case 'ᬌ':
         case 'ᬍ':
         case 'ᬎ':
         case 'ᬏ':
         case 'ᬐ':
         case 'ᬑ':
         case 'ᬒ':
         case 'ᬓ':
         case 'ᬔ':
         case 'ᬕ':
         case 'ᬖ':
         case 'ᬗ':
         case 'ᬘ':
         case 'ᬙ':
         case 'ᬚ':
         case 'ᬛ':
         case 'ᬜ':
         case 'ᬝ':
         case 'ᬞ':
         case 'ᬟ':
         case 'ᬠ':
         case 'ᬡ':
         case 'ᬢ':
         case 'ᬣ':
         case 'ᬤ':
         case 'ᬥ':
         case 'ᬦ':
         case 'ᬧ':
         case 'ᬨ':
         case 'ᬩ':
         case 'ᬪ':
         case 'ᬫ':
         case 'ᬬ':
         case 'ᬭ':
         case 'ᬮ':
         case 'ᬯ':
         case 'ᬰ':
         case 'ᬱ':
         case 'ᬲ':
         case 'ᬳ':
            this.matchRange('ᬅ', 'ᬳ');
            break;
         case 'ᭅ':
         case 'ᭆ':
         case 'ᭇ':
         case 'ᭈ':
         case 'ᭉ':
         case 'ᭊ':
         case 'ᭋ':
            this.matchRange('ᭅ', 'ᭋ');
            break;
         case 'ᮃ':
         case 'ᮄ':
         case 'ᮅ':
         case 'ᮆ':
         case 'ᮇ':
         case 'ᮈ':
         case 'ᮉ':
         case 'ᮊ':
         case 'ᮋ':
         case 'ᮌ':
         case 'ᮍ':
         case 'ᮎ':
         case 'ᮏ':
         case 'ᮐ':
         case 'ᮑ':
         case 'ᮒ':
         case 'ᮓ':
         case 'ᮔ':
         case 'ᮕ':
         case 'ᮖ':
         case 'ᮗ':
         case 'ᮘ':
         case 'ᮙ':
         case 'ᮚ':
         case 'ᮛ':
         case 'ᮜ':
         case 'ᮝ':
         case 'ᮞ':
         case 'ᮟ':
         case 'ᮠ':
            this.matchRange('ᮃ', 'ᮠ');
            break;
         case 'ᮮ':
         case 'ᮯ':
            this.matchRange('ᮮ', 'ᮯ');
            break;
         case 'ᯀ':
         case 'ᯁ':
         case 'ᯂ':
         case 'ᯃ':
         case 'ᯄ':
         case 'ᯅ':
         case 'ᯆ':
         case 'ᯇ':
         case 'ᯈ':
         case 'ᯉ':
         case 'ᯊ':
         case 'ᯋ':
         case 'ᯌ':
         case 'ᯍ':
         case 'ᯎ':
         case 'ᯏ':
         case 'ᯐ':
         case 'ᯑ':
         case 'ᯒ':
         case 'ᯓ':
         case 'ᯔ':
         case 'ᯕ':
         case 'ᯖ':
         case 'ᯗ':
         case 'ᯘ':
         case 'ᯙ':
         case 'ᯚ':
         case 'ᯛ':
         case 'ᯜ':
         case 'ᯝ':
         case 'ᯞ':
         case 'ᯟ':
         case 'ᯠ':
         case 'ᯡ':
         case 'ᯢ':
         case 'ᯣ':
         case 'ᯤ':
         case 'ᯥ':
            this.matchRange('ᯀ', 'ᯥ');
            break;
         case 'ᰀ':
         case 'ᰁ':
         case 'ᰂ':
         case 'ᰃ':
         case 'ᰄ':
         case 'ᰅ':
         case 'ᰆ':
         case 'ᰇ':
         case 'ᰈ':
         case 'ᰉ':
         case 'ᰊ':
         case 'ᰋ':
         case 'ᰌ':
         case 'ᰍ':
         case 'ᰎ':
         case 'ᰏ':
         case 'ᰐ':
         case 'ᰑ':
         case 'ᰒ':
         case 'ᰓ':
         case 'ᰔ':
         case 'ᰕ':
         case 'ᰖ':
         case 'ᰗ':
         case 'ᰘ':
         case 'ᰙ':
         case 'ᰚ':
         case 'ᰛ':
         case 'ᰜ':
         case 'ᰝ':
         case 'ᰞ':
         case 'ᰟ':
         case 'ᰠ':
         case 'ᰡ':
         case 'ᰢ':
         case 'ᰣ':
            this.matchRange('ᰀ', 'ᰣ');
            break;
         case 'ᱍ':
         case 'ᱎ':
         case 'ᱏ':
            this.matchRange('ᱍ', 'ᱏ');
            break;
         case 'ᱚ':
         case 'ᱛ':
         case 'ᱜ':
         case 'ᱝ':
         case 'ᱞ':
         case 'ᱟ':
         case 'ᱠ':
         case 'ᱡ':
         case 'ᱢ':
         case 'ᱣ':
         case 'ᱤ':
         case 'ᱥ':
         case 'ᱦ':
         case 'ᱧ':
         case 'ᱨ':
         case 'ᱩ':
         case 'ᱪ':
         case 'ᱫ':
         case 'ᱬ':
         case 'ᱭ':
         case 'ᱮ':
         case 'ᱯ':
         case 'ᱰ':
         case 'ᱱ':
         case 'ᱲ':
         case 'ᱳ':
         case 'ᱴ':
         case 'ᱵ':
         case 'ᱶ':
         case 'ᱷ':
         case 'ᱸ':
         case 'ᱹ':
         case 'ᱺ':
         case 'ᱻ':
         case 'ᱼ':
         case 'ᱽ':
            this.matchRange('ᱚ', 'ᱽ');
            break;
         case 'ᳩ':
         case 'ᳪ':
         case 'ᳫ':
         case 'ᳬ':
            this.matchRange('ᳩ', 'ᳬ');
            break;
         case 'ᳮ':
         case 'ᳯ':
         case 'ᳰ':
         case 'ᳱ':
            this.matchRange('ᳮ', 'ᳱ');
            break;
         case 'Ἐ':
         case 'Ἑ':
         case 'Ἒ':
         case 'Ἓ':
         case 'Ἔ':
         case 'Ἕ':
            this.matchRange('Ἐ', 'Ἕ');
            break;
         case 'ἠ':
         case 'ἡ':
         case 'ἢ':
         case 'ἣ':
         case 'ἤ':
         case 'ἥ':
         case 'ἦ':
         case 'ἧ':
         case 'Ἠ':
         case 'Ἡ':
         case 'Ἢ':
         case 'Ἣ':
         case 'Ἤ':
         case 'Ἥ':
         case 'Ἦ':
         case 'Ἧ':
         case 'ἰ':
         case 'ἱ':
         case 'ἲ':
         case 'ἳ':
         case 'ἴ':
         case 'ἵ':
         case 'ἶ':
         case 'ἷ':
         case 'Ἰ':
         case 'Ἱ':
         case 'Ἲ':
         case 'Ἳ':
         case 'Ἴ':
         case 'Ἵ':
         case 'Ἶ':
         case 'Ἷ':
         case 'ὀ':
         case 'ὁ':
         case 'ὂ':
         case 'ὃ':
         case 'ὄ':
         case 'ὅ':
            this.matchRange('ἠ', 'ὅ');
            break;
         case 'Ὀ':
         case 'Ὁ':
         case 'Ὂ':
         case 'Ὃ':
         case 'Ὄ':
         case 'Ὅ':
            this.matchRange('Ὀ', 'Ὅ');
            break;
         case 'ὐ':
         case 'ὑ':
         case 'ὒ':
         case 'ὓ':
         case 'ὔ':
         case 'ὕ':
         case 'ὖ':
         case 'ὗ':
            this.matchRange('ὐ', 'ὗ');
            break;
         case 'Ὑ':
            this.matchRange('Ὑ', 'Ὑ');
            break;
         case 'Ὓ':
            this.matchRange('Ὓ', 'Ὓ');
            break;
         case 'Ὕ':
            this.matchRange('Ὕ', 'Ὕ');
            break;
         case 'Ὗ':
         case 'ὠ':
         case 'ὡ':
         case 'ὢ':
         case 'ὣ':
         case 'ὤ':
         case 'ὥ':
         case 'ὦ':
         case 'ὧ':
         case 'Ὠ':
         case 'Ὡ':
         case 'Ὢ':
         case 'Ὣ':
         case 'Ὤ':
         case 'Ὥ':
         case 'Ὦ':
         case 'Ὧ':
         case 'ὰ':
         case 'ά':
         case 'ὲ':
         case 'έ':
         case 'ὴ':
         case 'ή':
         case 'ὶ':
         case 'ί':
         case 'ὸ':
         case 'ό':
         case 'ὺ':
         case 'ύ':
         case 'ὼ':
         case 'ώ':
            this.matchRange('Ὗ', 'ώ');
            break;
         case 'ᾀ':
         case 'ᾁ':
         case 'ᾂ':
         case 'ᾃ':
         case 'ᾄ':
         case 'ᾅ':
         case 'ᾆ':
         case 'ᾇ':
         case 'ᾈ':
         case 'ᾉ':
         case 'ᾊ':
         case 'ᾋ':
         case 'ᾌ':
         case 'ᾍ':
         case 'ᾎ':
         case 'ᾏ':
         case 'ᾐ':
         case 'ᾑ':
         case 'ᾒ':
         case 'ᾓ':
         case 'ᾔ':
         case 'ᾕ':
         case 'ᾖ':
         case 'ᾗ':
         case 'ᾘ':
         case 'ᾙ':
         case 'ᾚ':
         case 'ᾛ':
         case 'ᾜ':
         case 'ᾝ':
         case 'ᾞ':
         case 'ᾟ':
         case 'ᾠ':
         case 'ᾡ':
         case 'ᾢ':
         case 'ᾣ':
         case 'ᾤ':
         case 'ᾥ':
         case 'ᾦ':
         case 'ᾧ':
         case 'ᾨ':
         case 'ᾩ':
         case 'ᾪ':
         case 'ᾫ':
         case 'ᾬ':
         case 'ᾭ':
         case 'ᾮ':
         case 'ᾯ':
         case 'ᾰ':
         case 'ᾱ':
         case 'ᾲ':
         case 'ᾳ':
         case 'ᾴ':
            this.matchRange('ᾀ', 'ᾴ');
            break;
         case 'ᾶ':
         case 'ᾷ':
         case 'Ᾰ':
         case 'Ᾱ':
         case 'Ὰ':
         case 'Ά':
         case 'ᾼ':
            this.matchRange('ᾶ', 'ᾼ');
            break;
         case 'ι':
            this.matchRange('ι', 'ι');
            break;
         case 'ῂ':
         case 'ῃ':
         case 'ῄ':
            this.matchRange('ῂ', 'ῄ');
            break;
         case 'ῆ':
         case 'ῇ':
         case 'Ὲ':
         case 'Έ':
         case 'Ὴ':
         case 'Ή':
         case 'ῌ':
            this.matchRange('ῆ', 'ῌ');
            break;
         case 'ῐ':
         case 'ῑ':
         case 'ῒ':
         case 'ΐ':
            this.matchRange('ῐ', 'ΐ');
            break;
         case 'ῖ':
         case 'ῗ':
         case 'Ῐ':
         case 'Ῑ':
         case 'Ὶ':
         case 'Ί':
            this.matchRange('ῖ', 'Ί');
            break;
         case 'ῠ':
         case 'ῡ':
         case 'ῢ':
         case 'ΰ':
         case 'ῤ':
         case 'ῥ':
         case 'ῦ':
         case 'ῧ':
         case 'Ῠ':
         case 'Ῡ':
         case 'Ὺ':
         case 'Ύ':
         case 'Ῥ':
            this.matchRange('ῠ', 'Ῥ');
            break;
         case 'ῲ':
         case 'ῳ':
         case 'ῴ':
            this.matchRange('ῲ', 'ῴ');
            break;
         case 'ῶ':
         case 'ῷ':
         case 'Ὸ':
         case 'Ό':
         case 'Ὼ':
         case 'Ώ':
         case 'ῼ':
            this.matchRange('ῶ', 'ῼ');
            break;
         case '‿':
         case '⁀':
            this.matchRange('‿', '⁀');
            break;
         case '⁔':
            this.matchRange('⁔', '⁔');
            break;
         case 'ⁱ':
            this.matchRange('ⁱ', 'ⁱ');
            break;
         case 'ⁿ':
            this.matchRange('ⁿ', 'ⁿ');
            break;
         case 'ₐ':
         case 'ₑ':
         case 'ₒ':
         case 'ₓ':
         case 'ₔ':
         case 'ₕ':
         case 'ₖ':
         case 'ₗ':
         case 'ₘ':
         case 'ₙ':
         case 'ₚ':
         case 'ₛ':
         case 'ₜ':
            this.matchRange('ₐ', 'ₜ');
            break;
         case '₠':
         case '₡':
         case '₢':
         case '₣':
         case '₤':
         case '₥':
         case '₦':
         case '₧':
         case '₨':
         case '₩':
         case '₪':
         case '₫':
         case '€':
         case '₭':
         case '₮':
         case '₯':
         case '₰':
         case '₱':
         case '₲':
         case '₳':
         case '₴':
         case '₵':
         case '₶':
         case '₷':
         case '₸':
         case '₹':
            this.matchRange('₠', '₹');
            break;
         case 'ℂ':
            this.matchRange('ℂ', 'ℂ');
            break;
         case 'ℇ':
            this.matchRange('ℇ', 'ℇ');
            break;
         case 'ℊ':
         case 'ℋ':
         case 'ℌ':
         case 'ℍ':
         case 'ℎ':
         case 'ℏ':
         case 'ℐ':
         case 'ℑ':
         case 'ℒ':
         case 'ℓ':
            this.matchRange('ℊ', 'ℓ');
            break;
         case 'ℕ':
            this.matchRange('ℕ', 'ℕ');
            break;
         case 'ℙ':
         case 'ℚ':
         case 'ℛ':
         case 'ℜ':
         case 'ℝ':
            this.matchRange('ℙ', 'ℝ');
            break;
         case 'ℤ':
            this.matchRange('ℤ', 'ℤ');
            break;
         case 'Ω':
            this.matchRange('Ω', 'Ω');
            break;
         case 'ℨ':
            this.matchRange('ℨ', 'ℨ');
            break;
         case 'K':
         case 'Å':
         case 'ℬ':
         case 'ℭ':
            this.matchRange('K', 'ℭ');
            break;
         case 'ℯ':
         case 'ℰ':
         case 'ℱ':
         case 'Ⅎ':
         case 'ℳ':
         case 'ℴ':
         case 'ℵ':
         case 'ℶ':
         case 'ℷ':
         case 'ℸ':
         case 'ℹ':
            this.matchRange('ℯ', 'ℹ');
            break;
         case 'ℼ':
         case 'ℽ':
         case 'ℾ':
         case 'ℿ':
            this.matchRange('ℼ', 'ℿ');
            break;
         case 'ⅅ':
         case 'ⅆ':
         case 'ⅇ':
         case 'ⅈ':
         case 'ⅉ':
            this.matchRange('ⅅ', 'ⅉ');
            break;
         case 'ⅎ':
            this.matchRange('ⅎ', 'ⅎ');
            break;
         case 'Ⅰ':
         case 'Ⅱ':
         case 'Ⅲ':
         case 'Ⅳ':
         case 'Ⅴ':
         case 'Ⅵ':
         case 'Ⅶ':
         case 'Ⅷ':
         case 'Ⅸ':
         case 'Ⅹ':
         case 'Ⅺ':
         case 'Ⅻ':
         case 'Ⅼ':
         case 'Ⅽ':
         case 'Ⅾ':
         case 'Ⅿ':
         case 'ⅰ':
         case 'ⅱ':
         case 'ⅲ':
         case 'ⅳ':
         case 'ⅴ':
         case 'ⅵ':
         case 'ⅶ':
         case 'ⅷ':
         case 'ⅸ':
         case 'ⅹ':
         case 'ⅺ':
         case 'ⅻ':
         case 'ⅼ':
         case 'ⅽ':
         case 'ⅾ':
         case 'ⅿ':
         case 'ↀ':
         case 'ↁ':
         case 'ↂ':
         case 'Ↄ':
         case 'ↄ':
         case 'ↅ':
         case 'ↆ':
         case 'ↇ':
         case 'ↈ':
            this.matchRange('Ⅰ', 'ↈ');
            break;
         case 'Ⰰ':
         case 'Ⰱ':
         case 'Ⰲ':
         case 'Ⰳ':
         case 'Ⰴ':
         case 'Ⰵ':
         case 'Ⰶ':
         case 'Ⰷ':
         case 'Ⰸ':
         case 'Ⰹ':
         case 'Ⰺ':
         case 'Ⰻ':
         case 'Ⰼ':
         case 'Ⰽ':
         case 'Ⰾ':
         case 'Ⰿ':
         case 'Ⱀ':
         case 'Ⱁ':
         case 'Ⱂ':
         case 'Ⱃ':
         case 'Ⱄ':
         case 'Ⱅ':
         case 'Ⱆ':
         case 'Ⱇ':
         case 'Ⱈ':
         case 'Ⱉ':
         case 'Ⱊ':
         case 'Ⱋ':
         case 'Ⱌ':
         case 'Ⱍ':
         case 'Ⱎ':
         case 'Ⱏ':
         case 'Ⱐ':
         case 'Ⱑ':
         case 'Ⱒ':
         case 'Ⱓ':
         case 'Ⱔ':
         case 'Ⱕ':
         case 'Ⱖ':
         case 'Ⱗ':
         case 'Ⱘ':
         case 'Ⱙ':
         case 'Ⱚ':
         case 'Ⱛ':
         case 'Ⱜ':
         case 'Ⱝ':
         case 'Ⱞ':
            this.matchRange('Ⰰ', 'Ⱞ');
            break;
         case 'ⰰ':
         case 'ⰱ':
         case 'ⰲ':
         case 'ⰳ':
         case 'ⰴ':
         case 'ⰵ':
         case 'ⰶ':
         case 'ⰷ':
         case 'ⰸ':
         case 'ⰹ':
         case 'ⰺ':
         case 'ⰻ':
         case 'ⰼ':
         case 'ⰽ':
         case 'ⰾ':
         case 'ⰿ':
         case 'ⱀ':
         case 'ⱁ':
         case 'ⱂ':
         case 'ⱃ':
         case 'ⱄ':
         case 'ⱅ':
         case 'ⱆ':
         case 'ⱇ':
         case 'ⱈ':
         case 'ⱉ':
         case 'ⱊ':
         case 'ⱋ':
         case 'ⱌ':
         case 'ⱍ':
         case 'ⱎ':
         case 'ⱏ':
         case 'ⱐ':
         case 'ⱑ':
         case 'ⱒ':
         case 'ⱓ':
         case 'ⱔ':
         case 'ⱕ':
         case 'ⱖ':
         case 'ⱗ':
         case 'ⱘ':
         case 'ⱙ':
         case 'ⱚ':
         case 'ⱛ':
         case 'ⱜ':
         case 'ⱝ':
         case 'ⱞ':
            this.matchRange('ⰰ', 'ⱞ');
            break;
         case 'Ⳬ':
         case 'ⳬ':
         case 'Ⳮ':
         case 'ⳮ':
            this.matchRange('Ⳬ', 'ⳮ');
            break;
         case 'ⴀ':
         case 'ⴁ':
         case 'ⴂ':
         case 'ⴃ':
         case 'ⴄ':
         case 'ⴅ':
         case 'ⴆ':
         case 'ⴇ':
         case 'ⴈ':
         case 'ⴉ':
         case 'ⴊ':
         case 'ⴋ':
         case 'ⴌ':
         case 'ⴍ':
         case 'ⴎ':
         case 'ⴏ':
         case 'ⴐ':
         case 'ⴑ':
         case 'ⴒ':
         case 'ⴓ':
         case 'ⴔ':
         case 'ⴕ':
         case 'ⴖ':
         case 'ⴗ':
         case 'ⴘ':
         case 'ⴙ':
         case 'ⴚ':
         case 'ⴛ':
         case 'ⴜ':
         case 'ⴝ':
         case 'ⴞ':
         case 'ⴟ':
         case 'ⴠ':
         case 'ⴡ':
         case 'ⴢ':
         case 'ⴣ':
         case 'ⴤ':
         case 'ⴥ':
            this.matchRange('ⴀ', 'ⴥ');
            break;
         case 'ⴰ':
         case 'ⴱ':
         case 'ⴲ':
         case 'ⴳ':
         case 'ⴴ':
         case 'ⴵ':
         case 'ⴶ':
         case 'ⴷ':
         case 'ⴸ':
         case 'ⴹ':
         case 'ⴺ':
         case 'ⴻ':
         case 'ⴼ':
         case 'ⴽ':
         case 'ⴾ':
         case 'ⴿ':
         case 'ⵀ':
         case 'ⵁ':
         case 'ⵂ':
         case 'ⵃ':
         case 'ⵄ':
         case 'ⵅ':
         case 'ⵆ':
         case 'ⵇ':
         case 'ⵈ':
         case 'ⵉ':
         case 'ⵊ':
         case 'ⵋ':
         case 'ⵌ':
         case 'ⵍ':
         case 'ⵎ':
         case 'ⵏ':
         case 'ⵐ':
         case 'ⵑ':
         case 'ⵒ':
         case 'ⵓ':
         case 'ⵔ':
         case 'ⵕ':
         case 'ⵖ':
         case 'ⵗ':
         case 'ⵘ':
         case 'ⵙ':
         case 'ⵚ':
         case 'ⵛ':
         case 'ⵜ':
         case 'ⵝ':
         case 'ⵞ':
         case 'ⵟ':
         case 'ⵠ':
         case 'ⵡ':
         case 'ⵢ':
         case 'ⵣ':
         case 'ⵤ':
         case 'ⵥ':
            this.matchRange('ⴰ', 'ⵥ');
            break;
         case 'ⵯ':
            this.matchRange('ⵯ', 'ⵯ');
            break;
         case 'ⶀ':
         case 'ⶁ':
         case 'ⶂ':
         case 'ⶃ':
         case 'ⶄ':
         case 'ⶅ':
         case 'ⶆ':
         case 'ⶇ':
         case 'ⶈ':
         case 'ⶉ':
         case 'ⶊ':
         case 'ⶋ':
         case 'ⶌ':
         case 'ⶍ':
         case 'ⶎ':
         case 'ⶏ':
         case 'ⶐ':
         case 'ⶑ':
         case 'ⶒ':
         case 'ⶓ':
         case 'ⶔ':
         case 'ⶕ':
         case 'ⶖ':
            this.matchRange('ⶀ', 'ⶖ');
            break;
         case 'ⶠ':
         case 'ⶡ':
         case 'ⶢ':
         case 'ⶣ':
         case 'ⶤ':
         case 'ⶥ':
         case 'ⶦ':
            this.matchRange('ⶠ', 'ⶦ');
            break;
         case 'ⶨ':
         case 'ⶩ':
         case 'ⶪ':
         case 'ⶫ':
         case 'ⶬ':
         case 'ⶭ':
         case 'ⶮ':
            this.matchRange('ⶨ', 'ⶮ');
            break;
         case 'ⶰ':
         case 'ⶱ':
         case 'ⶲ':
         case 'ⶳ':
         case 'ⶴ':
         case 'ⶵ':
         case 'ⶶ':
            this.matchRange('ⶰ', 'ⶶ');
            break;
         case 'ⶸ':
         case 'ⶹ':
         case 'ⶺ':
         case 'ⶻ':
         case 'ⶼ':
         case 'ⶽ':
         case 'ⶾ':
            this.matchRange('ⶸ', 'ⶾ');
            break;
         case 'ⷀ':
         case 'ⷁ':
         case 'ⷂ':
         case 'ⷃ':
         case 'ⷄ':
         case 'ⷅ':
         case 'ⷆ':
            this.matchRange('ⷀ', 'ⷆ');
            break;
         case 'ⷈ':
         case 'ⷉ':
         case 'ⷊ':
         case 'ⷋ':
         case 'ⷌ':
         case 'ⷍ':
         case 'ⷎ':
            this.matchRange('ⷈ', 'ⷎ');
            break;
         case 'ⷐ':
         case 'ⷑ':
         case 'ⷒ':
         case 'ⷓ':
         case 'ⷔ':
         case 'ⷕ':
         case 'ⷖ':
            this.matchRange('ⷐ', 'ⷖ');
            break;
         case 'ⷘ':
         case 'ⷙ':
         case 'ⷚ':
         case 'ⷛ':
         case 'ⷜ':
         case 'ⷝ':
         case 'ⷞ':
            this.matchRange('ⷘ', 'ⷞ');
            break;
         case 'ⸯ':
            this.matchRange('ⸯ', 'ⸯ');
            break;
         case '々':
         case '〆':
         case '〇':
            this.matchRange('々', '〇');
            break;
         case '〡':
         case '〢':
         case '〣':
         case '〤':
         case '〥':
         case '〦':
         case '〧':
         case '〨':
         case '〩':
            this.matchRange('〡', '〩');
            break;
         case '〱':
         case '〲':
         case '〳':
         case '〴':
         case '〵':
            this.matchRange('〱', '〵');
            break;
         case '〸':
         case '〹':
         case '〺':
         case '〻':
         case '〼':
            this.matchRange('〸', '〼');
            break;
         case 'ぁ':
         case 'あ':
         case 'ぃ':
         case 'い':
         case 'ぅ':
         case 'う':
         case 'ぇ':
         case 'え':
         case 'ぉ':
         case 'お':
         case 'か':
         case 'が':
         case 'き':
         case 'ぎ':
         case 'く':
         case 'ぐ':
         case 'け':
         case 'げ':
         case 'こ':
         case 'ご':
         case 'さ':
         case 'ざ':
         case 'し':
         case 'じ':
         case 'す':
         case 'ず':
         case 'せ':
         case 'ぜ':
         case 'そ':
         case 'ぞ':
         case 'た':
         case 'だ':
         case 'ち':
         case 'ぢ':
         case 'っ':
         case 'つ':
         case 'づ':
         case 'て':
         case 'で':
         case 'と':
         case 'ど':
         case 'な':
         case 'に':
         case 'ぬ':
         case 'ね':
         case 'の':
         case 'は':
         case 'ば':
         case 'ぱ':
         case 'ひ':
         case 'び':
         case 'ぴ':
         case 'ふ':
         case 'ぶ':
         case 'ぷ':
         case 'へ':
         case 'べ':
         case 'ぺ':
         case 'ほ':
         case 'ぼ':
         case 'ぽ':
         case 'ま':
         case 'み':
         case 'む':
         case 'め':
         case 'も':
         case 'ゃ':
         case 'や':
         case 'ゅ':
         case 'ゆ':
         case 'ょ':
         case 'よ':
         case 'ら':
         case 'り':
         case 'る':
         case 'れ':
         case 'ろ':
         case 'ゎ':
         case 'わ':
         case 'ゐ':
         case 'ゑ':
         case 'を':
         case 'ん':
         case 'ゔ':
         case 'ゕ':
         case 'ゖ':
            this.matchRange('ぁ', 'ゖ');
            break;
         case 'ゝ':
         case 'ゞ':
         case 'ゟ':
            this.matchRange('ゝ', 'ゟ');
            break;
         case 'ァ':
         case 'ア':
         case 'ィ':
         case 'イ':
         case 'ゥ':
         case 'ウ':
         case 'ェ':
         case 'エ':
         case 'ォ':
         case 'オ':
         case 'カ':
         case 'ガ':
         case 'キ':
         case 'ギ':
         case 'ク':
         case 'グ':
         case 'ケ':
         case 'ゲ':
         case 'コ':
         case 'ゴ':
         case 'サ':
         case 'ザ':
         case 'シ':
         case 'ジ':
         case 'ス':
         case 'ズ':
         case 'セ':
         case 'ゼ':
         case 'ソ':
         case 'ゾ':
         case 'タ':
         case 'ダ':
         case 'チ':
         case 'ヂ':
         case 'ッ':
         case 'ツ':
         case 'ヅ':
         case 'テ':
         case 'デ':
         case 'ト':
         case 'ド':
         case 'ナ':
         case 'ニ':
         case 'ヌ':
         case 'ネ':
         case 'ノ':
         case 'ハ':
         case 'バ':
         case 'パ':
         case 'ヒ':
         case 'ビ':
         case 'ピ':
         case 'フ':
         case 'ブ':
         case 'プ':
         case 'ヘ':
         case 'ベ':
         case 'ペ':
         case 'ホ':
         case 'ボ':
         case 'ポ':
         case 'マ':
         case 'ミ':
         case 'ム':
         case 'メ':
         case 'モ':
         case 'ャ':
         case 'ヤ':
         case 'ュ':
         case 'ユ':
         case 'ョ':
         case 'ヨ':
         case 'ラ':
         case 'リ':
         case 'ル':
         case 'レ':
         case 'ロ':
         case 'ヮ':
         case 'ワ':
         case 'ヰ':
         case 'ヱ':
         case 'ヲ':
         case 'ン':
         case 'ヴ':
         case 'ヵ':
         case 'ヶ':
         case 'ヷ':
         case 'ヸ':
         case 'ヹ':
         case 'ヺ':
            this.matchRange('ァ', 'ヺ');
            break;
         case 'ー':
         case 'ヽ':
         case 'ヾ':
         case 'ヿ':
            this.matchRange('ー', 'ヿ');
            break;
         case 'ㄅ':
         case 'ㄆ':
         case 'ㄇ':
         case 'ㄈ':
         case 'ㄉ':
         case 'ㄊ':
         case 'ㄋ':
         case 'ㄌ':
         case 'ㄍ':
         case 'ㄎ':
         case 'ㄏ':
         case 'ㄐ':
         case 'ㄑ':
         case 'ㄒ':
         case 'ㄓ':
         case 'ㄔ':
         case 'ㄕ':
         case 'ㄖ':
         case 'ㄗ':
         case 'ㄘ':
         case 'ㄙ':
         case 'ㄚ':
         case 'ㄛ':
         case 'ㄜ':
         case 'ㄝ':
         case 'ㄞ':
         case 'ㄟ':
         case 'ㄠ':
         case 'ㄡ':
         case 'ㄢ':
         case 'ㄣ':
         case 'ㄤ':
         case 'ㄥ':
         case 'ㄦ':
         case 'ㄧ':
         case 'ㄨ':
         case 'ㄩ':
         case 'ㄪ':
         case 'ㄫ':
         case 'ㄬ':
         case 'ㄭ':
            this.matchRange('ㄅ', 'ㄭ');
            break;
         case 'ㄱ':
         case 'ㄲ':
         case 'ㄳ':
         case 'ㄴ':
         case 'ㄵ':
         case 'ㄶ':
         case 'ㄷ':
         case 'ㄸ':
         case 'ㄹ':
         case 'ㄺ':
         case 'ㄻ':
         case 'ㄼ':
         case 'ㄽ':
         case 'ㄾ':
         case 'ㄿ':
         case 'ㅀ':
         case 'ㅁ':
         case 'ㅂ':
         case 'ㅃ':
         case 'ㅄ':
         case 'ㅅ':
         case 'ㅆ':
         case 'ㅇ':
         case 'ㅈ':
         case 'ㅉ':
         case 'ㅊ':
         case 'ㅋ':
         case 'ㅌ':
         case 'ㅍ':
         case 'ㅎ':
         case 'ㅏ':
         case 'ㅐ':
         case 'ㅑ':
         case 'ㅒ':
         case 'ㅓ':
         case 'ㅔ':
         case 'ㅕ':
         case 'ㅖ':
         case 'ㅗ':
         case 'ㅘ':
         case 'ㅙ':
         case 'ㅚ':
         case 'ㅛ':
         case 'ㅜ':
         case 'ㅝ':
         case 'ㅞ':
         case 'ㅟ':
         case 'ㅠ':
         case 'ㅡ':
         case 'ㅢ':
         case 'ㅣ':
         case 'ㅤ':
         case 'ㅥ':
         case 'ㅦ':
         case 'ㅧ':
         case 'ㅨ':
         case 'ㅩ':
         case 'ㅪ':
         case 'ㅫ':
         case 'ㅬ':
         case 'ㅭ':
         case 'ㅮ':
         case 'ㅯ':
         case 'ㅰ':
         case 'ㅱ':
         case 'ㅲ':
         case 'ㅳ':
         case 'ㅴ':
         case 'ㅵ':
         case 'ㅶ':
         case 'ㅷ':
         case 'ㅸ':
         case 'ㅹ':
         case 'ㅺ':
         case 'ㅻ':
         case 'ㅼ':
         case 'ㅽ':
         case 'ㅾ':
         case 'ㅿ':
         case 'ㆀ':
         case 'ㆁ':
         case 'ㆂ':
         case 'ㆃ':
         case 'ㆄ':
         case 'ㆅ':
         case 'ㆆ':
         case 'ㆇ':
         case 'ㆈ':
         case 'ㆉ':
         case 'ㆊ':
         case 'ㆋ':
         case 'ㆌ':
         case 'ㆍ':
         case 'ㆎ':
            this.matchRange('ㄱ', 'ㆎ');
            break;
         case 'ㆠ':
         case 'ㆡ':
         case 'ㆢ':
         case 'ㆣ':
         case 'ㆤ':
         case 'ㆥ':
         case 'ㆦ':
         case 'ㆧ':
         case 'ㆨ':
         case 'ㆩ':
         case 'ㆪ':
         case 'ㆫ':
         case 'ㆬ':
         case 'ㆭ':
         case 'ㆮ':
         case 'ㆯ':
         case 'ㆰ':
         case 'ㆱ':
         case 'ㆲ':
         case 'ㆳ':
         case 'ㆴ':
         case 'ㆵ':
         case 'ㆶ':
         case 'ㆷ':
         case 'ㆸ':
         case 'ㆹ':
         case 'ㆺ':
            this.matchRange('ㆠ', 'ㆺ');
            break;
         case 'ㇰ':
         case 'ㇱ':
         case 'ㇲ':
         case 'ㇳ':
         case 'ㇴ':
         case 'ㇵ':
         case 'ㇶ':
         case 'ㇷ':
         case 'ㇸ':
         case 'ㇹ':
         case 'ㇺ':
         case 'ㇻ':
         case 'ㇼ':
         case 'ㇽ':
         case 'ㇾ':
         case 'ㇿ':
            this.matchRange('ㇰ', 'ㇿ');
            break;
         case 'ꓐ':
         case 'ꓑ':
         case 'ꓒ':
         case 'ꓓ':
         case 'ꓔ':
         case 'ꓕ':
         case 'ꓖ':
         case 'ꓗ':
         case 'ꓘ':
         case 'ꓙ':
         case 'ꓚ':
         case 'ꓛ':
         case 'ꓜ':
         case 'ꓝ':
         case 'ꓞ':
         case 'ꓟ':
         case 'ꓠ':
         case 'ꓡ':
         case 'ꓢ':
         case 'ꓣ':
         case 'ꓤ':
         case 'ꓥ':
         case 'ꓦ':
         case 'ꓧ':
         case 'ꓨ':
         case 'ꓩ':
         case 'ꓪ':
         case 'ꓫ':
         case 'ꓬ':
         case 'ꓭ':
         case 'ꓮ':
         case 'ꓯ':
         case 'ꓰ':
         case 'ꓱ':
         case 'ꓲ':
         case 'ꓳ':
         case 'ꓴ':
         case 'ꓵ':
         case 'ꓶ':
         case 'ꓷ':
         case 'ꓸ':
         case 'ꓹ':
         case 'ꓺ':
         case 'ꓻ':
         case 'ꓼ':
         case 'ꓽ':
            this.matchRange('ꓐ', 'ꓽ');
            break;
         case 'ꘐ':
         case 'ꘑ':
         case 'ꘒ':
         case 'ꘓ':
         case 'ꘔ':
         case 'ꘕ':
         case 'ꘖ':
         case 'ꘗ':
         case 'ꘘ':
         case 'ꘙ':
         case 'ꘚ':
         case 'ꘛ':
         case 'ꘜ':
         case 'ꘝ':
         case 'ꘞ':
         case 'ꘟ':
            this.matchRange('ꘐ', 'ꘟ');
            break;
         case 'ꘪ':
         case 'ꘫ':
            this.matchRange('ꘪ', 'ꘫ');
            break;
         case 'Ꙁ':
         case 'ꙁ':
         case 'Ꙃ':
         case 'ꙃ':
         case 'Ꙅ':
         case 'ꙅ':
         case 'Ꙇ':
         case 'ꙇ':
         case 'Ꙉ':
         case 'ꙉ':
         case 'Ꙋ':
         case 'ꙋ':
         case 'Ꙍ':
         case 'ꙍ':
         case 'Ꙏ':
         case 'ꙏ':
         case 'Ꙑ':
         case 'ꙑ':
         case 'Ꙓ':
         case 'ꙓ':
         case 'Ꙕ':
         case 'ꙕ':
         case 'Ꙗ':
         case 'ꙗ':
         case 'Ꙙ':
         case 'ꙙ':
         case 'Ꙛ':
         case 'ꙛ':
         case 'Ꙝ':
         case 'ꙝ':
         case 'Ꙟ':
         case 'ꙟ':
         case 'Ꙡ':
         case 'ꙡ':
         case 'Ꙣ':
         case 'ꙣ':
         case 'Ꙥ':
         case 'ꙥ':
         case 'Ꙧ':
         case 'ꙧ':
         case 'Ꙩ':
         case 'ꙩ':
         case 'Ꙫ':
         case 'ꙫ':
         case 'Ꙭ':
         case 'ꙭ':
         case 'ꙮ':
            this.matchRange('Ꙁ', 'ꙮ');
            break;
         case 'ꙿ':
         case 'Ꚁ':
         case 'ꚁ':
         case 'Ꚃ':
         case 'ꚃ':
         case 'Ꚅ':
         case 'ꚅ':
         case 'Ꚇ':
         case 'ꚇ':
         case 'Ꚉ':
         case 'ꚉ':
         case 'Ꚋ':
         case 'ꚋ':
         case 'Ꚍ':
         case 'ꚍ':
         case 'Ꚏ':
         case 'ꚏ':
         case 'Ꚑ':
         case 'ꚑ':
         case 'Ꚓ':
         case 'ꚓ':
         case 'Ꚕ':
         case 'ꚕ':
         case 'Ꚗ':
         case 'ꚗ':
            this.matchRange('ꙿ', 'ꚗ');
            break;
         case 'ꚠ':
         case 'ꚡ':
         case 'ꚢ':
         case 'ꚣ':
         case 'ꚤ':
         case 'ꚥ':
         case 'ꚦ':
         case 'ꚧ':
         case 'ꚨ':
         case 'ꚩ':
         case 'ꚪ':
         case 'ꚫ':
         case 'ꚬ':
         case 'ꚭ':
         case 'ꚮ':
         case 'ꚯ':
         case 'ꚰ':
         case 'ꚱ':
         case 'ꚲ':
         case 'ꚳ':
         case 'ꚴ':
         case 'ꚵ':
         case 'ꚶ':
         case 'ꚷ':
         case 'ꚸ':
         case 'ꚹ':
         case 'ꚺ':
         case 'ꚻ':
         case 'ꚼ':
         case 'ꚽ':
         case 'ꚾ':
         case 'ꚿ':
         case 'ꛀ':
         case 'ꛁ':
         case 'ꛂ':
         case 'ꛃ':
         case 'ꛄ':
         case 'ꛅ':
         case 'ꛆ':
         case 'ꛇ':
         case 'ꛈ':
         case 'ꛉ':
         case 'ꛊ':
         case 'ꛋ':
         case 'ꛌ':
         case 'ꛍ':
         case 'ꛎ':
         case 'ꛏ':
         case 'ꛐ':
         case 'ꛑ':
         case 'ꛒ':
         case 'ꛓ':
         case 'ꛔ':
         case 'ꛕ':
         case 'ꛖ':
         case 'ꛗ':
         case 'ꛘ':
         case 'ꛙ':
         case 'ꛚ':
         case 'ꛛ':
         case 'ꛜ':
         case 'ꛝ':
         case 'ꛞ':
         case 'ꛟ':
         case 'ꛠ':
         case 'ꛡ':
         case 'ꛢ':
         case 'ꛣ':
         case 'ꛤ':
         case 'ꛥ':
         case 'ꛦ':
         case 'ꛧ':
         case 'ꛨ':
         case 'ꛩ':
         case 'ꛪ':
         case 'ꛫ':
         case 'ꛬ':
         case 'ꛭ':
         case 'ꛮ':
         case 'ꛯ':
            this.matchRange('ꚠ', 'ꛯ');
            break;
         case 'ꜗ':
         case 'ꜘ':
         case 'ꜙ':
         case 'ꜚ':
         case 'ꜛ':
         case 'ꜜ':
         case 'ꜝ':
         case 'ꜞ':
         case 'ꜟ':
            this.matchRange('ꜗ', 'ꜟ');
            break;
         case 'Ꜣ':
         case 'ꜣ':
         case 'Ꜥ':
         case 'ꜥ':
         case 'Ꜧ':
         case 'ꜧ':
         case 'Ꜩ':
         case 'ꜩ':
         case 'Ꜫ':
         case 'ꜫ':
         case 'Ꜭ':
         case 'ꜭ':
         case 'Ꜯ':
         case 'ꜯ':
         case 'ꜰ':
         case 'ꜱ':
         case 'Ꜳ':
         case 'ꜳ':
         case 'Ꜵ':
         case 'ꜵ':
         case 'Ꜷ':
         case 'ꜷ':
         case 'Ꜹ':
         case 'ꜹ':
         case 'Ꜻ':
         case 'ꜻ':
         case 'Ꜽ':
         case 'ꜽ':
         case 'Ꜿ':
         case 'ꜿ':
         case 'Ꝁ':
         case 'ꝁ':
         case 'Ꝃ':
         case 'ꝃ':
         case 'Ꝅ':
         case 'ꝅ':
         case 'Ꝇ':
         case 'ꝇ':
         case 'Ꝉ':
         case 'ꝉ':
         case 'Ꝋ':
         case 'ꝋ':
         case 'Ꝍ':
         case 'ꝍ':
         case 'Ꝏ':
         case 'ꝏ':
         case 'Ꝑ':
         case 'ꝑ':
         case 'Ꝓ':
         case 'ꝓ':
         case 'Ꝕ':
         case 'ꝕ':
         case 'Ꝗ':
         case 'ꝗ':
         case 'Ꝙ':
         case 'ꝙ':
         case 'Ꝛ':
         case 'ꝛ':
         case 'Ꝝ':
         case 'ꝝ':
         case 'Ꝟ':
         case 'ꝟ':
         case 'Ꝡ':
         case 'ꝡ':
         case 'Ꝣ':
         case 'ꝣ':
         case 'Ꝥ':
         case 'ꝥ':
         case 'Ꝧ':
         case 'ꝧ':
         case 'Ꝩ':
         case 'ꝩ':
         case 'Ꝫ':
         case 'ꝫ':
         case 'Ꝭ':
         case 'ꝭ':
         case 'Ꝯ':
         case 'ꝯ':
         case 'ꝰ':
         case 'ꝱ':
         case 'ꝲ':
         case 'ꝳ':
         case 'ꝴ':
         case 'ꝵ':
         case 'ꝶ':
         case 'ꝷ':
         case 'ꝸ':
         case 'Ꝺ':
         case 'ꝺ':
         case 'Ꝼ':
         case 'ꝼ':
         case 'Ᵹ':
         case 'Ꝿ':
         case 'ꝿ':
         case 'Ꞁ':
         case 'ꞁ':
         case 'Ꞃ':
         case 'ꞃ':
         case 'Ꞅ':
         case 'ꞅ':
         case 'Ꞇ':
         case 'ꞇ':
         case 'ꞈ':
            this.matchRange('Ꜣ', 'ꞈ');
            break;
         case 'Ꞌ':
         case 'ꞌ':
         case 'Ɥ':
         case 'ꞎ':
            this.matchRange('Ꞌ', 'ꞎ');
            break;
         case 'Ꞑ':
         case 'ꞑ':
            this.matchRange('Ꞑ', 'ꞑ');
            break;
         case 'Ꞡ':
         case 'ꞡ':
         case 'Ꞣ':
         case 'ꞣ':
         case 'Ꞥ':
         case 'ꞥ':
         case 'Ꞧ':
         case 'ꞧ':
         case 'Ꞩ':
         case 'ꞩ':
            this.matchRange('Ꞡ', 'ꞩ');
            break;
         case 'ꟺ':
         case 'ꟻ':
         case 'ꟼ':
         case 'ꟽ':
         case 'ꟾ':
         case 'ꟿ':
         case 'ꠀ':
         case 'ꠁ':
            this.matchRange('ꟺ', 'ꠁ');
            break;
         case 'ꠃ':
         case 'ꠄ':
         case 'ꠅ':
            this.matchRange('ꠃ', 'ꠅ');
            break;
         case 'ꠇ':
         case 'ꠈ':
         case 'ꠉ':
         case 'ꠊ':
            this.matchRange('ꠇ', 'ꠊ');
            break;
         case 'ꠌ':
         case 'ꠍ':
         case 'ꠎ':
         case 'ꠏ':
         case 'ꠐ':
         case 'ꠑ':
         case 'ꠒ':
         case 'ꠓ':
         case 'ꠔ':
         case 'ꠕ':
         case 'ꠖ':
         case 'ꠗ':
         case 'ꠘ':
         case 'ꠙ':
         case 'ꠚ':
         case 'ꠛ':
         case 'ꠜ':
         case 'ꠝ':
         case 'ꠞ':
         case 'ꠟ':
         case 'ꠠ':
         case 'ꠡ':
         case 'ꠢ':
            this.matchRange('ꠌ', 'ꠢ');
            break;
         case '꠸':
            this.matchRange('꠸', '꠸');
            break;
         case 'ꡀ':
         case 'ꡁ':
         case 'ꡂ':
         case 'ꡃ':
         case 'ꡄ':
         case 'ꡅ':
         case 'ꡆ':
         case 'ꡇ':
         case 'ꡈ':
         case 'ꡉ':
         case 'ꡊ':
         case 'ꡋ':
         case 'ꡌ':
         case 'ꡍ':
         case 'ꡎ':
         case 'ꡏ':
         case 'ꡐ':
         case 'ꡑ':
         case 'ꡒ':
         case 'ꡓ':
         case 'ꡔ':
         case 'ꡕ':
         case 'ꡖ':
         case 'ꡗ':
         case 'ꡘ':
         case 'ꡙ':
         case 'ꡚ':
         case 'ꡛ':
         case 'ꡜ':
         case 'ꡝ':
         case 'ꡞ':
         case 'ꡟ':
         case 'ꡠ':
         case 'ꡡ':
         case 'ꡢ':
         case 'ꡣ':
         case 'ꡤ':
         case 'ꡥ':
         case 'ꡦ':
         case 'ꡧ':
         case 'ꡨ':
         case 'ꡩ':
         case 'ꡪ':
         case 'ꡫ':
         case 'ꡬ':
         case 'ꡭ':
         case 'ꡮ':
         case 'ꡯ':
         case 'ꡰ':
         case 'ꡱ':
         case 'ꡲ':
         case 'ꡳ':
            this.matchRange('ꡀ', 'ꡳ');
            break;
         case 'ꢂ':
         case 'ꢃ':
         case 'ꢄ':
         case 'ꢅ':
         case 'ꢆ':
         case 'ꢇ':
         case 'ꢈ':
         case 'ꢉ':
         case 'ꢊ':
         case 'ꢋ':
         case 'ꢌ':
         case 'ꢍ':
         case 'ꢎ':
         case 'ꢏ':
         case 'ꢐ':
         case 'ꢑ':
         case 'ꢒ':
         case 'ꢓ':
         case 'ꢔ':
         case 'ꢕ':
         case 'ꢖ':
         case 'ꢗ':
         case 'ꢘ':
         case 'ꢙ':
         case 'ꢚ':
         case 'ꢛ':
         case 'ꢜ':
         case 'ꢝ':
         case 'ꢞ':
         case 'ꢟ':
         case 'ꢠ':
         case 'ꢡ':
         case 'ꢢ':
         case 'ꢣ':
         case 'ꢤ':
         case 'ꢥ':
         case 'ꢦ':
         case 'ꢧ':
         case 'ꢨ':
         case 'ꢩ':
         case 'ꢪ':
         case 'ꢫ':
         case 'ꢬ':
         case 'ꢭ':
         case 'ꢮ':
         case 'ꢯ':
         case 'ꢰ':
         case 'ꢱ':
         case 'ꢲ':
         case 'ꢳ':
            this.matchRange('ꢂ', 'ꢳ');
            break;
         case 'ꣲ':
         case 'ꣳ':
         case 'ꣴ':
         case 'ꣵ':
         case 'ꣶ':
         case 'ꣷ':
            this.matchRange('ꣲ', 'ꣷ');
            break;
         case 'ꣻ':
            this.matchRange('ꣻ', 'ꣻ');
            break;
         case 'ꤊ':
         case 'ꤋ':
         case 'ꤌ':
         case 'ꤍ':
         case 'ꤎ':
         case 'ꤏ':
         case 'ꤐ':
         case 'ꤑ':
         case 'ꤒ':
         case 'ꤓ':
         case 'ꤔ':
         case 'ꤕ':
         case 'ꤖ':
         case 'ꤗ':
         case 'ꤘ':
         case 'ꤙ':
         case 'ꤚ':
         case 'ꤛ':
         case 'ꤜ':
         case 'ꤝ':
         case 'ꤞ':
         case 'ꤟ':
         case 'ꤠ':
         case 'ꤡ':
         case 'ꤢ':
         case 'ꤣ':
         case 'ꤤ':
         case 'ꤥ':
            this.matchRange('ꤊ', 'ꤥ');
            break;
         case 'ꤰ':
         case 'ꤱ':
         case 'ꤲ':
         case 'ꤳ':
         case 'ꤴ':
         case 'ꤵ':
         case 'ꤶ':
         case 'ꤷ':
         case 'ꤸ':
         case 'ꤹ':
         case 'ꤺ':
         case 'ꤻ':
         case 'ꤼ':
         case 'ꤽ':
         case 'ꤾ':
         case 'ꤿ':
         case 'ꥀ':
         case 'ꥁ':
         case 'ꥂ':
         case 'ꥃ':
         case 'ꥄ':
         case 'ꥅ':
         case 'ꥆ':
            this.matchRange('ꤰ', 'ꥆ');
            break;
         case 'ꥠ':
         case 'ꥡ':
         case 'ꥢ':
         case 'ꥣ':
         case 'ꥤ':
         case 'ꥥ':
         case 'ꥦ':
         case 'ꥧ':
         case 'ꥨ':
         case 'ꥩ':
         case 'ꥪ':
         case 'ꥫ':
         case 'ꥬ':
         case 'ꥭ':
         case 'ꥮ':
         case 'ꥯ':
         case 'ꥰ':
         case 'ꥱ':
         case 'ꥲ':
         case 'ꥳ':
         case 'ꥴ':
         case 'ꥵ':
         case 'ꥶ':
         case 'ꥷ':
         case 'ꥸ':
         case 'ꥹ':
         case 'ꥺ':
         case 'ꥻ':
         case 'ꥼ':
            this.matchRange('ꥠ', 'ꥼ');
            break;
         case 'ꦄ':
         case 'ꦅ':
         case 'ꦆ':
         case 'ꦇ':
         case 'ꦈ':
         case 'ꦉ':
         case 'ꦊ':
         case 'ꦋ':
         case 'ꦌ':
         case 'ꦍ':
         case 'ꦎ':
         case 'ꦏ':
         case 'ꦐ':
         case 'ꦑ':
         case 'ꦒ':
         case 'ꦓ':
         case 'ꦔ':
         case 'ꦕ':
         case 'ꦖ':
         case 'ꦗ':
         case 'ꦘ':
         case 'ꦙ':
         case 'ꦚ':
         case 'ꦛ':
         case 'ꦜ':
         case 'ꦝ':
         case 'ꦞ':
         case 'ꦟ':
         case 'ꦠ':
         case 'ꦡ':
         case 'ꦢ':
         case 'ꦣ':
         case 'ꦤ':
         case 'ꦥ':
         case 'ꦦ':
         case 'ꦧ':
         case 'ꦨ':
         case 'ꦩ':
         case 'ꦪ':
         case 'ꦫ':
         case 'ꦬ':
         case 'ꦭ':
         case 'ꦮ':
         case 'ꦯ':
         case 'ꦰ':
         case 'ꦱ':
         case 'ꦲ':
            this.matchRange('ꦄ', 'ꦲ');
            break;
         case 'ꧏ':
            this.matchRange('ꧏ', 'ꧏ');
            break;
         case 'ꨀ':
         case 'ꨁ':
         case 'ꨂ':
         case 'ꨃ':
         case 'ꨄ':
         case 'ꨅ':
         case 'ꨆ':
         case 'ꨇ':
         case 'ꨈ':
         case 'ꨉ':
         case 'ꨊ':
         case 'ꨋ':
         case 'ꨌ':
         case 'ꨍ':
         case 'ꨎ':
         case 'ꨏ':
         case 'ꨐ':
         case 'ꨑ':
         case 'ꨒ':
         case 'ꨓ':
         case 'ꨔ':
         case 'ꨕ':
         case 'ꨖ':
         case 'ꨗ':
         case 'ꨘ':
         case 'ꨙ':
         case 'ꨚ':
         case 'ꨛ':
         case 'ꨜ':
         case 'ꨝ':
         case 'ꨞ':
         case 'ꨟ':
         case 'ꨠ':
         case 'ꨡ':
         case 'ꨢ':
         case 'ꨣ':
         case 'ꨤ':
         case 'ꨥ':
         case 'ꨦ':
         case 'ꨧ':
         case 'ꨨ':
            this.matchRange('ꨀ', 'ꨨ');
            break;
         case 'ꩀ':
         case 'ꩁ':
         case 'ꩂ':
            this.matchRange('ꩀ', 'ꩂ');
            break;
         case 'ꩄ':
         case 'ꩅ':
         case 'ꩆ':
         case 'ꩇ':
         case 'ꩈ':
         case 'ꩉ':
         case 'ꩊ':
         case 'ꩋ':
            this.matchRange('ꩄ', 'ꩋ');
            break;
         case 'ꩠ':
         case 'ꩡ':
         case 'ꩢ':
         case 'ꩣ':
         case 'ꩤ':
         case 'ꩥ':
         case 'ꩦ':
         case 'ꩧ':
         case 'ꩨ':
         case 'ꩩ':
         case 'ꩪ':
         case 'ꩫ':
         case 'ꩬ':
         case 'ꩭ':
         case 'ꩮ':
         case 'ꩯ':
         case 'ꩰ':
         case 'ꩱ':
         case 'ꩲ':
         case 'ꩳ':
         case 'ꩴ':
         case 'ꩵ':
         case 'ꩶ':
            this.matchRange('ꩠ', 'ꩶ');
            break;
         case 'ꩺ':
            this.matchRange('ꩺ', 'ꩺ');
            break;
         case 'ꪀ':
         case 'ꪁ':
         case 'ꪂ':
         case 'ꪃ':
         case 'ꪄ':
         case 'ꪅ':
         case 'ꪆ':
         case 'ꪇ':
         case 'ꪈ':
         case 'ꪉ':
         case 'ꪊ':
         case 'ꪋ':
         case 'ꪌ':
         case 'ꪍ':
         case 'ꪎ':
         case 'ꪏ':
         case 'ꪐ':
         case 'ꪑ':
         case 'ꪒ':
         case 'ꪓ':
         case 'ꪔ':
         case 'ꪕ':
         case 'ꪖ':
         case 'ꪗ':
         case 'ꪘ':
         case 'ꪙ':
         case 'ꪚ':
         case 'ꪛ':
         case 'ꪜ':
         case 'ꪝ':
         case 'ꪞ':
         case 'ꪟ':
         case 'ꪠ':
         case 'ꪡ':
         case 'ꪢ':
         case 'ꪣ':
         case 'ꪤ':
         case 'ꪥ':
         case 'ꪦ':
         case 'ꪧ':
         case 'ꪨ':
         case 'ꪩ':
         case 'ꪪ':
         case 'ꪫ':
         case 'ꪬ':
         case 'ꪭ':
         case 'ꪮ':
         case 'ꪯ':
            this.matchRange('ꪀ', 'ꪯ');
            break;
         case 'ꪱ':
            this.matchRange('ꪱ', 'ꪱ');
            break;
         case 'ꪵ':
         case 'ꪶ':
            this.matchRange('ꪵ', 'ꪶ');
            break;
         case 'ꪹ':
         case 'ꪺ':
         case 'ꪻ':
         case 'ꪼ':
         case 'ꪽ':
            this.matchRange('ꪹ', 'ꪽ');
            break;
         case 'ꫀ':
            this.matchRange('ꫀ', 'ꫀ');
            break;
         case 'ꫂ':
            this.matchRange('ꫂ', 'ꫂ');
            break;
         case 'ꫛ':
         case 'ꫜ':
         case 'ꫝ':
            this.matchRange('ꫛ', 'ꫝ');
            break;
         case 'ꬁ':
         case 'ꬂ':
         case 'ꬃ':
         case 'ꬄ':
         case 'ꬅ':
         case 'ꬆ':
            this.matchRange('ꬁ', 'ꬆ');
            break;
         case 'ꬉ':
         case 'ꬊ':
         case 'ꬋ':
         case 'ꬌ':
         case 'ꬍ':
         case 'ꬎ':
            this.matchRange('ꬉ', 'ꬎ');
            break;
         case 'ꬑ':
         case 'ꬒ':
         case 'ꬓ':
         case 'ꬔ':
         case 'ꬕ':
         case 'ꬖ':
            this.matchRange('ꬑ', 'ꬖ');
            break;
         case 'ꬠ':
         case 'ꬡ':
         case 'ꬢ':
         case 'ꬣ':
         case 'ꬤ':
         case 'ꬥ':
         case 'ꬦ':
            this.matchRange('ꬠ', 'ꬦ');
            break;
         case 'ꬨ':
         case 'ꬩ':
         case 'ꬪ':
         case 'ꬫ':
         case 'ꬬ':
         case 'ꬭ':
         case 'ꬮ':
            this.matchRange('ꬨ', 'ꬮ');
            break;
         case 'ꯀ':
         case 'ꯁ':
         case 'ꯂ':
         case 'ꯃ':
         case 'ꯄ':
         case 'ꯅ':
         case 'ꯆ':
         case 'ꯇ':
         case 'ꯈ':
         case 'ꯉ':
         case 'ꯊ':
         case 'ꯋ':
         case 'ꯌ':
         case 'ꯍ':
         case 'ꯎ':
         case 'ꯏ':
         case 'ꯐ':
         case 'ꯑ':
         case 'ꯒ':
         case 'ꯓ':
         case 'ꯔ':
         case 'ꯕ':
         case 'ꯖ':
         case 'ꯗ':
         case 'ꯘ':
         case 'ꯙ':
         case 'ꯚ':
         case 'ꯛ':
         case 'ꯜ':
         case 'ꯝ':
         case 'ꯞ':
         case 'ꯟ':
         case 'ꯠ':
         case 'ꯡ':
         case 'ꯢ':
            this.matchRange('ꯀ', 'ꯢ');
            break;
         case 'ힰ':
         case 'ힱ':
         case 'ힲ':
         case 'ힳ':
         case 'ힴ':
         case 'ힵ':
         case 'ힶ':
         case 'ힷ':
         case 'ힸ':
         case 'ힹ':
         case 'ힺ':
         case 'ힻ':
         case 'ힼ':
         case 'ힽ':
         case 'ힾ':
         case 'ힿ':
         case 'ퟀ':
         case 'ퟁ':
         case 'ퟂ':
         case 'ퟃ':
         case 'ퟄ':
         case 'ퟅ':
         case 'ퟆ':
            this.matchRange('ힰ', 'ퟆ');
            break;
         case 'ퟋ':
         case 'ퟌ':
         case 'ퟍ':
         case 'ퟎ':
         case 'ퟏ':
         case 'ퟐ':
         case 'ퟑ':
         case 'ퟒ':
         case 'ퟓ':
         case 'ퟔ':
         case 'ퟕ':
         case 'ퟖ':
         case 'ퟗ':
         case 'ퟘ':
         case 'ퟙ':
         case 'ퟚ':
         case 'ퟛ':
         case 'ퟜ':
         case 'ퟝ':
         case 'ퟞ':
         case 'ퟟ':
         case 'ퟠ':
         case 'ퟡ':
         case 'ퟢ':
         case 'ퟣ':
         case 'ퟤ':
         case 'ퟥ':
         case 'ퟦ':
         case 'ퟧ':
         case 'ퟨ':
         case 'ퟩ':
         case 'ퟪ':
         case 'ퟫ':
         case 'ퟬ':
         case 'ퟭ':
         case 'ퟮ':
         case 'ퟯ':
         case 'ퟰ':
         case 'ퟱ':
         case 'ퟲ':
         case 'ퟳ':
         case 'ퟴ':
         case 'ퟵ':
         case 'ퟶ':
         case 'ퟷ':
         case 'ퟸ':
         case 'ퟹ':
         case 'ퟺ':
         case 'ퟻ':
            this.matchRange('ퟋ', 'ퟻ');
            break;
         case '侮':
         case '僧':
         case '免':
         case '勉':
         case '勤':
         case '卑':
         case '喝':
         case '嘆':
         case '器':
         case '塀':
         case '墨':
         case '層':
         case '屮':
         case '悔':
         case '慨':
         case '憎':
         case '懲':
         case '敏':
         case '既':
         case '暑':
         case '梅':
         case '海':
         case '渚':
         case '漢':
         case '煮':
         case '爫':
         case '琢':
         case '碑':
         case '社':
         case '祉':
         case '祈':
         case '祐':
         case '祖':
         case '祝':
         case '禍':
         case '禎':
         case '穀':
         case '突':
         case '節':
         case '練':
         case '縉':
         case '繁':
         case '署':
         case '者':
         case '臭':
         case '艹':
         case '艹':
         case '著':
         case '褐':
         case '視':
         case '謁':
         case '謹':
         case '賓':
         case '贈':
         case '辶':
         case '逸':
         case '難':
         case '響':
         case '頻':
         case '恵':
         case '𤋮':
         case '舘':
            this.matchRange('侮', '舘');
            break;
         case '並':
         case '况':
         case '全':
         case '侀':
         case '充':
         case '冀':
         case '勇':
         case '勺':
         case '喝':
         case '啕':
         case '喙':
         case '嗢':
         case '塚':
         case '墳':
         case '奄':
         case '奔':
         case '婢':
         case '嬨':
         case '廒':
         case '廙':
         case '彩':
         case '徭':
         case '惘':
         case '慎':
         case '愈':
         case '憎':
         case '慠':
         case '懲':
         case '戴':
         case '揄':
         case '搜':
         case '摒':
         case '敖':
         case '晴':
         case '朗':
         case '望':
         case '杖':
         case '歹':
         case '殺':
         case '流':
         case '滛':
         case '滋':
         case '漢':
         case '瀞':
         case '煮':
         case '瞧':
         case '爵':
         case '犯':
         case '猪':
         case '瑱':
         case '甆':
         case '画':
         case '瘝':
         case '瘟':
         case '益':
         case '盛':
         case '直':
         case '睊':
         case '着':
         case '磌':
         case '窱':
         case '節':
         case '类':
         case '絛':
         case '練':
         case '缾':
         case '者':
         case '荒':
         case '華':
         case '蝹':
         case '襁':
         case '覆':
         case '視':
         case '調':
         case '諸':
         case '請':
         case '謁':
         case '諾':
         case '諭':
         case '謹':
         case '變':
         case '贈':
         case '輸':
         case '遲':
         case '醙':
         case '鉶':
         case '陼':
         case '難':
         case '靖':
         case '韛':
         case '響':
         case '頋':
         case '頻':
         case '鬒':
         case '龜':
         case '𢡊':
         case '𢡄':
         case '𣏕':
         case '㮝':
         case '䀘':
         case '䀹':
         case '𥉉':
         case '𥳐':
         case '𧻓':
         case '齃':
         case '龎':
            this.matchRange('並', '龎');
            break;
         case 'ﬀ':
         case 'ﬁ':
         case 'ﬂ':
         case 'ﬃ':
         case 'ﬄ':
         case 'ﬅ':
         case 'ﬆ':
            this.matchRange('ﬀ', 'ﬆ');
            break;
         case 'ﬓ':
         case 'ﬔ':
         case 'ﬕ':
         case 'ﬖ':
         case 'ﬗ':
            this.matchRange('ﬓ', 'ﬗ');
            break;
         case 'יִ':
            this.matchRange('יִ', 'יִ');
            break;
         case 'ײַ':
         case 'ﬠ':
         case 'ﬡ':
         case 'ﬢ':
         case 'ﬣ':
         case 'ﬤ':
         case 'ﬥ':
         case 'ﬦ':
         case 'ﬧ':
         case 'ﬨ':
            this.matchRange('ײַ', 'ﬨ');
            break;
         case 'שׁ':
         case 'שׂ':
         case 'שּׁ':
         case 'שּׂ':
         case 'אַ':
         case 'אָ':
         case 'אּ':
         case 'בּ':
         case 'גּ':
         case 'דּ':
         case 'הּ':
         case 'וּ':
         case 'זּ':
            this.matchRange('שׁ', 'זּ');
            break;
         case 'טּ':
         case 'יּ':
         case 'ךּ':
         case 'כּ':
         case 'לּ':
            this.matchRange('טּ', 'לּ');
            break;
         case 'מּ':
            this.matchRange('מּ', 'מּ');
            break;
         case 'נּ':
         case 'סּ':
            this.matchRange('נּ', 'סּ');
            break;
         case 'ףּ':
         case 'פּ':
            this.matchRange('ףּ', 'פּ');
            break;
         case 'צּ':
         case 'קּ':
         case 'רּ':
         case 'שּ':
         case 'תּ':
         case 'וֹ':
         case 'בֿ':
         case 'כֿ':
         case 'פֿ':
         case 'ﭏ':
         case 'ﭐ':
         case 'ﭑ':
         case 'ﭒ':
         case 'ﭓ':
         case 'ﭔ':
         case 'ﭕ':
         case 'ﭖ':
         case 'ﭗ':
         case 'ﭘ':
         case 'ﭙ':
         case 'ﭚ':
         case 'ﭛ':
         case 'ﭜ':
         case 'ﭝ':
         case 'ﭞ':
         case 'ﭟ':
         case 'ﭠ':
         case 'ﭡ':
         case 'ﭢ':
         case 'ﭣ':
         case 'ﭤ':
         case 'ﭥ':
         case 'ﭦ':
         case 'ﭧ':
         case 'ﭨ':
         case 'ﭩ':
         case 'ﭪ':
         case 'ﭫ':
         case 'ﭬ':
         case 'ﭭ':
         case 'ﭮ':
         case 'ﭯ':
         case 'ﭰ':
         case 'ﭱ':
         case 'ﭲ':
         case 'ﭳ':
         case 'ﭴ':
         case 'ﭵ':
         case 'ﭶ':
         case 'ﭷ':
         case 'ﭸ':
         case 'ﭹ':
         case 'ﭺ':
         case 'ﭻ':
         case 'ﭼ':
         case 'ﭽ':
         case 'ﭾ':
         case 'ﭿ':
         case 'ﮀ':
         case 'ﮁ':
         case 'ﮂ':
         case 'ﮃ':
         case 'ﮄ':
         case 'ﮅ':
         case 'ﮆ':
         case 'ﮇ':
         case 'ﮈ':
         case 'ﮉ':
         case 'ﮊ':
         case 'ﮋ':
         case 'ﮌ':
         case 'ﮍ':
         case 'ﮎ':
         case 'ﮏ':
         case 'ﮐ':
         case 'ﮑ':
         case 'ﮒ':
         case 'ﮓ':
         case 'ﮔ':
         case 'ﮕ':
         case 'ﮖ':
         case 'ﮗ':
         case 'ﮘ':
         case 'ﮙ':
         case 'ﮚ':
         case 'ﮛ':
         case 'ﮜ':
         case 'ﮝ':
         case 'ﮞ':
         case 'ﮟ':
         case 'ﮠ':
         case 'ﮡ':
         case 'ﮢ':
         case 'ﮣ':
         case 'ﮤ':
         case 'ﮥ':
         case 'ﮦ':
         case 'ﮧ':
         case 'ﮨ':
         case 'ﮩ':
         case 'ﮪ':
         case 'ﮫ':
         case 'ﮬ':
         case 'ﮭ':
         case 'ﮮ':
         case 'ﮯ':
         case 'ﮰ':
         case 'ﮱ':
            this.matchRange('צּ', 'ﮱ');
            break;
         case 'ﵐ':
         case 'ﵑ':
         case 'ﵒ':
         case 'ﵓ':
         case 'ﵔ':
         case 'ﵕ':
         case 'ﵖ':
         case 'ﵗ':
         case 'ﵘ':
         case 'ﵙ':
         case 'ﵚ':
         case 'ﵛ':
         case 'ﵜ':
         case 'ﵝ':
         case 'ﵞ':
         case 'ﵟ':
         case 'ﵠ':
         case 'ﵡ':
         case 'ﵢ':
         case 'ﵣ':
         case 'ﵤ':
         case 'ﵥ':
         case 'ﵦ':
         case 'ﵧ':
         case 'ﵨ':
         case 'ﵩ':
         case 'ﵪ':
         case 'ﵫ':
         case 'ﵬ':
         case 'ﵭ':
         case 'ﵮ':
         case 'ﵯ':
         case 'ﵰ':
         case 'ﵱ':
         case 'ﵲ':
         case 'ﵳ':
         case 'ﵴ':
         case 'ﵵ':
         case 'ﵶ':
         case 'ﵷ':
         case 'ﵸ':
         case 'ﵹ':
         case 'ﵺ':
         case 'ﵻ':
         case 'ﵼ':
         case 'ﵽ':
         case 'ﵾ':
         case 'ﵿ':
         case 'ﶀ':
         case 'ﶁ':
         case 'ﶂ':
         case 'ﶃ':
         case 'ﶄ':
         case 'ﶅ':
         case 'ﶆ':
         case 'ﶇ':
         case 'ﶈ':
         case 'ﶉ':
         case 'ﶊ':
         case 'ﶋ':
         case 'ﶌ':
         case 'ﶍ':
         case 'ﶎ':
         case 'ﶏ':
            this.matchRange('ﵐ', 'ﶏ');
            break;
         case 'ﶒ':
         case 'ﶓ':
         case 'ﶔ':
         case 'ﶕ':
         case 'ﶖ':
         case 'ﶗ':
         case 'ﶘ':
         case 'ﶙ':
         case 'ﶚ':
         case 'ﶛ':
         case 'ﶜ':
         case 'ﶝ':
         case 'ﶞ':
         case 'ﶟ':
         case 'ﶠ':
         case 'ﶡ':
         case 'ﶢ':
         case 'ﶣ':
         case 'ﶤ':
         case 'ﶥ':
         case 'ﶦ':
         case 'ﶧ':
         case 'ﶨ':
         case 'ﶩ':
         case 'ﶪ':
         case 'ﶫ':
         case 'ﶬ':
         case 'ﶭ':
         case 'ﶮ':
         case 'ﶯ':
         case 'ﶰ':
         case 'ﶱ':
         case 'ﶲ':
         case 'ﶳ':
         case 'ﶴ':
         case 'ﶵ':
         case 'ﶶ':
         case 'ﶷ':
         case 'ﶸ':
         case 'ﶹ':
         case 'ﶺ':
         case 'ﶻ':
         case 'ﶼ':
         case 'ﶽ':
         case 'ﶾ':
         case 'ﶿ':
         case 'ﷀ':
         case 'ﷁ':
         case 'ﷂ':
         case 'ﷃ':
         case 'ﷄ':
         case 'ﷅ':
         case 'ﷆ':
         case 'ﷇ':
            this.matchRange('ﶒ', 'ﷇ');
            break;
         case 'ﷰ':
         case 'ﷱ':
         case 'ﷲ':
         case 'ﷳ':
         case 'ﷴ':
         case 'ﷵ':
         case 'ﷶ':
         case 'ﷷ':
         case 'ﷸ':
         case 'ﷹ':
         case 'ﷺ':
         case 'ﷻ':
         case '﷼':
            this.matchRange('ﷰ', '﷼');
            break;
         case '︳':
         case '︴':
            this.matchRange('︳', '︴');
            break;
         case '﹍':
         case '﹎':
         case '﹏':
            this.matchRange('﹍', '﹏');
            break;
         case '﹩':
            this.matchRange('﹩', '﹩');
            break;
         case 'ﹰ':
         case 'ﹱ':
         case 'ﹲ':
         case 'ﹳ':
         case 'ﹴ':
            this.matchRange('ﹰ', 'ﹴ');
            break;
         case '＄':
            this.matchRange('＄', '＄');
            break;
         case 'Ａ':
         case 'Ｂ':
         case 'Ｃ':
         case 'Ｄ':
         case 'Ｅ':
         case 'Ｆ':
         case 'Ｇ':
         case 'Ｈ':
         case 'Ｉ':
         case 'Ｊ':
         case 'Ｋ':
         case 'Ｌ':
         case 'Ｍ':
         case 'Ｎ':
         case 'Ｏ':
         case 'Ｐ':
         case 'Ｑ':
         case 'Ｒ':
         case 'Ｓ':
         case 'Ｔ':
         case 'Ｕ':
         case 'Ｖ':
         case 'Ｗ':
         case 'Ｘ':
         case 'Ｙ':
         case 'Ｚ':
            this.matchRange('Ａ', 'Ｚ');
            break;
         case '＿':
            this.matchRange('＿', '＿');
            break;
         case 'ａ':
         case 'ｂ':
         case 'ｃ':
         case 'ｄ':
         case 'ｅ':
         case 'ｆ':
         case 'ｇ':
         case 'ｈ':
         case 'ｉ':
         case 'ｊ':
         case 'ｋ':
         case 'ｌ':
         case 'ｍ':
         case 'ｎ':
         case 'ｏ':
         case 'ｐ':
         case 'ｑ':
         case 'ｒ':
         case 'ｓ':
         case 'ｔ':
         case 'ｕ':
         case 'ｖ':
         case 'ｗ':
         case 'ｘ':
         case 'ｙ':
         case 'ｚ':
            this.matchRange('ａ', 'ｚ');
            break;
         case 'ｦ':
         case 'ｧ':
         case 'ｨ':
         case 'ｩ':
         case 'ｪ':
         case 'ｫ':
         case 'ｬ':
         case 'ｭ':
         case 'ｮ':
         case 'ｯ':
         case 'ｰ':
         case 'ｱ':
         case 'ｲ':
         case 'ｳ':
         case 'ｴ':
         case 'ｵ':
         case 'ｶ':
         case 'ｷ':
         case 'ｸ':
         case 'ｹ':
         case 'ｺ':
         case 'ｻ':
         case 'ｼ':
         case 'ｽ':
         case 'ｾ':
         case 'ｿ':
         case 'ﾀ':
         case 'ﾁ':
         case 'ﾂ':
         case 'ﾃ':
         case 'ﾄ':
         case 'ﾅ':
         case 'ﾆ':
         case 'ﾇ':
         case 'ﾈ':
         case 'ﾉ':
         case 'ﾊ':
         case 'ﾋ':
         case 'ﾌ':
         case 'ﾍ':
         case 'ﾎ':
         case 'ﾏ':
         case 'ﾐ':
         case 'ﾑ':
         case 'ﾒ':
         case 'ﾓ':
         case 'ﾔ':
         case 'ﾕ':
         case 'ﾖ':
         case 'ﾗ':
         case 'ﾘ':
         case 'ﾙ':
         case 'ﾚ':
         case 'ﾛ':
         case 'ﾜ':
         case 'ﾝ':
         case 'ﾞ':
         case 'ﾟ':
         case 'ﾠ':
         case 'ﾡ':
         case 'ﾢ':
         case 'ﾣ':
         case 'ﾤ':
         case 'ﾥ':
         case 'ﾦ':
         case 'ﾧ':
         case 'ﾨ':
         case 'ﾩ':
         case 'ﾪ':
         case 'ﾫ':
         case 'ﾬ':
         case 'ﾭ':
         case 'ﾮ':
         case 'ﾯ':
         case 'ﾰ':
         case 'ﾱ':
         case 'ﾲ':
         case 'ﾳ':
         case 'ﾴ':
         case 'ﾵ':
         case 'ﾶ':
         case 'ﾷ':
         case 'ﾸ':
         case 'ﾹ':
         case 'ﾺ':
         case 'ﾻ':
         case 'ﾼ':
         case 'ﾽ':
         case 'ﾾ':
            this.matchRange('ｦ', 'ﾾ');
            break;
         case 'ￂ':
         case 'ￃ':
         case 'ￄ':
         case 'ￅ':
         case 'ￆ':
         case 'ￇ':
            this.matchRange('ￂ', 'ￇ');
            break;
         case 'ￊ':
         case 'ￋ':
         case 'ￌ':
         case 'ￍ':
         case 'ￎ':
         case 'ￏ':
            this.matchRange('ￊ', 'ￏ');
            break;
         case 'ￒ':
         case 'ￓ':
         case 'ￔ':
         case 'ￕ':
         case 'ￖ':
         case 'ￗ':
            this.matchRange('ￒ', 'ￗ');
            break;
         case 'ￚ':
         case 'ￛ':
         case 'ￜ':
            this.matchRange('ￚ', 'ￜ');
            break;
         case '￠':
         case '￡':
            this.matchRange('￠', '￡');
            break;
         case '￥':
         case '￦':
            this.matchRange('￥', '￦');
            break;
         default:
            if (this.LA(1) >= 248 && this.LA(1) <= 705) {
               this.matchRange('ø', 'ˁ');
            } else if (this.LA(1) >= 1015 && this.LA(1) <= 1153) {
               this.matchRange('Ϸ', 'ҁ');
            } else if (this.LA(1) >= 1162 && this.LA(1) <= 1319) {
               this.matchRange('Ҋ', 'ԧ');
            } else if (this.LA(1) >= 4352 && this.LA(1) <= 4680) {
               this.matchRange('ᄀ', 'ቈ');
            } else if (this.LA(1) >= 5121 && this.LA(1) <= 5740) {
               this.matchRange('ᐁ', 'ᙬ');
            } else if (this.LA(1) >= 7424 && this.LA(1) <= 7615) {
               this.matchRange('ᴀ', 'ᶿ');
            } else if (this.LA(1) >= 7680 && this.LA(1) <= 7957) {
               this.matchRange('Ḁ', 'ἕ');
            } else if (this.LA(1) >= 11360 && this.LA(1) <= 11492) {
               this.matchRange('Ⱡ', 'ⳤ');
            } else if (this.LA(1) >= 13312 && this.LA(1) <= 19893) {
               this.matchRange('㐀', '䶵');
            } else if (this.LA(1) >= 19968 && this.LA(1) <= '鿋') {
               this.matchRange('一', '鿋');
            } else if (this.LA(1) >= 'ꀀ' && this.LA(1) <= 'ꒌ') {
               this.matchRange('ꀀ', 'ꒌ');
            } else if (this.LA(1) >= 'ꔀ' && this.LA(1) <= 'ꘌ') {
               this.matchRange('ꔀ', 'ꘌ');
            } else if (this.LA(1) >= '가' && this.LA(1) <= '힣') {
               this.matchRange('가', '힣');
            } else if (this.LA(1) >= '豈' && this.LA(1) <= '鶴') {
               this.matchRange('豈', '鶴');
            } else if (this.LA(1) >= 'ﯓ' && this.LA(1) <= 'ﴽ') {
               this.matchRange('ﯓ', 'ﴽ');
            } else {
               if (this.LA(1) < 'ﹶ' || this.LA(1) > 'ﻼ') {
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.matchRange('ﹶ', 'ﻼ');
            }
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
      int _ttype = 34;
      this.matchRange('0', '9');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mUNICODEJAVAIDPART(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 38;
      switch (this.LA(1)) {
         case '\u0000':
         case '\u0001':
         case '\u0002':
         case '\u0003':
         case '\u0004':
         case '\u0005':
         case '\u0006':
         case '\u0007':
         case '\b':
            this.matchRange('\u0000', '\b');
            break;
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
            this.matchRange('\u000e', '\u001b');
            break;
         case '$':
            this.matchRange('$', '$');
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
            this.matchRange('_', '_');
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
            break;
         case '\u007f':
         case '\u0080':
         case '\u0081':
         case '\u0082':
         case '\u0083':
         case '\u0084':
         case '\u0085':
         case '\u0086':
         case '\u0087':
         case '\u0088':
         case '\u0089':
         case '\u008a':
         case '\u008b':
         case '\u008c':
         case '\u008d':
         case '\u008e':
         case '\u008f':
         case '\u0090':
         case '\u0091':
         case '\u0092':
         case '\u0093':
         case '\u0094':
         case '\u0095':
         case '\u0096':
         case '\u0097':
         case '\u0098':
         case '\u0099':
         case '\u009a':
         case '\u009b':
         case '\u009c':
         case '\u009d':
         case '\u009e':
         case '\u009f':
            this.matchRange('\u007f', '\u009f');
            break;
         case '¢':
         case '£':
         case '¤':
         case '¥':
            this.matchRange('¢', '¥');
            break;
         case 'ª':
            this.matchRange('ª', 'ª');
            break;
         case '\u00ad':
            this.matchRange('\u00ad', '\u00ad');
            break;
         case 'µ':
            this.matchRange('µ', 'µ');
            break;
         case 'º':
            this.matchRange('º', 'º');
            break;
         case 'À':
         case 'Á':
         case 'Â':
         case 'Ã':
         case 'Ä':
         case 'Å':
         case 'Æ':
         case 'Ç':
         case 'È':
         case 'É':
         case 'Ê':
         case 'Ë':
         case 'Ì':
         case 'Í':
         case 'Î':
         case 'Ï':
         case 'Ð':
         case 'Ñ':
         case 'Ò':
         case 'Ó':
         case 'Ô':
         case 'Õ':
         case 'Ö':
            this.matchRange('À', 'Ö');
            break;
         case 'Ø':
         case 'Ù':
         case 'Ú':
         case 'Û':
         case 'Ü':
         case 'Ý':
         case 'Þ':
         case 'ß':
         case 'à':
         case 'á':
         case 'â':
         case 'ã':
         case 'ä':
         case 'å':
         case 'æ':
         case 'ç':
         case 'è':
         case 'é':
         case 'ê':
         case 'ë':
         case 'ì':
         case 'í':
         case 'î':
         case 'ï':
         case 'ð':
         case 'ñ':
         case 'ò':
         case 'ó':
         case 'ô':
         case 'õ':
         case 'ö':
            this.matchRange('Ø', 'ö');
            break;
         case 'ˆ':
         case 'ˇ':
         case 'ˈ':
         case 'ˉ':
         case 'ˊ':
         case 'ˋ':
         case 'ˌ':
         case 'ˍ':
         case 'ˎ':
         case 'ˏ':
         case 'ː':
         case 'ˑ':
            this.matchRange('ˆ', 'ˑ');
            break;
         case 'ˠ':
         case 'ˡ':
         case 'ˢ':
         case 'ˣ':
         case 'ˤ':
            this.matchRange('ˠ', 'ˤ');
            break;
         case 'ˬ':
            this.matchRange('ˬ', 'ˬ');
            break;
         case 'ˮ':
            this.matchRange('ˮ', 'ˮ');
            break;
         case '̀':
         case '́':
         case '̂':
         case '̃':
         case '̄':
         case '̅':
         case '̆':
         case '̇':
         case '̈':
         case '̉':
         case '̊':
         case '̋':
         case '̌':
         case '̍':
         case '̎':
         case '̏':
         case '̐':
         case '̑':
         case '̒':
         case '̓':
         case '̔':
         case '̕':
         case '̖':
         case '̗':
         case '̘':
         case '̙':
         case '̚':
         case '̛':
         case '̜':
         case '̝':
         case '̞':
         case '̟':
         case '̠':
         case '̡':
         case '̢':
         case '̣':
         case '̤':
         case '̥':
         case '̦':
         case '̧':
         case '̨':
         case '̩':
         case '̪':
         case '̫':
         case '̬':
         case '̭':
         case '̮':
         case '̯':
         case '̰':
         case '̱':
         case '̲':
         case '̳':
         case '̴':
         case '̵':
         case '̶':
         case '̷':
         case '̸':
         case '̹':
         case '̺':
         case '̻':
         case '̼':
         case '̽':
         case '̾':
         case '̿':
         case '̀':
         case '́':
         case '͂':
         case '̓':
         case '̈́':
         case 'ͅ':
         case '͆':
         case '͇':
         case '͈':
         case '͉':
         case '͊':
         case '͋':
         case '͌':
         case '͍':
         case '͎':
         case '͏':
         case '͐':
         case '͑':
         case '͒':
         case '͓':
         case '͔':
         case '͕':
         case '͖':
         case '͗':
         case '͘':
         case '͙':
         case '͚':
         case '͛':
         case '͜':
         case '͝':
         case '͞':
         case '͟':
         case '͠':
         case '͡':
         case '͢':
         case 'ͣ':
         case 'ͤ':
         case 'ͥ':
         case 'ͦ':
         case 'ͧ':
         case 'ͨ':
         case 'ͩ':
         case 'ͪ':
         case 'ͫ':
         case 'ͬ':
         case 'ͭ':
         case 'ͮ':
         case 'ͯ':
         case 'Ͱ':
         case 'ͱ':
         case 'Ͳ':
         case 'ͳ':
         case 'ʹ':
            this.matchRange('̀', 'ʹ');
            break;
         case 'Ͷ':
         case 'ͷ':
            this.matchRange('Ͷ', 'ͷ');
            break;
         case 'ͺ':
         case 'ͻ':
         case 'ͼ':
         case 'ͽ':
            this.matchRange('ͺ', 'ͽ');
            break;
         case 'Ά':
            this.matchRange('Ά', 'Ά');
            break;
         case 'Έ':
         case 'Ή':
         case 'Ί':
            this.matchRange('Έ', 'Ί');
            break;
         case 'Ό':
            this.matchRange('Ό', 'Ό');
            break;
         case 'Ύ':
         case 'Ώ':
         case 'ΐ':
         case 'Α':
         case 'Β':
         case 'Γ':
         case 'Δ':
         case 'Ε':
         case 'Ζ':
         case 'Η':
         case 'Θ':
         case 'Ι':
         case 'Κ':
         case 'Λ':
         case 'Μ':
         case 'Ν':
         case 'Ξ':
         case 'Ο':
         case 'Π':
         case 'Ρ':
            this.matchRange('Ύ', 'Ρ');
            break;
         case 'Σ':
         case 'Τ':
         case 'Υ':
         case 'Φ':
         case 'Χ':
         case 'Ψ':
         case 'Ω':
         case 'Ϊ':
         case 'Ϋ':
         case 'ά':
         case 'έ':
         case 'ή':
         case 'ί':
         case 'ΰ':
         case 'α':
         case 'β':
         case 'γ':
         case 'δ':
         case 'ε':
         case 'ζ':
         case 'η':
         case 'θ':
         case 'ι':
         case 'κ':
         case 'λ':
         case 'μ':
         case 'ν':
         case 'ξ':
         case 'ο':
         case 'π':
         case 'ρ':
         case 'ς':
         case 'σ':
         case 'τ':
         case 'υ':
         case 'φ':
         case 'χ':
         case 'ψ':
         case 'ω':
         case 'ϊ':
         case 'ϋ':
         case 'ό':
         case 'ύ':
         case 'ώ':
         case 'Ϗ':
         case 'ϐ':
         case 'ϑ':
         case 'ϒ':
         case 'ϓ':
         case 'ϔ':
         case 'ϕ':
         case 'ϖ':
         case 'ϗ':
         case 'Ϙ':
         case 'ϙ':
         case 'Ϛ':
         case 'ϛ':
         case 'Ϝ':
         case 'ϝ':
         case 'Ϟ':
         case 'ϟ':
         case 'Ϡ':
         case 'ϡ':
         case 'Ϣ':
         case 'ϣ':
         case 'Ϥ':
         case 'ϥ':
         case 'Ϧ':
         case 'ϧ':
         case 'Ϩ':
         case 'ϩ':
         case 'Ϫ':
         case 'ϫ':
         case 'Ϭ':
         case 'ϭ':
         case 'Ϯ':
         case 'ϯ':
         case 'ϰ':
         case 'ϱ':
         case 'ϲ':
         case 'ϳ':
         case 'ϴ':
         case 'ϵ':
            this.matchRange('Σ', 'ϵ');
            break;
         case '҃':
         case '҄':
         case '҅':
         case '҆':
         case '҇':
            this.matchRange('҃', '҇');
            break;
         case 'Ա':
         case 'Բ':
         case 'Գ':
         case 'Դ':
         case 'Ե':
         case 'Զ':
         case 'Է':
         case 'Ը':
         case 'Թ':
         case 'Ժ':
         case 'Ի':
         case 'Լ':
         case 'Խ':
         case 'Ծ':
         case 'Կ':
         case 'Հ':
         case 'Ձ':
         case 'Ղ':
         case 'Ճ':
         case 'Մ':
         case 'Յ':
         case 'Ն':
         case 'Շ':
         case 'Ո':
         case 'Չ':
         case 'Պ':
         case 'Ջ':
         case 'Ռ':
         case 'Ս':
         case 'Վ':
         case 'Տ':
         case 'Ր':
         case 'Ց':
         case 'Ւ':
         case 'Փ':
         case 'Ք':
         case 'Օ':
         case 'Ֆ':
            this.matchRange('Ա', 'Ֆ');
            break;
         case 'ՙ':
            this.matchRange('ՙ', 'ՙ');
            break;
         case 'ա':
         case 'բ':
         case 'գ':
         case 'դ':
         case 'ե':
         case 'զ':
         case 'է':
         case 'ը':
         case 'թ':
         case 'ժ':
         case 'ի':
         case 'լ':
         case 'խ':
         case 'ծ':
         case 'կ':
         case 'հ':
         case 'ձ':
         case 'ղ':
         case 'ճ':
         case 'մ':
         case 'յ':
         case 'ն':
         case 'շ':
         case 'ո':
         case 'չ':
         case 'պ':
         case 'ջ':
         case 'ռ':
         case 'ս':
         case 'վ':
         case 'տ':
         case 'ր':
         case 'ց':
         case 'ւ':
         case 'փ':
         case 'ք':
         case 'օ':
         case 'ֆ':
         case 'և':
            this.matchRange('ա', 'և');
            break;
         case '֑':
         case '֒':
         case '֓':
         case '֔':
         case '֕':
         case '֖':
         case '֗':
         case '֘':
         case '֙':
         case '֚':
         case '֛':
         case '֜':
         case '֝':
         case '֞':
         case '֟':
         case '֠':
         case '֡':
         case '֢':
         case '֣':
         case '֤':
         case '֥':
         case '֦':
         case '֧':
         case '֨':
         case '֩':
         case '֪':
         case '֫':
         case '֬':
         case '֭':
         case '֮':
         case '֯':
         case 'ְ':
         case 'ֱ':
         case 'ֲ':
         case 'ֳ':
         case 'ִ':
         case 'ֵ':
         case 'ֶ':
         case 'ַ':
         case 'ָ':
         case 'ֹ':
         case 'ֺ':
         case 'ֻ':
         case 'ּ':
         case 'ֽ':
            this.matchRange('֑', 'ֽ');
            break;
         case 'ֿ':
            this.matchRange('ֿ', 'ֿ');
            break;
         case 'ׁ':
         case 'ׂ':
            this.matchRange('ׁ', 'ׂ');
            break;
         case 'ׄ':
         case 'ׅ':
            this.matchRange('ׄ', 'ׅ');
            break;
         case 'ׇ':
            this.matchRange('ׇ', 'ׇ');
            break;
         case 'א':
         case 'ב':
         case 'ג':
         case 'ד':
         case 'ה':
         case 'ו':
         case 'ז':
         case 'ח':
         case 'ט':
         case 'י':
         case 'ך':
         case 'כ':
         case 'ל':
         case 'ם':
         case 'מ':
         case 'ן':
         case 'נ':
         case 'ס':
         case 'ע':
         case 'ף':
         case 'פ':
         case 'ץ':
         case 'צ':
         case 'ק':
         case 'ר':
         case 'ש':
         case 'ת':
            this.matchRange('א', 'ת');
            break;
         case 'װ':
         case 'ױ':
         case 'ײ':
            this.matchRange('װ', 'ײ');
            break;
         case '\u0600':
         case '\u0601':
         case '\u0602':
         case '\u0603':
            this.matchRange('\u0600', '\u0603');
            break;
         case '؋':
            this.matchRange('؋', '؋');
            break;
         case 'ؐ':
         case 'ؑ':
         case 'ؒ':
         case 'ؓ':
         case 'ؔ':
         case 'ؕ':
         case 'ؖ':
         case 'ؗ':
         case 'ؘ':
         case 'ؙ':
         case 'ؚ':
            this.matchRange('ؐ', 'ؚ');
            break;
         case 'ؠ':
         case 'ء':
         case 'آ':
         case 'أ':
         case 'ؤ':
         case 'إ':
         case 'ئ':
         case 'ا':
         case 'ب':
         case 'ة':
         case 'ت':
         case 'ث':
         case 'ج':
         case 'ح':
         case 'خ':
         case 'د':
         case 'ذ':
         case 'ر':
         case 'ز':
         case 'س':
         case 'ش':
         case 'ص':
         case 'ض':
         case 'ط':
         case 'ظ':
         case 'ع':
         case 'غ':
         case 'ػ':
         case 'ؼ':
         case 'ؽ':
         case 'ؾ':
         case 'ؿ':
         case 'ـ':
         case 'ف':
         case 'ق':
         case 'ك':
         case 'ل':
         case 'م':
         case 'ن':
         case 'ه':
         case 'و':
         case 'ى':
         case 'ي':
         case 'ً':
         case 'ٌ':
         case 'ٍ':
         case 'َ':
         case 'ُ':
         case 'ِ':
         case 'ّ':
         case 'ْ':
         case 'ٓ':
         case 'ٔ':
         case 'ٕ':
         case 'ٖ':
         case 'ٗ':
         case '٘':
         case 'ٙ':
         case 'ٚ':
         case 'ٛ':
         case 'ٜ':
         case 'ٝ':
         case 'ٞ':
         case 'ٟ':
         case '٠':
         case '١':
         case '٢':
         case '٣':
         case '٤':
         case '٥':
         case '٦':
         case '٧':
         case '٨':
         case '٩':
            this.matchRange('ؠ', '٩');
            break;
         case 'ٮ':
         case 'ٯ':
         case 'ٰ':
         case 'ٱ':
         case 'ٲ':
         case 'ٳ':
         case 'ٴ':
         case 'ٵ':
         case 'ٶ':
         case 'ٷ':
         case 'ٸ':
         case 'ٹ':
         case 'ٺ':
         case 'ٻ':
         case 'ټ':
         case 'ٽ':
         case 'پ':
         case 'ٿ':
         case 'ڀ':
         case 'ځ':
         case 'ڂ':
         case 'ڃ':
         case 'ڄ':
         case 'څ':
         case 'چ':
         case 'ڇ':
         case 'ڈ':
         case 'ډ':
         case 'ڊ':
         case 'ڋ':
         case 'ڌ':
         case 'ڍ':
         case 'ڎ':
         case 'ڏ':
         case 'ڐ':
         case 'ڑ':
         case 'ڒ':
         case 'ړ':
         case 'ڔ':
         case 'ڕ':
         case 'ږ':
         case 'ڗ':
         case 'ژ':
         case 'ڙ':
         case 'ښ':
         case 'ڛ':
         case 'ڜ':
         case 'ڝ':
         case 'ڞ':
         case 'ڟ':
         case 'ڠ':
         case 'ڡ':
         case 'ڢ':
         case 'ڣ':
         case 'ڤ':
         case 'ڥ':
         case 'ڦ':
         case 'ڧ':
         case 'ڨ':
         case 'ک':
         case 'ڪ':
         case 'ګ':
         case 'ڬ':
         case 'ڭ':
         case 'ڮ':
         case 'گ':
         case 'ڰ':
         case 'ڱ':
         case 'ڲ':
         case 'ڳ':
         case 'ڴ':
         case 'ڵ':
         case 'ڶ':
         case 'ڷ':
         case 'ڸ':
         case 'ڹ':
         case 'ں':
         case 'ڻ':
         case 'ڼ':
         case 'ڽ':
         case 'ھ':
         case 'ڿ':
         case 'ۀ':
         case 'ہ':
         case 'ۂ':
         case 'ۃ':
         case 'ۄ':
         case 'ۅ':
         case 'ۆ':
         case 'ۇ':
         case 'ۈ':
         case 'ۉ':
         case 'ۊ':
         case 'ۋ':
         case 'ی':
         case 'ۍ':
         case 'ێ':
         case 'ۏ':
         case 'ې':
         case 'ۑ':
         case 'ے':
         case 'ۓ':
            this.matchRange('ٮ', 'ۓ');
            break;
         case 'ە':
         case 'ۖ':
         case 'ۗ':
         case 'ۘ':
         case 'ۙ':
         case 'ۚ':
         case 'ۛ':
         case 'ۜ':
         case '\u06dd':
            this.matchRange('ە', '\u06dd');
            break;
         case '۟':
         case '۠':
         case 'ۡ':
         case 'ۢ':
         case 'ۣ':
         case 'ۤ':
         case 'ۥ':
         case 'ۦ':
         case 'ۧ':
         case 'ۨ':
            this.matchRange('۟', 'ۨ');
            break;
         case '۪':
         case '۫':
         case '۬':
         case 'ۭ':
         case 'ۮ':
         case 'ۯ':
         case '۰':
         case '۱':
         case '۲':
         case '۳':
         case '۴':
         case '۵':
         case '۶':
         case '۷':
         case '۸':
         case '۹':
         case 'ۺ':
         case 'ۻ':
         case 'ۼ':
            this.matchRange('۪', 'ۼ');
            break;
         case 'ۿ':
            this.matchRange('ۿ', 'ۿ');
            break;
         case '\u070f':
         case 'ܐ':
         case 'ܑ':
         case 'ܒ':
         case 'ܓ':
         case 'ܔ':
         case 'ܕ':
         case 'ܖ':
         case 'ܗ':
         case 'ܘ':
         case 'ܙ':
         case 'ܚ':
         case 'ܛ':
         case 'ܜ':
         case 'ܝ':
         case 'ܞ':
         case 'ܟ':
         case 'ܠ':
         case 'ܡ':
         case 'ܢ':
         case 'ܣ':
         case 'ܤ':
         case 'ܥ':
         case 'ܦ':
         case 'ܧ':
         case 'ܨ':
         case 'ܩ':
         case 'ܪ':
         case 'ܫ':
         case 'ܬ':
         case 'ܭ':
         case 'ܮ':
         case 'ܯ':
         case 'ܰ':
         case 'ܱ':
         case 'ܲ':
         case 'ܳ':
         case 'ܴ':
         case 'ܵ':
         case 'ܶ':
         case 'ܷ':
         case 'ܸ':
         case 'ܹ':
         case 'ܺ':
         case 'ܻ':
         case 'ܼ':
         case 'ܽ':
         case 'ܾ':
         case 'ܿ':
         case '݀':
         case '݁':
         case '݂':
         case '݃':
         case '݄':
         case '݅':
         case '݆':
         case '݇':
         case '݈':
         case '݉':
         case '݊':
            this.matchRange('\u070f', '݊');
            break;
         case 'ݍ':
         case 'ݎ':
         case 'ݏ':
         case 'ݐ':
         case 'ݑ':
         case 'ݒ':
         case 'ݓ':
         case 'ݔ':
         case 'ݕ':
         case 'ݖ':
         case 'ݗ':
         case 'ݘ':
         case 'ݙ':
         case 'ݚ':
         case 'ݛ':
         case 'ݜ':
         case 'ݝ':
         case 'ݞ':
         case 'ݟ':
         case 'ݠ':
         case 'ݡ':
         case 'ݢ':
         case 'ݣ':
         case 'ݤ':
         case 'ݥ':
         case 'ݦ':
         case 'ݧ':
         case 'ݨ':
         case 'ݩ':
         case 'ݪ':
         case 'ݫ':
         case 'ݬ':
         case 'ݭ':
         case 'ݮ':
         case 'ݯ':
         case 'ݰ':
         case 'ݱ':
         case 'ݲ':
         case 'ݳ':
         case 'ݴ':
         case 'ݵ':
         case 'ݶ':
         case 'ݷ':
         case 'ݸ':
         case 'ݹ':
         case 'ݺ':
         case 'ݻ':
         case 'ݼ':
         case 'ݽ':
         case 'ݾ':
         case 'ݿ':
         case 'ހ':
         case 'ށ':
         case 'ނ':
         case 'ރ':
         case 'ބ':
         case 'ޅ':
         case 'ކ':
         case 'އ':
         case 'ވ':
         case 'މ':
         case 'ފ':
         case 'ދ':
         case 'ތ':
         case 'ލ':
         case 'ގ':
         case 'ޏ':
         case 'ސ':
         case 'ޑ':
         case 'ޒ':
         case 'ޓ':
         case 'ޔ':
         case 'ޕ':
         case 'ޖ':
         case 'ޗ':
         case 'ޘ':
         case 'ޙ':
         case 'ޚ':
         case 'ޛ':
         case 'ޜ':
         case 'ޝ':
         case 'ޞ':
         case 'ޟ':
         case 'ޠ':
         case 'ޡ':
         case 'ޢ':
         case 'ޣ':
         case 'ޤ':
         case 'ޥ':
         case 'ަ':
         case 'ާ':
         case 'ި':
         case 'ީ':
         case 'ު':
         case 'ޫ':
         case 'ެ':
         case 'ޭ':
         case 'ޮ':
         case 'ޯ':
         case 'ް':
         case 'ޱ':
            this.matchRange('ݍ', 'ޱ');
            break;
         case '߀':
         case '߁':
         case '߂':
         case '߃':
         case '߄':
         case '߅':
         case '߆':
         case '߇':
         case '߈':
         case '߉':
         case 'ߊ':
         case 'ߋ':
         case 'ߌ':
         case 'ߍ':
         case 'ߎ':
         case 'ߏ':
         case 'ߐ':
         case 'ߑ':
         case 'ߒ':
         case 'ߓ':
         case 'ߔ':
         case 'ߕ':
         case 'ߖ':
         case 'ߗ':
         case 'ߘ':
         case 'ߙ':
         case 'ߚ':
         case 'ߛ':
         case 'ߜ':
         case 'ߝ':
         case 'ߞ':
         case 'ߟ':
         case 'ߠ':
         case 'ߡ':
         case 'ߢ':
         case 'ߣ':
         case 'ߤ':
         case 'ߥ':
         case 'ߦ':
         case 'ߧ':
         case 'ߨ':
         case 'ߩ':
         case 'ߪ':
         case '߫':
         case '߬':
         case '߭':
         case '߮':
         case '߯':
         case '߰':
         case '߱':
         case '߲':
         case '߳':
         case 'ߴ':
         case 'ߵ':
            this.matchRange('߀', 'ߵ');
            break;
         case 'ߺ':
            this.matchRange('ߺ', 'ߺ');
            break;
         case 'ࠀ':
         case 'ࠁ':
         case 'ࠂ':
         case 'ࠃ':
         case 'ࠄ':
         case 'ࠅ':
         case 'ࠆ':
         case 'ࠇ':
         case 'ࠈ':
         case 'ࠉ':
         case 'ࠊ':
         case 'ࠋ':
         case 'ࠌ':
         case 'ࠍ':
         case 'ࠎ':
         case 'ࠏ':
         case 'ࠐ':
         case 'ࠑ':
         case 'ࠒ':
         case 'ࠓ':
         case 'ࠔ':
         case 'ࠕ':
         case 'ࠖ':
         case 'ࠗ':
         case '࠘':
         case '࠙':
         case 'ࠚ':
         case 'ࠛ':
         case 'ࠜ':
         case 'ࠝ':
         case 'ࠞ':
         case 'ࠟ':
         case 'ࠠ':
         case 'ࠡ':
         case 'ࠢ':
         case 'ࠣ':
         case 'ࠤ':
         case 'ࠥ':
         case 'ࠦ':
         case 'ࠧ':
         case 'ࠨ':
         case 'ࠩ':
         case 'ࠪ':
         case 'ࠫ':
         case 'ࠬ':
         case '࠭':
            this.matchRange('ࠀ', '࠭');
            break;
         case 'ࡀ':
         case 'ࡁ':
         case 'ࡂ':
         case 'ࡃ':
         case 'ࡄ':
         case 'ࡅ':
         case 'ࡆ':
         case 'ࡇ':
         case 'ࡈ':
         case 'ࡉ':
         case 'ࡊ':
         case 'ࡋ':
         case 'ࡌ':
         case 'ࡍ':
         case 'ࡎ':
         case 'ࡏ':
         case 'ࡐ':
         case 'ࡑ':
         case 'ࡒ':
         case 'ࡓ':
         case 'ࡔ':
         case 'ࡕ':
         case 'ࡖ':
         case 'ࡗ':
         case 'ࡘ':
         case '࡙':
         case '࡚':
         case '࡛':
            this.matchRange('ࡀ', '࡛');
            break;
         case 'ऀ':
         case 'ँ':
         case 'ं':
         case 'ः':
         case 'ऄ':
         case 'अ':
         case 'आ':
         case 'इ':
         case 'ई':
         case 'उ':
         case 'ऊ':
         case 'ऋ':
         case 'ऌ':
         case 'ऍ':
         case 'ऎ':
         case 'ए':
         case 'ऐ':
         case 'ऑ':
         case 'ऒ':
         case 'ओ':
         case 'औ':
         case 'क':
         case 'ख':
         case 'ग':
         case 'घ':
         case 'ङ':
         case 'च':
         case 'छ':
         case 'ज':
         case 'झ':
         case 'ञ':
         case 'ट':
         case 'ठ':
         case 'ड':
         case 'ढ':
         case 'ण':
         case 'त':
         case 'थ':
         case 'द':
         case 'ध':
         case 'न':
         case 'ऩ':
         case 'प':
         case 'फ':
         case 'ब':
         case 'भ':
         case 'म':
         case 'य':
         case 'र':
         case 'ऱ':
         case 'ल':
         case 'ळ':
         case 'ऴ':
         case 'व':
         case 'श':
         case 'ष':
         case 'स':
         case 'ह':
         case 'ऺ':
         case 'ऻ':
         case '़':
         case 'ऽ':
         case 'ा':
         case 'ि':
         case 'ी':
         case 'ु':
         case 'ू':
         case 'ृ':
         case 'ॄ':
         case 'ॅ':
         case 'ॆ':
         case 'े':
         case 'ै':
         case 'ॉ':
         case 'ॊ':
         case 'ो':
         case 'ौ':
         case '्':
         case 'ॎ':
         case 'ॏ':
         case 'ॐ':
         case '॑':
         case '॒':
         case '॓':
         case '॔':
         case 'ॕ':
         case 'ॖ':
         case 'ॗ':
         case 'क़':
         case 'ख़':
         case 'ग़':
         case 'ज़':
         case 'ड़':
         case 'ढ़':
         case 'फ़':
         case 'य़':
         case 'ॠ':
         case 'ॡ':
         case 'ॢ':
         case 'ॣ':
            this.matchRange('ऀ', 'ॣ');
            break;
         case '०':
         case '१':
         case '२':
         case '३':
         case '४':
         case '५':
         case '६':
         case '७':
         case '८':
         case '९':
            this.matchRange('०', '९');
            break;
         case 'ॱ':
         case 'ॲ':
         case 'ॳ':
         case 'ॴ':
         case 'ॵ':
         case 'ॶ':
         case 'ॷ':
            this.matchRange('ॱ', 'ॷ');
            break;
         case 'ॹ':
         case 'ॺ':
         case 'ॻ':
         case 'ॼ':
         case 'ॽ':
         case 'ॾ':
         case 'ॿ':
            this.matchRange('ॹ', 'ॿ');
            break;
         case 'ঁ':
         case 'ং':
         case 'ঃ':
            this.matchRange('ঁ', 'ঃ');
            break;
         case 'অ':
         case 'আ':
         case 'ই':
         case 'ঈ':
         case 'উ':
         case 'ঊ':
         case 'ঋ':
         case 'ঌ':
            this.matchRange('অ', 'ঌ');
            break;
         case 'এ':
         case 'ঐ':
            this.matchRange('এ', 'ঐ');
            break;
         case 'ও':
         case 'ঔ':
         case 'ক':
         case 'খ':
         case 'গ':
         case 'ঘ':
         case 'ঙ':
         case 'চ':
         case 'ছ':
         case 'জ':
         case 'ঝ':
         case 'ঞ':
         case 'ট':
         case 'ঠ':
         case 'ড':
         case 'ঢ':
         case 'ণ':
         case 'ত':
         case 'থ':
         case 'দ':
         case 'ধ':
         case 'ন':
            this.matchRange('ও', 'ন');
            break;
         case 'প':
         case 'ফ':
         case 'ব':
         case 'ভ':
         case 'ম':
         case 'য':
         case 'র':
            this.matchRange('প', 'র');
            break;
         case 'ল':
            this.matchRange('ল', 'ল');
            break;
         case 'শ':
         case 'ষ':
         case 'স':
         case 'হ':
            this.matchRange('শ', 'হ');
            break;
         case '়':
         case 'ঽ':
         case 'া':
         case 'ি':
         case 'ী':
         case 'ু':
         case 'ূ':
         case 'ৃ':
         case 'ৄ':
            this.matchRange('়', 'ৄ');
            break;
         case 'ে':
         case 'ৈ':
            this.matchRange('ে', 'ৈ');
            break;
         case 'ো':
         case 'ৌ':
         case '্':
         case 'ৎ':
            this.matchRange('ো', 'ৎ');
            break;
         case 'ৗ':
            this.matchRange('ৗ', 'ৗ');
            break;
         case 'ড়':
         case 'ঢ়':
            this.matchRange('ড়', 'ঢ়');
            break;
         case 'য়':
         case 'ৠ':
         case 'ৡ':
         case 'ৢ':
         case 'ৣ':
            this.matchRange('য়', 'ৣ');
            break;
         case '০':
         case '১':
         case '২':
         case '৩':
         case '৪':
         case '৫':
         case '৬':
         case '৭':
         case '৮':
         case '৯':
         case 'ৰ':
         case 'ৱ':
         case '৲':
         case '৳':
            this.matchRange('০', '৳');
            break;
         case '৻':
            this.matchRange('৻', '৻');
            break;
         case 'ਁ':
         case 'ਂ':
         case 'ਃ':
            this.matchRange('ਁ', 'ਃ');
            break;
         case 'ਅ':
         case 'ਆ':
         case 'ਇ':
         case 'ਈ':
         case 'ਉ':
         case 'ਊ':
            this.matchRange('ਅ', 'ਊ');
            break;
         case 'ਏ':
         case 'ਐ':
            this.matchRange('ਏ', 'ਐ');
            break;
         case 'ਓ':
         case 'ਔ':
         case 'ਕ':
         case 'ਖ':
         case 'ਗ':
         case 'ਘ':
         case 'ਙ':
         case 'ਚ':
         case 'ਛ':
         case 'ਜ':
         case 'ਝ':
         case 'ਞ':
         case 'ਟ':
         case 'ਠ':
         case 'ਡ':
         case 'ਢ':
         case 'ਣ':
         case 'ਤ':
         case 'ਥ':
         case 'ਦ':
         case 'ਧ':
         case 'ਨ':
            this.matchRange('ਓ', 'ਨ');
            break;
         case 'ਪ':
         case 'ਫ':
         case 'ਬ':
         case 'ਭ':
         case 'ਮ':
         case 'ਯ':
         case 'ਰ':
            this.matchRange('ਪ', 'ਰ');
            break;
         case 'ਲ':
         case 'ਲ਼':
            this.matchRange('ਲ', 'ਲ਼');
            break;
         case 'ਵ':
         case 'ਸ਼':
            this.matchRange('ਵ', 'ਸ਼');
            break;
         case 'ਸ':
         case 'ਹ':
            this.matchRange('ਸ', 'ਹ');
            break;
         case '਼':
            this.matchRange('਼', '਼');
            break;
         case 'ਾ':
         case 'ਿ':
         case 'ੀ':
         case 'ੁ':
         case 'ੂ':
            this.matchRange('ਾ', 'ੂ');
            break;
         case 'ੇ':
         case 'ੈ':
            this.matchRange('ੇ', 'ੈ');
            break;
         case 'ੋ':
         case 'ੌ':
         case '੍':
            this.matchRange('ੋ', '੍');
            break;
         case 'ੑ':
            this.matchRange('ੑ', 'ੑ');
            break;
         case 'ਖ਼':
         case 'ਗ਼':
         case 'ਜ਼':
         case 'ੜ':
            this.matchRange('ਖ਼', 'ੜ');
            break;
         case 'ਫ਼':
            this.matchRange('ਫ਼', 'ਫ਼');
            break;
         case '੦':
         case '੧':
         case '੨':
         case '੩':
         case '੪':
         case '੫':
         case '੬':
         case '੭':
         case '੮':
         case '੯':
         case 'ੰ':
         case 'ੱ':
         case 'ੲ':
         case 'ੳ':
         case 'ੴ':
         case 'ੵ':
            this.matchRange('੦', 'ੵ');
            break;
         case 'ઁ':
         case 'ં':
         case 'ઃ':
            this.matchRange('ઁ', 'ઃ');
            break;
         case 'અ':
         case 'આ':
         case 'ઇ':
         case 'ઈ':
         case 'ઉ':
         case 'ઊ':
         case 'ઋ':
         case 'ઌ':
         case 'ઍ':
            this.matchRange('અ', 'ઍ');
            break;
         case 'એ':
         case 'ઐ':
         case 'ઑ':
            this.matchRange('એ', 'ઑ');
            break;
         case 'ઓ':
         case 'ઔ':
         case 'ક':
         case 'ખ':
         case 'ગ':
         case 'ઘ':
         case 'ઙ':
         case 'ચ':
         case 'છ':
         case 'જ':
         case 'ઝ':
         case 'ઞ':
         case 'ટ':
         case 'ઠ':
         case 'ડ':
         case 'ઢ':
         case 'ણ':
         case 'ત':
         case 'થ':
         case 'દ':
         case 'ધ':
         case 'ન':
            this.matchRange('ઓ', 'ન');
            break;
         case 'પ':
         case 'ફ':
         case 'બ':
         case 'ભ':
         case 'મ':
         case 'ય':
         case 'ર':
            this.matchRange('પ', 'ર');
            break;
         case 'લ':
         case 'ળ':
            this.matchRange('લ', 'ળ');
            break;
         case 'વ':
         case 'શ':
         case 'ષ':
         case 'સ':
         case 'હ':
            this.matchRange('વ', 'હ');
            break;
         case '઼':
         case 'ઽ':
         case 'ા':
         case 'િ':
         case 'ી':
         case 'ુ':
         case 'ૂ':
         case 'ૃ':
         case 'ૄ':
         case 'ૅ':
            this.matchRange('઼', 'ૅ');
            break;
         case 'ે':
         case 'ૈ':
         case 'ૉ':
            this.matchRange('ે', 'ૉ');
            break;
         case 'ો':
         case 'ૌ':
         case '્':
            this.matchRange('ો', '્');
            break;
         case 'ૐ':
            this.matchRange('ૐ', 'ૐ');
            break;
         case 'ૠ':
         case 'ૡ':
         case 'ૢ':
         case 'ૣ':
            this.matchRange('ૠ', 'ૣ');
            break;
         case '૦':
         case '૧':
         case '૨':
         case '૩':
         case '૪':
         case '૫':
         case '૬':
         case '૭':
         case '૮':
         case '૯':
            this.matchRange('૦', '૯');
            break;
         case '૱':
            this.matchRange('૱', '૱');
            break;
         case 'ଁ':
         case 'ଂ':
         case 'ଃ':
            this.matchRange('ଁ', 'ଃ');
            break;
         case 'ଅ':
         case 'ଆ':
         case 'ଇ':
         case 'ଈ':
         case 'ଉ':
         case 'ଊ':
         case 'ଋ':
         case 'ଌ':
            this.matchRange('ଅ', 'ଌ');
            break;
         case 'ଏ':
         case 'ଐ':
            this.matchRange('ଏ', 'ଐ');
            break;
         case 'ଓ':
         case 'ଔ':
         case 'କ':
         case 'ଖ':
         case 'ଗ':
         case 'ଘ':
         case 'ଙ':
         case 'ଚ':
         case 'ଛ':
         case 'ଜ':
         case 'ଝ':
         case 'ଞ':
         case 'ଟ':
         case 'ଠ':
         case 'ଡ':
         case 'ଢ':
         case 'ଣ':
         case 'ତ':
         case 'ଥ':
         case 'ଦ':
         case 'ଧ':
         case 'ନ':
            this.matchRange('ଓ', 'ନ');
            break;
         case 'ପ':
         case 'ଫ':
         case 'ବ':
         case 'ଭ':
         case 'ମ':
         case 'ଯ':
         case 'ର':
            this.matchRange('ପ', 'ର');
            break;
         case 'ଲ':
         case 'ଳ':
            this.matchRange('ଲ', 'ଳ');
            break;
         case 'ଵ':
         case 'ଶ':
         case 'ଷ':
         case 'ସ':
         case 'ହ':
            this.matchRange('ଵ', 'ହ');
            break;
         case '଼':
         case 'ଽ':
         case 'ା':
         case 'ି':
         case 'ୀ':
         case 'ୁ':
         case 'ୂ':
         case 'ୃ':
         case 'ୄ':
            this.matchRange('଼', 'ୄ');
            break;
         case 'େ':
         case 'ୈ':
            this.matchRange('େ', 'ୈ');
            break;
         case 'ୋ':
         case 'ୌ':
         case '୍':
            this.matchRange('ୋ', '୍');
            break;
         case 'ୖ':
         case 'ୗ':
            this.matchRange('ୖ', 'ୗ');
            break;
         case 'ଡ଼':
         case 'ଢ଼':
            this.matchRange('ଡ଼', 'ଢ଼');
            break;
         case 'ୟ':
         case 'ୠ':
         case 'ୡ':
         case 'ୢ':
         case 'ୣ':
            this.matchRange('ୟ', 'ୣ');
            break;
         case '୦':
         case '୧':
         case '୨':
         case '୩':
         case '୪':
         case '୫':
         case '୬':
         case '୭':
         case '୮':
         case '୯':
            this.matchRange('୦', '୯');
            break;
         case 'ୱ':
            this.matchRange('ୱ', 'ୱ');
            break;
         case 'ஂ':
         case 'ஃ':
            this.matchRange('ஂ', 'ஃ');
            break;
         case 'அ':
         case 'ஆ':
         case 'இ':
         case 'ஈ':
         case 'உ':
         case 'ஊ':
            this.matchRange('அ', 'ஊ');
            break;
         case 'எ':
         case 'ஏ':
         case 'ஐ':
            this.matchRange('எ', 'ஐ');
            break;
         case 'ஒ':
         case 'ஓ':
         case 'ஔ':
         case 'க':
            this.matchRange('ஒ', 'க');
            break;
         case 'ங':
         case 'ச':
            this.matchRange('ங', 'ச');
            break;
         case 'ஜ':
            this.matchRange('ஜ', 'ஜ');
            break;
         case 'ஞ':
         case 'ட':
            this.matchRange('ஞ', 'ட');
            break;
         case 'ண':
         case 'த':
            this.matchRange('ண', 'த');
            break;
         case 'ந':
         case 'ன':
         case 'ப':
            this.matchRange('ந', 'ப');
            break;
         case 'ம':
         case 'ய':
         case 'ர':
         case 'ற':
         case 'ல':
         case 'ள':
         case 'ழ':
         case 'வ':
         case 'ஶ':
         case 'ஷ':
         case 'ஸ':
         case 'ஹ':
            this.matchRange('ம', 'ஹ');
            break;
         case 'ா':
         case 'ி':
         case 'ீ':
         case 'ு':
         case 'ூ':
            this.matchRange('ா', 'ூ');
            break;
         case 'ெ':
         case 'ே':
         case 'ை':
            this.matchRange('ெ', 'ை');
            break;
         case 'ொ':
         case 'ோ':
         case 'ௌ':
         case '்':
            this.matchRange('ொ', '்');
            break;
         case 'ௐ':
            this.matchRange('ௐ', 'ௐ');
            break;
         case 'ௗ':
            this.matchRange('ௗ', 'ௗ');
            break;
         case '௦':
         case '௧':
         case '௨':
         case '௩':
         case '௪':
         case '௫':
         case '௬':
         case '௭':
         case '௮':
         case '௯':
            this.matchRange('௦', '௯');
            break;
         case '௹':
            this.matchRange('௹', '௹');
            break;
         case 'ఁ':
         case 'ం':
         case 'ః':
            this.matchRange('ఁ', 'ః');
            break;
         case 'అ':
         case 'ఆ':
         case 'ఇ':
         case 'ఈ':
         case 'ఉ':
         case 'ఊ':
         case 'ఋ':
         case 'ఌ':
            this.matchRange('అ', 'ఌ');
            break;
         case 'ఎ':
         case 'ఏ':
         case 'ఐ':
            this.matchRange('ఎ', 'ఐ');
            break;
         case 'ఒ':
         case 'ఓ':
         case 'ఔ':
         case 'క':
         case 'ఖ':
         case 'గ':
         case 'ఘ':
         case 'ఙ':
         case 'చ':
         case 'ఛ':
         case 'జ':
         case 'ఝ':
         case 'ఞ':
         case 'ట':
         case 'ఠ':
         case 'డ':
         case 'ఢ':
         case 'ణ':
         case 'త':
         case 'థ':
         case 'ద':
         case 'ధ':
         case 'న':
            this.matchRange('ఒ', 'న');
            break;
         case 'ప':
         case 'ఫ':
         case 'బ':
         case 'భ':
         case 'మ':
         case 'య':
         case 'ర':
         case 'ఱ':
         case 'ల':
         case 'ళ':
            this.matchRange('ప', 'ళ');
            break;
         case 'వ':
         case 'శ':
         case 'ష':
         case 'స':
         case 'హ':
            this.matchRange('వ', 'హ');
            break;
         case 'ఽ':
         case 'ా':
         case 'ి':
         case 'ీ':
         case 'ు':
         case 'ూ':
         case 'ృ':
         case 'ౄ':
            this.matchRange('ఽ', 'ౄ');
            break;
         case 'ె':
         case 'ే':
         case 'ై':
            this.matchRange('ె', 'ై');
            break;
         case 'ొ':
         case 'ో':
         case 'ౌ':
         case '్':
            this.matchRange('ొ', '్');
            break;
         case 'ౕ':
         case 'ౖ':
            this.matchRange('ౕ', 'ౖ');
            break;
         case 'ౘ':
         case 'ౙ':
            this.matchRange('ౘ', 'ౙ');
            break;
         case 'ౠ':
         case 'ౡ':
         case 'ౢ':
         case 'ౣ':
            this.matchRange('ౠ', 'ౣ');
            break;
         case '౦':
         case '౧':
         case '౨':
         case '౩':
         case '౪':
         case '౫':
         case '౬':
         case '౭':
         case '౮':
         case '౯':
            this.matchRange('౦', '౯');
            break;
         case 'ಂ':
         case 'ಃ':
            this.matchRange('ಂ', 'ಃ');
            break;
         case 'ಅ':
         case 'ಆ':
         case 'ಇ':
         case 'ಈ':
         case 'ಉ':
         case 'ಊ':
         case 'ಋ':
         case 'ಌ':
            this.matchRange('ಅ', 'ಌ');
            break;
         case 'ಎ':
         case 'ಏ':
         case 'ಐ':
            this.matchRange('ಎ', 'ಐ');
            break;
         case 'ಒ':
         case 'ಓ':
         case 'ಔ':
         case 'ಕ':
         case 'ಖ':
         case 'ಗ':
         case 'ಘ':
         case 'ಙ':
         case 'ಚ':
         case 'ಛ':
         case 'ಜ':
         case 'ಝ':
         case 'ಞ':
         case 'ಟ':
         case 'ಠ':
         case 'ಡ':
         case 'ಢ':
         case 'ಣ':
         case 'ತ':
         case 'ಥ':
         case 'ದ':
         case 'ಧ':
         case 'ನ':
            this.matchRange('ಒ', 'ನ');
            break;
         case 'ಪ':
         case 'ಫ':
         case 'ಬ':
         case 'ಭ':
         case 'ಮ':
         case 'ಯ':
         case 'ರ':
         case 'ಱ':
         case 'ಲ':
         case 'ಳ':
            this.matchRange('ಪ', 'ಳ');
            break;
         case 'ವ':
         case 'ಶ':
         case 'ಷ':
         case 'ಸ':
         case 'ಹ':
            this.matchRange('ವ', 'ಹ');
            break;
         case '಼':
         case 'ಽ':
         case 'ಾ':
         case 'ಿ':
         case 'ೀ':
         case 'ು':
         case 'ೂ':
         case 'ೃ':
         case 'ೄ':
            this.matchRange('಼', 'ೄ');
            break;
         case 'ೆ':
         case 'ೇ':
         case 'ೈ':
            this.matchRange('ೆ', 'ೈ');
            break;
         case 'ೊ':
         case 'ೋ':
         case 'ೌ':
         case '್':
            this.matchRange('ೊ', '್');
            break;
         case 'ೕ':
         case 'ೖ':
            this.matchRange('ೕ', 'ೖ');
            break;
         case 'ೞ':
            this.matchRange('ೞ', 'ೞ');
            break;
         case 'ೠ':
         case 'ೡ':
         case 'ೢ':
         case 'ೣ':
            this.matchRange('ೠ', 'ೣ');
            break;
         case '೦':
         case '೧':
         case '೨':
         case '೩':
         case '೪':
         case '೫':
         case '೬':
         case '೭':
         case '೮':
         case '೯':
            this.matchRange('೦', '೯');
            break;
         case 'ೱ':
         case 'ೲ':
            this.matchRange('ೱ', 'ೲ');
            break;
         case 'ം':
         case 'ഃ':
            this.matchRange('ം', 'ഃ');
            break;
         case 'അ':
         case 'ആ':
         case 'ഇ':
         case 'ഈ':
         case 'ഉ':
         case 'ഊ':
         case 'ഋ':
         case 'ഌ':
            this.matchRange('അ', 'ഌ');
            break;
         case 'എ':
         case 'ഏ':
         case 'ഐ':
            this.matchRange('എ', 'ഐ');
            break;
         case 'ഒ':
         case 'ഓ':
         case 'ഔ':
         case 'ക':
         case 'ഖ':
         case 'ഗ':
         case 'ഘ':
         case 'ങ':
         case 'ച':
         case 'ഛ':
         case 'ജ':
         case 'ഝ':
         case 'ഞ':
         case 'ട':
         case 'ഠ':
         case 'ഡ':
         case 'ഢ':
         case 'ണ':
         case 'ത':
         case 'ഥ':
         case 'ദ':
         case 'ധ':
         case 'ന':
         case 'ഩ':
         case 'പ':
         case 'ഫ':
         case 'ബ':
         case 'ഭ':
         case 'മ':
         case 'യ':
         case 'ര':
         case 'റ':
         case 'ല':
         case 'ള':
         case 'ഴ':
         case 'വ':
         case 'ശ':
         case 'ഷ':
         case 'സ':
         case 'ഹ':
         case 'ഺ':
            this.matchRange('ഒ', 'ഺ');
            break;
         case 'ഽ':
         case 'ാ':
         case 'ി':
         case 'ീ':
         case 'ു':
         case 'ൂ':
         case 'ൃ':
         case 'ൄ':
            this.matchRange('ഽ', 'ൄ');
            break;
         case 'െ':
         case 'േ':
         case 'ൈ':
            this.matchRange('െ', 'ൈ');
            break;
         case 'ൊ':
         case 'ോ':
         case 'ൌ':
         case '്':
         case 'ൎ':
            this.matchRange('ൊ', 'ൎ');
            break;
         case 'ൗ':
            this.matchRange('ൗ', 'ൗ');
            break;
         case 'ൠ':
         case 'ൡ':
         case 'ൢ':
         case 'ൣ':
            this.matchRange('ൠ', 'ൣ');
            break;
         case '൦':
         case '൧':
         case '൨':
         case '൩':
         case '൪':
         case '൫':
         case '൬':
         case '൭':
         case '൮':
         case '൯':
            this.matchRange('൦', '൯');
            break;
         case 'ൺ':
         case 'ൻ':
         case 'ർ':
         case 'ൽ':
         case 'ൾ':
         case 'ൿ':
            this.matchRange('ൺ', 'ൿ');
            break;
         case 'ං':
         case 'ඃ':
            this.matchRange('ං', 'ඃ');
            break;
         case 'අ':
         case 'ආ':
         case 'ඇ':
         case 'ඈ':
         case 'ඉ':
         case 'ඊ':
         case 'උ':
         case 'ඌ':
         case 'ඍ':
         case 'ඎ':
         case 'ඏ':
         case 'ඐ':
         case 'එ':
         case 'ඒ':
         case 'ඓ':
         case 'ඔ':
         case 'ඕ':
         case 'ඖ':
            this.matchRange('අ', 'ඖ');
            break;
         case 'ක':
         case 'ඛ':
         case 'ග':
         case 'ඝ':
         case 'ඞ':
         case 'ඟ':
         case 'ච':
         case 'ඡ':
         case 'ජ':
         case 'ඣ':
         case 'ඤ':
         case 'ඥ':
         case 'ඦ':
         case 'ට':
         case 'ඨ':
         case 'ඩ':
         case 'ඪ':
         case 'ණ':
         case 'ඬ':
         case 'ත':
         case 'ථ':
         case 'ද':
         case 'ධ':
         case 'න':
            this.matchRange('ක', 'න');
            break;
         case 'ඳ':
         case 'ප':
         case 'ඵ':
         case 'බ':
         case 'භ':
         case 'ම':
         case 'ඹ':
         case 'ය':
         case 'ර':
            this.matchRange('ඳ', 'ර');
            break;
         case 'ල':
            this.matchRange('ල', 'ල');
            break;
         case 'ව':
         case 'ශ':
         case 'ෂ':
         case 'ස':
         case 'හ':
         case 'ළ':
         case 'ෆ':
            this.matchRange('ව', 'ෆ');
            break;
         case '්':
            this.matchRange('්', '්');
            break;
         case 'ා':
         case 'ැ':
         case 'ෑ':
         case 'ි':
         case 'ී':
         case 'ු':
            this.matchRange('ා', 'ු');
            break;
         case 'ූ':
            this.matchRange('ූ', 'ූ');
            break;
         case 'ෘ':
         case 'ෙ':
         case 'ේ':
         case 'ෛ':
         case 'ො':
         case 'ෝ':
         case 'ෞ':
         case 'ෟ':
            this.matchRange('ෘ', 'ෟ');
            break;
         case 'ෲ':
         case 'ෳ':
            this.matchRange('ෲ', 'ෳ');
            break;
         case 'ก':
         case 'ข':
         case 'ฃ':
         case 'ค':
         case 'ฅ':
         case 'ฆ':
         case 'ง':
         case 'จ':
         case 'ฉ':
         case 'ช':
         case 'ซ':
         case 'ฌ':
         case 'ญ':
         case 'ฎ':
         case 'ฏ':
         case 'ฐ':
         case 'ฑ':
         case 'ฒ':
         case 'ณ':
         case 'ด':
         case 'ต':
         case 'ถ':
         case 'ท':
         case 'ธ':
         case 'น':
         case 'บ':
         case 'ป':
         case 'ผ':
         case 'ฝ':
         case 'พ':
         case 'ฟ':
         case 'ภ':
         case 'ม':
         case 'ย':
         case 'ร':
         case 'ฤ':
         case 'ล':
         case 'ฦ':
         case 'ว':
         case 'ศ':
         case 'ษ':
         case 'ส':
         case 'ห':
         case 'ฬ':
         case 'อ':
         case 'ฮ':
         case 'ฯ':
         case 'ะ':
         case 'ั':
         case 'า':
         case 'ำ':
         case 'ิ':
         case 'ี':
         case 'ึ':
         case 'ื':
         case 'ุ':
         case 'ู':
         case 'ฺ':
            this.matchRange('ก', 'ฺ');
            break;
         case '฿':
         case 'เ':
         case 'แ':
         case 'โ':
         case 'ใ':
         case 'ไ':
         case 'ๅ':
         case 'ๆ':
         case '็':
         case '่':
         case '้':
         case '๊':
         case '๋':
         case '์':
         case 'ํ':
         case '๎':
            this.matchRange('฿', '๎');
            break;
         case '๐':
         case '๑':
         case '๒':
         case '๓':
         case '๔':
         case '๕':
         case '๖':
         case '๗':
         case '๘':
         case '๙':
            this.matchRange('๐', '๙');
            break;
         case 'ກ':
         case 'ຂ':
            this.matchRange('ກ', 'ຂ');
            break;
         case 'ຄ':
            this.matchRange('ຄ', 'ຄ');
            break;
         case 'ງ':
         case 'ຈ':
            this.matchRange('ງ', 'ຈ');
            break;
         case 'ຊ':
            this.matchRange('ຊ', 'ຊ');
            break;
         case 'ຍ':
            this.matchRange('ຍ', 'ຍ');
            break;
         case 'ດ':
         case 'ຕ':
         case 'ຖ':
         case 'ທ':
            this.matchRange('ດ', 'ທ');
            break;
         case 'ນ':
         case 'ບ':
         case 'ປ':
         case 'ຜ':
         case 'ຝ':
         case 'ພ':
         case 'ຟ':
            this.matchRange('ນ', 'ຟ');
            break;
         case 'ມ':
         case 'ຢ':
         case 'ຣ':
            this.matchRange('ມ', 'ຣ');
            break;
         case 'ລ':
            this.matchRange('ລ', 'ລ');
            break;
         case 'ວ':
            this.matchRange('ວ', 'ວ');
            break;
         case 'ສ':
         case 'ຫ':
            this.matchRange('ສ', 'ຫ');
            break;
         case 'ອ':
         case 'ຮ':
         case 'ຯ':
         case 'ະ':
         case 'ັ':
         case 'າ':
         case 'ຳ':
         case 'ິ':
         case 'ີ':
         case 'ຶ':
         case 'ື':
         case 'ຸ':
         case 'ູ':
            this.matchRange('ອ', 'ູ');
            break;
         case 'ົ':
         case 'ຼ':
         case 'ຽ':
            this.matchRange('ົ', 'ຽ');
            break;
         case 'ເ':
         case 'ແ':
         case 'ໂ':
         case 'ໃ':
         case 'ໄ':
            this.matchRange('ເ', 'ໄ');
            break;
         case 'ໆ':
            this.matchRange('ໆ', 'ໆ');
            break;
         case '່':
         case '້':
         case '໊':
         case '໋':
         case '໌':
         case 'ໍ':
            this.matchRange('່', 'ໍ');
            break;
         case '໐':
         case '໑':
         case '໒':
         case '໓':
         case '໔':
         case '໕':
         case '໖':
         case '໗':
         case '໘':
         case '໙':
            this.matchRange('໐', '໙');
            break;
         case 'ໜ':
         case 'ໝ':
            this.matchRange('ໜ', 'ໝ');
            break;
         case 'ༀ':
            this.matchRange('ༀ', 'ༀ');
            break;
         case '༘':
         case '༙':
            this.matchRange('༘', '༙');
            break;
         case '༠':
         case '༡':
         case '༢':
         case '༣':
         case '༤':
         case '༥':
         case '༦':
         case '༧':
         case '༨':
         case '༩':
            this.matchRange('༠', '༩');
            break;
         case '༵':
            this.matchRange('༵', '༵');
            break;
         case '༷':
            this.matchRange('༷', '༷');
            break;
         case '༹':
            this.matchRange('༹', '༹');
            break;
         case '༾':
         case '༿':
         case 'ཀ':
         case 'ཁ':
         case 'ག':
         case 'གྷ':
         case 'ང':
         case 'ཅ':
         case 'ཆ':
         case 'ཇ':
            this.matchRange('༾', 'ཇ');
            break;
         case 'ཉ':
         case 'ཊ':
         case 'ཋ':
         case 'ཌ':
         case 'ཌྷ':
         case 'ཎ':
         case 'ཏ':
         case 'ཐ':
         case 'ད':
         case 'དྷ':
         case 'ན':
         case 'པ':
         case 'ཕ':
         case 'བ':
         case 'བྷ':
         case 'མ':
         case 'ཙ':
         case 'ཚ':
         case 'ཛ':
         case 'ཛྷ':
         case 'ཝ':
         case 'ཞ':
         case 'ཟ':
         case 'འ':
         case 'ཡ':
         case 'ར':
         case 'ལ':
         case 'ཤ':
         case 'ཥ':
         case 'ས':
         case 'ཧ':
         case 'ཨ':
         case 'ཀྵ':
         case 'ཪ':
         case 'ཫ':
         case 'ཬ':
            this.matchRange('ཉ', 'ཬ');
            break;
         case 'ཱ':
         case 'ི':
         case 'ཱི':
         case 'ུ':
         case 'ཱུ':
         case 'ྲྀ':
         case 'ཷ':
         case 'ླྀ':
         case 'ཹ':
         case 'ེ':
         case 'ཻ':
         case 'ོ':
         case 'ཽ':
         case 'ཾ':
         case 'ཿ':
         case 'ྀ':
         case 'ཱྀ':
         case 'ྂ':
         case 'ྃ':
         case '྄':
            this.matchRange('ཱ', '྄');
            break;
         case '྆':
         case '྇':
         case 'ྈ':
         case 'ྉ':
         case 'ྊ':
         case 'ྋ':
         case 'ྌ':
         case 'ྍ':
         case 'ྎ':
         case 'ྏ':
         case 'ྐ':
         case 'ྑ':
         case 'ྒ':
         case 'ྒྷ':
         case 'ྔ':
         case 'ྕ':
         case 'ྖ':
         case 'ྗ':
            this.matchRange('྆', 'ྗ');
            break;
         case 'ྙ':
         case 'ྚ':
         case 'ྛ':
         case 'ྜ':
         case 'ྜྷ':
         case 'ྞ':
         case 'ྟ':
         case 'ྠ':
         case 'ྡ':
         case 'ྡྷ':
         case 'ྣ':
         case 'ྤ':
         case 'ྥ':
         case 'ྦ':
         case 'ྦྷ':
         case 'ྨ':
         case 'ྩ':
         case 'ྪ':
         case 'ྫ':
         case 'ྫྷ':
         case 'ྭ':
         case 'ྮ':
         case 'ྯ':
         case 'ྰ':
         case 'ྱ':
         case 'ྲ':
         case 'ླ':
         case 'ྴ':
         case 'ྵ':
         case 'ྶ':
         case 'ྷ':
         case 'ྸ':
         case 'ྐྵ':
         case 'ྺ':
         case 'ྻ':
         case 'ྼ':
            this.matchRange('ྙ', 'ྼ');
            break;
         case '࿆':
            this.matchRange('࿆', '࿆');
            break;
         case 'က':
         case 'ခ':
         case 'ဂ':
         case 'ဃ':
         case 'င':
         case 'စ':
         case 'ဆ':
         case 'ဇ':
         case 'ဈ':
         case 'ဉ':
         case 'ည':
         case 'ဋ':
         case 'ဌ':
         case 'ဍ':
         case 'ဎ':
         case 'ဏ':
         case 'တ':
         case 'ထ':
         case 'ဒ':
         case 'ဓ':
         case 'န':
         case 'ပ':
         case 'ဖ':
         case 'ဗ':
         case 'ဘ':
         case 'မ':
         case 'ယ':
         case 'ရ':
         case 'လ':
         case 'ဝ':
         case 'သ':
         case 'ဟ':
         case 'ဠ':
         case 'အ':
         case 'ဢ':
         case 'ဣ':
         case 'ဤ':
         case 'ဥ':
         case 'ဦ':
         case 'ဧ':
         case 'ဨ':
         case 'ဩ':
         case 'ဪ':
         case 'ါ':
         case 'ာ':
         case 'ိ':
         case 'ီ':
         case 'ု':
         case 'ူ':
         case 'ေ':
         case 'ဲ':
         case 'ဳ':
         case 'ဴ':
         case 'ဵ':
         case 'ံ':
         case '့':
         case 'း':
         case '္':
         case '်':
         case 'ျ':
         case 'ြ':
         case 'ွ':
         case 'ှ':
         case 'ဿ':
         case '၀':
         case '၁':
         case '၂':
         case '၃':
         case '၄':
         case '၅':
         case '၆':
         case '၇':
         case '၈':
         case '၉':
            this.matchRange('က', '၉');
            break;
         case 'ၐ':
         case 'ၑ':
         case 'ၒ':
         case 'ၓ':
         case 'ၔ':
         case 'ၕ':
         case 'ၖ':
         case 'ၗ':
         case 'ၘ':
         case 'ၙ':
         case 'ၚ':
         case 'ၛ':
         case 'ၜ':
         case 'ၝ':
         case 'ၞ':
         case 'ၟ':
         case 'ၠ':
         case 'ၡ':
         case 'ၢ':
         case 'ၣ':
         case 'ၤ':
         case 'ၥ':
         case 'ၦ':
         case 'ၧ':
         case 'ၨ':
         case 'ၩ':
         case 'ၪ':
         case 'ၫ':
         case 'ၬ':
         case 'ၭ':
         case 'ၮ':
         case 'ၯ':
         case 'ၰ':
         case 'ၱ':
         case 'ၲ':
         case 'ၳ':
         case 'ၴ':
         case 'ၵ':
         case 'ၶ':
         case 'ၷ':
         case 'ၸ':
         case 'ၹ':
         case 'ၺ':
         case 'ၻ':
         case 'ၼ':
         case 'ၽ':
         case 'ၾ':
         case 'ၿ':
         case 'ႀ':
         case 'ႁ':
         case 'ႂ':
         case 'ႃ':
         case 'ႄ':
         case 'ႅ':
         case 'ႆ':
         case 'ႇ':
         case 'ႈ':
         case 'ႉ':
         case 'ႊ':
         case 'ႋ':
         case 'ႌ':
         case 'ႍ':
         case 'ႎ':
         case 'ႏ':
         case '႐':
         case '႑':
         case '႒':
         case '႓':
         case '႔':
         case '႕':
         case '႖':
         case '႗':
         case '႘':
         case '႙':
         case 'ႚ':
         case 'ႛ':
         case 'ႜ':
         case 'ႝ':
            this.matchRange('ၐ', 'ႝ');
            break;
         case 'Ⴀ':
         case 'Ⴁ':
         case 'Ⴂ':
         case 'Ⴃ':
         case 'Ⴄ':
         case 'Ⴅ':
         case 'Ⴆ':
         case 'Ⴇ':
         case 'Ⴈ':
         case 'Ⴉ':
         case 'Ⴊ':
         case 'Ⴋ':
         case 'Ⴌ':
         case 'Ⴍ':
         case 'Ⴎ':
         case 'Ⴏ':
         case 'Ⴐ':
         case 'Ⴑ':
         case 'Ⴒ':
         case 'Ⴓ':
         case 'Ⴔ':
         case 'Ⴕ':
         case 'Ⴖ':
         case 'Ⴗ':
         case 'Ⴘ':
         case 'Ⴙ':
         case 'Ⴚ':
         case 'Ⴛ':
         case 'Ⴜ':
         case 'Ⴝ':
         case 'Ⴞ':
         case 'Ⴟ':
         case 'Ⴠ':
         case 'Ⴡ':
         case 'Ⴢ':
         case 'Ⴣ':
         case 'Ⴤ':
         case 'Ⴥ':
            this.matchRange('Ⴀ', 'Ⴥ');
            break;
         case 'ა':
         case 'ბ':
         case 'გ':
         case 'დ':
         case 'ე':
         case 'ვ':
         case 'ზ':
         case 'თ':
         case 'ი':
         case 'კ':
         case 'ლ':
         case 'მ':
         case 'ნ':
         case 'ო':
         case 'პ':
         case 'ჟ':
         case 'რ':
         case 'ს':
         case 'ტ':
         case 'უ':
         case 'ფ':
         case 'ქ':
         case 'ღ':
         case 'ყ':
         case 'შ':
         case 'ჩ':
         case 'ც':
         case 'ძ':
         case 'წ':
         case 'ჭ':
         case 'ხ':
         case 'ჯ':
         case 'ჰ':
         case 'ჱ':
         case 'ჲ':
         case 'ჳ':
         case 'ჴ':
         case 'ჵ':
         case 'ჶ':
         case 'ჷ':
         case 'ჸ':
         case 'ჹ':
         case 'ჺ':
            this.matchRange('ა', 'ჺ');
            break;
         case 'ჼ':
            this.matchRange('ჼ', 'ჼ');
            break;
         case 'ቊ':
         case 'ቋ':
         case 'ቌ':
         case 'ቍ':
            this.matchRange('ቊ', 'ቍ');
            break;
         case 'ቐ':
         case 'ቑ':
         case 'ቒ':
         case 'ቓ':
         case 'ቔ':
         case 'ቕ':
         case 'ቖ':
            this.matchRange('ቐ', 'ቖ');
            break;
         case 'ቘ':
            this.matchRange('ቘ', 'ቘ');
            break;
         case 'ቚ':
         case 'ቛ':
         case 'ቜ':
         case 'ቝ':
            this.matchRange('ቚ', 'ቝ');
            break;
         case 'በ':
         case 'ቡ':
         case 'ቢ':
         case 'ባ':
         case 'ቤ':
         case 'ብ':
         case 'ቦ':
         case 'ቧ':
         case 'ቨ':
         case 'ቩ':
         case 'ቪ':
         case 'ቫ':
         case 'ቬ':
         case 'ቭ':
         case 'ቮ':
         case 'ቯ':
         case 'ተ':
         case 'ቱ':
         case 'ቲ':
         case 'ታ':
         case 'ቴ':
         case 'ት':
         case 'ቶ':
         case 'ቷ':
         case 'ቸ':
         case 'ቹ':
         case 'ቺ':
         case 'ቻ':
         case 'ቼ':
         case 'ች':
         case 'ቾ':
         case 'ቿ':
         case 'ኀ':
         case 'ኁ':
         case 'ኂ':
         case 'ኃ':
         case 'ኄ':
         case 'ኅ':
         case 'ኆ':
         case 'ኇ':
         case 'ኈ':
            this.matchRange('በ', 'ኈ');
            break;
         case 'ኊ':
         case 'ኋ':
         case 'ኌ':
         case 'ኍ':
            this.matchRange('ኊ', 'ኍ');
            break;
         case 'ነ':
         case 'ኑ':
         case 'ኒ':
         case 'ና':
         case 'ኔ':
         case 'ን':
         case 'ኖ':
         case 'ኗ':
         case 'ኘ':
         case 'ኙ':
         case 'ኚ':
         case 'ኛ':
         case 'ኜ':
         case 'ኝ':
         case 'ኞ':
         case 'ኟ':
         case 'አ':
         case 'ኡ':
         case 'ኢ':
         case 'ኣ':
         case 'ኤ':
         case 'እ':
         case 'ኦ':
         case 'ኧ':
         case 'ከ':
         case 'ኩ':
         case 'ኪ':
         case 'ካ':
         case 'ኬ':
         case 'ክ':
         case 'ኮ':
         case 'ኯ':
         case 'ኰ':
            this.matchRange('ነ', 'ኰ');
            break;
         case 'ኲ':
         case 'ኳ':
         case 'ኴ':
         case 'ኵ':
            this.matchRange('ኲ', 'ኵ');
            break;
         case 'ኸ':
         case 'ኹ':
         case 'ኺ':
         case 'ኻ':
         case 'ኼ':
         case 'ኽ':
         case 'ኾ':
            this.matchRange('ኸ', 'ኾ');
            break;
         case 'ዀ':
            this.matchRange('ዀ', 'ዀ');
            break;
         case 'ዂ':
         case 'ዃ':
         case 'ዄ':
         case 'ዅ':
            this.matchRange('ዂ', 'ዅ');
            break;
         case 'ወ':
         case 'ዉ':
         case 'ዊ':
         case 'ዋ':
         case 'ዌ':
         case 'ው':
         case 'ዎ':
         case 'ዏ':
         case 'ዐ':
         case 'ዑ':
         case 'ዒ':
         case 'ዓ':
         case 'ዔ':
         case 'ዕ':
         case 'ዖ':
            this.matchRange('ወ', 'ዖ');
            break;
         case 'ዘ':
         case 'ዙ':
         case 'ዚ':
         case 'ዛ':
         case 'ዜ':
         case 'ዝ':
         case 'ዞ':
         case 'ዟ':
         case 'ዠ':
         case 'ዡ':
         case 'ዢ':
         case 'ዣ':
         case 'ዤ':
         case 'ዥ':
         case 'ዦ':
         case 'ዧ':
         case 'የ':
         case 'ዩ':
         case 'ዪ':
         case 'ያ':
         case 'ዬ':
         case 'ይ':
         case 'ዮ':
         case 'ዯ':
         case 'ደ':
         case 'ዱ':
         case 'ዲ':
         case 'ዳ':
         case 'ዴ':
         case 'ድ':
         case 'ዶ':
         case 'ዷ':
         case 'ዸ':
         case 'ዹ':
         case 'ዺ':
         case 'ዻ':
         case 'ዼ':
         case 'ዽ':
         case 'ዾ':
         case 'ዿ':
         case 'ጀ':
         case 'ጁ':
         case 'ጂ':
         case 'ጃ':
         case 'ጄ':
         case 'ጅ':
         case 'ጆ':
         case 'ጇ':
         case 'ገ':
         case 'ጉ':
         case 'ጊ':
         case 'ጋ':
         case 'ጌ':
         case 'ግ':
         case 'ጎ':
         case 'ጏ':
         case 'ጐ':
            this.matchRange('ዘ', 'ጐ');
            break;
         case 'ጒ':
         case 'ጓ':
         case 'ጔ':
         case 'ጕ':
            this.matchRange('ጒ', 'ጕ');
            break;
         case 'ጘ':
         case 'ጙ':
         case 'ጚ':
         case 'ጛ':
         case 'ጜ':
         case 'ጝ':
         case 'ጞ':
         case 'ጟ':
         case 'ጠ':
         case 'ጡ':
         case 'ጢ':
         case 'ጣ':
         case 'ጤ':
         case 'ጥ':
         case 'ጦ':
         case 'ጧ':
         case 'ጨ':
         case 'ጩ':
         case 'ጪ':
         case 'ጫ':
         case 'ጬ':
         case 'ጭ':
         case 'ጮ':
         case 'ጯ':
         case 'ጰ':
         case 'ጱ':
         case 'ጲ':
         case 'ጳ':
         case 'ጴ':
         case 'ጵ':
         case 'ጶ':
         case 'ጷ':
         case 'ጸ':
         case 'ጹ':
         case 'ጺ':
         case 'ጻ':
         case 'ጼ':
         case 'ጽ':
         case 'ጾ':
         case 'ጿ':
         case 'ፀ':
         case 'ፁ':
         case 'ፂ':
         case 'ፃ':
         case 'ፄ':
         case 'ፅ':
         case 'ፆ':
         case 'ፇ':
         case 'ፈ':
         case 'ፉ':
         case 'ፊ':
         case 'ፋ':
         case 'ፌ':
         case 'ፍ':
         case 'ፎ':
         case 'ፏ':
         case 'ፐ':
         case 'ፑ':
         case 'ፒ':
         case 'ፓ':
         case 'ፔ':
         case 'ፕ':
         case 'ፖ':
         case 'ፗ':
         case 'ፘ':
         case 'ፙ':
         case 'ፚ':
            this.matchRange('ጘ', 'ፚ');
            break;
         case '፝':
         case '፞':
         case '፟':
            this.matchRange('፝', '፟');
            break;
         case 'ᎀ':
         case 'ᎁ':
         case 'ᎂ':
         case 'ᎃ':
         case 'ᎄ':
         case 'ᎅ':
         case 'ᎆ':
         case 'ᎇ':
         case 'ᎈ':
         case 'ᎉ':
         case 'ᎊ':
         case 'ᎋ':
         case 'ᎌ':
         case 'ᎍ':
         case 'ᎎ':
         case 'ᎏ':
            this.matchRange('ᎀ', 'ᎏ');
            break;
         case 'Ꭰ':
         case 'Ꭱ':
         case 'Ꭲ':
         case 'Ꭳ':
         case 'Ꭴ':
         case 'Ꭵ':
         case 'Ꭶ':
         case 'Ꭷ':
         case 'Ꭸ':
         case 'Ꭹ':
         case 'Ꭺ':
         case 'Ꭻ':
         case 'Ꭼ':
         case 'Ꭽ':
         case 'Ꭾ':
         case 'Ꭿ':
         case 'Ꮀ':
         case 'Ꮁ':
         case 'Ꮂ':
         case 'Ꮃ':
         case 'Ꮄ':
         case 'Ꮅ':
         case 'Ꮆ':
         case 'Ꮇ':
         case 'Ꮈ':
         case 'Ꮉ':
         case 'Ꮊ':
         case 'Ꮋ':
         case 'Ꮌ':
         case 'Ꮍ':
         case 'Ꮎ':
         case 'Ꮏ':
         case 'Ꮐ':
         case 'Ꮑ':
         case 'Ꮒ':
         case 'Ꮓ':
         case 'Ꮔ':
         case 'Ꮕ':
         case 'Ꮖ':
         case 'Ꮗ':
         case 'Ꮘ':
         case 'Ꮙ':
         case 'Ꮚ':
         case 'Ꮛ':
         case 'Ꮜ':
         case 'Ꮝ':
         case 'Ꮞ':
         case 'Ꮟ':
         case 'Ꮠ':
         case 'Ꮡ':
         case 'Ꮢ':
         case 'Ꮣ':
         case 'Ꮤ':
         case 'Ꮥ':
         case 'Ꮦ':
         case 'Ꮧ':
         case 'Ꮨ':
         case 'Ꮩ':
         case 'Ꮪ':
         case 'Ꮫ':
         case 'Ꮬ':
         case 'Ꮭ':
         case 'Ꮮ':
         case 'Ꮯ':
         case 'Ꮰ':
         case 'Ꮱ':
         case 'Ꮲ':
         case 'Ꮳ':
         case 'Ꮴ':
         case 'Ꮵ':
         case 'Ꮶ':
         case 'Ꮷ':
         case 'Ꮸ':
         case 'Ꮹ':
         case 'Ꮺ':
         case 'Ꮻ':
         case 'Ꮼ':
         case 'Ꮽ':
         case 'Ꮾ':
         case 'Ꮿ':
         case 'Ᏸ':
         case 'Ᏹ':
         case 'Ᏺ':
         case 'Ᏻ':
         case 'Ᏼ':
            this.matchRange('Ꭰ', 'Ᏼ');
            break;
         case 'ᙯ':
         case 'ᙰ':
         case 'ᙱ':
         case 'ᙲ':
         case 'ᙳ':
         case 'ᙴ':
         case 'ᙵ':
         case 'ᙶ':
         case 'ᙷ':
         case 'ᙸ':
         case 'ᙹ':
         case 'ᙺ':
         case 'ᙻ':
         case 'ᙼ':
         case 'ᙽ':
         case 'ᙾ':
         case 'ᙿ':
            this.matchRange('ᙯ', 'ᙿ');
            break;
         case 'ᚁ':
         case 'ᚂ':
         case 'ᚃ':
         case 'ᚄ':
         case 'ᚅ':
         case 'ᚆ':
         case 'ᚇ':
         case 'ᚈ':
         case 'ᚉ':
         case 'ᚊ':
         case 'ᚋ':
         case 'ᚌ':
         case 'ᚍ':
         case 'ᚎ':
         case 'ᚏ':
         case 'ᚐ':
         case 'ᚑ':
         case 'ᚒ':
         case 'ᚓ':
         case 'ᚔ':
         case 'ᚕ':
         case 'ᚖ':
         case 'ᚗ':
         case 'ᚘ':
         case 'ᚙ':
         case 'ᚚ':
            this.matchRange('ᚁ', 'ᚚ');
            break;
         case 'ᚠ':
         case 'ᚡ':
         case 'ᚢ':
         case 'ᚣ':
         case 'ᚤ':
         case 'ᚥ':
         case 'ᚦ':
         case 'ᚧ':
         case 'ᚨ':
         case 'ᚩ':
         case 'ᚪ':
         case 'ᚫ':
         case 'ᚬ':
         case 'ᚭ':
         case 'ᚮ':
         case 'ᚯ':
         case 'ᚰ':
         case 'ᚱ':
         case 'ᚲ':
         case 'ᚳ':
         case 'ᚴ':
         case 'ᚵ':
         case 'ᚶ':
         case 'ᚷ':
         case 'ᚸ':
         case 'ᚹ':
         case 'ᚺ':
         case 'ᚻ':
         case 'ᚼ':
         case 'ᚽ':
         case 'ᚾ':
         case 'ᚿ':
         case 'ᛀ':
         case 'ᛁ':
         case 'ᛂ':
         case 'ᛃ':
         case 'ᛄ':
         case 'ᛅ':
         case 'ᛆ':
         case 'ᛇ':
         case 'ᛈ':
         case 'ᛉ':
         case 'ᛊ':
         case 'ᛋ':
         case 'ᛌ':
         case 'ᛍ':
         case 'ᛎ':
         case 'ᛏ':
         case 'ᛐ':
         case 'ᛑ':
         case 'ᛒ':
         case 'ᛓ':
         case 'ᛔ':
         case 'ᛕ':
         case 'ᛖ':
         case 'ᛗ':
         case 'ᛘ':
         case 'ᛙ':
         case 'ᛚ':
         case 'ᛛ':
         case 'ᛜ':
         case 'ᛝ':
         case 'ᛞ':
         case 'ᛟ':
         case 'ᛠ':
         case 'ᛡ':
         case 'ᛢ':
         case 'ᛣ':
         case 'ᛤ':
         case 'ᛥ':
         case 'ᛦ':
         case 'ᛧ':
         case 'ᛨ':
         case 'ᛩ':
         case 'ᛪ':
            this.matchRange('ᚠ', 'ᛪ');
            break;
         case 'ᛮ':
         case 'ᛯ':
         case 'ᛰ':
            this.matchRange('ᛮ', 'ᛰ');
            break;
         case 'ᜀ':
         case 'ᜁ':
         case 'ᜂ':
         case 'ᜃ':
         case 'ᜄ':
         case 'ᜅ':
         case 'ᜆ':
         case 'ᜇ':
         case 'ᜈ':
         case 'ᜉ':
         case 'ᜊ':
         case 'ᜋ':
         case 'ᜌ':
            this.matchRange('ᜀ', 'ᜌ');
            break;
         case 'ᜎ':
         case 'ᜏ':
         case 'ᜐ':
         case 'ᜑ':
         case 'ᜒ':
         case 'ᜓ':
         case '᜔':
            this.matchRange('ᜎ', '᜔');
            break;
         case 'ᜠ':
         case 'ᜡ':
         case 'ᜢ':
         case 'ᜣ':
         case 'ᜤ':
         case 'ᜥ':
         case 'ᜦ':
         case 'ᜧ':
         case 'ᜨ':
         case 'ᜩ':
         case 'ᜪ':
         case 'ᜫ':
         case 'ᜬ':
         case 'ᜭ':
         case 'ᜮ':
         case 'ᜯ':
         case 'ᜰ':
         case 'ᜱ':
         case 'ᜲ':
         case 'ᜳ':
         case '᜴':
            this.matchRange('ᜠ', '᜴');
            break;
         case 'ᝀ':
         case 'ᝁ':
         case 'ᝂ':
         case 'ᝃ':
         case 'ᝄ':
         case 'ᝅ':
         case 'ᝆ':
         case 'ᝇ':
         case 'ᝈ':
         case 'ᝉ':
         case 'ᝊ':
         case 'ᝋ':
         case 'ᝌ':
         case 'ᝍ':
         case 'ᝎ':
         case 'ᝏ':
         case 'ᝐ':
         case 'ᝑ':
         case 'ᝒ':
         case 'ᝓ':
            this.matchRange('ᝀ', 'ᝓ');
            break;
         case 'ᝠ':
         case 'ᝡ':
         case 'ᝢ':
         case 'ᝣ':
         case 'ᝤ':
         case 'ᝥ':
         case 'ᝦ':
         case 'ᝧ':
         case 'ᝨ':
         case 'ᝩ':
         case 'ᝪ':
         case 'ᝫ':
         case 'ᝬ':
            this.matchRange('ᝠ', 'ᝬ');
            break;
         case 'ᝮ':
         case 'ᝯ':
         case 'ᝰ':
            this.matchRange('ᝮ', 'ᝰ');
            break;
         case 'ᝲ':
         case 'ᝳ':
            this.matchRange('ᝲ', 'ᝳ');
            break;
         case 'ក':
         case 'ខ':
         case 'គ':
         case 'ឃ':
         case 'ង':
         case 'ច':
         case 'ឆ':
         case 'ជ':
         case 'ឈ':
         case 'ញ':
         case 'ដ':
         case 'ឋ':
         case 'ឌ':
         case 'ឍ':
         case 'ណ':
         case 'ត':
         case 'ថ':
         case 'ទ':
         case 'ធ':
         case 'ន':
         case 'ប':
         case 'ផ':
         case 'ព':
         case 'ភ':
         case 'ម':
         case 'យ':
         case 'រ':
         case 'ល':
         case 'វ':
         case 'ឝ':
         case 'ឞ':
         case 'ស':
         case 'ហ':
         case 'ឡ':
         case 'អ':
         case 'ឣ':
         case 'ឤ':
         case 'ឥ':
         case 'ឦ':
         case 'ឧ':
         case 'ឨ':
         case 'ឩ':
         case 'ឪ':
         case 'ឫ':
         case 'ឬ':
         case 'ឭ':
         case 'ឮ':
         case 'ឯ':
         case 'ឰ':
         case 'ឱ':
         case 'ឲ':
         case 'ឳ':
         case '឴':
         case '឵':
         case 'ា':
         case 'ិ':
         case 'ី':
         case 'ឹ':
         case 'ឺ':
         case 'ុ':
         case 'ូ':
         case 'ួ':
         case 'ើ':
         case 'ឿ':
         case 'ៀ':
         case 'េ':
         case 'ែ':
         case 'ៃ':
         case 'ោ':
         case 'ៅ':
         case 'ំ':
         case 'ះ':
         case 'ៈ':
         case '៉':
         case '៊':
         case '់':
         case '៌':
         case '៍':
         case '៎':
         case '៏':
         case '័':
         case '៑':
         case '្':
         case '៓':
            this.matchRange('ក', '៓');
            break;
         case 'ៗ':
            this.matchRange('ៗ', 'ៗ');
            break;
         case '៛':
         case 'ៜ':
         case '៝':
            this.matchRange('៛', '៝');
            break;
         case '០':
         case '១':
         case '២':
         case '៣':
         case '៤':
         case '៥':
         case '៦':
         case '៧':
         case '៨':
         case '៩':
            this.matchRange('០', '៩');
            break;
         case '᠋':
         case '᠌':
         case '᠍':
            this.matchRange('᠋', '᠍');
            break;
         case '᠐':
         case '᠑':
         case '᠒':
         case '᠓':
         case '᠔':
         case '᠕':
         case '᠖':
         case '᠗':
         case '᠘':
         case '᠙':
            this.matchRange('᠐', '᠙');
            break;
         case 'ᠠ':
         case 'ᠡ':
         case 'ᠢ':
         case 'ᠣ':
         case 'ᠤ':
         case 'ᠥ':
         case 'ᠦ':
         case 'ᠧ':
         case 'ᠨ':
         case 'ᠩ':
         case 'ᠪ':
         case 'ᠫ':
         case 'ᠬ':
         case 'ᠭ':
         case 'ᠮ':
         case 'ᠯ':
         case 'ᠰ':
         case 'ᠱ':
         case 'ᠲ':
         case 'ᠳ':
         case 'ᠴ':
         case 'ᠵ':
         case 'ᠶ':
         case 'ᠷ':
         case 'ᠸ':
         case 'ᠹ':
         case 'ᠺ':
         case 'ᠻ':
         case 'ᠼ':
         case 'ᠽ':
         case 'ᠾ':
         case 'ᠿ':
         case 'ᡀ':
         case 'ᡁ':
         case 'ᡂ':
         case 'ᡃ':
         case 'ᡄ':
         case 'ᡅ':
         case 'ᡆ':
         case 'ᡇ':
         case 'ᡈ':
         case 'ᡉ':
         case 'ᡊ':
         case 'ᡋ':
         case 'ᡌ':
         case 'ᡍ':
         case 'ᡎ':
         case 'ᡏ':
         case 'ᡐ':
         case 'ᡑ':
         case 'ᡒ':
         case 'ᡓ':
         case 'ᡔ':
         case 'ᡕ':
         case 'ᡖ':
         case 'ᡗ':
         case 'ᡘ':
         case 'ᡙ':
         case 'ᡚ':
         case 'ᡛ':
         case 'ᡜ':
         case 'ᡝ':
         case 'ᡞ':
         case 'ᡟ':
         case 'ᡠ':
         case 'ᡡ':
         case 'ᡢ':
         case 'ᡣ':
         case 'ᡤ':
         case 'ᡥ':
         case 'ᡦ':
         case 'ᡧ':
         case 'ᡨ':
         case 'ᡩ':
         case 'ᡪ':
         case 'ᡫ':
         case 'ᡬ':
         case 'ᡭ':
         case 'ᡮ':
         case 'ᡯ':
         case 'ᡰ':
         case 'ᡱ':
         case 'ᡲ':
         case 'ᡳ':
         case 'ᡴ':
         case 'ᡵ':
         case 'ᡶ':
         case 'ᡷ':
            this.matchRange('ᠠ', 'ᡷ');
            break;
         case 'ᢀ':
         case 'ᢁ':
         case 'ᢂ':
         case 'ᢃ':
         case 'ᢄ':
         case 'ᢅ':
         case 'ᢆ':
         case 'ᢇ':
         case 'ᢈ':
         case 'ᢉ':
         case 'ᢊ':
         case 'ᢋ':
         case 'ᢌ':
         case 'ᢍ':
         case 'ᢎ':
         case 'ᢏ':
         case 'ᢐ':
         case 'ᢑ':
         case 'ᢒ':
         case 'ᢓ':
         case 'ᢔ':
         case 'ᢕ':
         case 'ᢖ':
         case 'ᢗ':
         case 'ᢘ':
         case 'ᢙ':
         case 'ᢚ':
         case 'ᢛ':
         case 'ᢜ':
         case 'ᢝ':
         case 'ᢞ':
         case 'ᢟ':
         case 'ᢠ':
         case 'ᢡ':
         case 'ᢢ':
         case 'ᢣ':
         case 'ᢤ':
         case 'ᢥ':
         case 'ᢦ':
         case 'ᢧ':
         case 'ᢨ':
         case 'ᢩ':
         case 'ᢪ':
            this.matchRange('ᢀ', 'ᢪ');
            break;
         case 'ᢰ':
         case 'ᢱ':
         case 'ᢲ':
         case 'ᢳ':
         case 'ᢴ':
         case 'ᢵ':
         case 'ᢶ':
         case 'ᢷ':
         case 'ᢸ':
         case 'ᢹ':
         case 'ᢺ':
         case 'ᢻ':
         case 'ᢼ':
         case 'ᢽ':
         case 'ᢾ':
         case 'ᢿ':
         case 'ᣀ':
         case 'ᣁ':
         case 'ᣂ':
         case 'ᣃ':
         case 'ᣄ':
         case 'ᣅ':
         case 'ᣆ':
         case 'ᣇ':
         case 'ᣈ':
         case 'ᣉ':
         case 'ᣊ':
         case 'ᣋ':
         case 'ᣌ':
         case 'ᣍ':
         case 'ᣎ':
         case 'ᣏ':
         case 'ᣐ':
         case 'ᣑ':
         case 'ᣒ':
         case 'ᣓ':
         case 'ᣔ':
         case 'ᣕ':
         case 'ᣖ':
         case 'ᣗ':
         case 'ᣘ':
         case 'ᣙ':
         case 'ᣚ':
         case 'ᣛ':
         case 'ᣜ':
         case 'ᣝ':
         case 'ᣞ':
         case 'ᣟ':
         case 'ᣠ':
         case 'ᣡ':
         case 'ᣢ':
         case 'ᣣ':
         case 'ᣤ':
         case 'ᣥ':
         case 'ᣦ':
         case 'ᣧ':
         case 'ᣨ':
         case 'ᣩ':
         case 'ᣪ':
         case 'ᣫ':
         case 'ᣬ':
         case 'ᣭ':
         case 'ᣮ':
         case 'ᣯ':
         case 'ᣰ':
         case 'ᣱ':
         case 'ᣲ':
         case 'ᣳ':
         case 'ᣴ':
         case 'ᣵ':
            this.matchRange('ᢰ', 'ᣵ');
            break;
         case 'ᤀ':
         case 'ᤁ':
         case 'ᤂ':
         case 'ᤃ':
         case 'ᤄ':
         case 'ᤅ':
         case 'ᤆ':
         case 'ᤇ':
         case 'ᤈ':
         case 'ᤉ':
         case 'ᤊ':
         case 'ᤋ':
         case 'ᤌ':
         case 'ᤍ':
         case 'ᤎ':
         case 'ᤏ':
         case 'ᤐ':
         case 'ᤑ':
         case 'ᤒ':
         case 'ᤓ':
         case 'ᤔ':
         case 'ᤕ':
         case 'ᤖ':
         case 'ᤗ':
         case 'ᤘ':
         case 'ᤙ':
         case 'ᤚ':
         case 'ᤛ':
         case 'ᤜ':
            this.matchRange('ᤀ', 'ᤜ');
            break;
         case 'ᤠ':
         case 'ᤡ':
         case 'ᤢ':
         case 'ᤣ':
         case 'ᤤ':
         case 'ᤥ':
         case 'ᤦ':
         case 'ᤧ':
         case 'ᤨ':
         case 'ᤩ':
         case 'ᤪ':
         case 'ᤫ':
            this.matchRange('ᤠ', 'ᤫ');
            break;
         case 'ᤰ':
         case 'ᤱ':
         case 'ᤲ':
         case 'ᤳ':
         case 'ᤴ':
         case 'ᤵ':
         case 'ᤶ':
         case 'ᤷ':
         case 'ᤸ':
         case '᤹':
         case '᤺':
         case '᤻':
            this.matchRange('ᤰ', '᤻');
            break;
         case '᥆':
         case '᥇':
         case '᥈':
         case '᥉':
         case '᥊':
         case '᥋':
         case '᥌':
         case '᥍':
         case '᥎':
         case '᥏':
         case 'ᥐ':
         case 'ᥑ':
         case 'ᥒ':
         case 'ᥓ':
         case 'ᥔ':
         case 'ᥕ':
         case 'ᥖ':
         case 'ᥗ':
         case 'ᥘ':
         case 'ᥙ':
         case 'ᥚ':
         case 'ᥛ':
         case 'ᥜ':
         case 'ᥝ':
         case 'ᥞ':
         case 'ᥟ':
         case 'ᥠ':
         case 'ᥡ':
         case 'ᥢ':
         case 'ᥣ':
         case 'ᥤ':
         case 'ᥥ':
         case 'ᥦ':
         case 'ᥧ':
         case 'ᥨ':
         case 'ᥩ':
         case 'ᥪ':
         case 'ᥫ':
         case 'ᥬ':
         case 'ᥭ':
            this.matchRange('᥆', 'ᥭ');
            break;
         case 'ᥰ':
         case 'ᥱ':
         case 'ᥲ':
         case 'ᥳ':
         case 'ᥴ':
            this.matchRange('ᥰ', 'ᥴ');
            break;
         case 'ᦀ':
         case 'ᦁ':
         case 'ᦂ':
         case 'ᦃ':
         case 'ᦄ':
         case 'ᦅ':
         case 'ᦆ':
         case 'ᦇ':
         case 'ᦈ':
         case 'ᦉ':
         case 'ᦊ':
         case 'ᦋ':
         case 'ᦌ':
         case 'ᦍ':
         case 'ᦎ':
         case 'ᦏ':
         case 'ᦐ':
         case 'ᦑ':
         case 'ᦒ':
         case 'ᦓ':
         case 'ᦔ':
         case 'ᦕ':
         case 'ᦖ':
         case 'ᦗ':
         case 'ᦘ':
         case 'ᦙ':
         case 'ᦚ':
         case 'ᦛ':
         case 'ᦜ':
         case 'ᦝ':
         case 'ᦞ':
         case 'ᦟ':
         case 'ᦠ':
         case 'ᦡ':
         case 'ᦢ':
         case 'ᦣ':
         case 'ᦤ':
         case 'ᦥ':
         case 'ᦦ':
         case 'ᦧ':
         case 'ᦨ':
         case 'ᦩ':
         case 'ᦪ':
         case 'ᦫ':
            this.matchRange('ᦀ', 'ᦫ');
            break;
         case 'ᦰ':
         case 'ᦱ':
         case 'ᦲ':
         case 'ᦳ':
         case 'ᦴ':
         case 'ᦵ':
         case 'ᦶ':
         case 'ᦷ':
         case 'ᦸ':
         case 'ᦹ':
         case 'ᦺ':
         case 'ᦻ':
         case 'ᦼ':
         case 'ᦽ':
         case 'ᦾ':
         case 'ᦿ':
         case 'ᧀ':
         case 'ᧁ':
         case 'ᧂ':
         case 'ᧃ':
         case 'ᧄ':
         case 'ᧅ':
         case 'ᧆ':
         case 'ᧇ':
         case 'ᧈ':
         case 'ᧉ':
            this.matchRange('ᦰ', 'ᧉ');
            break;
         case '᧐':
         case '᧑':
         case '᧒':
         case '᧓':
         case '᧔':
         case '᧕':
         case '᧖':
         case '᧗':
         case '᧘':
         case '᧙':
            this.matchRange('᧐', '᧙');
            break;
         case 'ᨀ':
         case 'ᨁ':
         case 'ᨂ':
         case 'ᨃ':
         case 'ᨄ':
         case 'ᨅ':
         case 'ᨆ':
         case 'ᨇ':
         case 'ᨈ':
         case 'ᨉ':
         case 'ᨊ':
         case 'ᨋ':
         case 'ᨌ':
         case 'ᨍ':
         case 'ᨎ':
         case 'ᨏ':
         case 'ᨐ':
         case 'ᨑ':
         case 'ᨒ':
         case 'ᨓ':
         case 'ᨔ':
         case 'ᨕ':
         case 'ᨖ':
         case 'ᨗ':
         case 'ᨘ':
         case 'ᨙ':
         case 'ᨚ':
         case 'ᨛ':
            this.matchRange('ᨀ', 'ᨛ');
            break;
         case 'ᨠ':
         case 'ᨡ':
         case 'ᨢ':
         case 'ᨣ':
         case 'ᨤ':
         case 'ᨥ':
         case 'ᨦ':
         case 'ᨧ':
         case 'ᨨ':
         case 'ᨩ':
         case 'ᨪ':
         case 'ᨫ':
         case 'ᨬ':
         case 'ᨭ':
         case 'ᨮ':
         case 'ᨯ':
         case 'ᨰ':
         case 'ᨱ':
         case 'ᨲ':
         case 'ᨳ':
         case 'ᨴ':
         case 'ᨵ':
         case 'ᨶ':
         case 'ᨷ':
         case 'ᨸ':
         case 'ᨹ':
         case 'ᨺ':
         case 'ᨻ':
         case 'ᨼ':
         case 'ᨽ':
         case 'ᨾ':
         case 'ᨿ':
         case 'ᩀ':
         case 'ᩁ':
         case 'ᩂ':
         case 'ᩃ':
         case 'ᩄ':
         case 'ᩅ':
         case 'ᩆ':
         case 'ᩇ':
         case 'ᩈ':
         case 'ᩉ':
         case 'ᩊ':
         case 'ᩋ':
         case 'ᩌ':
         case 'ᩍ':
         case 'ᩎ':
         case 'ᩏ':
         case 'ᩐ':
         case 'ᩑ':
         case 'ᩒ':
         case 'ᩓ':
         case 'ᩔ':
         case 'ᩕ':
         case 'ᩖ':
         case 'ᩗ':
         case 'ᩘ':
         case 'ᩙ':
         case 'ᩚ':
         case 'ᩛ':
         case 'ᩜ':
         case 'ᩝ':
         case 'ᩞ':
            this.matchRange('ᨠ', 'ᩞ');
            break;
         case '᩠':
         case 'ᩡ':
         case 'ᩢ':
         case 'ᩣ':
         case 'ᩤ':
         case 'ᩥ':
         case 'ᩦ':
         case 'ᩧ':
         case 'ᩨ':
         case 'ᩩ':
         case 'ᩪ':
         case 'ᩫ':
         case 'ᩬ':
         case 'ᩭ':
         case 'ᩮ':
         case 'ᩯ':
         case 'ᩰ':
         case 'ᩱ':
         case 'ᩲ':
         case 'ᩳ':
         case 'ᩴ':
         case '᩵':
         case '᩶':
         case '᩷':
         case '᩸':
         case '᩹':
         case '᩺':
         case '᩻':
         case '᩼':
            this.matchRange('᩠', '᩼');
            break;
         case '᩿':
         case '᪀':
         case '᪁':
         case '᪂':
         case '᪃':
         case '᪄':
         case '᪅':
         case '᪆':
         case '᪇':
         case '᪈':
         case '᪉':
            this.matchRange('᩿', '᪉');
            break;
         case '᪐':
         case '᪑':
         case '᪒':
         case '᪓':
         case '᪔':
         case '᪕':
         case '᪖':
         case '᪗':
         case '᪘':
         case '᪙':
            this.matchRange('᪐', '᪙');
            break;
         case 'ᪧ':
            this.matchRange('ᪧ', 'ᪧ');
            break;
         case 'ᬀ':
         case 'ᬁ':
         case 'ᬂ':
         case 'ᬃ':
         case 'ᬄ':
         case 'ᬅ':
         case 'ᬆ':
         case 'ᬇ':
         case 'ᬈ':
         case 'ᬉ':
         case 'ᬊ':
         case 'ᬋ':
         case 'ᬌ':
         case 'ᬍ':
         case 'ᬎ':
         case 'ᬏ':
         case 'ᬐ':
         case 'ᬑ':
         case 'ᬒ':
         case 'ᬓ':
         case 'ᬔ':
         case 'ᬕ':
         case 'ᬖ':
         case 'ᬗ':
         case 'ᬘ':
         case 'ᬙ':
         case 'ᬚ':
         case 'ᬛ':
         case 'ᬜ':
         case 'ᬝ':
         case 'ᬞ':
         case 'ᬟ':
         case 'ᬠ':
         case 'ᬡ':
         case 'ᬢ':
         case 'ᬣ':
         case 'ᬤ':
         case 'ᬥ':
         case 'ᬦ':
         case 'ᬧ':
         case 'ᬨ':
         case 'ᬩ':
         case 'ᬪ':
         case 'ᬫ':
         case 'ᬬ':
         case 'ᬭ':
         case 'ᬮ':
         case 'ᬯ':
         case 'ᬰ':
         case 'ᬱ':
         case 'ᬲ':
         case 'ᬳ':
         case '᬴':
         case 'ᬵ':
         case 'ᬶ':
         case 'ᬷ':
         case 'ᬸ':
         case 'ᬹ':
         case 'ᬺ':
         case 'ᬻ':
         case 'ᬼ':
         case 'ᬽ':
         case 'ᬾ':
         case 'ᬿ':
         case 'ᭀ':
         case 'ᭁ':
         case 'ᭂ':
         case 'ᭃ':
         case '᭄':
         case 'ᭅ':
         case 'ᭆ':
         case 'ᭇ':
         case 'ᭈ':
         case 'ᭉ':
         case 'ᭊ':
         case 'ᭋ':
            this.matchRange('ᬀ', 'ᭋ');
            break;
         case '᭐':
         case '᭑':
         case '᭒':
         case '᭓':
         case '᭔':
         case '᭕':
         case '᭖':
         case '᭗':
         case '᭘':
         case '᭙':
            this.matchRange('᭐', '᭙');
            break;
         case '᭫':
         case '᭬':
         case '᭭':
         case '᭮':
         case '᭯':
         case '᭰':
         case '᭱':
         case '᭲':
         case '᭳':
            this.matchRange('᭫', '᭳');
            break;
         case 'ᮀ':
         case 'ᮁ':
         case 'ᮂ':
         case 'ᮃ':
         case 'ᮄ':
         case 'ᮅ':
         case 'ᮆ':
         case 'ᮇ':
         case 'ᮈ':
         case 'ᮉ':
         case 'ᮊ':
         case 'ᮋ':
         case 'ᮌ':
         case 'ᮍ':
         case 'ᮎ':
         case 'ᮏ':
         case 'ᮐ':
         case 'ᮑ':
         case 'ᮒ':
         case 'ᮓ':
         case 'ᮔ':
         case 'ᮕ':
         case 'ᮖ':
         case 'ᮗ':
         case 'ᮘ':
         case 'ᮙ':
         case 'ᮚ':
         case 'ᮛ':
         case 'ᮜ':
         case 'ᮝ':
         case 'ᮞ':
         case 'ᮟ':
         case 'ᮠ':
         case 'ᮡ':
         case 'ᮢ':
         case 'ᮣ':
         case 'ᮤ':
         case 'ᮥ':
         case 'ᮦ':
         case 'ᮧ':
         case 'ᮨ':
         case 'ᮩ':
         case '᮪':
            this.matchRange('ᮀ', '᮪');
            break;
         case 'ᮮ':
         case 'ᮯ':
         case '᮰':
         case '᮱':
         case '᮲':
         case '᮳':
         case '᮴':
         case '᮵':
         case '᮶':
         case '᮷':
         case '᮸':
         case '᮹':
            this.matchRange('ᮮ', '᮹');
            break;
         case 'ᯀ':
         case 'ᯁ':
         case 'ᯂ':
         case 'ᯃ':
         case 'ᯄ':
         case 'ᯅ':
         case 'ᯆ':
         case 'ᯇ':
         case 'ᯈ':
         case 'ᯉ':
         case 'ᯊ':
         case 'ᯋ':
         case 'ᯌ':
         case 'ᯍ':
         case 'ᯎ':
         case 'ᯏ':
         case 'ᯐ':
         case 'ᯑ':
         case 'ᯒ':
         case 'ᯓ':
         case 'ᯔ':
         case 'ᯕ':
         case 'ᯖ':
         case 'ᯗ':
         case 'ᯘ':
         case 'ᯙ':
         case 'ᯚ':
         case 'ᯛ':
         case 'ᯜ':
         case 'ᯝ':
         case 'ᯞ':
         case 'ᯟ':
         case 'ᯠ':
         case 'ᯡ':
         case 'ᯢ':
         case 'ᯣ':
         case 'ᯤ':
         case 'ᯥ':
         case '᯦':
         case 'ᯧ':
         case 'ᯨ':
         case 'ᯩ':
         case 'ᯪ':
         case 'ᯫ':
         case 'ᯬ':
         case 'ᯭ':
         case 'ᯮ':
         case 'ᯯ':
         case 'ᯰ':
         case 'ᯱ':
         case '᯲':
         case '᯳':
            this.matchRange('ᯀ', '᯳');
            break;
         case 'ᰀ':
         case 'ᰁ':
         case 'ᰂ':
         case 'ᰃ':
         case 'ᰄ':
         case 'ᰅ':
         case 'ᰆ':
         case 'ᰇ':
         case 'ᰈ':
         case 'ᰉ':
         case 'ᰊ':
         case 'ᰋ':
         case 'ᰌ':
         case 'ᰍ':
         case 'ᰎ':
         case 'ᰏ':
         case 'ᰐ':
         case 'ᰑ':
         case 'ᰒ':
         case 'ᰓ':
         case 'ᰔ':
         case 'ᰕ':
         case 'ᰖ':
         case 'ᰗ':
         case 'ᰘ':
         case 'ᰙ':
         case 'ᰚ':
         case 'ᰛ':
         case 'ᰜ':
         case 'ᰝ':
         case 'ᰞ':
         case 'ᰟ':
         case 'ᰠ':
         case 'ᰡ':
         case 'ᰢ':
         case 'ᰣ':
         case 'ᰤ':
         case 'ᰥ':
         case 'ᰦ':
         case 'ᰧ':
         case 'ᰨ':
         case 'ᰩ':
         case 'ᰪ':
         case 'ᰫ':
         case 'ᰬ':
         case 'ᰭ':
         case 'ᰮ':
         case 'ᰯ':
         case 'ᰰ':
         case 'ᰱ':
         case 'ᰲ':
         case 'ᰳ':
         case 'ᰴ':
         case 'ᰵ':
         case 'ᰶ':
         case '᰷':
            this.matchRange('ᰀ', '᰷');
            break;
         case '᱀':
         case '᱁':
         case '᱂':
         case '᱃':
         case '᱄':
         case '᱅':
         case '᱆':
         case '᱇':
         case '᱈':
         case '᱉':
            this.matchRange('᱀', '᱉');
            break;
         case 'ᱍ':
         case 'ᱎ':
         case 'ᱏ':
         case '᱐':
         case '᱑':
         case '᱒':
         case '᱓':
         case '᱔':
         case '᱕':
         case '᱖':
         case '᱗':
         case '᱘':
         case '᱙':
         case 'ᱚ':
         case 'ᱛ':
         case 'ᱜ':
         case 'ᱝ':
         case 'ᱞ':
         case 'ᱟ':
         case 'ᱠ':
         case 'ᱡ':
         case 'ᱢ':
         case 'ᱣ':
         case 'ᱤ':
         case 'ᱥ':
         case 'ᱦ':
         case 'ᱧ':
         case 'ᱨ':
         case 'ᱩ':
         case 'ᱪ':
         case 'ᱫ':
         case 'ᱬ':
         case 'ᱭ':
         case 'ᱮ':
         case 'ᱯ':
         case 'ᱰ':
         case 'ᱱ':
         case 'ᱲ':
         case 'ᱳ':
         case 'ᱴ':
         case 'ᱵ':
         case 'ᱶ':
         case 'ᱷ':
         case 'ᱸ':
         case 'ᱹ':
         case 'ᱺ':
         case 'ᱻ':
         case 'ᱼ':
         case 'ᱽ':
            this.matchRange('ᱍ', 'ᱽ');
            break;
         case '᳐':
         case '᳑':
         case '᳒':
            this.matchRange('᳐', '᳒');
            break;
         case '᳔':
         case '᳕':
         case '᳖':
         case '᳗':
         case '᳘':
         case '᳙':
         case '᳚':
         case '᳛':
         case '᳜':
         case '᳝':
         case '᳞':
         case '᳟':
         case '᳠':
         case '᳡':
         case '᳢':
         case '᳣':
         case '᳤':
         case '᳥':
         case '᳦':
         case '᳧':
         case '᳨':
         case 'ᳩ':
         case 'ᳪ':
         case 'ᳫ':
         case 'ᳬ':
         case '᳭':
         case 'ᳮ':
         case 'ᳯ':
         case 'ᳰ':
         case 'ᳱ':
         case 'ᳲ':
            this.matchRange('᳔', 'ᳲ');
            break;
         case 'Ἐ':
         case 'Ἑ':
         case 'Ἒ':
         case 'Ἓ':
         case 'Ἔ':
         case 'Ἕ':
            this.matchRange('Ἐ', 'Ἕ');
            break;
         case 'ἠ':
         case 'ἡ':
         case 'ἢ':
         case 'ἣ':
         case 'ἤ':
         case 'ἥ':
         case 'ἦ':
         case 'ἧ':
         case 'Ἠ':
         case 'Ἡ':
         case 'Ἢ':
         case 'Ἣ':
         case 'Ἤ':
         case 'Ἥ':
         case 'Ἦ':
         case 'Ἧ':
         case 'ἰ':
         case 'ἱ':
         case 'ἲ':
         case 'ἳ':
         case 'ἴ':
         case 'ἵ':
         case 'ἶ':
         case 'ἷ':
         case 'Ἰ':
         case 'Ἱ':
         case 'Ἲ':
         case 'Ἳ':
         case 'Ἴ':
         case 'Ἵ':
         case 'Ἶ':
         case 'Ἷ':
         case 'ὀ':
         case 'ὁ':
         case 'ὂ':
         case 'ὃ':
         case 'ὄ':
         case 'ὅ':
            this.matchRange('ἠ', 'ὅ');
            break;
         case 'Ὀ':
         case 'Ὁ':
         case 'Ὂ':
         case 'Ὃ':
         case 'Ὄ':
         case 'Ὅ':
            this.matchRange('Ὀ', 'Ὅ');
            break;
         case 'ὐ':
         case 'ὑ':
         case 'ὒ':
         case 'ὓ':
         case 'ὔ':
         case 'ὕ':
         case 'ὖ':
         case 'ὗ':
            this.matchRange('ὐ', 'ὗ');
            break;
         case 'Ὑ':
            this.matchRange('Ὑ', 'Ὑ');
            break;
         case 'Ὓ':
            this.matchRange('Ὓ', 'Ὓ');
            break;
         case 'Ὕ':
            this.matchRange('Ὕ', 'Ὕ');
            break;
         case 'Ὗ':
         case 'ὠ':
         case 'ὡ':
         case 'ὢ':
         case 'ὣ':
         case 'ὤ':
         case 'ὥ':
         case 'ὦ':
         case 'ὧ':
         case 'Ὠ':
         case 'Ὡ':
         case 'Ὢ':
         case 'Ὣ':
         case 'Ὤ':
         case 'Ὥ':
         case 'Ὦ':
         case 'Ὧ':
         case 'ὰ':
         case 'ά':
         case 'ὲ':
         case 'έ':
         case 'ὴ':
         case 'ή':
         case 'ὶ':
         case 'ί':
         case 'ὸ':
         case 'ό':
         case 'ὺ':
         case 'ύ':
         case 'ὼ':
         case 'ώ':
            this.matchRange('Ὗ', 'ώ');
            break;
         case 'ᾀ':
         case 'ᾁ':
         case 'ᾂ':
         case 'ᾃ':
         case 'ᾄ':
         case 'ᾅ':
         case 'ᾆ':
         case 'ᾇ':
         case 'ᾈ':
         case 'ᾉ':
         case 'ᾊ':
         case 'ᾋ':
         case 'ᾌ':
         case 'ᾍ':
         case 'ᾎ':
         case 'ᾏ':
         case 'ᾐ':
         case 'ᾑ':
         case 'ᾒ':
         case 'ᾓ':
         case 'ᾔ':
         case 'ᾕ':
         case 'ᾖ':
         case 'ᾗ':
         case 'ᾘ':
         case 'ᾙ':
         case 'ᾚ':
         case 'ᾛ':
         case 'ᾜ':
         case 'ᾝ':
         case 'ᾞ':
         case 'ᾟ':
         case 'ᾠ':
         case 'ᾡ':
         case 'ᾢ':
         case 'ᾣ':
         case 'ᾤ':
         case 'ᾥ':
         case 'ᾦ':
         case 'ᾧ':
         case 'ᾨ':
         case 'ᾩ':
         case 'ᾪ':
         case 'ᾫ':
         case 'ᾬ':
         case 'ᾭ':
         case 'ᾮ':
         case 'ᾯ':
         case 'ᾰ':
         case 'ᾱ':
         case 'ᾲ':
         case 'ᾳ':
         case 'ᾴ':
            this.matchRange('ᾀ', 'ᾴ');
            break;
         case 'ᾶ':
         case 'ᾷ':
         case 'Ᾰ':
         case 'Ᾱ':
         case 'Ὰ':
         case 'Ά':
         case 'ᾼ':
            this.matchRange('ᾶ', 'ᾼ');
            break;
         case 'ι':
            this.matchRange('ι', 'ι');
            break;
         case 'ῂ':
         case 'ῃ':
         case 'ῄ':
            this.matchRange('ῂ', 'ῄ');
            break;
         case 'ῆ':
         case 'ῇ':
         case 'Ὲ':
         case 'Έ':
         case 'Ὴ':
         case 'Ή':
         case 'ῌ':
            this.matchRange('ῆ', 'ῌ');
            break;
         case 'ῐ':
         case 'ῑ':
         case 'ῒ':
         case 'ΐ':
            this.matchRange('ῐ', 'ΐ');
            break;
         case 'ῖ':
         case 'ῗ':
         case 'Ῐ':
         case 'Ῑ':
         case 'Ὶ':
         case 'Ί':
            this.matchRange('ῖ', 'Ί');
            break;
         case 'ῠ':
         case 'ῡ':
         case 'ῢ':
         case 'ΰ':
         case 'ῤ':
         case 'ῥ':
         case 'ῦ':
         case 'ῧ':
         case 'Ῠ':
         case 'Ῡ':
         case 'Ὺ':
         case 'Ύ':
         case 'Ῥ':
            this.matchRange('ῠ', 'Ῥ');
            break;
         case 'ῲ':
         case 'ῳ':
         case 'ῴ':
            this.matchRange('ῲ', 'ῴ');
            break;
         case 'ῶ':
         case 'ῷ':
         case 'Ὸ':
         case 'Ό':
         case 'Ὼ':
         case 'Ώ':
         case 'ῼ':
            this.matchRange('ῶ', 'ῼ');
            break;
         case '\u200b':
         case '\u200c':
         case '\u200d':
         case '\u200e':
         case '\u200f':
            this.matchRange('\u200b', '\u200f');
            break;
         case '\u202a':
         case '\u202b':
         case '\u202c':
         case '\u202d':
         case '\u202e':
            this.matchRange('\u202a', '\u202e');
            break;
         case '‿':
         case '⁀':
            this.matchRange('‿', '⁀');
            break;
         case '⁔':
            this.matchRange('⁔', '⁔');
            break;
         case '\u2060':
         case '\u2061':
         case '\u2062':
         case '\u2063':
         case '\u2064':
            this.matchRange('\u2060', '\u2064');
            break;
         case '\u206a':
         case '\u206b':
         case '\u206c':
         case '\u206d':
         case '\u206e':
         case '\u206f':
            this.matchRange('\u206a', '\u206f');
            break;
         case 'ⁱ':
            this.matchRange('ⁱ', 'ⁱ');
            break;
         case 'ⁿ':
            this.matchRange('ⁿ', 'ⁿ');
            break;
         case 'ₐ':
         case 'ₑ':
         case 'ₒ':
         case 'ₓ':
         case 'ₔ':
         case 'ₕ':
         case 'ₖ':
         case 'ₗ':
         case 'ₘ':
         case 'ₙ':
         case 'ₚ':
         case 'ₛ':
         case 'ₜ':
            this.matchRange('ₐ', 'ₜ');
            break;
         case '₠':
         case '₡':
         case '₢':
         case '₣':
         case '₤':
         case '₥':
         case '₦':
         case '₧':
         case '₨':
         case '₩':
         case '₪':
         case '₫':
         case '€':
         case '₭':
         case '₮':
         case '₯':
         case '₰':
         case '₱':
         case '₲':
         case '₳':
         case '₴':
         case '₵':
         case '₶':
         case '₷':
         case '₸':
         case '₹':
            this.matchRange('₠', '₹');
            break;
         case '⃐':
         case '⃑':
         case '⃒':
         case '⃓':
         case '⃔':
         case '⃕':
         case '⃖':
         case '⃗':
         case '⃘':
         case '⃙':
         case '⃚':
         case '⃛':
         case '⃜':
            this.matchRange('⃐', '⃜');
            break;
         case '⃡':
            this.matchRange('⃡', '⃡');
            break;
         case '⃥':
         case '⃦':
         case '⃧':
         case '⃨':
         case '⃩':
         case '⃪':
         case '⃫':
         case '⃬':
         case '⃭':
         case '⃮':
         case '⃯':
         case '⃰':
            this.matchRange('⃥', '⃰');
            break;
         case 'ℂ':
            this.matchRange('ℂ', 'ℂ');
            break;
         case 'ℇ':
            this.matchRange('ℇ', 'ℇ');
            break;
         case 'ℊ':
         case 'ℋ':
         case 'ℌ':
         case 'ℍ':
         case 'ℎ':
         case 'ℏ':
         case 'ℐ':
         case 'ℑ':
         case 'ℒ':
         case 'ℓ':
            this.matchRange('ℊ', 'ℓ');
            break;
         case 'ℕ':
            this.matchRange('ℕ', 'ℕ');
            break;
         case 'ℙ':
         case 'ℚ':
         case 'ℛ':
         case 'ℜ':
         case 'ℝ':
            this.matchRange('ℙ', 'ℝ');
            break;
         case 'ℤ':
            this.matchRange('ℤ', 'ℤ');
            break;
         case 'Ω':
            this.matchRange('Ω', 'Ω');
            break;
         case 'ℨ':
            this.matchRange('ℨ', 'ℨ');
            break;
         case 'K':
         case 'Å':
         case 'ℬ':
         case 'ℭ':
            this.matchRange('K', 'ℭ');
            break;
         case 'ℯ':
         case 'ℰ':
         case 'ℱ':
         case 'Ⅎ':
         case 'ℳ':
         case 'ℴ':
         case 'ℵ':
         case 'ℶ':
         case 'ℷ':
         case 'ℸ':
         case 'ℹ':
            this.matchRange('ℯ', 'ℹ');
            break;
         case 'ℼ':
         case 'ℽ':
         case 'ℾ':
         case 'ℿ':
            this.matchRange('ℼ', 'ℿ');
            break;
         case 'ⅅ':
         case 'ⅆ':
         case 'ⅇ':
         case 'ⅈ':
         case 'ⅉ':
            this.matchRange('ⅅ', 'ⅉ');
            break;
         case 'ⅎ':
            this.matchRange('ⅎ', 'ⅎ');
            break;
         case 'Ⅰ':
         case 'Ⅱ':
         case 'Ⅲ':
         case 'Ⅳ':
         case 'Ⅴ':
         case 'Ⅵ':
         case 'Ⅶ':
         case 'Ⅷ':
         case 'Ⅸ':
         case 'Ⅹ':
         case 'Ⅺ':
         case 'Ⅻ':
         case 'Ⅼ':
         case 'Ⅽ':
         case 'Ⅾ':
         case 'Ⅿ':
         case 'ⅰ':
         case 'ⅱ':
         case 'ⅲ':
         case 'ⅳ':
         case 'ⅴ':
         case 'ⅵ':
         case 'ⅶ':
         case 'ⅷ':
         case 'ⅸ':
         case 'ⅹ':
         case 'ⅺ':
         case 'ⅻ':
         case 'ⅼ':
         case 'ⅽ':
         case 'ⅾ':
         case 'ⅿ':
         case 'ↀ':
         case 'ↁ':
         case 'ↂ':
         case 'Ↄ':
         case 'ↄ':
         case 'ↅ':
         case 'ↆ':
         case 'ↇ':
         case 'ↈ':
            this.matchRange('Ⅰ', 'ↈ');
            break;
         case 'Ⰰ':
         case 'Ⰱ':
         case 'Ⰲ':
         case 'Ⰳ':
         case 'Ⰴ':
         case 'Ⰵ':
         case 'Ⰶ':
         case 'Ⰷ':
         case 'Ⰸ':
         case 'Ⰹ':
         case 'Ⰺ':
         case 'Ⰻ':
         case 'Ⰼ':
         case 'Ⰽ':
         case 'Ⰾ':
         case 'Ⰿ':
         case 'Ⱀ':
         case 'Ⱁ':
         case 'Ⱂ':
         case 'Ⱃ':
         case 'Ⱄ':
         case 'Ⱅ':
         case 'Ⱆ':
         case 'Ⱇ':
         case 'Ⱈ':
         case 'Ⱉ':
         case 'Ⱊ':
         case 'Ⱋ':
         case 'Ⱌ':
         case 'Ⱍ':
         case 'Ⱎ':
         case 'Ⱏ':
         case 'Ⱐ':
         case 'Ⱑ':
         case 'Ⱒ':
         case 'Ⱓ':
         case 'Ⱔ':
         case 'Ⱕ':
         case 'Ⱖ':
         case 'Ⱗ':
         case 'Ⱘ':
         case 'Ⱙ':
         case 'Ⱚ':
         case 'Ⱛ':
         case 'Ⱜ':
         case 'Ⱝ':
         case 'Ⱞ':
            this.matchRange('Ⰰ', 'Ⱞ');
            break;
         case 'ⰰ':
         case 'ⰱ':
         case 'ⰲ':
         case 'ⰳ':
         case 'ⰴ':
         case 'ⰵ':
         case 'ⰶ':
         case 'ⰷ':
         case 'ⰸ':
         case 'ⰹ':
         case 'ⰺ':
         case 'ⰻ':
         case 'ⰼ':
         case 'ⰽ':
         case 'ⰾ':
         case 'ⰿ':
         case 'ⱀ':
         case 'ⱁ':
         case 'ⱂ':
         case 'ⱃ':
         case 'ⱄ':
         case 'ⱅ':
         case 'ⱆ':
         case 'ⱇ':
         case 'ⱈ':
         case 'ⱉ':
         case 'ⱊ':
         case 'ⱋ':
         case 'ⱌ':
         case 'ⱍ':
         case 'ⱎ':
         case 'ⱏ':
         case 'ⱐ':
         case 'ⱑ':
         case 'ⱒ':
         case 'ⱓ':
         case 'ⱔ':
         case 'ⱕ':
         case 'ⱖ':
         case 'ⱗ':
         case 'ⱘ':
         case 'ⱙ':
         case 'ⱚ':
         case 'ⱛ':
         case 'ⱜ':
         case 'ⱝ':
         case 'ⱞ':
            this.matchRange('ⰰ', 'ⱞ');
            break;
         case 'Ⳬ':
         case 'ⳬ':
         case 'Ⳮ':
         case 'ⳮ':
         case '⳯':
         case '⳰':
         case '⳱':
            this.matchRange('Ⳬ', '⳱');
            break;
         case 'ⴀ':
         case 'ⴁ':
         case 'ⴂ':
         case 'ⴃ':
         case 'ⴄ':
         case 'ⴅ':
         case 'ⴆ':
         case 'ⴇ':
         case 'ⴈ':
         case 'ⴉ':
         case 'ⴊ':
         case 'ⴋ':
         case 'ⴌ':
         case 'ⴍ':
         case 'ⴎ':
         case 'ⴏ':
         case 'ⴐ':
         case 'ⴑ':
         case 'ⴒ':
         case 'ⴓ':
         case 'ⴔ':
         case 'ⴕ':
         case 'ⴖ':
         case 'ⴗ':
         case 'ⴘ':
         case 'ⴙ':
         case 'ⴚ':
         case 'ⴛ':
         case 'ⴜ':
         case 'ⴝ':
         case 'ⴞ':
         case 'ⴟ':
         case 'ⴠ':
         case 'ⴡ':
         case 'ⴢ':
         case 'ⴣ':
         case 'ⴤ':
         case 'ⴥ':
            this.matchRange('ⴀ', 'ⴥ');
            break;
         case 'ⴰ':
         case 'ⴱ':
         case 'ⴲ':
         case 'ⴳ':
         case 'ⴴ':
         case 'ⴵ':
         case 'ⴶ':
         case 'ⴷ':
         case 'ⴸ':
         case 'ⴹ':
         case 'ⴺ':
         case 'ⴻ':
         case 'ⴼ':
         case 'ⴽ':
         case 'ⴾ':
         case 'ⴿ':
         case 'ⵀ':
         case 'ⵁ':
         case 'ⵂ':
         case 'ⵃ':
         case 'ⵄ':
         case 'ⵅ':
         case 'ⵆ':
         case 'ⵇ':
         case 'ⵈ':
         case 'ⵉ':
         case 'ⵊ':
         case 'ⵋ':
         case 'ⵌ':
         case 'ⵍ':
         case 'ⵎ':
         case 'ⵏ':
         case 'ⵐ':
         case 'ⵑ':
         case 'ⵒ':
         case 'ⵓ':
         case 'ⵔ':
         case 'ⵕ':
         case 'ⵖ':
         case 'ⵗ':
         case 'ⵘ':
         case 'ⵙ':
         case 'ⵚ':
         case 'ⵛ':
         case 'ⵜ':
         case 'ⵝ':
         case 'ⵞ':
         case 'ⵟ':
         case 'ⵠ':
         case 'ⵡ':
         case 'ⵢ':
         case 'ⵣ':
         case 'ⵤ':
         case 'ⵥ':
            this.matchRange('ⴰ', 'ⵥ');
            break;
         case 'ⵯ':
            this.matchRange('ⵯ', 'ⵯ');
            break;
         case '⵿':
         case 'ⶀ':
         case 'ⶁ':
         case 'ⶂ':
         case 'ⶃ':
         case 'ⶄ':
         case 'ⶅ':
         case 'ⶆ':
         case 'ⶇ':
         case 'ⶈ':
         case 'ⶉ':
         case 'ⶊ':
         case 'ⶋ':
         case 'ⶌ':
         case 'ⶍ':
         case 'ⶎ':
         case 'ⶏ':
         case 'ⶐ':
         case 'ⶑ':
         case 'ⶒ':
         case 'ⶓ':
         case 'ⶔ':
         case 'ⶕ':
         case 'ⶖ':
            this.matchRange('⵿', 'ⶖ');
            break;
         case 'ⶠ':
         case 'ⶡ':
         case 'ⶢ':
         case 'ⶣ':
         case 'ⶤ':
         case 'ⶥ':
         case 'ⶦ':
            this.matchRange('ⶠ', 'ⶦ');
            break;
         case 'ⶨ':
         case 'ⶩ':
         case 'ⶪ':
         case 'ⶫ':
         case 'ⶬ':
         case 'ⶭ':
         case 'ⶮ':
            this.matchRange('ⶨ', 'ⶮ');
            break;
         case 'ⶰ':
         case 'ⶱ':
         case 'ⶲ':
         case 'ⶳ':
         case 'ⶴ':
         case 'ⶵ':
         case 'ⶶ':
            this.matchRange('ⶰ', 'ⶶ');
            break;
         case 'ⶸ':
         case 'ⶹ':
         case 'ⶺ':
         case 'ⶻ':
         case 'ⶼ':
         case 'ⶽ':
         case 'ⶾ':
            this.matchRange('ⶸ', 'ⶾ');
            break;
         case 'ⷀ':
         case 'ⷁ':
         case 'ⷂ':
         case 'ⷃ':
         case 'ⷄ':
         case 'ⷅ':
         case 'ⷆ':
            this.matchRange('ⷀ', 'ⷆ');
            break;
         case 'ⷈ':
         case 'ⷉ':
         case 'ⷊ':
         case 'ⷋ':
         case 'ⷌ':
         case 'ⷍ':
         case 'ⷎ':
            this.matchRange('ⷈ', 'ⷎ');
            break;
         case 'ⷐ':
         case 'ⷑ':
         case 'ⷒ':
         case 'ⷓ':
         case 'ⷔ':
         case 'ⷕ':
         case 'ⷖ':
            this.matchRange('ⷐ', 'ⷖ');
            break;
         case 'ⷘ':
         case 'ⷙ':
         case 'ⷚ':
         case 'ⷛ':
         case 'ⷜ':
         case 'ⷝ':
         case 'ⷞ':
            this.matchRange('ⷘ', 'ⷞ');
            break;
         case 'ⷠ':
         case 'ⷡ':
         case 'ⷢ':
         case 'ⷣ':
         case 'ⷤ':
         case 'ⷥ':
         case 'ⷦ':
         case 'ⷧ':
         case 'ⷨ':
         case 'ⷩ':
         case 'ⷪ':
         case 'ⷫ':
         case 'ⷬ':
         case 'ⷭ':
         case 'ⷮ':
         case 'ⷯ':
         case 'ⷰ':
         case 'ⷱ':
         case 'ⷲ':
         case 'ⷳ':
         case 'ⷴ':
         case 'ⷵ':
         case 'ⷶ':
         case 'ⷷ':
         case 'ⷸ':
         case 'ⷹ':
         case 'ⷺ':
         case 'ⷻ':
         case 'ⷼ':
         case 'ⷽ':
         case 'ⷾ':
         case 'ⷿ':
            this.matchRange('ⷠ', 'ⷿ');
            break;
         case 'ⸯ':
            this.matchRange('ⸯ', 'ⸯ');
            break;
         case '々':
         case '〆':
         case '〇':
            this.matchRange('々', '〇');
            break;
         case '〡':
         case '〢':
         case '〣':
         case '〤':
         case '〥':
         case '〦':
         case '〧':
         case '〨':
         case '〩':
         case '〪':
         case '〫':
         case '〬':
         case '〭':
         case '〮':
         case '〯':
            this.matchRange('〡', '〯');
            break;
         case '〱':
         case '〲':
         case '〳':
         case '〴':
         case '〵':
            this.matchRange('〱', '〵');
            break;
         case '〸':
         case '〹':
         case '〺':
         case '〻':
         case '〼':
            this.matchRange('〸', '〼');
            break;
         case 'ぁ':
         case 'あ':
         case 'ぃ':
         case 'い':
         case 'ぅ':
         case 'う':
         case 'ぇ':
         case 'え':
         case 'ぉ':
         case 'お':
         case 'か':
         case 'が':
         case 'き':
         case 'ぎ':
         case 'く':
         case 'ぐ':
         case 'け':
         case 'げ':
         case 'こ':
         case 'ご':
         case 'さ':
         case 'ざ':
         case 'し':
         case 'じ':
         case 'す':
         case 'ず':
         case 'せ':
         case 'ぜ':
         case 'そ':
         case 'ぞ':
         case 'た':
         case 'だ':
         case 'ち':
         case 'ぢ':
         case 'っ':
         case 'つ':
         case 'づ':
         case 'て':
         case 'で':
         case 'と':
         case 'ど':
         case 'な':
         case 'に':
         case 'ぬ':
         case 'ね':
         case 'の':
         case 'は':
         case 'ば':
         case 'ぱ':
         case 'ひ':
         case 'び':
         case 'ぴ':
         case 'ふ':
         case 'ぶ':
         case 'ぷ':
         case 'へ':
         case 'べ':
         case 'ぺ':
         case 'ほ':
         case 'ぼ':
         case 'ぽ':
         case 'ま':
         case 'み':
         case 'む':
         case 'め':
         case 'も':
         case 'ゃ':
         case 'や':
         case 'ゅ':
         case 'ゆ':
         case 'ょ':
         case 'よ':
         case 'ら':
         case 'り':
         case 'る':
         case 'れ':
         case 'ろ':
         case 'ゎ':
         case 'わ':
         case 'ゐ':
         case 'ゑ':
         case 'を':
         case 'ん':
         case 'ゔ':
         case 'ゕ':
         case 'ゖ':
            this.matchRange('ぁ', 'ゖ');
            break;
         case '゙':
         case '゚':
            this.matchRange('゙', '゚');
            break;
         case 'ゝ':
         case 'ゞ':
         case 'ゟ':
            this.matchRange('ゝ', 'ゟ');
            break;
         case 'ァ':
         case 'ア':
         case 'ィ':
         case 'イ':
         case 'ゥ':
         case 'ウ':
         case 'ェ':
         case 'エ':
         case 'ォ':
         case 'オ':
         case 'カ':
         case 'ガ':
         case 'キ':
         case 'ギ':
         case 'ク':
         case 'グ':
         case 'ケ':
         case 'ゲ':
         case 'コ':
         case 'ゴ':
         case 'サ':
         case 'ザ':
         case 'シ':
         case 'ジ':
         case 'ス':
         case 'ズ':
         case 'セ':
         case 'ゼ':
         case 'ソ':
         case 'ゾ':
         case 'タ':
         case 'ダ':
         case 'チ':
         case 'ヂ':
         case 'ッ':
         case 'ツ':
         case 'ヅ':
         case 'テ':
         case 'デ':
         case 'ト':
         case 'ド':
         case 'ナ':
         case 'ニ':
         case 'ヌ':
         case 'ネ':
         case 'ノ':
         case 'ハ':
         case 'バ':
         case 'パ':
         case 'ヒ':
         case 'ビ':
         case 'ピ':
         case 'フ':
         case 'ブ':
         case 'プ':
         case 'ヘ':
         case 'ベ':
         case 'ペ':
         case 'ホ':
         case 'ボ':
         case 'ポ':
         case 'マ':
         case 'ミ':
         case 'ム':
         case 'メ':
         case 'モ':
         case 'ャ':
         case 'ヤ':
         case 'ュ':
         case 'ユ':
         case 'ョ':
         case 'ヨ':
         case 'ラ':
         case 'リ':
         case 'ル':
         case 'レ':
         case 'ロ':
         case 'ヮ':
         case 'ワ':
         case 'ヰ':
         case 'ヱ':
         case 'ヲ':
         case 'ン':
         case 'ヴ':
         case 'ヵ':
         case 'ヶ':
         case 'ヷ':
         case 'ヸ':
         case 'ヹ':
         case 'ヺ':
            this.matchRange('ァ', 'ヺ');
            break;
         case 'ー':
         case 'ヽ':
         case 'ヾ':
         case 'ヿ':
            this.matchRange('ー', 'ヿ');
            break;
         case 'ㄅ':
         case 'ㄆ':
         case 'ㄇ':
         case 'ㄈ':
         case 'ㄉ':
         case 'ㄊ':
         case 'ㄋ':
         case 'ㄌ':
         case 'ㄍ':
         case 'ㄎ':
         case 'ㄏ':
         case 'ㄐ':
         case 'ㄑ':
         case 'ㄒ':
         case 'ㄓ':
         case 'ㄔ':
         case 'ㄕ':
         case 'ㄖ':
         case 'ㄗ':
         case 'ㄘ':
         case 'ㄙ':
         case 'ㄚ':
         case 'ㄛ':
         case 'ㄜ':
         case 'ㄝ':
         case 'ㄞ':
         case 'ㄟ':
         case 'ㄠ':
         case 'ㄡ':
         case 'ㄢ':
         case 'ㄣ':
         case 'ㄤ':
         case 'ㄥ':
         case 'ㄦ':
         case 'ㄧ':
         case 'ㄨ':
         case 'ㄩ':
         case 'ㄪ':
         case 'ㄫ':
         case 'ㄬ':
         case 'ㄭ':
            this.matchRange('ㄅ', 'ㄭ');
            break;
         case 'ㄱ':
         case 'ㄲ':
         case 'ㄳ':
         case 'ㄴ':
         case 'ㄵ':
         case 'ㄶ':
         case 'ㄷ':
         case 'ㄸ':
         case 'ㄹ':
         case 'ㄺ':
         case 'ㄻ':
         case 'ㄼ':
         case 'ㄽ':
         case 'ㄾ':
         case 'ㄿ':
         case 'ㅀ':
         case 'ㅁ':
         case 'ㅂ':
         case 'ㅃ':
         case 'ㅄ':
         case 'ㅅ':
         case 'ㅆ':
         case 'ㅇ':
         case 'ㅈ':
         case 'ㅉ':
         case 'ㅊ':
         case 'ㅋ':
         case 'ㅌ':
         case 'ㅍ':
         case 'ㅎ':
         case 'ㅏ':
         case 'ㅐ':
         case 'ㅑ':
         case 'ㅒ':
         case 'ㅓ':
         case 'ㅔ':
         case 'ㅕ':
         case 'ㅖ':
         case 'ㅗ':
         case 'ㅘ':
         case 'ㅙ':
         case 'ㅚ':
         case 'ㅛ':
         case 'ㅜ':
         case 'ㅝ':
         case 'ㅞ':
         case 'ㅟ':
         case 'ㅠ':
         case 'ㅡ':
         case 'ㅢ':
         case 'ㅣ':
         case 'ㅤ':
         case 'ㅥ':
         case 'ㅦ':
         case 'ㅧ':
         case 'ㅨ':
         case 'ㅩ':
         case 'ㅪ':
         case 'ㅫ':
         case 'ㅬ':
         case 'ㅭ':
         case 'ㅮ':
         case 'ㅯ':
         case 'ㅰ':
         case 'ㅱ':
         case 'ㅲ':
         case 'ㅳ':
         case 'ㅴ':
         case 'ㅵ':
         case 'ㅶ':
         case 'ㅷ':
         case 'ㅸ':
         case 'ㅹ':
         case 'ㅺ':
         case 'ㅻ':
         case 'ㅼ':
         case 'ㅽ':
         case 'ㅾ':
         case 'ㅿ':
         case 'ㆀ':
         case 'ㆁ':
         case 'ㆂ':
         case 'ㆃ':
         case 'ㆄ':
         case 'ㆅ':
         case 'ㆆ':
         case 'ㆇ':
         case 'ㆈ':
         case 'ㆉ':
         case 'ㆊ':
         case 'ㆋ':
         case 'ㆌ':
         case 'ㆍ':
         case 'ㆎ':
            this.matchRange('ㄱ', 'ㆎ');
            break;
         case 'ㆠ':
         case 'ㆡ':
         case 'ㆢ':
         case 'ㆣ':
         case 'ㆤ':
         case 'ㆥ':
         case 'ㆦ':
         case 'ㆧ':
         case 'ㆨ':
         case 'ㆩ':
         case 'ㆪ':
         case 'ㆫ':
         case 'ㆬ':
         case 'ㆭ':
         case 'ㆮ':
         case 'ㆯ':
         case 'ㆰ':
         case 'ㆱ':
         case 'ㆲ':
         case 'ㆳ':
         case 'ㆴ':
         case 'ㆵ':
         case 'ㆶ':
         case 'ㆷ':
         case 'ㆸ':
         case 'ㆹ':
         case 'ㆺ':
            this.matchRange('ㆠ', 'ㆺ');
            break;
         case 'ㇰ':
         case 'ㇱ':
         case 'ㇲ':
         case 'ㇳ':
         case 'ㇴ':
         case 'ㇵ':
         case 'ㇶ':
         case 'ㇷ':
         case 'ㇸ':
         case 'ㇹ':
         case 'ㇺ':
         case 'ㇻ':
         case 'ㇼ':
         case 'ㇽ':
         case 'ㇾ':
         case 'ㇿ':
            this.matchRange('ㇰ', 'ㇿ');
            break;
         case 'ꓐ':
         case 'ꓑ':
         case 'ꓒ':
         case 'ꓓ':
         case 'ꓔ':
         case 'ꓕ':
         case 'ꓖ':
         case 'ꓗ':
         case 'ꓘ':
         case 'ꓙ':
         case 'ꓚ':
         case 'ꓛ':
         case 'ꓜ':
         case 'ꓝ':
         case 'ꓞ':
         case 'ꓟ':
         case 'ꓠ':
         case 'ꓡ':
         case 'ꓢ':
         case 'ꓣ':
         case 'ꓤ':
         case 'ꓥ':
         case 'ꓦ':
         case 'ꓧ':
         case 'ꓨ':
         case 'ꓩ':
         case 'ꓪ':
         case 'ꓫ':
         case 'ꓬ':
         case 'ꓭ':
         case 'ꓮ':
         case 'ꓯ':
         case 'ꓰ':
         case 'ꓱ':
         case 'ꓲ':
         case 'ꓳ':
         case 'ꓴ':
         case 'ꓵ':
         case 'ꓶ':
         case 'ꓷ':
         case 'ꓸ':
         case 'ꓹ':
         case 'ꓺ':
         case 'ꓻ':
         case 'ꓼ':
         case 'ꓽ':
            this.matchRange('ꓐ', 'ꓽ');
            break;
         case 'ꘐ':
         case 'ꘑ':
         case 'ꘒ':
         case 'ꘓ':
         case 'ꘔ':
         case 'ꘕ':
         case 'ꘖ':
         case 'ꘗ':
         case 'ꘘ':
         case 'ꘙ':
         case 'ꘚ':
         case 'ꘛ':
         case 'ꘜ':
         case 'ꘝ':
         case 'ꘞ':
         case 'ꘟ':
         case '꘠':
         case '꘡':
         case '꘢':
         case '꘣':
         case '꘤':
         case '꘥':
         case '꘦':
         case '꘧':
         case '꘨':
         case '꘩':
         case 'ꘪ':
         case 'ꘫ':
            this.matchRange('ꘐ', 'ꘫ');
            break;
         case 'Ꙁ':
         case 'ꙁ':
         case 'Ꙃ':
         case 'ꙃ':
         case 'Ꙅ':
         case 'ꙅ':
         case 'Ꙇ':
         case 'ꙇ':
         case 'Ꙉ':
         case 'ꙉ':
         case 'Ꙋ':
         case 'ꙋ':
         case 'Ꙍ':
         case 'ꙍ':
         case 'Ꙏ':
         case 'ꙏ':
         case 'Ꙑ':
         case 'ꙑ':
         case 'Ꙓ':
         case 'ꙓ':
         case 'Ꙕ':
         case 'ꙕ':
         case 'Ꙗ':
         case 'ꙗ':
         case 'Ꙙ':
         case 'ꙙ':
         case 'Ꙛ':
         case 'ꙛ':
         case 'Ꙝ':
         case 'ꙝ':
         case 'Ꙟ':
         case 'ꙟ':
         case 'Ꙡ':
         case 'ꙡ':
         case 'Ꙣ':
         case 'ꙣ':
         case 'Ꙥ':
         case 'ꙥ':
         case 'Ꙧ':
         case 'ꙧ':
         case 'Ꙩ':
         case 'ꙩ':
         case 'Ꙫ':
         case 'ꙫ':
         case 'Ꙭ':
         case 'ꙭ':
         case 'ꙮ':
         case '꙯':
            this.matchRange('Ꙁ', '꙯');
            break;
         case '꙼':
         case '꙽':
            this.matchRange('꙼', '꙽');
            break;
         case 'ꙿ':
         case 'Ꚁ':
         case 'ꚁ':
         case 'Ꚃ':
         case 'ꚃ':
         case 'Ꚅ':
         case 'ꚅ':
         case 'Ꚇ':
         case 'ꚇ':
         case 'Ꚉ':
         case 'ꚉ':
         case 'Ꚋ':
         case 'ꚋ':
         case 'Ꚍ':
         case 'ꚍ':
         case 'Ꚏ':
         case 'ꚏ':
         case 'Ꚑ':
         case 'ꚑ':
         case 'Ꚓ':
         case 'ꚓ':
         case 'Ꚕ':
         case 'ꚕ':
         case 'Ꚗ':
         case 'ꚗ':
            this.matchRange('ꙿ', 'ꚗ');
            break;
         case 'ꚠ':
         case 'ꚡ':
         case 'ꚢ':
         case 'ꚣ':
         case 'ꚤ':
         case 'ꚥ':
         case 'ꚦ':
         case 'ꚧ':
         case 'ꚨ':
         case 'ꚩ':
         case 'ꚪ':
         case 'ꚫ':
         case 'ꚬ':
         case 'ꚭ':
         case 'ꚮ':
         case 'ꚯ':
         case 'ꚰ':
         case 'ꚱ':
         case 'ꚲ':
         case 'ꚳ':
         case 'ꚴ':
         case 'ꚵ':
         case 'ꚶ':
         case 'ꚷ':
         case 'ꚸ':
         case 'ꚹ':
         case 'ꚺ':
         case 'ꚻ':
         case 'ꚼ':
         case 'ꚽ':
         case 'ꚾ':
         case 'ꚿ':
         case 'ꛀ':
         case 'ꛁ':
         case 'ꛂ':
         case 'ꛃ':
         case 'ꛄ':
         case 'ꛅ':
         case 'ꛆ':
         case 'ꛇ':
         case 'ꛈ':
         case 'ꛉ':
         case 'ꛊ':
         case 'ꛋ':
         case 'ꛌ':
         case 'ꛍ':
         case 'ꛎ':
         case 'ꛏ':
         case 'ꛐ':
         case 'ꛑ':
         case 'ꛒ':
         case 'ꛓ':
         case 'ꛔ':
         case 'ꛕ':
         case 'ꛖ':
         case 'ꛗ':
         case 'ꛘ':
         case 'ꛙ':
         case 'ꛚ':
         case 'ꛛ':
         case 'ꛜ':
         case 'ꛝ':
         case 'ꛞ':
         case 'ꛟ':
         case 'ꛠ':
         case 'ꛡ':
         case 'ꛢ':
         case 'ꛣ':
         case 'ꛤ':
         case 'ꛥ':
         case 'ꛦ':
         case 'ꛧ':
         case 'ꛨ':
         case 'ꛩ':
         case 'ꛪ':
         case 'ꛫ':
         case 'ꛬ':
         case 'ꛭ':
         case 'ꛮ':
         case 'ꛯ':
         case '꛰':
         case '꛱':
            this.matchRange('ꚠ', '꛱');
            break;
         case 'ꜗ':
         case 'ꜘ':
         case 'ꜙ':
         case 'ꜚ':
         case 'ꜛ':
         case 'ꜜ':
         case 'ꜝ':
         case 'ꜞ':
         case 'ꜟ':
            this.matchRange('ꜗ', 'ꜟ');
            break;
         case 'Ꜣ':
         case 'ꜣ':
         case 'Ꜥ':
         case 'ꜥ':
         case 'Ꜧ':
         case 'ꜧ':
         case 'Ꜩ':
         case 'ꜩ':
         case 'Ꜫ':
         case 'ꜫ':
         case 'Ꜭ':
         case 'ꜭ':
         case 'Ꜯ':
         case 'ꜯ':
         case 'ꜰ':
         case 'ꜱ':
         case 'Ꜳ':
         case 'ꜳ':
         case 'Ꜵ':
         case 'ꜵ':
         case 'Ꜷ':
         case 'ꜷ':
         case 'Ꜹ':
         case 'ꜹ':
         case 'Ꜻ':
         case 'ꜻ':
         case 'Ꜽ':
         case 'ꜽ':
         case 'Ꜿ':
         case 'ꜿ':
         case 'Ꝁ':
         case 'ꝁ':
         case 'Ꝃ':
         case 'ꝃ':
         case 'Ꝅ':
         case 'ꝅ':
         case 'Ꝇ':
         case 'ꝇ':
         case 'Ꝉ':
         case 'ꝉ':
         case 'Ꝋ':
         case 'ꝋ':
         case 'Ꝍ':
         case 'ꝍ':
         case 'Ꝏ':
         case 'ꝏ':
         case 'Ꝑ':
         case 'ꝑ':
         case 'Ꝓ':
         case 'ꝓ':
         case 'Ꝕ':
         case 'ꝕ':
         case 'Ꝗ':
         case 'ꝗ':
         case 'Ꝙ':
         case 'ꝙ':
         case 'Ꝛ':
         case 'ꝛ':
         case 'Ꝝ':
         case 'ꝝ':
         case 'Ꝟ':
         case 'ꝟ':
         case 'Ꝡ':
         case 'ꝡ':
         case 'Ꝣ':
         case 'ꝣ':
         case 'Ꝥ':
         case 'ꝥ':
         case 'Ꝧ':
         case 'ꝧ':
         case 'Ꝩ':
         case 'ꝩ':
         case 'Ꝫ':
         case 'ꝫ':
         case 'Ꝭ':
         case 'ꝭ':
         case 'Ꝯ':
         case 'ꝯ':
         case 'ꝰ':
         case 'ꝱ':
         case 'ꝲ':
         case 'ꝳ':
         case 'ꝴ':
         case 'ꝵ':
         case 'ꝶ':
         case 'ꝷ':
         case 'ꝸ':
         case 'Ꝺ':
         case 'ꝺ':
         case 'Ꝼ':
         case 'ꝼ':
         case 'Ᵹ':
         case 'Ꝿ':
         case 'ꝿ':
         case 'Ꞁ':
         case 'ꞁ':
         case 'Ꞃ':
         case 'ꞃ':
         case 'Ꞅ':
         case 'ꞅ':
         case 'Ꞇ':
         case 'ꞇ':
         case 'ꞈ':
            this.matchRange('Ꜣ', 'ꞈ');
            break;
         case 'Ꞌ':
         case 'ꞌ':
         case 'Ɥ':
         case 'ꞎ':
            this.matchRange('Ꞌ', 'ꞎ');
            break;
         case 'Ꞑ':
         case 'ꞑ':
            this.matchRange('Ꞑ', 'ꞑ');
            break;
         case 'Ꞡ':
         case 'ꞡ':
         case 'Ꞣ':
         case 'ꞣ':
         case 'Ꞥ':
         case 'ꞥ':
         case 'Ꞧ':
         case 'ꞧ':
         case 'Ꞩ':
         case 'ꞩ':
            this.matchRange('Ꞡ', 'ꞩ');
            break;
         case 'ꟺ':
         case 'ꟻ':
         case 'ꟼ':
         case 'ꟽ':
         case 'ꟾ':
         case 'ꟿ':
         case 'ꠀ':
         case 'ꠁ':
         case 'ꠂ':
         case 'ꠃ':
         case 'ꠄ':
         case 'ꠅ':
         case '꠆':
         case 'ꠇ':
         case 'ꠈ':
         case 'ꠉ':
         case 'ꠊ':
         case 'ꠋ':
         case 'ꠌ':
         case 'ꠍ':
         case 'ꠎ':
         case 'ꠏ':
         case 'ꠐ':
         case 'ꠑ':
         case 'ꠒ':
         case 'ꠓ':
         case 'ꠔ':
         case 'ꠕ':
         case 'ꠖ':
         case 'ꠗ':
         case 'ꠘ':
         case 'ꠙ':
         case 'ꠚ':
         case 'ꠛ':
         case 'ꠜ':
         case 'ꠝ':
         case 'ꠞ':
         case 'ꠟ':
         case 'ꠠ':
         case 'ꠡ':
         case 'ꠢ':
         case 'ꠣ':
         case 'ꠤ':
         case 'ꠥ':
         case 'ꠦ':
         case 'ꠧ':
            this.matchRange('ꟺ', 'ꠧ');
            break;
         case '꠸':
            this.matchRange('꠸', '꠸');
            break;
         case 'ꡀ':
         case 'ꡁ':
         case 'ꡂ':
         case 'ꡃ':
         case 'ꡄ':
         case 'ꡅ':
         case 'ꡆ':
         case 'ꡇ':
         case 'ꡈ':
         case 'ꡉ':
         case 'ꡊ':
         case 'ꡋ':
         case 'ꡌ':
         case 'ꡍ':
         case 'ꡎ':
         case 'ꡏ':
         case 'ꡐ':
         case 'ꡑ':
         case 'ꡒ':
         case 'ꡓ':
         case 'ꡔ':
         case 'ꡕ':
         case 'ꡖ':
         case 'ꡗ':
         case 'ꡘ':
         case 'ꡙ':
         case 'ꡚ':
         case 'ꡛ':
         case 'ꡜ':
         case 'ꡝ':
         case 'ꡞ':
         case 'ꡟ':
         case 'ꡠ':
         case 'ꡡ':
         case 'ꡢ':
         case 'ꡣ':
         case 'ꡤ':
         case 'ꡥ':
         case 'ꡦ':
         case 'ꡧ':
         case 'ꡨ':
         case 'ꡩ':
         case 'ꡪ':
         case 'ꡫ':
         case 'ꡬ':
         case 'ꡭ':
         case 'ꡮ':
         case 'ꡯ':
         case 'ꡰ':
         case 'ꡱ':
         case 'ꡲ':
         case 'ꡳ':
            this.matchRange('ꡀ', 'ꡳ');
            break;
         case 'ꢀ':
         case 'ꢁ':
         case 'ꢂ':
         case 'ꢃ':
         case 'ꢄ':
         case 'ꢅ':
         case 'ꢆ':
         case 'ꢇ':
         case 'ꢈ':
         case 'ꢉ':
         case 'ꢊ':
         case 'ꢋ':
         case 'ꢌ':
         case 'ꢍ':
         case 'ꢎ':
         case 'ꢏ':
         case 'ꢐ':
         case 'ꢑ':
         case 'ꢒ':
         case 'ꢓ':
         case 'ꢔ':
         case 'ꢕ':
         case 'ꢖ':
         case 'ꢗ':
         case 'ꢘ':
         case 'ꢙ':
         case 'ꢚ':
         case 'ꢛ':
         case 'ꢜ':
         case 'ꢝ':
         case 'ꢞ':
         case 'ꢟ':
         case 'ꢠ':
         case 'ꢡ':
         case 'ꢢ':
         case 'ꢣ':
         case 'ꢤ':
         case 'ꢥ':
         case 'ꢦ':
         case 'ꢧ':
         case 'ꢨ':
         case 'ꢩ':
         case 'ꢪ':
         case 'ꢫ':
         case 'ꢬ':
         case 'ꢭ':
         case 'ꢮ':
         case 'ꢯ':
         case 'ꢰ':
         case 'ꢱ':
         case 'ꢲ':
         case 'ꢳ':
         case 'ꢴ':
         case 'ꢵ':
         case 'ꢶ':
         case 'ꢷ':
         case 'ꢸ':
         case 'ꢹ':
         case 'ꢺ':
         case 'ꢻ':
         case 'ꢼ':
         case 'ꢽ':
         case 'ꢾ':
         case 'ꢿ':
         case 'ꣀ':
         case 'ꣁ':
         case 'ꣂ':
         case 'ꣃ':
         case '꣄':
            this.matchRange('ꢀ', '꣄');
            break;
         case '꣐':
         case '꣑':
         case '꣒':
         case '꣓':
         case '꣔':
         case '꣕':
         case '꣖':
         case '꣗':
         case '꣘':
         case '꣙':
            this.matchRange('꣐', '꣙');
            break;
         case '꣠':
         case '꣡':
         case '꣢':
         case '꣣':
         case '꣤':
         case '꣥':
         case '꣦':
         case '꣧':
         case '꣨':
         case '꣩':
         case '꣪':
         case '꣫':
         case '꣬':
         case '꣭':
         case '꣮':
         case '꣯':
         case '꣰':
         case '꣱':
         case 'ꣲ':
         case 'ꣳ':
         case 'ꣴ':
         case 'ꣵ':
         case 'ꣶ':
         case 'ꣷ':
            this.matchRange('꣠', 'ꣷ');
            break;
         case 'ꣻ':
            this.matchRange('ꣻ', 'ꣻ');
            break;
         case '꤀':
         case '꤁':
         case '꤂':
         case '꤃':
         case '꤄':
         case '꤅':
         case '꤆':
         case '꤇':
         case '꤈':
         case '꤉':
         case 'ꤊ':
         case 'ꤋ':
         case 'ꤌ':
         case 'ꤍ':
         case 'ꤎ':
         case 'ꤏ':
         case 'ꤐ':
         case 'ꤑ':
         case 'ꤒ':
         case 'ꤓ':
         case 'ꤔ':
         case 'ꤕ':
         case 'ꤖ':
         case 'ꤗ':
         case 'ꤘ':
         case 'ꤙ':
         case 'ꤚ':
         case 'ꤛ':
         case 'ꤜ':
         case 'ꤝ':
         case 'ꤞ':
         case 'ꤟ':
         case 'ꤠ':
         case 'ꤡ':
         case 'ꤢ':
         case 'ꤣ':
         case 'ꤤ':
         case 'ꤥ':
         case 'ꤦ':
         case 'ꤧ':
         case 'ꤨ':
         case 'ꤩ':
         case 'ꤪ':
         case '꤫':
         case '꤬':
         case '꤭':
            this.matchRange('꤀', '꤭');
            break;
         case 'ꤰ':
         case 'ꤱ':
         case 'ꤲ':
         case 'ꤳ':
         case 'ꤴ':
         case 'ꤵ':
         case 'ꤶ':
         case 'ꤷ':
         case 'ꤸ':
         case 'ꤹ':
         case 'ꤺ':
         case 'ꤻ':
         case 'ꤼ':
         case 'ꤽ':
         case 'ꤾ':
         case 'ꤿ':
         case 'ꥀ':
         case 'ꥁ':
         case 'ꥂ':
         case 'ꥃ':
         case 'ꥄ':
         case 'ꥅ':
         case 'ꥆ':
         case 'ꥇ':
         case 'ꥈ':
         case 'ꥉ':
         case 'ꥊ':
         case 'ꥋ':
         case 'ꥌ':
         case 'ꥍ':
         case 'ꥎ':
         case 'ꥏ':
         case 'ꥐ':
         case 'ꥑ':
         case 'ꥒ':
         case '꥓':
            this.matchRange('ꤰ', '꥓');
            break;
         case 'ꥠ':
         case 'ꥡ':
         case 'ꥢ':
         case 'ꥣ':
         case 'ꥤ':
         case 'ꥥ':
         case 'ꥦ':
         case 'ꥧ':
         case 'ꥨ':
         case 'ꥩ':
         case 'ꥪ':
         case 'ꥫ':
         case 'ꥬ':
         case 'ꥭ':
         case 'ꥮ':
         case 'ꥯ':
         case 'ꥰ':
         case 'ꥱ':
         case 'ꥲ':
         case 'ꥳ':
         case 'ꥴ':
         case 'ꥵ':
         case 'ꥶ':
         case 'ꥷ':
         case 'ꥸ':
         case 'ꥹ':
         case 'ꥺ':
         case 'ꥻ':
         case 'ꥼ':
            this.matchRange('ꥠ', 'ꥼ');
            break;
         case 'ꦀ':
         case 'ꦁ':
         case 'ꦂ':
         case 'ꦃ':
         case 'ꦄ':
         case 'ꦅ':
         case 'ꦆ':
         case 'ꦇ':
         case 'ꦈ':
         case 'ꦉ':
         case 'ꦊ':
         case 'ꦋ':
         case 'ꦌ':
         case 'ꦍ':
         case 'ꦎ':
         case 'ꦏ':
         case 'ꦐ':
         case 'ꦑ':
         case 'ꦒ':
         case 'ꦓ':
         case 'ꦔ':
         case 'ꦕ':
         case 'ꦖ':
         case 'ꦗ':
         case 'ꦘ':
         case 'ꦙ':
         case 'ꦚ':
         case 'ꦛ':
         case 'ꦜ':
         case 'ꦝ':
         case 'ꦞ':
         case 'ꦟ':
         case 'ꦠ':
         case 'ꦡ':
         case 'ꦢ':
         case 'ꦣ':
         case 'ꦤ':
         case 'ꦥ':
         case 'ꦦ':
         case 'ꦧ':
         case 'ꦨ':
         case 'ꦩ':
         case 'ꦪ':
         case 'ꦫ':
         case 'ꦬ':
         case 'ꦭ':
         case 'ꦮ':
         case 'ꦯ':
         case 'ꦰ':
         case 'ꦱ':
         case 'ꦲ':
         case '꦳':
         case 'ꦴ':
         case 'ꦵ':
         case 'ꦶ':
         case 'ꦷ':
         case 'ꦸ':
         case 'ꦹ':
         case 'ꦺ':
         case 'ꦻ':
         case 'ꦼ':
         case 'ꦽ':
         case 'ꦾ':
         case 'ꦿ':
         case '꧀':
            this.matchRange('ꦀ', '꧀');
            break;
         case 'ꧏ':
         case '꧐':
         case '꧑':
         case '꧒':
         case '꧓':
         case '꧔':
         case '꧕':
         case '꧖':
         case '꧗':
         case '꧘':
         case '꧙':
            this.matchRange('ꧏ', '꧙');
            break;
         case 'ꨀ':
         case 'ꨁ':
         case 'ꨂ':
         case 'ꨃ':
         case 'ꨄ':
         case 'ꨅ':
         case 'ꨆ':
         case 'ꨇ':
         case 'ꨈ':
         case 'ꨉ':
         case 'ꨊ':
         case 'ꨋ':
         case 'ꨌ':
         case 'ꨍ':
         case 'ꨎ':
         case 'ꨏ':
         case 'ꨐ':
         case 'ꨑ':
         case 'ꨒ':
         case 'ꨓ':
         case 'ꨔ':
         case 'ꨕ':
         case 'ꨖ':
         case 'ꨗ':
         case 'ꨘ':
         case 'ꨙ':
         case 'ꨚ':
         case 'ꨛ':
         case 'ꨜ':
         case 'ꨝ':
         case 'ꨞ':
         case 'ꨟ':
         case 'ꨠ':
         case 'ꨡ':
         case 'ꨢ':
         case 'ꨣ':
         case 'ꨤ':
         case 'ꨥ':
         case 'ꨦ':
         case 'ꨧ':
         case 'ꨨ':
         case 'ꨩ':
         case 'ꨪ':
         case 'ꨫ':
         case 'ꨬ':
         case 'ꨭ':
         case 'ꨮ':
         case 'ꨯ':
         case 'ꨰ':
         case 'ꨱ':
         case 'ꨲ':
         case 'ꨳ':
         case 'ꨴ':
         case 'ꨵ':
         case 'ꨶ':
            this.matchRange('ꨀ', 'ꨶ');
            break;
         case 'ꩀ':
         case 'ꩁ':
         case 'ꩂ':
         case 'ꩃ':
         case 'ꩄ':
         case 'ꩅ':
         case 'ꩆ':
         case 'ꩇ':
         case 'ꩈ':
         case 'ꩉ':
         case 'ꩊ':
         case 'ꩋ':
         case 'ꩌ':
         case 'ꩍ':
            this.matchRange('ꩀ', 'ꩍ');
            break;
         case '꩐':
         case '꩑':
         case '꩒':
         case '꩓':
         case '꩔':
         case '꩕':
         case '꩖':
         case '꩗':
         case '꩘':
         case '꩙':
            this.matchRange('꩐', '꩙');
            break;
         case 'ꩠ':
         case 'ꩡ':
         case 'ꩢ':
         case 'ꩣ':
         case 'ꩤ':
         case 'ꩥ':
         case 'ꩦ':
         case 'ꩧ':
         case 'ꩨ':
         case 'ꩩ':
         case 'ꩪ':
         case 'ꩫ':
         case 'ꩬ':
         case 'ꩭ':
         case 'ꩮ':
         case 'ꩯ':
         case 'ꩰ':
         case 'ꩱ':
         case 'ꩲ':
         case 'ꩳ':
         case 'ꩴ':
         case 'ꩵ':
         case 'ꩶ':
            this.matchRange('ꩠ', 'ꩶ');
            break;
         case 'ꩺ':
         case 'ꩻ':
            this.matchRange('ꩺ', 'ꩻ');
            break;
         case 'ꪀ':
         case 'ꪁ':
         case 'ꪂ':
         case 'ꪃ':
         case 'ꪄ':
         case 'ꪅ':
         case 'ꪆ':
         case 'ꪇ':
         case 'ꪈ':
         case 'ꪉ':
         case 'ꪊ':
         case 'ꪋ':
         case 'ꪌ':
         case 'ꪍ':
         case 'ꪎ':
         case 'ꪏ':
         case 'ꪐ':
         case 'ꪑ':
         case 'ꪒ':
         case 'ꪓ':
         case 'ꪔ':
         case 'ꪕ':
         case 'ꪖ':
         case 'ꪗ':
         case 'ꪘ':
         case 'ꪙ':
         case 'ꪚ':
         case 'ꪛ':
         case 'ꪜ':
         case 'ꪝ':
         case 'ꪞ':
         case 'ꪟ':
         case 'ꪠ':
         case 'ꪡ':
         case 'ꪢ':
         case 'ꪣ':
         case 'ꪤ':
         case 'ꪥ':
         case 'ꪦ':
         case 'ꪧ':
         case 'ꪨ':
         case 'ꪩ':
         case 'ꪪ':
         case 'ꪫ':
         case 'ꪬ':
         case 'ꪭ':
         case 'ꪮ':
         case 'ꪯ':
         case 'ꪰ':
         case 'ꪱ':
         case 'ꪲ':
         case 'ꪳ':
         case 'ꪴ':
         case 'ꪵ':
         case 'ꪶ':
         case 'ꪷ':
         case 'ꪸ':
         case 'ꪹ':
         case 'ꪺ':
         case 'ꪻ':
         case 'ꪼ':
         case 'ꪽ':
         case 'ꪾ':
         case '꪿':
         case 'ꫀ':
         case '꫁':
         case 'ꫂ':
            this.matchRange('ꪀ', 'ꫂ');
            break;
         case 'ꫛ':
         case 'ꫜ':
         case 'ꫝ':
            this.matchRange('ꫛ', 'ꫝ');
            break;
         case 'ꬁ':
         case 'ꬂ':
         case 'ꬃ':
         case 'ꬄ':
         case 'ꬅ':
         case 'ꬆ':
            this.matchRange('ꬁ', 'ꬆ');
            break;
         case 'ꬉ':
         case 'ꬊ':
         case 'ꬋ':
         case 'ꬌ':
         case 'ꬍ':
         case 'ꬎ':
            this.matchRange('ꬉ', 'ꬎ');
            break;
         case 'ꬑ':
         case 'ꬒ':
         case 'ꬓ':
         case 'ꬔ':
         case 'ꬕ':
         case 'ꬖ':
            this.matchRange('ꬑ', 'ꬖ');
            break;
         case 'ꬠ':
         case 'ꬡ':
         case 'ꬢ':
         case 'ꬣ':
         case 'ꬤ':
         case 'ꬥ':
         case 'ꬦ':
            this.matchRange('ꬠ', 'ꬦ');
            break;
         case 'ꬨ':
         case 'ꬩ':
         case 'ꬪ':
         case 'ꬫ':
         case 'ꬬ':
         case 'ꬭ':
         case 'ꬮ':
            this.matchRange('ꬨ', 'ꬮ');
            break;
         case 'ꯀ':
         case 'ꯁ':
         case 'ꯂ':
         case 'ꯃ':
         case 'ꯄ':
         case 'ꯅ':
         case 'ꯆ':
         case 'ꯇ':
         case 'ꯈ':
         case 'ꯉ':
         case 'ꯊ':
         case 'ꯋ':
         case 'ꯌ':
         case 'ꯍ':
         case 'ꯎ':
         case 'ꯏ':
         case 'ꯐ':
         case 'ꯑ':
         case 'ꯒ':
         case 'ꯓ':
         case 'ꯔ':
         case 'ꯕ':
         case 'ꯖ':
         case 'ꯗ':
         case 'ꯘ':
         case 'ꯙ':
         case 'ꯚ':
         case 'ꯛ':
         case 'ꯜ':
         case 'ꯝ':
         case 'ꯞ':
         case 'ꯟ':
         case 'ꯠ':
         case 'ꯡ':
         case 'ꯢ':
         case 'ꯣ':
         case 'ꯤ':
         case 'ꯥ':
         case 'ꯦ':
         case 'ꯧ':
         case 'ꯨ':
         case 'ꯩ':
         case 'ꯪ':
            this.matchRange('ꯀ', 'ꯪ');
            break;
         case '꯬':
         case '꯭':
            this.matchRange('꯬', '꯭');
            break;
         case '꯰':
         case '꯱':
         case '꯲':
         case '꯳':
         case '꯴':
         case '꯵':
         case '꯶':
         case '꯷':
         case '꯸':
         case '꯹':
            this.matchRange('꯰', '꯹');
            break;
         case 'ힰ':
         case 'ힱ':
         case 'ힲ':
         case 'ힳ':
         case 'ힴ':
         case 'ힵ':
         case 'ힶ':
         case 'ힷ':
         case 'ힸ':
         case 'ힹ':
         case 'ힺ':
         case 'ힻ':
         case 'ힼ':
         case 'ힽ':
         case 'ힾ':
         case 'ힿ':
         case 'ퟀ':
         case 'ퟁ':
         case 'ퟂ':
         case 'ퟃ':
         case 'ퟄ':
         case 'ퟅ':
         case 'ퟆ':
            this.matchRange('ힰ', 'ퟆ');
            break;
         case 'ퟋ':
         case 'ퟌ':
         case 'ퟍ':
         case 'ퟎ':
         case 'ퟏ':
         case 'ퟐ':
         case 'ퟑ':
         case 'ퟒ':
         case 'ퟓ':
         case 'ퟔ':
         case 'ퟕ':
         case 'ퟖ':
         case 'ퟗ':
         case 'ퟘ':
         case 'ퟙ':
         case 'ퟚ':
         case 'ퟛ':
         case 'ퟜ':
         case 'ퟝ':
         case 'ퟞ':
         case 'ퟟ':
         case 'ퟠ':
         case 'ퟡ':
         case 'ퟢ':
         case 'ퟣ':
         case 'ퟤ':
         case 'ퟥ':
         case 'ퟦ':
         case 'ퟧ':
         case 'ퟨ':
         case 'ퟩ':
         case 'ퟪ':
         case 'ퟫ':
         case 'ퟬ':
         case 'ퟭ':
         case 'ퟮ':
         case 'ퟯ':
         case 'ퟰ':
         case 'ퟱ':
         case 'ퟲ':
         case 'ퟳ':
         case 'ퟴ':
         case 'ퟵ':
         case 'ퟶ':
         case 'ퟷ':
         case 'ퟸ':
         case 'ퟹ':
         case 'ퟺ':
         case 'ퟻ':
            this.matchRange('ퟋ', 'ퟻ');
            break;
         case '侮':
         case '僧':
         case '免':
         case '勉':
         case '勤':
         case '卑':
         case '喝':
         case '嘆':
         case '器':
         case '塀':
         case '墨':
         case '層':
         case '屮':
         case '悔':
         case '慨':
         case '憎':
         case '懲':
         case '敏':
         case '既':
         case '暑':
         case '梅':
         case '海':
         case '渚':
         case '漢':
         case '煮':
         case '爫':
         case '琢':
         case '碑':
         case '社':
         case '祉':
         case '祈':
         case '祐':
         case '祖':
         case '祝':
         case '禍':
         case '禎':
         case '穀':
         case '突':
         case '節':
         case '練':
         case '縉':
         case '繁':
         case '署':
         case '者':
         case '臭':
         case '艹':
         case '艹':
         case '著':
         case '褐':
         case '視':
         case '謁':
         case '謹':
         case '賓':
         case '贈':
         case '辶':
         case '逸':
         case '難':
         case '響':
         case '頻':
         case '恵':
         case '𤋮':
         case '舘':
            this.matchRange('侮', '舘');
            break;
         case '並':
         case '况':
         case '全':
         case '侀':
         case '充':
         case '冀':
         case '勇':
         case '勺':
         case '喝':
         case '啕':
         case '喙':
         case '嗢':
         case '塚':
         case '墳':
         case '奄':
         case '奔':
         case '婢':
         case '嬨':
         case '廒':
         case '廙':
         case '彩':
         case '徭':
         case '惘':
         case '慎':
         case '愈':
         case '憎':
         case '慠':
         case '懲':
         case '戴':
         case '揄':
         case '搜':
         case '摒':
         case '敖':
         case '晴':
         case '朗':
         case '望':
         case '杖':
         case '歹':
         case '殺':
         case '流':
         case '滛':
         case '滋':
         case '漢':
         case '瀞':
         case '煮':
         case '瞧':
         case '爵':
         case '犯':
         case '猪':
         case '瑱':
         case '甆':
         case '画':
         case '瘝':
         case '瘟':
         case '益':
         case '盛':
         case '直':
         case '睊':
         case '着':
         case '磌':
         case '窱':
         case '節':
         case '类':
         case '絛':
         case '練':
         case '缾':
         case '者':
         case '荒':
         case '華':
         case '蝹':
         case '襁':
         case '覆':
         case '視':
         case '調':
         case '諸':
         case '請':
         case '謁':
         case '諾':
         case '諭':
         case '謹':
         case '變':
         case '贈':
         case '輸':
         case '遲':
         case '醙':
         case '鉶':
         case '陼':
         case '難':
         case '靖':
         case '韛':
         case '響':
         case '頋':
         case '頻':
         case '鬒':
         case '龜':
         case '𢡊':
         case '𢡄':
         case '𣏕':
         case '㮝':
         case '䀘':
         case '䀹':
         case '𥉉':
         case '𥳐':
         case '𧻓':
         case '齃':
         case '龎':
            this.matchRange('並', '龎');
            break;
         case 'ﬀ':
         case 'ﬁ':
         case 'ﬂ':
         case 'ﬃ':
         case 'ﬄ':
         case 'ﬅ':
         case 'ﬆ':
            this.matchRange('ﬀ', 'ﬆ');
            break;
         case 'ﬓ':
         case 'ﬔ':
         case 'ﬕ':
         case 'ﬖ':
         case 'ﬗ':
            this.matchRange('ﬓ', 'ﬗ');
            break;
         case 'יִ':
         case 'ﬞ':
         case 'ײַ':
         case 'ﬠ':
         case 'ﬡ':
         case 'ﬢ':
         case 'ﬣ':
         case 'ﬤ':
         case 'ﬥ':
         case 'ﬦ':
         case 'ﬧ':
         case 'ﬨ':
            this.matchRange('יִ', 'ﬨ');
            break;
         case 'שׁ':
         case 'שׂ':
         case 'שּׁ':
         case 'שּׂ':
         case 'אַ':
         case 'אָ':
         case 'אּ':
         case 'בּ':
         case 'גּ':
         case 'דּ':
         case 'הּ':
         case 'וּ':
         case 'זּ':
            this.matchRange('שׁ', 'זּ');
            break;
         case 'טּ':
         case 'יּ':
         case 'ךּ':
         case 'כּ':
         case 'לּ':
            this.matchRange('טּ', 'לּ');
            break;
         case 'מּ':
            this.matchRange('מּ', 'מּ');
            break;
         case 'נּ':
         case 'סּ':
            this.matchRange('נּ', 'סּ');
            break;
         case 'ףּ':
         case 'פּ':
            this.matchRange('ףּ', 'פּ');
            break;
         case 'צּ':
         case 'קּ':
         case 'רּ':
         case 'שּ':
         case 'תּ':
         case 'וֹ':
         case 'בֿ':
         case 'כֿ':
         case 'פֿ':
         case 'ﭏ':
         case 'ﭐ':
         case 'ﭑ':
         case 'ﭒ':
         case 'ﭓ':
         case 'ﭔ':
         case 'ﭕ':
         case 'ﭖ':
         case 'ﭗ':
         case 'ﭘ':
         case 'ﭙ':
         case 'ﭚ':
         case 'ﭛ':
         case 'ﭜ':
         case 'ﭝ':
         case 'ﭞ':
         case 'ﭟ':
         case 'ﭠ':
         case 'ﭡ':
         case 'ﭢ':
         case 'ﭣ':
         case 'ﭤ':
         case 'ﭥ':
         case 'ﭦ':
         case 'ﭧ':
         case 'ﭨ':
         case 'ﭩ':
         case 'ﭪ':
         case 'ﭫ':
         case 'ﭬ':
         case 'ﭭ':
         case 'ﭮ':
         case 'ﭯ':
         case 'ﭰ':
         case 'ﭱ':
         case 'ﭲ':
         case 'ﭳ':
         case 'ﭴ':
         case 'ﭵ':
         case 'ﭶ':
         case 'ﭷ':
         case 'ﭸ':
         case 'ﭹ':
         case 'ﭺ':
         case 'ﭻ':
         case 'ﭼ':
         case 'ﭽ':
         case 'ﭾ':
         case 'ﭿ':
         case 'ﮀ':
         case 'ﮁ':
         case 'ﮂ':
         case 'ﮃ':
         case 'ﮄ':
         case 'ﮅ':
         case 'ﮆ':
         case 'ﮇ':
         case 'ﮈ':
         case 'ﮉ':
         case 'ﮊ':
         case 'ﮋ':
         case 'ﮌ':
         case 'ﮍ':
         case 'ﮎ':
         case 'ﮏ':
         case 'ﮐ':
         case 'ﮑ':
         case 'ﮒ':
         case 'ﮓ':
         case 'ﮔ':
         case 'ﮕ':
         case 'ﮖ':
         case 'ﮗ':
         case 'ﮘ':
         case 'ﮙ':
         case 'ﮚ':
         case 'ﮛ':
         case 'ﮜ':
         case 'ﮝ':
         case 'ﮞ':
         case 'ﮟ':
         case 'ﮠ':
         case 'ﮡ':
         case 'ﮢ':
         case 'ﮣ':
         case 'ﮤ':
         case 'ﮥ':
         case 'ﮦ':
         case 'ﮧ':
         case 'ﮨ':
         case 'ﮩ':
         case 'ﮪ':
         case 'ﮫ':
         case 'ﮬ':
         case 'ﮭ':
         case 'ﮮ':
         case 'ﮯ':
         case 'ﮰ':
         case 'ﮱ':
            this.matchRange('צּ', 'ﮱ');
            break;
         case 'ﵐ':
         case 'ﵑ':
         case 'ﵒ':
         case 'ﵓ':
         case 'ﵔ':
         case 'ﵕ':
         case 'ﵖ':
         case 'ﵗ':
         case 'ﵘ':
         case 'ﵙ':
         case 'ﵚ':
         case 'ﵛ':
         case 'ﵜ':
         case 'ﵝ':
         case 'ﵞ':
         case 'ﵟ':
         case 'ﵠ':
         case 'ﵡ':
         case 'ﵢ':
         case 'ﵣ':
         case 'ﵤ':
         case 'ﵥ':
         case 'ﵦ':
         case 'ﵧ':
         case 'ﵨ':
         case 'ﵩ':
         case 'ﵪ':
         case 'ﵫ':
         case 'ﵬ':
         case 'ﵭ':
         case 'ﵮ':
         case 'ﵯ':
         case 'ﵰ':
         case 'ﵱ':
         case 'ﵲ':
         case 'ﵳ':
         case 'ﵴ':
         case 'ﵵ':
         case 'ﵶ':
         case 'ﵷ':
         case 'ﵸ':
         case 'ﵹ':
         case 'ﵺ':
         case 'ﵻ':
         case 'ﵼ':
         case 'ﵽ':
         case 'ﵾ':
         case 'ﵿ':
         case 'ﶀ':
         case 'ﶁ':
         case 'ﶂ':
         case 'ﶃ':
         case 'ﶄ':
         case 'ﶅ':
         case 'ﶆ':
         case 'ﶇ':
         case 'ﶈ':
         case 'ﶉ':
         case 'ﶊ':
         case 'ﶋ':
         case 'ﶌ':
         case 'ﶍ':
         case 'ﶎ':
         case 'ﶏ':
            this.matchRange('ﵐ', 'ﶏ');
            break;
         case 'ﶒ':
         case 'ﶓ':
         case 'ﶔ':
         case 'ﶕ':
         case 'ﶖ':
         case 'ﶗ':
         case 'ﶘ':
         case 'ﶙ':
         case 'ﶚ':
         case 'ﶛ':
         case 'ﶜ':
         case 'ﶝ':
         case 'ﶞ':
         case 'ﶟ':
         case 'ﶠ':
         case 'ﶡ':
         case 'ﶢ':
         case 'ﶣ':
         case 'ﶤ':
         case 'ﶥ':
         case 'ﶦ':
         case 'ﶧ':
         case 'ﶨ':
         case 'ﶩ':
         case 'ﶪ':
         case 'ﶫ':
         case 'ﶬ':
         case 'ﶭ':
         case 'ﶮ':
         case 'ﶯ':
         case 'ﶰ':
         case 'ﶱ':
         case 'ﶲ':
         case 'ﶳ':
         case 'ﶴ':
         case 'ﶵ':
         case 'ﶶ':
         case 'ﶷ':
         case 'ﶸ':
         case 'ﶹ':
         case 'ﶺ':
         case 'ﶻ':
         case 'ﶼ':
         case 'ﶽ':
         case 'ﶾ':
         case 'ﶿ':
         case 'ﷀ':
         case 'ﷁ':
         case 'ﷂ':
         case 'ﷃ':
         case 'ﷄ':
         case 'ﷅ':
         case 'ﷆ':
         case 'ﷇ':
            this.matchRange('ﶒ', 'ﷇ');
            break;
         case 'ﷰ':
         case 'ﷱ':
         case 'ﷲ':
         case 'ﷳ':
         case 'ﷴ':
         case 'ﷵ':
         case 'ﷶ':
         case 'ﷷ':
         case 'ﷸ':
         case 'ﷹ':
         case 'ﷺ':
         case 'ﷻ':
         case '﷼':
            this.matchRange('ﷰ', '﷼');
            break;
         case '︀':
         case '︁':
         case '︂':
         case '︃':
         case '︄':
         case '︅':
         case '︆':
         case '︇':
         case '︈':
         case '︉':
         case '︊':
         case '︋':
         case '︌':
         case '︍':
         case '︎':
         case '️':
            this.matchRange('︀', '️');
            break;
         case '︠':
         case '︡':
         case '︢':
         case '︣':
         case '︤':
         case '︥':
         case '︦':
            this.matchRange('︠', '︦');
            break;
         case '︳':
         case '︴':
            this.matchRange('︳', '︴');
            break;
         case '﹍':
         case '﹎':
         case '﹏':
            this.matchRange('﹍', '﹏');
            break;
         case '﹩':
            this.matchRange('﹩', '﹩');
            break;
         case 'ﹰ':
         case 'ﹱ':
         case 'ﹲ':
         case 'ﹳ':
         case 'ﹴ':
            this.matchRange('ﹰ', 'ﹴ');
            break;
         case '\ufeff':
            this.matchRange('\ufeff', '\ufeff');
            break;
         case '＄':
            this.matchRange('＄', '＄');
            break;
         case '０':
         case '１':
         case '２':
         case '３':
         case '４':
         case '５':
         case '６':
         case '７':
         case '８':
         case '９':
            this.matchRange('０', '９');
            break;
         case 'Ａ':
         case 'Ｂ':
         case 'Ｃ':
         case 'Ｄ':
         case 'Ｅ':
         case 'Ｆ':
         case 'Ｇ':
         case 'Ｈ':
         case 'Ｉ':
         case 'Ｊ':
         case 'Ｋ':
         case 'Ｌ':
         case 'Ｍ':
         case 'Ｎ':
         case 'Ｏ':
         case 'Ｐ':
         case 'Ｑ':
         case 'Ｒ':
         case 'Ｓ':
         case 'Ｔ':
         case 'Ｕ':
         case 'Ｖ':
         case 'Ｗ':
         case 'Ｘ':
         case 'Ｙ':
         case 'Ｚ':
            this.matchRange('Ａ', 'Ｚ');
            break;
         case '＿':
            this.matchRange('＿', '＿');
            break;
         case 'ａ':
         case 'ｂ':
         case 'ｃ':
         case 'ｄ':
         case 'ｅ':
         case 'ｆ':
         case 'ｇ':
         case 'ｈ':
         case 'ｉ':
         case 'ｊ':
         case 'ｋ':
         case 'ｌ':
         case 'ｍ':
         case 'ｎ':
         case 'ｏ':
         case 'ｐ':
         case 'ｑ':
         case 'ｒ':
         case 'ｓ':
         case 'ｔ':
         case 'ｕ':
         case 'ｖ':
         case 'ｗ':
         case 'ｘ':
         case 'ｙ':
         case 'ｚ':
            this.matchRange('ａ', 'ｚ');
            break;
         case 'ｦ':
         case 'ｧ':
         case 'ｨ':
         case 'ｩ':
         case 'ｪ':
         case 'ｫ':
         case 'ｬ':
         case 'ｭ':
         case 'ｮ':
         case 'ｯ':
         case 'ｰ':
         case 'ｱ':
         case 'ｲ':
         case 'ｳ':
         case 'ｴ':
         case 'ｵ':
         case 'ｶ':
         case 'ｷ':
         case 'ｸ':
         case 'ｹ':
         case 'ｺ':
         case 'ｻ':
         case 'ｼ':
         case 'ｽ':
         case 'ｾ':
         case 'ｿ':
         case 'ﾀ':
         case 'ﾁ':
         case 'ﾂ':
         case 'ﾃ':
         case 'ﾄ':
         case 'ﾅ':
         case 'ﾆ':
         case 'ﾇ':
         case 'ﾈ':
         case 'ﾉ':
         case 'ﾊ':
         case 'ﾋ':
         case 'ﾌ':
         case 'ﾍ':
         case 'ﾎ':
         case 'ﾏ':
         case 'ﾐ':
         case 'ﾑ':
         case 'ﾒ':
         case 'ﾓ':
         case 'ﾔ':
         case 'ﾕ':
         case 'ﾖ':
         case 'ﾗ':
         case 'ﾘ':
         case 'ﾙ':
         case 'ﾚ':
         case 'ﾛ':
         case 'ﾜ':
         case 'ﾝ':
         case 'ﾞ':
         case 'ﾟ':
         case 'ﾠ':
         case 'ﾡ':
         case 'ﾢ':
         case 'ﾣ':
         case 'ﾤ':
         case 'ﾥ':
         case 'ﾦ':
         case 'ﾧ':
         case 'ﾨ':
         case 'ﾩ':
         case 'ﾪ':
         case 'ﾫ':
         case 'ﾬ':
         case 'ﾭ':
         case 'ﾮ':
         case 'ﾯ':
         case 'ﾰ':
         case 'ﾱ':
         case 'ﾲ':
         case 'ﾳ':
         case 'ﾴ':
         case 'ﾵ':
         case 'ﾶ':
         case 'ﾷ':
         case 'ﾸ':
         case 'ﾹ':
         case 'ﾺ':
         case 'ﾻ':
         case 'ﾼ':
         case 'ﾽ':
         case 'ﾾ':
            this.matchRange('ｦ', 'ﾾ');
            break;
         case 'ￂ':
         case 'ￃ':
         case 'ￄ':
         case 'ￅ':
         case 'ￆ':
         case 'ￇ':
            this.matchRange('ￂ', 'ￇ');
            break;
         case 'ￊ':
         case 'ￋ':
         case 'ￌ':
         case 'ￍ':
         case 'ￎ':
         case 'ￏ':
            this.matchRange('ￊ', 'ￏ');
            break;
         case 'ￒ':
         case 'ￓ':
         case 'ￔ':
         case 'ￕ':
         case 'ￖ':
         case 'ￗ':
            this.matchRange('ￒ', 'ￗ');
            break;
         case 'ￚ':
         case 'ￛ':
         case 'ￜ':
            this.matchRange('ￚ', 'ￜ');
            break;
         case '￠':
         case '￡':
            this.matchRange('￠', '￡');
            break;
         case '￥':
         case '￦':
            this.matchRange('￥', '￦');
            break;
         case '\ufff9':
         case '\ufffa':
         case '\ufffb':
            this.matchRange('\ufff9', '\ufffb');
            break;
         default:
            if (this.LA(1) >= 248 && this.LA(1) <= 705) {
               this.matchRange('ø', 'ˁ');
            } else if (this.LA(1) >= 1015 && this.LA(1) <= 1153) {
               this.matchRange('Ϸ', 'ҁ');
            } else if (this.LA(1) >= 1162 && this.LA(1) <= 1319) {
               this.matchRange('Ҋ', 'ԧ');
            } else if (this.LA(1) >= 4352 && this.LA(1) <= 4680) {
               this.matchRange('ᄀ', 'ቈ');
            } else if (this.LA(1) >= 5121 && this.LA(1) <= 5740) {
               this.matchRange('ᐁ', 'ᙬ');
            } else if (this.LA(1) >= 7424 && this.LA(1) <= 7654) {
               this.matchRange('ᴀ', 'ᷦ');
            } else if (this.LA(1) >= 7676 && this.LA(1) <= 7957) {
               this.matchRange('᷼', 'ἕ');
            } else if (this.LA(1) >= 11360 && this.LA(1) <= 11492) {
               this.matchRange('Ⱡ', 'ⳤ');
            } else if (this.LA(1) >= 13312 && this.LA(1) <= 19893) {
               this.matchRange('㐀', '䶵');
            } else if (this.LA(1) >= 19968 && this.LA(1) <= '鿋') {
               this.matchRange('一', '鿋');
            } else if (this.LA(1) >= 'ꀀ' && this.LA(1) <= 'ꒌ') {
               this.matchRange('ꀀ', 'ꒌ');
            } else if (this.LA(1) >= 'ꔀ' && this.LA(1) <= 'ꘌ') {
               this.matchRange('ꔀ', 'ꘌ');
            } else if (this.LA(1) >= '가' && this.LA(1) <= '힣') {
               this.matchRange('가', '힣');
            } else if (this.LA(1) >= '豈' && this.LA(1) <= '鶴') {
               this.matchRange('豈', '鶴');
            } else if (this.LA(1) >= 'ﯓ' && this.LA(1) <= 'ﴽ') {
               this.matchRange('ﯓ', 'ﴽ');
            } else {
               if (this.LA(1) < 'ﹶ' || this.LA(1) > 'ﻼ') {
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.matchRange('ﹶ', 'ﻼ');
            }
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 28;
      this.match('\'');

      while(true) {
         while(this.LA(1) != '\'' || this.LA(2) != '\'') {
            if (!_tokenSet_3.member(this.LA(1))) {
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

   public final void mINTEGER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      byte _ttype;
      Token _token;
      int _begin;
      label167: {
         _token = null;
         _begin = this.text.length();
         _ttype = 31;
         Token f1 = null;
         Token f2 = null;
         Token f3 = null;
         Token f4 = null;
         boolean isDecimal = false;
         Token t = null;
         int _cnt79;
         switch (this.LA(1)) {
            case '.':
               this.match('.');
               if (this.LA(1) >= '0' && this.LA(1) <= '9') {
                  for(_cnt79 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt79) {
                     this.matchRange('0', '9');
                  }

                  if (_cnt79 < 1) {
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }

                  if (this.LA(1) == 'E' || this.LA(1) == 'e') {
                     this.mEXPONENT(false);
                  }

                  if (_tokenSet_4.member(this.LA(1))) {
                     this.mSUFFIX(true);
                     f1 = this._returnToken;
                  }

                  _ttype = 32;
               }
               break label167;
            case '/':
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
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

         label162:
         switch (this.LA(1)) {
            case '0':
               this.match('0');
               isDecimal = true;
               switch (this.LA(1)) {
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                     for(_cnt79 = 0; this.LA(1) >= '0' && this.LA(1) <= '7'; ++_cnt79) {
                        this.matchRange('0', '7');
                     }

                     if (_cnt79 < 1) {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }
                     break label162;
                  case 'X':
                  case 'x':
                     switch (this.LA(1)) {
                        case 'X':
                           this.match('X');
                           break;
                        case 'x':
                           this.match('x');
                           break;
                        default:
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }

                     for(_cnt79 = 0; _tokenSet_5.member(this.LA(1)); ++_cnt79) {
                        this.mHEX_DIGIT(false);
                     }

                     if (_cnt79 < 1) {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }
                  default:
                     break label162;
               }
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
               this.matchRange('1', '9');

               while(this.LA(1) >= '0' && this.LA(1) <= '9') {
                  this.matchRange('0', '9');
               }

               isDecimal = true;
               break;
            default:
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         if (this.LA(1) != 'L' && this.LA(1) != 'l') {
            if (_tokenSet_6.member(this.LA(1)) && isDecimal) {
               switch (this.LA(1)) {
                  case '.':
                     this.match('.');

                     while(this.LA(1) >= '0' && this.LA(1) <= '9') {
                        this.matchRange('0', '9');
                     }

                     if (this.LA(1) == 'E' || this.LA(1) == 'e') {
                        this.mEXPONENT(false);
                     }

                     if (_tokenSet_4.member(this.LA(1))) {
                        this.mSUFFIX(true);
                        f2 = this._returnToken;
                     }
                     break;
                  case 'D':
                  case 'F':
                  case 'd':
                  case 'f':
                     this.mSUFFIX(true);
                     f4 = this._returnToken;
                     break;
                  case 'E':
                  case 'e':
                     this.mEXPONENT(false);
                     if (_tokenSet_4.member(this.LA(1))) {
                        this.mSUFFIX(true);
                        f3 = this._returnToken;
                     }
                     break;
                  default:
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               _ttype = 32;
            }
         } else {
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
         }
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
      int _ttype = 40;
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
            this.match('+');
            break;
         case ',':
         case '.':
         case '/':
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         case '-':
            this.match('-');
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

      int _cnt111;
      for(_cnt111 = 0; this.LA(1) >= '0' && this.LA(1) <= '9'; ++_cnt111) {
         this.matchRange('0', '9');
      }

      if (_cnt111 >= 1) {
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final void mSUFFIX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 41;
      switch (this.LA(1)) {
         case 'D':
            this.match('D');
            break;
         case 'F':
            this.match('F');
            break;
         case 'd':
            this.match('d');
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

   protected final void mHEX_DIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 35;
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
         case '\f':
            this.match('\f');
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

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[4088];
      data[0] = 68719476736L;
      data[1] = 576460745995190270L;
      data[2] = 297242231151001600L;
      data[3] = -36028797027352577L;

      int i;
      for(i = 4; i <= 10; ++i) {
         data[i] = -1L;
      }

      data[11] = 88094074470339L;
      data[13] = 4386224562082152448L;
      data[14] = -17179879616L;
      data[15] = -18014398509481985L;

      for(i = 16; i <= 17; ++i) {
         data[i] = -1L;
      }

      data[18] = -1021L;
      data[19] = -1L;
      data[20] = -561850441793537L;
      data[21] = -8547991553L;
      data[22] = 255L;
      data[23] = 1979120929931264L;
      data[24] = -4294965248L;
      data[25] = -351843720886273L;
      data[26] = -1L;
      data[27] = -7205547885240254465L;
      data[28] = 281474976514048L;
      data[29] = -8192L;
      data[30] = 563224831328255L;
      data[31] = 301749971126844416L;
      data[32] = 1168302407679L;
      data[33] = 33554431L;
      data[36] = 2594073385365405680L;
      data[37] = -72620526828191744L;
      data[38] = 2577745637692514272L;
      data[39] = 580682892791791616L;
      data[40] = 247132830528276448L;
      data[41] = 7881300924956672L;
      data[42] = 2589004636761079776L;
      data[43] = 562962838388736L;
      data[44] = 2589004636760940512L;
      data[45] = 562965791113216L;
      data[46] = 288167810662516712L;
      data[47] = 144115188075921408L;
      data[48] = 2589567586714640352L;
      data[49] = 12935233536L;
      data[50] = 2589567586714640352L;
      data[51] = 1688863818907648L;
      data[52] = 2882303761516978144L;
      data[53] = -288230363266793472L;
      data[54] = 3457638613854978016L;
      data[55] = 127L;
      data[56] = -9219431387180826626L;
      data[57] = 127L;
      data[58] = 2309762420256548246L;
      data[59] = 805306463L;
      data[60] = 1L;
      data[61] = 35184372088575L;
      data[62] = 7936L;
      data[64] = -9223363240761753601L;
      data[65] = -8514196127940608L;
      data[66] = -4294950909L;
      data[67] = 1729382256910204991L;

      for(i = 68; i <= 72; ++i) {
         data[i] = -1L;
      }

      data[73] = -3263218177L;
      data[74] = 9168765891372858879L;
      data[75] = -8388803L;
      data[76] = -12713985L;
      data[77] = 134217727L;
      data[78] = -4294901761L;
      data[79] = 9007199254740991L;
      data[80] = -2L;

      for(i = 81; i <= 88; ++i) {
         data[i] = -1L;
      }

      data[89] = -105553116266497L;
      data[90] = -4160749570L;
      data[91] = 501377302265855L;
      data[92] = 1125895612129279L;
      data[93] = 527761286627327L;
      data[94] = 4503599627370495L;
      data[95] = 411041792L;
      data[96] = -4294967296L;
      data[97] = 72057594037927935L;
      data[98] = -274877906944001L;
      data[99] = 18014398509481983L;
      data[100] = 536870911L;
      data[101] = 8796093022142464L;
      data[102] = 17592186044415L;
      data[103] = 254L;
      data[104] = -4286578689L;
      data[105] = 2097151L;
      data[106] = 549755813888L;
      data[108] = 4503599627370464L;
      data[109] = 4064L;
      data[110] = 211114822467576L;
      data[111] = 274877906943L;
      data[112] = 68719476735L;
      data[113] = 4611686018360336384L;
      data[115] = 1088516511498240L;

      for(i = 116; i <= 118; ++i) {
         data[i] = -1L;
      }

      for(i = 120; i <= 123; ++i) {
         data[i] = -1L;
      }

      data[124] = -3233808385L;
      data[125] = 4611686017001275199L;
      data[126] = 6908521828386340863L;
      data[127] = 2295745090394464220L;
      data[128] = Long.MIN_VALUE;
      data[129] = -9222809086900305919L;
      data[130] = 288230372393549824L;
      data[132] = -864764451093480316L;
      data[133] = -4294949920L;
      data[134] = 511L;
      data[176] = -140737488355329L;
      data[177] = -2147483649L;
      data[178] = -1L;
      data[179] = 132078834286591L;
      data[180] = -281200098803713L;
      data[181] = 141012366262271L;
      data[182] = 9187201948305063935L;
      data[183] = 2139062143L;
      data[184] = 140737488355328L;
      data[192] = 2251241253188403424L;
      data[193] = -2L;
      data[194] = -4823449601L;
      data[195] = -576460752303423489L;
      data[196] = -492581209243680L;
      data[197] = -1L;
      data[198] = 576460748008488959L;
      data[199] = -281474976710656L;

      for(i = 208; i <= 309; ++i) {
         data[i] = -1L;
      }

      data[310] = 18014398509481983L;

      for(i = 312; i <= 638; ++i) {
         data[i] = -1L;
      }

      data[639] = 4095L;

      for(i = 640; i <= 657; ++i) {
         data[i] = -1L;
      }

      data[658] = 8191L;
      data[659] = 4611686018427322368L;

      for(i = 660; i <= 663; ++i) {
         data[i] = -1L;
      }

      data[664] = 13198434443263L;
      data[665] = -9223231299366420481L;
      data[666] = -4278190081L;
      data[667] = 281474976710655L;
      data[668] = -12893290496L;
      data[669] = -1L;
      data[670] = 4393751771647L;
      data[671] = -288230376151711744L;
      data[672] = 72057628397664187L;
      data[673] = 4503599627370495L;
      data[674] = 4503599627370492L;
      data[675] = 647392446434508800L;
      data[676] = -281200098804736L;
      data[677] = 2305843004918726783L;
      data[678] = 2251799813685232L;
      data[679] = 32768L;
      data[680] = 2199023255551L;
      data[681] = 324259168875712503L;
      data[682] = 4495436853045886975L;
      data[683] = 939524101L;
      data[684] = 140183445864062L;
      data[687] = 34359738367L;

      for(i = 688; i <= 861; ++i) {
         data[i] = -1L;
      }

      data[862] = -281406257233921L;
      data[863] = 1152921504606845055L;

      for(i = 996; i <= 999; ++i) {
         data[i] = -1L;
      }

      for(i = 1000; i <= 1001; ++i) {
         data[i] = -211106232532993L;
      }

      data[1002] = -1L;
      data[1003] = 67108863L;
      data[1004] = 6881498030004502655L;
      data[1005] = -37L;
      data[1006] = 1125899906842623L;
      data[1007] = -524288L;

      for(i = 1008; i <= 1011; ++i) {
         data[i] = -1L;
      }

      data[1012] = 4611686018427387903L;
      data[1013] = -65536L;
      data[1014] = -196609L;
      data[1015] = 2305561534236983551L;
      data[1016] = 6755399441055744L;
      data[1017] = -9286475208138752L;
      data[1018] = -1L;
      data[1019] = 2305843009213693951L;
      data[1020] = -8646911293141286896L;
      data[1021] = -274743689218L;
      data[1022] = Long.MAX_VALUE;
      data[1023] = 425688104188L;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[1025];
      data[1] = 576460743847706622L;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[4088];
      data[0] = 287948970162897407L;
      data[1] = -8646911290859585538L;
      data[2] = 297277419818057727L;
      data[3] = -36028797027352577L;

      int i;
      for(i = 4; i <= 10; ++i) {
         data[i] = -1L;
      }

      data[11] = 88094074470339L;
      data[12] = -1L;
      data[13] = 4386506037058863103L;
      data[14] = -17179879616L;
      data[15] = -18014398509481985L;

      for(i = 16; i <= 17; ++i) {
         data[i] = -1L;
      }

      data[18] = -773L;
      data[19] = -1L;
      data[20] = -561850441793537L;
      data[21] = -8547991553L;
      data[22] = -4611686018427518721L;
      data[23] = 1979120929931446L;
      data[24] = -4160813041L;
      data[25] = -65970697666561L;
      data[26] = -1L;
      data[27] = -6917531227739127809L;
      data[28] = -32768L;
      data[29] = -6145L;
      data[30] = 1125899906842623L;
      data[31] = 306244774661193727L;
      data[32] = 70368744177663L;
      data[33] = 268435455L;
      data[36] = -1L;
      data[37] = -72339275173068801L;
      data[38] = -881018876128026642L;
      data[39] = 580964144438606239L;
      data[40] = -3211631683292264466L;
      data[41] = 18014125208779143L;
      data[42] = -869759877059461138L;
      data[43] = 844214476815295L;
      data[44] = -869759877059600402L;
      data[45] = 844217442122143L;
      data[46] = -4323518207764871188L;
      data[47] = 144396388183129543L;
      data[48] = -2022118431712747538L;
      data[49] = 281264579952095L;
      data[50] = -869196927105900564L;
      data[51] = 1970115463626207L;
      data[52] = -1729382256910409748L;
      data[53] = -287949111619977761L;
      data[54] = 3457638613854978028L;
      data[55] = 3377704004977791L;
      data[56] = -8646911284551352322L;
      data[57] = 67076095L;
      data[58] = 4323434403644581270L;
      data[59] = 872365919L;
      data[60] = -4422530440275951615L;
      data[61] = -527765581332737L;
      data[62] = 2305843009196916703L;
      data[63] = 64L;
      data[64] = -1L;
      data[65] = -64513L;
      data[66] = -3221225473L;
      data[67] = 1729382256910204991L;

      for(i = 68; i <= 72; ++i) {
         data[i] = -1L;
      }

      data[73] = -3263218177L;
      data[74] = 9168765891372858879L;
      data[75] = -8388803L;
      data[76] = -12713985L;
      data[77] = 3892314111L;
      data[78] = -4294901761L;
      data[79] = 9007199254740991L;
      data[80] = -2L;

      for(i = 81; i <= 88; ++i) {
         data[i] = -1L;
      }

      data[89] = -105553116266497L;
      data[90] = -4160749570L;
      data[91] = 501377302265855L;
      data[92] = 9007194961862655L;
      data[93] = 3905461007941631L;
      data[94] = -1L;
      data[95] = 4394700505087L;
      data[96] = -4227909632L;
      data[97] = 72057594037927935L;
      data[98] = -272678883688449L;
      data[99] = 18014398509481983L;
      data[100] = 1152657618058084351L;
      data[101] = 8796093022207936L;
      data[102] = -263882790666241L;
      data[103] = 67044351L;
      data[104] = -4026531841L;
      data[105] = -6917529029788565505L;
      data[106] = 549822858239L;
      data[108] = -1L;
      data[109] = 4494803601395711L;
      data[110] = 288168803500556287L;
      data[111] = 4503599627370495L;
      data[112] = 72057594037927935L;
      data[113] = 4611686018427380735L;
      data[115] = 2251799813095424L;

      for(i = 116; i <= 118; ++i) {
         data[i] = -1L;
      }

      data[119] = -1152920954851033089L;

      for(i = 120; i <= 123; ++i) {
         data[i] = -1L;
      }

      data[124] = -3233808385L;
      data[125] = 4611686017001275199L;
      data[126] = 6908521828386340863L;
      data[127] = 2295745090394464220L;
      data[128] = -9223235697412868096L;
      data[129] = -9222531876826120191L;
      data[130] = 288230372393549824L;
      data[131] = 562821641207808L;
      data[132] = -864764451093480316L;
      data[133] = -4294949920L;
      data[134] = 511L;
      data[176] = -140737488355329L;
      data[177] = -2147483649L;
      data[178] = -1L;
      data[179] = 1117241252773887L;
      data[180] = -281200098803713L;
      data[181] = -9223231024488513537L;
      data[182] = 9187201948305063935L;
      data[183] = -2155905153L;
      data[184] = 140737488355328L;
      data[192] = 2251518330118602976L;
      data[193] = -2L;
      data[194] = -4722786305L;
      data[195] = -576460752303423489L;
      data[196] = -492581209243680L;
      data[197] = -1L;
      data[198] = 576460748008488959L;
      data[199] = -281474976710656L;

      for(i = 208; i <= 309; ++i) {
         data[i] = -1L;
      }

      data[310] = 18014398509481983L;

      for(i = 312; i <= 638; ++i) {
         data[i] = -1L;
      }

      data[639] = 4095L;

      for(i = 640; i <= 657; ++i) {
         data[i] = -1L;
      }

      data[658] = 8191L;
      data[659] = 4611686018427322368L;

      for(i = 660; i <= 663; ++i) {
         data[i] = -1L;
      }

      data[664] = 17592185987071L;
      data[665] = -5764326048057524225L;
      data[666] = -4278190081L;
      data[667] = 1125899906842623L;
      data[668] = -12893290496L;
      data[669] = -1L;
      data[670] = 4393751771647L;
      data[671] = -288230376151711744L;
      data[672] = 72058693549555711L;
      data[673] = 4503599627370495L;
      data[674] = -1L;
      data[675] = 648518342113427487L;
      data[676] = -211106232532993L;
      data[677] = 2305843004919775231L;
      data[678] = -1L;
      data[679] = 67076097L;
      data[680] = 36028797018963967L;
      data[681] = 900719921246191615L;
      data[682] = -1L;
      data[683] = 939524103L;
      data[684] = 140183445864062L;
      data[687] = 288010473826156543L;

      for(i = 688; i <= 861; ++i) {
         data[i] = -1L;
      }

      data[862] = -281406257233921L;
      data[863] = 1152921504606845055L;

      for(i = 996; i <= 999; ++i) {
         data[i] = -1L;
      }

      for(i = 1000; i <= 1001; ++i) {
         data[i] = -211106232532993L;
      }

      data[1002] = -1L;
      data[1003] = 67108863L;
      data[1004] = 6881498031078244479L;
      data[1005] = -37L;
      data[1006] = 1125899906842623L;
      data[1007] = -524288L;

      for(i = 1008; i <= 1011; ++i) {
         data[i] = -1L;
      }

      data[1012] = 4611686018427387903L;
      data[1013] = -65536L;
      data[1014] = -196609L;
      data[1015] = 2305561534236983551L;
      data[1016] = 6755944901967871L;
      data[1017] = -9286475208138752L;
      data[1018] = -1L;
      data[1019] = -6917529027641081857L;
      data[1020] = -8646911293074243568L;
      data[1021] = -274743689218L;
      data[1022] = Long.MAX_VALUE;
      data[1023] = 1008806742219095292L;
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[2048];
      data[0] = -549755813889L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[1025];
      data[1] = 343597383760L;
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[1025];
      data[0] = 287948901175001088L;
      data[1] = 541165879422L;
      return data;
   }

   private static final long[] mk_tokenSet_6() {
      long[] data = new long[1025];
      data[0] = 70368744177664L;
      data[1] = 481036337264L;
      return data;
   }
}
