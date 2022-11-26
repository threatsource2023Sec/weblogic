package weblogic.xml.babel.scanner;

import java.io.IOException;

final class ExternalID {
   private ScannerState state;
   private SystemLiteral systemLiteral;
   private SystemLiteral pubidLiteral;
   private Space space;

   ExternalID(ScannerState state) {
      this.state = state;
      this.systemLiteral = new SystemLiteral(state);
      this.pubidLiteral = new SystemLiteral(state);
      this.space = new Space(state);
   }

   public boolean read() throws IOException, ScannerException {
      switch (this.state.currentChar) {
         case 'P':
            this.state.expect("PUBLIC");
            this.state.pushToken(this.state.tokenFactory.createToken(26));
            this.space.read();
            this.pubidLiteral.read();
            this.space.read();
            if (this.state.currentChar == '"' || this.state.currentChar == '\'') {
               this.systemLiteral.read();
            }
            break;
         case 'S':
            this.state.expect("SYSTEM");
            this.state.pushToken(this.state.tokenFactory.createToken(25));
            this.space.read();
            this.systemLiteral.read();
            break;
         default:
            return false;
      }

      return true;
   }
}
