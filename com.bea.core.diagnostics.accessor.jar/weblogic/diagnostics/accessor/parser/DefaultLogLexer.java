package weblogic.diagnostics.accessor.parser;

import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.CharScanner;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.InputBuffer;
import antlr.LexerSharedInputState;
import antlr.NoViableAltForCharException;
import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;

public class DefaultLogLexer extends CharScanner implements DefaultLogLexerTokenTypes, TokenStream {
   private static final boolean DEBUG = false;
   private boolean hitEOF;

   public void uponEOF() throws TokenStreamException, CharStreamException {
      this.hitEOF = true;
      super.uponEOF();
   }

   public boolean isEOFReached() {
      return this.hitEOF;
   }

   public DefaultLogLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public DefaultLogLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public DefaultLogLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public DefaultLogLexer(LexerSharedInputState state) {
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
               if (this.LA(1) == '#' && this.getColumn() == 1) {
                  this.mBEGIN_LOG_RECORD(true);
                  theRetToken = this._returnToken;
               } else if (this.LA(1) == '<') {
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

   public final void mBEGIN_LOG_RECORD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 4;
      if (this.getColumn() != 1) {
         throw new SemanticException("getColumn()==1");
      } else {
         this.match("####");
         if (_createToken && _token == null && _ttype != -1) {
            _token = this.makeToken(_ttype);
            _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
         }

         this._returnToken = _token;
      }
   }

   public final void mLOGFIELD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      int _saveIndex = this.text.length();
      this.mSTART_DELIMITER(false);
      this.text.setLength(_saveIndex);

      while(this.LA(1) != '>' || this.LA(2) != ' ') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 0 && this.LA(2) <= '\ufffe' && this.LA(3) >= 0 && this.LA(3) <= '\ufffe') {
            this.mNEWLINE(false);
         } else if (this.LA(1) == '&' && this.LA(2) == 'l' && this.LA(3) == 't') {
            this.mESCAPED_START(false);
         } else if (this.LA(1) == '&' && this.LA(2) == 'g' && this.LA(3) == 't') {
            this.mESCAPED_END(false);
         } else {
            if (this.LA(1) < 0 || this.LA(1) > '\ufffe' || this.LA(2) < 0 || this.LA(2) > '\ufffe' || this.LA(3) < 0 || this.LA(3) > '\ufffe') {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      _saveIndex = this.text.length();
      this.mEND_DELIMITER(false);
      this.text.setLength(_saveIndex);
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSTART_DELIMITER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 9;
      this.match("<");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      switch (this.LA(1)) {
         case '\n':
            this.match('\n');
            this.newline();
            break;
         case '\r':
            this.match('\r');
            this.match('\n');
            this.newline();
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

   protected final void mESCAPED_START(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 7;
      this.match("&lt;");
      this.text.setLength(_begin);
      this.text.append("<");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mESCAPED_END(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 8;
      this.match("&gt;");
      this.text.setLength(_begin);
      this.text.append(">");
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mEND_DELIMITER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 10;
      this.match("> ");
      if (this.LA(1) == '\n' || this.LA(1) == '\r') {
         this.mNEWLINE(false);
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }
}
