package weblogic.xml.babel.scanner;

import java.io.IOException;
import weblogic.xml.babel.reader.XmlChars;

final class ProcessingInstruction {
   private ScannerState state;
   private Name name;
   private Space space;

   ProcessingInstruction(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.space = new Space(state);
   }

   void read() throws IOException, ScannerException {
      this.state.pushToken(this.state.tokenFactory.createToken(8));
      this.name.read();
      this.state.skipSpace();
      int i = 0;
      this.state.mark();

      while(true) {
         while(this.state.currentChar != '?') {
            if (XmlChars.isSpace(this.state.currentChar)) {
               this.state.pushToken(this.state.tokenFactory.createToken(17, this.state.getString(i)));
               this.space.read();
               i = 0;
               this.state.mark();
            } else {
               this.state.checkedRead();
               ++i;
            }
         }

         this.state.read();
         ++i;

         while(this.state.currentChar == '?') {
            this.state.read();
            ++i;
         }

         if (this.state.currentChar == '>') {
            --i;
            this.state.read();
            if (i > 0) {
               this.state.pushToken(this.state.tokenFactory.createToken(17, this.state.getString(i)));
            }

            this.state.pushToken(this.state.tokenFactory.createToken(9));
            return;
         }

         this.state.read();
         ++i;
      }
   }

   public void clear() {
      this.space.init();
   }
}
