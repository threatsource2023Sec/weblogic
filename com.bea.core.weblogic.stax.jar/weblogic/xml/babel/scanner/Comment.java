package weblogic.xml.babel.scanner;

import java.io.IOException;

final class Comment {
   private ScannerState state;
   private boolean skip;

   Comment(ScannerState state) {
      this.state = state;
      this.skip = true;
   }

   Comment(ScannerState state, boolean skip) {
      this.state = state;
      this.skip = skip;
   }

   private void ignoreData() throws IOException, ScannerException {
      while(true) {
         if (this.state.currentChar == '-') {
            this.state.checkedRead();
            if (this.state.currentChar == '-') {
               this.state.checkedRead();
               if (this.state.currentChar == '>') {
                  this.state.read();
                  this.state.pushToken(this.state.tokenFactory.createToken(7));
                  return;
               }

               throw new ScannerException(" Comments may not contain '--'", this.state);
            }
         }

         this.state.checkedRead();
      }
   }

   private void bufferData() throws IOException, ScannerException {
      int i = 0;
      this.state.mark();

      while(true) {
         if (this.state.currentChar == '-') {
            this.state.checkedRead();
            if (this.state.currentChar == '-') {
               this.state.checkedRead();
               if (this.state.currentChar == '>') {
                  this.state.read();
                  this.state.pushToken(this.state.createToken(7, i));
                  return;
               }

               throw new ScannerException("Comments may not contain '--'", this.state);
            }

            ++i;
         }

         this.state.checkedRead();
         ++i;
      }
   }

   void read() throws IOException, ScannerException {
      if (this.skip) {
         this.ignoreData();
      } else {
         this.bufferData();
      }

   }
}
