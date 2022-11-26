package weblogic.xml.babel.scanner;

import java.io.IOException;
import java.util.Arrays;

final class CharData {
   private final ScannerState scannerState;
   private final boolean[] terminateCharacters;

   CharData(ScannerState state) {
      this(state, "<&");
   }

   CharData(ScannerState state, String terminateCharacters) {
      this.terminateCharacters = new boolean[256];
      Arrays.fill(this.terminateCharacters, false);
      this.scannerState = state;
      char[] initChar = terminateCharacters.toCharArray();

      for(int i = 0; i < initChar.length; ++i) {
         this.terminateCharacters[initChar[i]] = true;
      }

   }

   private boolean isTerminateChar(char i) {
      return i > 0 && i < this.terminateCharacters.length && this.terminateCharacters[i];
   }

   boolean read() throws IOException, ScannerException {
      int i = 0;
      ScannerState state = this.scannerState;
      state.mark();

      while(!this.isTerminateChar(state.currentChar) && !ScannerState.checkSize(i) && !state.hasReachedEOF()) {
         state.checkedRead();
         ++i;
      }

      if (i > 0) {
         state.pushToken(state.createToken(13, i));
         return true;
      } else {
         state.unMark();
         return false;
      }
   }
}
