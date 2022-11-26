package weblogic.xml.babel.scanner;

import java.io.IOException;

final class SystemLiteral {
   private ScannerState state;
   private CharData singleQuoteData;
   private CharData doubleQuoteData;

   SystemLiteral(ScannerState state) {
      this.state = state;
      this.singleQuoteData = new CharData(state, "'");
      this.doubleQuoteData = new CharData(state, "\"");
   }

   void read() throws IOException, ScannerException {
      if (this.state.currentChar == '"') {
         this.state.read();
         this.doubleQuoteData.read();
      } else {
         this.state.read();
         this.singleQuoteData.read();
      }

      this.state.read();
   }
}
