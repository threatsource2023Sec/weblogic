package weblogic.xml.babel.scanner;

import java.io.IOException;

final class NotationDeclaration {
   private ScannerState state;
   private Name name;
   private DTDSpace space;
   private ExternalID externalID;
   private SystemLiteral pubidLiteral;

   NotationDeclaration(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.space = new DTDSpace(state);
      this.externalID = new ExternalID(state);
      this.pubidLiteral = new SystemLiteral(state);
   }

   public void read() throws IOException, ScannerException {
      this.space.checkedRead();
      this.state.pushToken(this.state.tokenFactory.createToken(55));
      this.name.read();
      this.space.checkedRead();
      if (!this.externalID.read()) {
         throw new ScannerException("A Notation declaration must contain a SYSTEM or PUBLIC ID.", this.state);
      } else {
         this.state.skipSpace();
         this.state.expect('>');
         this.state.pushToken(this.state.tokenFactory.createToken(39));
      }
   }
}
