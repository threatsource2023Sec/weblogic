package weblogic.xml.babel.scanner;

import java.io.IOException;

final class ElementDeclaration {
   private ScannerState state;
   private Name name;

   ElementDeclaration(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
   }

   public void read() throws IOException, ScannerException {
      this.state.pushToken(this.state.tokenFactory.createToken(38));
      this.state.skipDTDSpace();
      this.name.read();
      this.readContentSpec();
      this.state.skipDTDSpace();
      this.state.expect('>');
      this.state.pushToken(this.state.tokenFactory.createToken(39));
   }

   private void readContentSpec() throws IOException, ScannerException {
      this.state.skipDTDSpace();
      switch (this.state.currentChar) {
         case '(':
            this.readContent();
            break;
         case 'A':
            this.state.expect("ANY");
            this.state.pushToken(this.state.tokenFactory.createToken(29));
            break;
         case 'E':
            this.state.expect("EMPTY");
            this.state.pushToken(this.state.tokenFactory.createToken(28));
            break;
         default:
            throw new ScannerException("A valid content specification was expected.  This includes EMPTY, ANY, or a valid content model", this.state);
      }

   }

   private void readContent() throws IOException, ScannerException {
      this.state.expect('(');
      this.state.pushToken(this.state.tokenFactory.createToken(57));
      int index = this.state.currentIndex();
      this.state.skipDTDSpace();
      if (this.state.currentChar == '#') {
         this.state.assign(index, this.state.tokenFactory.createToken(59));
         this.readMixed();
      } else {
         this.readChildren(index);
         this.state.expect(')');
         this.state.pushToken(this.state.tokenFactory.createToken(31));
         this.readOperator();
      }

   }

   private void finishChoice() throws IOException, ScannerException {
      while(this.state.currentChar == '|') {
         this.state.read();
         this.state.pushToken(this.state.tokenFactory.createToken(35));
         this.state.skipDTDSpace();
         this.readContentParticle();
         this.state.skipDTDSpace();
      }

   }

   private void finishSequence() throws IOException, ScannerException {
      while(this.state.currentChar == ',') {
         this.state.read();
         this.state.skipDTDSpace();
         this.state.pushToken(this.state.tokenFactory.createToken(36));
         this.readContentParticle();
         this.state.skipDTDSpace();
      }

   }

   private void dispatchGroup() throws IOException, ScannerException {
      this.state.expect('(');
      this.state.pushToken(this.state.tokenFactory.createToken(57));
      int index = this.state.currentIndex();
      this.state.skipDTDSpace();
      this.readContentParticle();
      this.state.skipDTDSpace();
      switch (this.state.currentChar) {
         case ',':
            this.state.assign(index, this.state.tokenFactory.createToken(57));
            this.finishSequence();
            break;
         case '|':
            this.state.assign(index, this.state.tokenFactory.createToken(56));
            this.finishChoice();
      }

      this.state.skipDTDSpace();
      this.state.expect(')');
      this.state.pushToken(this.state.tokenFactory.createToken(31));
   }

   private void readChildren(int index) throws IOException, ScannerException {
      this.readContentParticle();
      this.state.skipDTDSpace();
      switch (this.state.currentChar) {
         case ',':
            this.state.assign(index, this.state.tokenFactory.createToken(57));
            this.finishSequence();
            break;
         case '|':
            this.state.assign(index, this.state.tokenFactory.createToken(56));
            this.finishChoice();
      }

      this.state.skipDTDSpace();
   }

   private void readContentParticle() throws IOException, ScannerException {
      this.state.skipDTDSpace();
      if (this.state.currentChar == '(') {
         this.dispatchGroup();
      } else {
         this.name.read();
      }

      this.readOperator();
   }

   private void readOperator() throws IOException, ScannerException {
      switch (this.state.currentChar) {
         case '*':
            this.state.read();
            this.state.pushToken(this.state.tokenFactory.createToken(33));
            break;
         case '+':
            this.state.read();
            this.state.pushToken(this.state.tokenFactory.createToken(34));
            break;
         case '?':
            this.state.read();
            this.state.pushToken(this.state.tokenFactory.createToken(32));
      }

   }

   private void readMixed() throws IOException, ScannerException {
      this.state.expect("#PCDATA");
      this.state.pushToken(this.state.tokenFactory.createToken(37));
      this.state.skipDTDSpace();
      if (this.state.currentChar != '|') {
         this.state.expect(')');
         this.state.pushToken(this.state.tokenFactory.createToken(31));
      } else {
         while(true) {
            if (this.state.currentChar != '|') {
               this.state.skipDTDSpace();
               this.state.expect(')');
               this.state.pushToken(this.state.tokenFactory.createToken(31));
               this.state.expect('*');
               this.state.pushToken(this.state.tokenFactory.createToken(33));
               break;
            }

            this.state.read();
            this.state.pushToken(this.state.tokenFactory.createToken(35));
            this.state.skipDTDSpace();
            this.name.read();
         }
      }

   }
}
