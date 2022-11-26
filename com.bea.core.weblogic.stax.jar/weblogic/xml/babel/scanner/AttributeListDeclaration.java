package weblogic.xml.babel.scanner;

import java.io.IOException;

final class AttributeListDeclaration {
   private ScannerState state;
   private Name name;
   private Nmtoken nmtoken;
   private AttributeValue attValue;
   private PEReference perReference;

   AttributeListDeclaration(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.nmtoken = new Nmtoken(state);
      this.attValue = new AttributeValue(state);
   }

   public void read() throws IOException, ScannerException {
      this.state.pushToken(this.state.tokenFactory.createToken(40));
      this.state.skipDTDSpace();
      this.name.read();
      this.state.skipDTDSpace();

      while(Name.isNameBegin(this.state.currentChar)) {
         this.readAttributeDefinition();
         this.state.skipDTDSpace();
      }

      this.state.skipDTDSpace();
      this.state.expect('>');
      this.state.pushToken(this.state.tokenFactory.createToken(39));
   }

   private void readAttributeDefinition() throws IOException, ScannerException {
      this.state.skipDTDSpace();
      this.name.read();
      this.readAttributeType();
      this.readDefaultDeclaration();
   }

   private void readAttributeType() throws IOException, ScannerException {
      this.state.skipDTDSpace();
      if (this.state.currentChar == '(') {
         this.readEnumeratedType();
      } else {
         String type = this.name.stringRead();
         if (type.equals("CDATA")) {
            this.state.pushToken(this.state.tokenFactory.createToken(11));
         } else if (type.equals("ID")) {
            this.state.pushToken(this.state.tokenFactory.createToken(41));
         } else if (type.equals("IDREF")) {
            this.state.pushToken(this.state.tokenFactory.createToken(42));
         } else if (type.equals("IDREFS")) {
            this.state.pushToken(this.state.tokenFactory.createToken(43));
         } else if (type.equals("ENTITY")) {
            this.state.pushToken(this.state.tokenFactory.createToken(44));
         } else if (type.equals("ENTITIES")) {
            this.state.pushToken(this.state.tokenFactory.createToken(45));
         } else if (type.equals("NMTOKEN")) {
            this.state.pushToken(this.state.tokenFactory.createToken(46));
         } else if (type.equals("NMTOKENS")) {
            this.state.pushToken(this.state.tokenFactory.createToken(47));
         } else {
            if (!type.equals("NOTATION")) {
               throw new ScannerException("An attribute definition must contain an attribute type which can be an enumerated type or one of the following: CDATA, ID, IDREF, IDREFS, ENTITY, ENTITIES, NMTOKEN, NMTOKENS. [" + type + "] was read", this.state);
            }

            this.readNotation();
         }

      }
   }

   private void readNotation() throws IOException, ScannerException {
      this.state.pushToken(this.state.tokenFactory.createToken(48));
      this.state.skipDTDSpace();
      this.state.expect('(');
      this.state.pushToken(this.state.tokenFactory.createToken(30));
      this.state.skipDTDSpace();
      this.name.read();
      this.state.skipDTDSpace();

      while(this.state.currentChar == '|') {
         this.state.read();
         this.state.pushToken(this.state.tokenFactory.createToken(35));
         this.state.skipDTDSpace();
         this.name.read();
         this.state.skipDTDSpace();
      }

      this.state.expect(')');
      this.state.pushToken(this.state.tokenFactory.createToken(31));
   }

   private void readEnumeratedType() throws IOException, ScannerException {
      this.state.expect('(');
      this.state.pushToken(this.state.tokenFactory.createToken(30));
      this.state.skipDTDSpace();
      this.nmtoken.read();
      this.state.skipDTDSpace();

      while(this.state.currentChar == '|') {
         this.state.read();
         this.state.pushToken(this.state.tokenFactory.createToken(35));
         this.state.skipDTDSpace();
         this.nmtoken.read();
         this.state.skipDTDSpace();
      }

      this.state.expect(')');
      this.state.pushToken(this.state.tokenFactory.createToken(31));
   }

   private void readDefaultDeclaration() throws IOException, ScannerException {
      this.state.skipDTDSpace();
      if (this.state.currentChar == '#') {
         this.state.read();
         String type = this.name.stringRead();
         if (type.equals("IMPLIED")) {
            this.state.pushToken(this.state.tokenFactory.createToken(50));
            return;
         }

         if (type.equals("REQUIRED")) {
            this.state.pushToken(this.state.tokenFactory.createToken(49));
            return;
         }

         if (type.equals("FIXED")) {
            this.state.pushToken(this.state.tokenFactory.createToken(51));
            this.state.skipDTDSpace();
            this.attValue.read();
         }
      } else {
         this.attValue.read();
      }

   }
}
