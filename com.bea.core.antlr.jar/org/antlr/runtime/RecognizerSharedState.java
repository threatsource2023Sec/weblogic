package org.antlr.runtime;

import java.util.Map;

public class RecognizerSharedState {
   public BitSet[] following = new BitSet[100];
   public int _fsp = -1;
   public boolean errorRecovery = false;
   public int lastErrorIndex = -1;
   public boolean failed = false;
   public int syntaxErrors = 0;
   public int backtracking = 0;
   public Map[] ruleMemo;
   public Token token;
   public int tokenStartCharIndex = -1;
   public int tokenStartLine;
   public int tokenStartCharPositionInLine;
   public int channel;
   public int type;
   public String text;

   public RecognizerSharedState() {
   }

   public RecognizerSharedState(RecognizerSharedState state) {
      if (this.following.length < state.following.length) {
         this.following = new BitSet[state.following.length];
      }

      System.arraycopy(state.following, 0, this.following, 0, state.following.length);
      this._fsp = state._fsp;
      this.errorRecovery = state.errorRecovery;
      this.lastErrorIndex = state.lastErrorIndex;
      this.failed = state.failed;
      this.syntaxErrors = state.syntaxErrors;
      this.backtracking = state.backtracking;
      if (state.ruleMemo != null) {
         this.ruleMemo = (Map[])(new Map[state.ruleMemo.length]);
         System.arraycopy(state.ruleMemo, 0, this.ruleMemo, 0, state.ruleMemo.length);
      }

      this.token = state.token;
      this.tokenStartCharIndex = state.tokenStartCharIndex;
      this.tokenStartCharPositionInLine = state.tokenStartCharPositionInLine;
      this.channel = state.channel;
      this.type = state.type;
      this.text = state.text;
   }
}
