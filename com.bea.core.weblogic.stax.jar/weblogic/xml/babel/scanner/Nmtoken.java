package weblogic.xml.babel.scanner;

import java.io.IOException;
import weblogic.xml.babel.reader.XmlChars;

final class Nmtoken {
   private ScannerState state;

   Nmtoken(ScannerState state) {
      this.state = state;
   }

   void read() throws IOException, ScannerException {
      int i = 0;
      if (!XmlChars.isNameChar(this.state.currentChar)) {
         throw new ScannerException(" '" + this.state.currentChar + "' expected a valid beginning name character", this.state);
      } else {
         this.state.mark();
         this.state.read();
         ++i;

         while(XmlChars.isNameChar(this.state.currentChar)) {
            this.state.read();
            ++i;
         }

         this.state.pushToken(this.state.tokenFactory.createToken(46, this.state.getString(i)));
      }
   }
}
