package weblogic.diagnostics.archive.filestore;

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

public class AccessLogLexer extends CharScanner implements AccessLogLexerTokenTypes, TokenStream {
   private static final boolean DEBUG = false;
   private boolean hitEOF;
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());

   public void uponEOF() throws TokenStreamException, CharStreamException {
      this.hitEOF = true;
      super.uponEOF();
   }

   public boolean isEOFReached() {
      return this.hitEOF;
   }

   public AccessLogLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public AccessLogLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public AccessLogLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public AccessLogLexer(LexerSharedInputState state) {
      super(state);
      this.hitEOF = false;
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
         this.resetText();

         try {
            try {
               if (this.LA(1) != '\n' && this.LA(1) != '\r') {
                  if (_tokenSet_0.member(this.LA(1))) {
                     this.mLOGFIELD(true);
                     theRetToken = this._returnToken;
                  } else {
                     if (this.LA(1) != '\uffff') {
                        this.consume();
                        continue;
                     }

                     this.uponEOF();
                     this._returnToken = this.makeToken(1);
                  }
               } else {
                  this.mNEWLINE(true);
                  theRetToken = this._returnToken;
               }

               if (this._returnToken != null) {
                  int _ttype = this._returnToken.getType();
                  _ttype = this.testLiteralsTable(_ttype);
                  this._returnToken.setType(_ttype);
                  return this._returnToken;
               }
            } catch (RecognitionException var5) {
               if (this.getCommitToPath()) {
                  throw new TokenStreamRecognitionException(var5);
               }

               this.consume();
            }
         } catch (CharStreamException var6) {
            if (var6 instanceof CharStreamIOException) {
               throw new TokenStreamIOException(((CharStreamIOException)var6).io);
            }

            throw new TokenStreamException(var6.getMessage());
         }
      }
   }

   public final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 4;
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

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSPECIAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      switch (this.LA(1)) {
         case '"':
            this.match('"');
            break;
         case '[':
            this.match('[');
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

   protected final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
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

   protected final void mNOT_WS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 7;
      this.match(_tokenSet_0);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   public final void mLOGFIELD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
      int _saveIndex;
      switch (this.LA(1)) {
         case '"':
            _saveIndex = this.text.length();
            this.match('"');
            this.text.setLength(_saveIndex);

            while(_tokenSet_2.member(this.LA(1))) {
               this.matchNot('"');
            }

            _saveIndex = this.text.length();
            this.match('"');
            this.text.setLength(_saveIndex);
            break;
         case '[':
            _saveIndex = this.text.length();
            this.match('[');
            this.text.setLength(_saveIndex);

            while(_tokenSet_1.member(this.LA(1))) {
               this.matchNot(']');
            }

            _saveIndex = this.text.length();
            this.match(']');
            this.text.setLength(_saveIndex);
            break;
         default:
            if (!_tokenSet_3.member(this.LA(1))) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.match(_tokenSet_3);

            while(_tokenSet_0.member(this.LA(1))) {
               this.mNOT_WS(false);
            }
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[2048];
      data[0] = -4294977025L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[2048];
      data[0] = -1L;
      data[1] = -536870913L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[2048];
      data[0] = -17179869185L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[2048];
      data[0] = -21474846209L;
      data[1] = -134217729L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }
}
