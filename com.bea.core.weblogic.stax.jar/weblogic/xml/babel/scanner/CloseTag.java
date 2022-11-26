package weblogic.xml.babel.scanner;

import java.io.IOException;

final class CloseTag {
   private final ScannerState scannerState;
   private final Name name;

   CloseTag(ScannerState state) {
      this.scannerState = state;
      this.name = new Name(state);
   }

   void read() throws IOException, ScannerException {
      ScannerState state = this.scannerState;
      state.pushToken(state.tokenFactory.createToken(4));
      this.name.read();
      state.skipSpace();
      state.expect('>');
      state.pushToken(state.tokenFactory.createToken(2));
   }
}
