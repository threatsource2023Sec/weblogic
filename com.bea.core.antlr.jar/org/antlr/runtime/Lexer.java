package org.antlr.runtime;

public abstract class Lexer extends BaseRecognizer implements TokenSource {
   protected CharStream input;

   public Lexer() {
   }

   public Lexer(CharStream input) {
      this.input = input;
   }

   public Lexer(CharStream input, RecognizerSharedState state) {
      super(state);
      this.input = input;
   }

   public void reset() {
      super.reset();
      if (this.input != null) {
         this.input.seek(0);
      }

      if (this.state != null) {
         this.state.token = null;
         this.state.type = 0;
         this.state.channel = 0;
         this.state.tokenStartCharIndex = -1;
         this.state.tokenStartCharPositionInLine = -1;
         this.state.tokenStartLine = -1;
         this.state.text = null;
      }
   }

   public Token nextToken() {
      while(true) {
         this.state.token = null;
         this.state.channel = 0;
         this.state.tokenStartCharIndex = this.input.index();
         this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
         this.state.tokenStartLine = this.input.getLine();
         this.state.text = null;
         if (this.input.LA(1) == -1) {
            return this.getEOFToken();
         }

         try {
            this.mTokens();
            if (this.state.token == null) {
               this.emit();
            } else if (this.state.token == Token.SKIP_TOKEN) {
               continue;
            }

            return this.state.token;
         } catch (MismatchedRangeException var2) {
            this.reportError(var2);
         } catch (MismatchedTokenException var3) {
            this.reportError(var3);
         } catch (RecognitionException var4) {
            this.reportError(var4);
            this.recover(var4);
         }
      }
   }

   public Token getEOFToken() {
      Token eof = new CommonToken(this.input, -1, 0, this.input.index(), this.input.index());
      eof.setLine(this.getLine());
      eof.setCharPositionInLine(this.getCharPositionInLine());
      return eof;
   }

   public void skip() {
      this.state.token = Token.SKIP_TOKEN;
   }

   public abstract void mTokens() throws RecognitionException;

   public void setCharStream(CharStream input) {
      this.input = null;
      this.reset();
      this.input = input;
   }

   public CharStream getCharStream() {
      return this.input;
   }

   public String getSourceName() {
      return this.input.getSourceName();
   }

   public void emit(Token token) {
      this.state.token = token;
   }

   public Token emit() {
      Token t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, this.getCharIndex() - 1);
      t.setLine(this.state.tokenStartLine);
      t.setText(this.state.text);
      t.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
      this.emit(t);
      return t;
   }

   public void match(String s) throws MismatchedTokenException {
      for(int i = 0; i < s.length(); this.state.failed = false) {
         if (this.input.LA(1) != s.charAt(i)) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
               return;
            }

            MismatchedTokenException mte = new MismatchedTokenException(s.charAt(i), this.input);
            this.recover(mte);
            throw mte;
         }

         ++i;
         this.input.consume();
      }

   }

   public void matchAny() {
      this.input.consume();
   }

   public void match(int c) throws MismatchedTokenException {
      if (this.input.LA(1) != c) {
         if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            MismatchedTokenException mte = new MismatchedTokenException(c, this.input);
            this.recover(mte);
            throw mte;
         }
      } else {
         this.input.consume();
         this.state.failed = false;
      }
   }

   public void matchRange(int a, int b) throws MismatchedRangeException {
      if (this.input.LA(1) >= a && this.input.LA(1) <= b) {
         this.input.consume();
         this.state.failed = false;
      } else if (this.state.backtracking > 0) {
         this.state.failed = true;
      } else {
         MismatchedRangeException mre = new MismatchedRangeException(a, b, this.input);
         this.recover(mre);
         throw mre;
      }
   }

   public int getLine() {
      return this.input.getLine();
   }

   public int getCharPositionInLine() {
      return this.input.getCharPositionInLine();
   }

   public int getCharIndex() {
      return this.input.index();
   }

   public String getText() {
      return this.state.text != null ? this.state.text : this.input.substring(this.state.tokenStartCharIndex, this.getCharIndex() - 1);
   }

   public void setText(String text) {
      this.state.text = text;
   }

   public void reportError(RecognitionException e) {
      this.displayRecognitionError(this.getTokenNames(), e);
   }

   public String getErrorMessage(RecognitionException e, String[] tokenNames) {
      String msg;
      if (e instanceof MismatchedTokenException) {
         MismatchedTokenException mte = (MismatchedTokenException)e;
         msg = "mismatched character " + this.getCharErrorDisplay(e.c) + " expecting " + this.getCharErrorDisplay(mte.expecting);
      } else if (e instanceof NoViableAltException) {
         NoViableAltException nvae = (NoViableAltException)e;
         msg = "no viable alternative at character " + this.getCharErrorDisplay(e.c);
      } else if (e instanceof EarlyExitException) {
         EarlyExitException eee = (EarlyExitException)e;
         msg = "required (...)+ loop did not match anything at character " + this.getCharErrorDisplay(e.c);
      } else if (e instanceof MismatchedNotSetException) {
         MismatchedNotSetException mse = (MismatchedNotSetException)e;
         msg = "mismatched character " + this.getCharErrorDisplay(e.c) + " expecting set " + mse.expecting;
      } else if (e instanceof MismatchedSetException) {
         MismatchedSetException mse = (MismatchedSetException)e;
         msg = "mismatched character " + this.getCharErrorDisplay(e.c) + " expecting set " + mse.expecting;
      } else if (e instanceof MismatchedRangeException) {
         MismatchedRangeException mre = (MismatchedRangeException)e;
         msg = "mismatched character " + this.getCharErrorDisplay(e.c) + " expecting set " + this.getCharErrorDisplay(mre.a) + ".." + this.getCharErrorDisplay(mre.b);
      } else {
         msg = super.getErrorMessage(e, tokenNames);
      }

      return msg;
   }

   public String getCharErrorDisplay(int c) {
      String s = String.valueOf((char)c);
      switch (c) {
         case -1:
            s = "<EOF>";
            break;
         case 9:
            s = "\\t";
            break;
         case 10:
            s = "\\n";
            break;
         case 13:
            s = "\\r";
      }

      return "'" + s + "'";
   }

   public void recover(RecognitionException re) {
      this.input.consume();
   }

   public void traceIn(String ruleName, int ruleIndex) {
      String inputSymbol = (char)this.input.LT(1) + " line=" + this.getLine() + ":" + this.getCharPositionInLine();
      super.traceIn(ruleName, ruleIndex, inputSymbol);
   }

   public void traceOut(String ruleName, int ruleIndex) {
      String inputSymbol = (char)this.input.LT(1) + " line=" + this.getLine() + ":" + this.getCharPositionInLine();
      super.traceOut(ruleName, ruleIndex, inputSymbol);
   }
}
