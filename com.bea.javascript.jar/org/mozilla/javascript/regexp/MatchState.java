package org.mozilla.javascript.regexp;

import org.mozilla.javascript.Scriptable;

class MatchState {
   boolean inputExhausted;
   boolean anchoring;
   int pcend;
   int cpbegin;
   int cpend;
   int start;
   int skipped;
   byte flags;
   int parenCount;
   SubString[] maybeParens;
   SubString[] parens;
   Scriptable scope;
   char[] input;

   public int noMoreInput() {
      this.inputExhausted = true;
      return -1;
   }
}
