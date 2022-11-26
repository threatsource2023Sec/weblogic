package org.apache.oro.text.awk;

import java.util.BitSet;

final class DFAState {
   int _stateNumber;
   BitSet _state;

   DFAState(BitSet var1, int var2) {
      this._state = var1;
      this._stateNumber = var2;
   }
}
