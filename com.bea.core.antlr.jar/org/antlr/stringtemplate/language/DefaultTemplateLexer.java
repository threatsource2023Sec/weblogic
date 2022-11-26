package org.antlr.stringtemplate.language;

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
import antlr.collections.impl.BitSet;
import java.io.InputStream;
import java.io.Reader;
import java.util.Hashtable;
import org.antlr.stringtemplate.StringTemplate;

public class DefaultTemplateLexer extends CharScanner implements TemplateParserTokenTypes, TokenStream {
   protected String currentIndent;
   protected StringTemplate self;
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
   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());

   public DefaultTemplateLexer(StringTemplate self, Reader r) {
      this(r);
      this.self = self;
   }

   public void reportError(RecognitionException e) {
      this.self.error("$...$ chunk lexer error", e);
   }

   protected boolean upcomingELSE(int i) throws CharStreamException {
      return this.LA(i) == '$' && this.LA(i + 1) == 'e' && this.LA(i + 2) == 'l' && this.LA(i + 3) == 's' && this.LA(i + 4) == 'e' && this.LA(i + 5) == '$';
   }

   protected boolean upcomingENDIF(int i) throws CharStreamException {
      return this.LA(i) == '$' && this.LA(i + 1) == 'e' && this.LA(i + 2) == 'n' && this.LA(i + 3) == 'd' && this.LA(i + 4) == 'i' && this.LA(i + 5) == 'f' && this.LA(i + 6) == '$';
   }

   protected boolean upcomingAtEND(int i) throws CharStreamException {
      return this.LA(i) == '$' && this.LA(i + 1) == '@' && this.LA(i + 2) == 'e' && this.LA(i + 3) == 'n' && this.LA(i + 4) == 'd' && this.LA(i + 5) == '$';
   }

   protected boolean upcomingNewline(int i) throws CharStreamException {
      return this.LA(i) == '\r' && this.LA(i + 1) == '\n' || this.LA(i) == '\n';
   }

   public DefaultTemplateLexer(InputStream in) {
      this((InputBuffer)(new ByteBuffer(in)));
   }

   public DefaultTemplateLexer(Reader in) {
      this((InputBuffer)(new CharBuffer(in)));
   }

   public DefaultTemplateLexer(InputBuffer ib) {
      this(new LexerSharedInputState(ib));
   }

   public DefaultTemplateLexer(LexerSharedInputState state) {
      super(state);
      this.currentIndent = null;
      this.caseSensitiveLiterals = true;
      this.setCaseSensitive(true);
      this.literals = new Hashtable();
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
                  case '\n':
                  case '\r':
                     this.mNEWLINE(true);
                     theRetToken = this._returnToken;
                     break;
                  case '$':
                     this.mACTION(true);
                     theRetToken = this._returnToken;
                     break;
                  default:
                     if (_tokenSet_0.member(this.LA(1)) && this.LA(1) != '\r' && this.LA(1) != '\n') {
                        this.mLITERAL(true);
                        theRetToken = this._returnToken;
                     } else {
                        if (this.LA(1) != '\uffff') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.uponEOF();
                        this._returnToken = this.makeToken(1);
                     }
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

   public final void mLITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 4;
      Token ind = null;
      if (this.LA(1) != '\r' && this.LA(1) != '\n') {
         int _cnt11 = 0;

         while(true) {
            int loopStartIndex = this.text.length();
            int col = this.getColumn();
            int _saveIndex;
            if (this.LA(1) == '\\' && this.LA(2) == '$') {
               _saveIndex = this.text.length();
               this.match('\\');
               this.text.setLength(_saveIndex);
               this.match('$');
            } else if (this.LA(1) == '\\' && this.LA(2) == '\\') {
               _saveIndex = this.text.length();
               this.match('\\');
               this.text.setLength(_saveIndex);
               this.match('\\');
            } else if (this.LA(1) == '\\' && _tokenSet_1.member(this.LA(2))) {
               this.match('\\');
               this.matchNot('$');
            } else if (this.LA(1) != '\t' && this.LA(1) != ' ') {
               if (!_tokenSet_0.member(this.LA(1))) {
                  if (_cnt11 >= 1) {
                     if ((new String(this.text.getBuffer(), _begin, this.text.length() - _begin)).length() == 0) {
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

               this.match(_tokenSet_0);
            } else {
               this.mINDENT(true);
               ind = this._returnToken;
               if (col == 1 && this.LA(1) == '$') {
                  this.currentIndent = ind.getText();
                  this.text.setLength(loopStartIndex);
               } else {
                  this.currentIndent = null;
               }
            }

            ++_cnt11;
         }
      } else {
         throw new SemanticException("LA(1)!='\\r'&&LA(1)!='\\n'");
      }
   }

   protected final void mINDENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 21;
      int _cnt76 = 0;

      while(true) {
         if (this.LA(1) == ' ') {
            this.match(' ');
         } else {
            if (this.LA(1) != '\t') {
               if (_cnt76 >= 1) {
                  if (_createToken && _token == null && _ttype != -1) {
                     _token = this.makeToken(_ttype);
                     _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                  }

                  this._returnToken = _token;
                  return;
               }

               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.match('\t');
         }

         ++_cnt76;
      }
   }

   public final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 5;
      switch (this.LA(1)) {
         case '\r':
            this.match('\r');
         case '\n':
            this.match('\n');
            this.newline();
            this.currentIndent = null;
            if (_createToken && _token == null && _ttype != -1) {
               _token = this.makeToken(_ttype);
               _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
            }

            this._returnToken = _token;
            return;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   public final void mACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 6;
      int startCol = this.getColumn();
      if (this.LA(1) == '$' && this.LA(2) == '\\' && this.LA(3) == '\\' && this.LA(4) == '$' && _tokenSet_2.member(this.LA(5))) {
         this.mLINE_BREAK(false);
         _ttype = -1;
      } else {
         int _saveIndex;
         boolean atLeft;
         int _cnt39;
         if (this.LA(1) == '$' && this.LA(2) == '\\' && _tokenSet_3.member(this.LA(3)) && _tokenSet_4.member(this.LA(4))) {
            StringBuffer buf = new StringBuffer();
            atLeft = false;
            _saveIndex = this.text.length();
            this.match('$');
            this.text.setLength(_saveIndex);

            for(_cnt39 = 0; this.LA(1) == '\\'; ++_cnt39) {
               char uc = this.mESC_CHAR(false);
               buf.append(uc);
            }

            if (_cnt39 < 1) {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            _saveIndex = this.text.length();
            this.match('$');
            this.text.setLength(_saveIndex);
            this.text.setLength(_begin);
            this.text.append(buf.toString());
            _ttype = 4;
         } else if (this.LA(1) == '$' && this.LA(2) == '!' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe' && this.LA(4) >= 1 && this.LA(4) <= '\ufffe') {
            this.mCOMMENT(false);
            _ttype = -1;
         } else {
            if (this.LA(1) != '$' || !_tokenSet_1.member(this.LA(2)) || this.LA(3) < 1 || this.LA(3) > '\ufffe') {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            if (this.LA(1) == '$' && this.LA(2) == 'i' && this.LA(3) == 'f' && (this.LA(4) == ' ' || this.LA(4) == '(') && _tokenSet_5.member(this.LA(5)) && this.LA(6) >= 1 && this.LA(6) <= '\ufffe' && this.LA(7) >= 1 && this.LA(7) <= '\ufffe') {
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               this.match("if");

               while(this.LA(1) == ' ') {
                  _saveIndex = this.text.length();
                  this.match(' ');
                  this.text.setLength(_saveIndex);
               }

               this.match("(");
               this.mIF_EXPR(false);
               this.match(")");
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               _ttype = 7;
               if (this.LA(1) == '\n' || this.LA(1) == '\r') {
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
               }
            } else if (this.LA(1) == '$' && this.LA(2) == 'e' && this.LA(3) == 'l' && this.LA(4) == 's' && this.LA(5) == 'e' && this.LA(6) == 'i' && this.LA(7) == 'f') {
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               this.match("elseif");

               while(this.LA(1) == ' ') {
                  _saveIndex = this.text.length();
                  this.match(' ');
                  this.text.setLength(_saveIndex);
               }

               this.match("(");
               this.mIF_EXPR(false);
               this.match(")");
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               _ttype = 8;
               if (this.LA(1) == '\n' || this.LA(1) == '\r') {
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
               }
            } else if (this.LA(1) == '$' && this.LA(2) == 'e' && this.LA(3) == 'n' && this.LA(4) == 'd' && this.LA(5) == 'i' && this.LA(6) == 'f' && this.LA(7) == '$') {
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               this.match("endif");
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               _ttype = 10;
               if ((this.LA(1) == '\n' || this.LA(1) == '\r') && startCol == 1) {
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
               }
            } else if (this.LA(1) == '$' && this.LA(2) == 'e' && this.LA(3) == 'l' && this.LA(4) == 's' && this.LA(5) == 'e' && this.LA(6) == '$') {
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               this.match("else");
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               _ttype = 9;
               if (this.LA(1) == '\n' || this.LA(1) == '\r') {
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
               }
            } else if (this.LA(1) == '$' && this.LA(2) == '@' && _tokenSet_6.member(this.LA(3)) && this.LA(4) >= 1 && this.LA(4) <= '\ufffe' && this.LA(5) >= 1 && this.LA(5) <= '\ufffe' && this.LA(6) >= 1 && this.LA(6) <= '\ufffe') {
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               _saveIndex = this.text.length();
               this.match('@');
               this.text.setLength(_saveIndex);

               int _cnt32;
               for(_cnt32 = 0; _tokenSet_6.member(this.LA(1)); ++_cnt32) {
                  this.match(_tokenSet_6);
               }

               if (_cnt32 < 1) {
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               label332:
               switch (this.LA(1)) {
                  case '$':
                     _saveIndex = this.text.length();
                     this.match('$');
                     this.text.setLength(_saveIndex);
                     _ttype = 12;
                     String t = new String(this.text.getBuffer(), _begin, this.text.length() - _begin);
                     this.text.setLength(_begin);
                     this.text.append(t + "::=");
                     if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 1 && this.LA(2) <= '\ufffe' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe') {
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
                     } else if (this.LA(1) < 1 || this.LA(1) > '\ufffe' || this.LA(2) < 1 || this.LA(2) > '\ufffe') {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }

                     atLeft = false;

                     for(_cnt39 = 0; this.LA(1) >= 1 && this.LA(1) <= '\ufffe' && this.LA(2) >= 1 && this.LA(2) <= '\ufffe' && !this.upcomingAtEND(1) && (!this.upcomingNewline(1) || !this.upcomingAtEND(2)); ++_cnt39) {
                        if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 1 && this.LA(2) <= '\ufffe') {
                           switch (this.LA(1)) {
                              case '\r':
                                 this.match('\r');
                              case '\n':
                                 this.match('\n');
                                 this.newline();
                                 atLeft = true;
                                 break;
                              default:
                                 throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                           }
                        } else {
                           if (this.LA(1) < 1 || this.LA(1) > '\ufffe' || this.LA(2) < 1 || this.LA(2) > '\ufffe') {
                              throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                           }

                           this.matchNot('\uffff');
                           atLeft = false;
                        }
                     }

                     if (_cnt39 < 1) {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }

                     if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 1 && this.LA(2) <= '\ufffe') {
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
                              atLeft = true;
                              break;
                           default:
                              throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }
                     } else if (this.LA(1) < 1 || this.LA(1) > '\ufffe') {
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                     }

                     if (this.LA(1) == '$' && this.LA(2) == '@') {
                        _saveIndex = this.text.length();
                        this.match("$@end$");
                        this.text.setLength(_saveIndex);
                     } else {
                        if (this.LA(1) < 1 || this.LA(1) > '\ufffe') {
                           throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }

                        this.matchNot('\uffff');
                        this.self.error("missing region " + t + " $@end$ tag");
                     }

                     if ((this.LA(1) == '\n' || this.LA(1) == '\r') && atLeft) {
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
                              break label332;
                           default:
                              throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                        }
                     }
                     break;
                  case '(':
                     _saveIndex = this.text.length();
                     this.match("()");
                     this.text.setLength(_saveIndex);
                     _saveIndex = this.text.length();
                     this.match('$');
                     this.text.setLength(_saveIndex);
                     _ttype = 11;
                     break;
                  default:
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }
            } else {
               if (this.LA(1) != '$' || !_tokenSet_1.member(this.LA(2)) || this.LA(3) < 1 || this.LA(3) > '\ufffe') {
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
               this.mEXPR(false);
               _saveIndex = this.text.length();
               this.match('$');
               this.text.setLength(_saveIndex);
            }

            ChunkToken t = new ChunkToken(_ttype, new String(this.text.getBuffer(), _begin, this.text.length() - _begin), this.currentIndent);
            _token = t;
         }
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         ((Token)_token).setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = (Token)_token;
   }

   protected final void mLINE_BREAK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 23;
      this.match("$\\\\$");
      switch (this.LA(1)) {
         case '\t':
         case ' ':
            this.mINDENT(false);
         case '\n':
         case '\r':
            switch (this.LA(1)) {
               case '\n':
                  break;
               case '\r':
                  this.match('\r');
                  break;
               default:
                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            this.match('\n');
            this.newline();
            if (this.LA(1) == '\t' || this.LA(1) == ' ') {
               this.mINDENT(false);
            }

            if (_createToken && _token == null && _ttype != -1) {
               _token = this.makeToken(_ttype);
               _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
            }

            this._returnToken = _token;
            return;
         default:
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
      }
   }

   protected final char mESC_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      char uc = false;
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 16;
      Token a = null;
      Token b = null;
      Token c = null;
      Token d = null;
      int _saveIndex;
      char uc;
      if (this.LA(1) == '\\' && this.LA(2) == 'n') {
         _saveIndex = this.text.length();
         this.match("\\n");
         this.text.setLength(_saveIndex);
         uc = '\n';
      } else if (this.LA(1) == '\\' && this.LA(2) == 'r') {
         _saveIndex = this.text.length();
         this.match("\\r");
         this.text.setLength(_saveIndex);
         uc = '\r';
      } else if (this.LA(1) == '\\' && this.LA(2) == 't') {
         _saveIndex = this.text.length();
         this.match("\\t");
         this.text.setLength(_saveIndex);
         uc = '\t';
      } else if (this.LA(1) == '\\' && this.LA(2) == ' ') {
         _saveIndex = this.text.length();
         this.match("\\ ");
         this.text.setLength(_saveIndex);
         uc = ' ';
      } else {
         if (this.LA(1) != '\\' || this.LA(2) != 'u') {
            throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
         }

         _saveIndex = this.text.length();
         this.match("\\u");
         this.text.setLength(_saveIndex);
         _saveIndex = this.text.length();
         this.mHEX(true);
         this.text.setLength(_saveIndex);
         a = this._returnToken;
         _saveIndex = this.text.length();
         this.mHEX(true);
         this.text.setLength(_saveIndex);
         b = this._returnToken;
         _saveIndex = this.text.length();
         this.mHEX(true);
         this.text.setLength(_saveIndex);
         c = this._returnToken;
         _saveIndex = this.text.length();
         this.mHEX(true);
         this.text.setLength(_saveIndex);
         d = this._returnToken;
         uc = (char)Integer.parseInt(a.getText() + b.getText() + c.getText() + d.getText(), 16);
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
      return uc;
   }

   protected final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 22;
      int startCol = this.getColumn();
      this.match("$!");

      while(this.LA(1) != '!' || this.LA(2) != '$') {
         if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 1 && this.LA(2) <= '\ufffe' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe') {
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
            if (this.LA(1) < 1 || this.LA(1) > '\ufffe' || this.LA(2) < 1 || this.LA(2) > '\ufffe' || this.LA(3) < 1 || this.LA(3) > '\ufffe') {
               break;
            }

            this.matchNot('\uffff');
         }
      }

      this.match("!$");
      if ((this.LA(1) == '\n' || this.LA(1) == '\r') && startCol == 1) {
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
      }

      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mIF_EXPR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 15;
      int _cnt64 = 0;

      while(true) {
         label34:
         switch (this.LA(1)) {
            case '\n':
            case '\r':
               switch (this.LA(1)) {
                  case '\r':
                     this.match('\r');
                  case '\n':
                     this.match('\n');
                     this.newline();
                     break label34;
                  default:
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }
            case '(':
               this.mNESTED_PARENS(false);
               break;
            case '\\':
               this.mESC(false);
               break;
            case '{':
               this.mSUBTEMPLATE(false);
               break;
            default:
               if (!_tokenSet_7.member(this.LA(1))) {
                  if (_cnt64 >= 1) {
                     if (_createToken && _token == null && _ttype != -1) {
                        _token = this.makeToken(_ttype);
                        _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                     }

                     this._returnToken = _token;
                     return;
                  }

                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.matchNot(')');
         }

         ++_cnt64;
      }
   }

   protected final void mEXPR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 13;
      int _cnt52 = 0;

      while(true) {
         label101: {
            switch (this.LA(1)) {
               case '\n':
               case '\r':
                  switch (this.LA(1)) {
                     case '\r':
                        this.match('\r');
                     case '\n':
                        this.match('\n');
                        this.newline();
                        break label101;
                     default:
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }
               case '\\':
                  this.mESC(false);
                  break label101;
               case '{':
                  this.mSUBTEMPLATE(false);
                  break label101;
            }

            if (this.LA(1) != '+' && this.LA(1) != '=' || this.LA(2) != '"' && this.LA(2) != '<') {
               if ((this.LA(1) == '+' || this.LA(1) == '=') && this.LA(2) == '{') {
                  switch (this.LA(1)) {
                     case '+':
                        this.match('+');
                        break;
                     case '=':
                        this.match('=');
                        break;
                     default:
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }

                  this.mSUBTEMPLATE(false);
               } else if ((this.LA(1) == '+' || this.LA(1) == '=') && _tokenSet_8.member(this.LA(2))) {
                  switch (this.LA(1)) {
                     case '+':
                        this.match('+');
                        break;
                     case '=':
                        this.match('=');
                        break;
                     default:
                        throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }

                  this.match(_tokenSet_8);
               } else {
                  if (!_tokenSet_9.member(this.LA(1))) {
                     if (_cnt52 >= 1) {
                        if (_createToken && _token == null && _ttype != -1) {
                           _token = this.makeToken(_ttype);
                           _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                        }

                        this._returnToken = _token;
                        return;
                     }

                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
                  }

                  this.matchNot('$');
               }
            } else {
               switch (this.LA(1)) {
                  case '+':
                     this.match('+');
                     break;
                  case '=':
                     this.match('=');
                     break;
                  default:
                     throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.mTEMPLATE(false);
            }
         }

         ++_cnt52;
      }
   }

   protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 17;
      this.match('\\');
      this.matchNot('\uffff');
      if (_createToken && _token == null && _ttype != -1) {
         _token = this.makeToken(_ttype);
         _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
      }

      this._returnToken = _token;
   }

   protected final void mSUBTEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 19;
      this.match('{');

      while(true) {
         while(true) {
            switch (this.LA(1)) {
               case '\\':
                  this.mESC(false);
                  break;
               case '{':
                  this.mSUBTEMPLATE(false);
                  break;
               default:
                  if (!_tokenSet_10.member(this.LA(1))) {
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
      }
   }

   protected final void mTEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      byte _ttype;
      Token _token;
      int _begin;
      _token = null;
      _begin = this.text.length();
      _ttype = 14;
      label105:
      switch (this.LA(1)) {
         case '"':
            this.match('"');

            while(true) {
               while(this.LA(1) != '\\') {
                  if (!_tokenSet_11.member(this.LA(1))) {
                     this.match('"');
                     break label105;
                  }

                  this.matchNot('"');
               }

               this.mESC(false);
            }
         case '<':
            this.match("<<");
            int _saveIndex;
            if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 1 && this.LA(2) <= '\ufffe' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe' && this.LA(4) >= 1 && this.LA(4) <= '\ufffe') {
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
            } else if (this.LA(1) < 1 || this.LA(1) > '\ufffe' || this.LA(2) < 1 || this.LA(2) > '\ufffe' || this.LA(3) < 1 || this.LA(3) > '\ufffe') {
               throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
            }

            while(this.LA(1) != '>' || this.LA(2) != '>' || this.LA(3) < 1 || this.LA(3) > '\ufffe') {
               if (this.LA(1) == '\r' && this.LA(2) == '\n' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe' && this.LA(4) >= 1 && this.LA(4) <= '\ufffe' && this.LA(5) >= 1 && this.LA(5) <= '\ufffe' && this.LA(3) == '>' && this.LA(4) == '>') {
                  _saveIndex = this.text.length();
                  this.match('\r');
                  this.text.setLength(_saveIndex);
                  _saveIndex = this.text.length();
                  this.match('\n');
                  this.text.setLength(_saveIndex);
                  this.newline();
               } else if (this.LA(1) == '\n' && this.LA(2) >= 1 && this.LA(2) <= '\ufffe' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe' && this.LA(4) >= 1 && this.LA(4) <= '\ufffe' && this.LA(2) == '>' && this.LA(3) == '>') {
                  _saveIndex = this.text.length();
                  this.match('\n');
                  this.text.setLength(_saveIndex);
                  this.newline();
               } else if ((this.LA(1) == '\n' || this.LA(1) == '\r') && this.LA(2) >= 1 && this.LA(2) <= '\ufffe' && this.LA(3) >= 1 && this.LA(3) <= '\ufffe' && this.LA(4) >= 1 && this.LA(4) <= '\ufffe') {
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
                  if (this.LA(1) < 1 || this.LA(1) > '\ufffe' || this.LA(2) < 1 || this.LA(2) > '\ufffe' || this.LA(3) < 1 || this.LA(3) > '\ufffe' || this.LA(4) < 1 || this.LA(4) > '\ufffe') {
                     break;
                  }

                  this.matchNot('\uffff');
               }
            }

            this.match(">>");
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

   protected final void mNESTED_PARENS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 20;
      this.match('(');
      int _cnt73 = 0;

      while(true) {
         switch (this.LA(1)) {
            case '(':
               this.mNESTED_PARENS(false);
               break;
            case '\\':
               this.mESC(false);
               break;
            default:
               if (!_tokenSet_12.member(this.LA(1))) {
                  if (_cnt73 >= 1) {
                     this.match(')');
                     if (_createToken && _token == null && _ttype != -1) {
                        _token = this.makeToken(_ttype);
                        _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
                     }

                     this._returnToken = _token;
                     return;
                  }

                  throw new NoViableAltForCharException(this.LA(1), this.getFilename(), this.getLine(), this.getColumn());
               }

               this.matchNot(')');
         }

         ++_cnt73;
      }
   }

   protected final void mHEX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
      Token _token = null;
      int _begin = this.text.length();
      int _ttype = 18;
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

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[2048];
      data[0] = -68719485954L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[2048];
      data[0] = -68719476738L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[1025];
      data[0] = 4294977024L;
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[1025];
      data[0] = 4294967296L;
      data[1] = 14707067533131776L;
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[1025];
      data[0] = 287948969894477824L;
      data[1] = 541434314878L;
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[2048];
      data[0] = -2199023255554L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_6() {
      long[] data = new long[2048];
      data[0] = -1168231104514L;

      for(int i = 1; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_7() {
      long[] data = new long[2048];
      data[0] = -3298534892546L;
      data[1] = -576460752571858945L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_8() {
      long[] data = new long[2048];
      data[0] = -1152921521786716162L;
      data[1] = -576460752303423489L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_9() {
      long[] data = new long[2048];
      data[0] = -2305851874026202114L;
      data[1] = -576460752571858945L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_10() {
      long[] data = new long[2048];
      data[0] = -2L;
      data[1] = -2882303761785552897L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_11() {
      long[] data = new long[2048];
      data[0] = -17179869186L;
      data[1] = -268435457L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }

   private static final long[] mk_tokenSet_12() {
      long[] data = new long[2048];
      data[0] = -3298534883330L;
      data[1] = -268435457L;

      for(int i = 2; i <= 1022; ++i) {
         data[i] = -1L;
      }

      data[1023] = Long.MAX_VALUE;
      return data;
   }
}
