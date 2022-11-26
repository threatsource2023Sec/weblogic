package weblogic.xml.babel.scanner;

import java.io.IOException;

final class DTDSpace {
   private Space space;
   private ScannerState state;
   private PEReference perReference;

   DTDSpace(ScannerState state) {
      this.state = state;
      this.space = new Space(state);
      this.perReference = new PEReference(state);
   }

   void read() throws IOException, ScannerException {
      this.space.read();
   }

   void checkedRead() throws IOException, ScannerException {
      this.read();
      if (this.state.currentChar == '%') {
         while(true) {
            if (this.state.currentChar != '%') {
               this.read();
               break;
            }

            this.perReference.read();
         }
      }

   }
}
