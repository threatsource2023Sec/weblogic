package weblogic.xml.babel.scanner;

import java.io.IOException;

final class OpenTag {
   private ScannerState state;
   private Name name;
   private AttributeValue attributeValue;

   OpenTag(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.attributeValue = new AttributeValue(state);
   }

   void read() throws IOException, ScannerException {
      this.state.pushToken(this.state.tokenFactory.createToken(1));
      this.name.read();
      Token prefix = this.name.getPrefix();
      Token localName = this.name.getLocalName();

      while(true) {
         this.state.skipSpace();
         if (this.state.currentChar == '>') {
            this.state.read();
            this.state.pushToken(this.state.tokenFactory.createToken(2));
            return;
         }

         if (this.state.currentChar == '/') {
            this.state.read();
            this.state.expect('>');
            this.state.pushToken(this.state.tokenFactory.createToken(2));
            this.state.pushToken(this.state.tokenFactory.createToken(4));
            if (prefix != null) {
               this.state.pushToken(prefix);
               this.state.pushToken(localName);
            } else {
               this.state.pushToken(localName);
            }

            this.state.pushToken(this.state.tokenFactory.createToken(2));
            return;
         }

         this.name.read();
         this.state.skipSpace();
         this.state.expect('=');
         this.state.skipSpace();
         this.attributeValue.read();
      }
   }
}
