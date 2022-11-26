package weblogic.xml.babel.scanner;

import java.io.IOException;

final class CData {
   private ScannerState state;
   private Space space;

   CData(ScannerState state) {
      this.state = state;
      this.space = new Space(state);
   }

   void read() throws IOException, ScannerException {
      this.read(true);
   }

   void read(boolean start) throws IOException, ScannerException {
      int i = 0;
      this.state.mark();
      if (start) {
         this.state.pushToken(this.state.tokenFactory.createToken(10));
      }

      while(true) {
         if (this.state.currentChar == ']') {
            this.state.read();
            if (this.state.currentChar != ']') {
               ++i;
            } else {
               this.state.read();

               while(this.state.currentChar == ']') {
                  this.state.read();
                  ++i;
               }

               if (this.state.currentChar == '>') {
                  this.state.read();
                  if (i > 0) {
                     this.state.pushToken(this.state.createToken(11, i));
                  }

                  this.state.pushToken(this.state.tokenFactory.createToken(12));
                  return;
               }

               i += 2;
            }
         }

         ScannerState var10000 = this.state;
         if (ScannerState.isEOL(this.state.currentChar)) {
            this.state.pushToken(this.state.createToken(11, i));
            this.space.read();
            this.state.setState(11);
            return;
         }

         var10000 = this.state;
         if (ScannerState.checkSize(i)) {
            this.state.pushToken(this.state.createToken(11, i));
            this.state.setState(11);
            return;
         }

         this.state.checkedRead();
         ++i;
      }
   }

   public void clear() {
      this.space.init();
   }
}
