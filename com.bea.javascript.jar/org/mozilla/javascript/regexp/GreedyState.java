package org.mozilla.javascript.regexp;

class GreedyState {
   MatchState state;
   RENode kid;
   RENode next;
   RENode stop;
   int kidCount;
   int maxKid;
}
