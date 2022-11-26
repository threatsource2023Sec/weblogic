package weblogic.servlet.ejb2jsp;

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
import weblogic.servlet.ejb2jsp.dd.BeanDescriptor;

public class EJB2JSPLexer extends CharScanner implements EJB2JSPLexerTokenTypes, TokenStream {
   private static final int NO_COMMENT = 0;
   private static final int SLASH_COMMENT = 1;
   private static final int STD_COMMENT = 2;
   BeanDescriptor desc;
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

   public void setDescriptor(BeanDescriptor d) {
      this.desc = d;
   }

   private String showLA(int l) throws CharStreamException {
      StringBuffer sb = new StringBuffer();
      sb.append("LA(1,");
      sb.append(l);
      sb.append(")=>#");

      for(int i = 1; i <= l; ++i) {
         sb.append(this.LA(i));
      }

      sb.append('#');
      return sb.toString();
   }

   void p(String s) {
      System.err.println("[ejb2jsp @line " + this.getLine() + "]: " + s);
   }

   public void parse() throws TokenStreamException {
      long start = System.currentTimeMillis();

      while(this.nextToken().getType() != 1) {
      }

   }

   public EJB2JSPLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public EJB2JSPLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public EJB2JSPLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public EJB2JSPLexer(LexerSharedInputState state) {
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
         this.setCommitToPath(false);
         int _m = this.mark();
         this.resetText();

         try {
            try {
               if (_tokenSet_0.member(this.LA(1)) && _tokenSet_1.member(this.LA(2)) && this.LA(3) >= 3 && this.LA(3) <= 255) {
                  this.mTOKEN(true);
                  theRetToken = this._returnToken;
               } else if (_tokenSet_2.member(this.LA(1)) && _tokenSet_3.member(this.LA(2)) && _tokenSet_4.member(this.LA(3))) {
                  this.mMETHOD_DECLARATION(true);
                  theRetToken = this._returnToken;
               } else {
                  if (this.LA(1) != '\uffff') {
                     this.commit();

                     try {
                        this.mCODE(false);
                     } catch (RecognitionException var8) {
                        this.reportError(var8);
                        this.consume();
                     }
                     continue;
                  }

                  this.uponEOF();
                  this._returnToken = this.makeToken(1);
               }

               this.commit();
               if (this._returnToken != null) {
                  int _ttype = this._returnToken.getType();
                  _ttype = this.testLiteralsTable(_ttype);
                  this._returnToken.setType(_ttype);
                  return this._returnToken;
               }
            } catch (RecognitionException var9) {
               if (this.getCommitToPath()) {
                  throw new TokenStreamRecognitionException(var9);
               }

               this.rewind(_m);
               this.resetText();

               try {
                  this.mCODE(false);
               } catch (RecognitionException var7) {
                  this.reportError(var7);
                  this.consume();
               }
            }
         } catch (CharStreamException var10) {
            if (var10 instanceof CharStreamIOException) {
               throw new TokenStreamIOException(((CharStreamIOException)var10).io);
            }

            throw new TokenStreamException(var10.getMessage());
         }
      }
   }

   public final void mTOKEN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 4;
      boolean synPredMatched3 = false;
      if (this.LA(1) == '/' && this.LA(2) == '/') {
         int _m3 = this.mark();
         synPredMatched3 = true;
         ++this.inputState.guessing;

         try {
            this.match("//");
         } catch (RecognitionException var11) {
            synPredMatched3 = false;
         }

         this.rewind(_m3);
         --this.inputState.guessing;
      }

      if (synPredMatched3) {
         this.mSLASH_COMMENT(false);
      } else {
         boolean synPredMatched5 = false;
         if (this.LA(1) == '/' && this.LA(2) == '*') {
            int _m5 = this.mark();
            synPredMatched5 = true;
            ++this.inputState.guessing;

            try {
               this.match("/*");
            } catch (RecognitionException var10) {
               synPredMatched5 = false;
            }

            this.rewind(_m5);
            --this.inputState.guessing;
         }

         if (synPredMatched5) {
            this.mSTANDARD_COMMENT(false);
         } else {
            if (!_tokenSet_2.member(this.LA(1))) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.mMETHOD_DECLARATION(false);
         }
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSLASH_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 1;
      Token content = null;
      this.match("//");
      this.mSLASH_COMMENT_CONTENT(true);
      content = this._returnToken;
      switch (this.LA(1)) {
         case '\n':
            this.match('\n');
            break;
         case '\r':
            this.match('\r');
            break;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }

      if (this.inputState.guessing == 0) {
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSTANDARD_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
      Token content = null;
      this.match("/*");
      this.mSTANDARD_COMMENT_CONTENT(true);
      content = this._returnToken;
      this.match("*/");
      if (this.inputState.guessing == 0) {
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mMETHOD_DECLARATION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      Token retType = null;
      Token methodName = null;
      List types = new ArrayList();
      List names = new ArrayList();
      switch (this.LA(1)) {
         case 'p':
            this.match("public");
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            int _cnt9;
            for(_cnt9 = 0; _tokenSet_5.member(this.LA(1)); ++_cnt9) {
               this.mWS(false);
            }

            if (_cnt9 < 1) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            } else {
               this.mWORD(true);
               retType = this._returnToken;

               while(_tokenSet_5.member(this.LA(1))) {
                  this.mWS(false);
               }

               this.mWORD(true);
               methodName = this._returnToken;

               while(_tokenSet_5.member(this.LA(1))) {
                  this.mWS(false);
               }

               this.match("(");
               if (this.inputState.guessing == 0) {
               }

               this.mARGLIST(false, types, names);

               while(_tokenSet_5.member(this.LA(1))) {
                  this.mWS(false);
               }

               this.match(")");
               if (this.inputState.guessing == 0) {
                  SourceMethodInfo smi = new SourceMethodInfo(methodName.getText(), retType.getText(), types, names);
                  this.desc.resolveSource(smi);
               }

               if (_createToken && _token == null && _ttype != -1) {
                  _token = this.makeToken(_ttype);
                  _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
               }

               this._returnToken = _token;
               return;
            }
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 13;
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

   protected final void mWORD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      this.mLETTER(false);

      while(true) {
         while(true) {
            switch (this.LA(1)) {
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
               case '\\':
               default:
                  if (!_tokenSet_6.member(this.LA(1)) || !_tokenSet_7.member(this.LA(2))) {
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
                  break;
               case '[':
               case ']':
                  this.mBRACE(false);
            }
         }
      }
   }

   protected final void mARGLIST(boolean _createToken, List type, List argnames) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;

      while(_tokenSet_3.member(this.LA(1)) && _tokenSet_4.member(this.LA(2)) && _tokenSet_4.member(this.LA(3))) {
         while(_tokenSet_5.member(this.LA(1))) {
            this.mWS(false);
         }

         this.mARG(false, type, argnames);

         while(_tokenSet_5.member(this.LA(1)) && _tokenSet_8.member(this.LA(2))) {
            this.mWS(false);
         }

         switch (this.LA(1)) {
            case '\t':
            case '\n':
            case '\r':
            case ' ':
            case ')':
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
            case '*':
            case '+':
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
            case ',':
               this.mCOMMA(false);
         }
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mARG(boolean _createToken, List type, List argname) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 7;
      Token typeName = null;
      Token typeVal = null;
      this.mWORD(true);
      typeName = this._returnToken;

      int _cnt27;
      for(_cnt27 = 0; _tokenSet_5.member(this.LA(1)); ++_cnt27) {
         this.mWS(false);
      }

      if (_cnt27 >= 1) {
         this.mWORD(true);
         typeVal = this._returnToken;
         if (this.inputState.guessing == 0) {
            String name = typeName.getText();
            String val = typeVal.getText();
            type.add(name);
            argname.add(val);
         }

         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      } else {
         throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 14;
      this.match(',');
      if (this.inputState.guessing == 0) {
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;

      while(true) {
         while(this.LA(1) == '/' && this.LA(2) == '*') {
            this.mSTANDARD_COMMENT(false);
         }

         if (this.LA(1) != '/' || this.LA(2) != '/') {
            if (_createToken && _token == null && _ttype != -1) {
               _token = this.makeToken(_ttype);
               _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
            }

            this._returnToken = _token;
            return;
         }

         this.mSLASH_COMMENT(false);
      }
   }

   protected final void mSTANDARD_COMMENT_CONTENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 10;

      while((this.LA(1) != '*' || this.LA(2) != '/') && this.LA(1) >= 3 && this.LA(1) <= 255 && this.LA(2) >= 3 && this.LA(2) <= 255 && this.LA(3) >= 3 && this.LA(3) <= 255) {
         this.matchNot('\uffff');
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSLASH_COMMENT_CONTENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 12;

      while(_tokenSet_9.member(this.LA(1))) {
         this.match(_tokenSet_9);
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
      int _ttype = 15;
      this.match('.');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mDASH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 16;
      this.match('-');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSTAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
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

   protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 18;
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
      int _ttype = 19;
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

   protected final void mBRACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 20;
      switch (this.LA(1)) {
         case '[':
            this.match('[');
            break;
         case ']':
            this.match(']');
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

   protected final void mIMPORT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      this.mLETTER(false);

      while(true) {
         switch (this.LA(1)) {
            case '*':
               this.mSTAR(false);
               break;
            case '+':
            case ',':
            case '-':
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

   protected final void mCODE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      char c = false;
      char c = this.LA(1);
      this.matchNot('\uffff');
      if (this.inputState.guessing == 0 && c == '\n') {
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{140741783332352L, 281474976710656L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{145139829843456L, 576460745995190270L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{4294977024L, 281474976710656L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[]{4294977024L, 576460745995190270L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[]{288019274214155776L, 576460746666278910L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[]{4294977024L, 0L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_6() {
      long[] data = new long[]{0L, 576460745995190270L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_7() {
      long[] data = new long[]{288040164935083520L, 576460746666278910L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_8() {
      long[] data = new long[]{19795504276992L, 576460745995190270L, 0L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_9() {
      long[] data = new long[8];
      data[0] = -9224L;

      for(int i = 1; i <= 3; ++i) {
         data[i] = -1L;
      }

      return data;
   }
}
