package weblogic.xml.babel.scanner;

import java.io.IOException;

final class EntityValue {
   private ScannerState state;
   private Reference reference;
   private PEReference peReference;
   private CharData singleQuoteData;
   private CharData doubleQuoteData;
   private String text;

   EntityValue(ScannerState state) {
      this.state = state;
      this.reference = new Reference(state);
      this.reference.setInDTD(true);
      this.singleQuoteData = new CharData(state, "%&'");
      this.doubleQuoteData = new CharData(state, "%&\"");
      this.peReference = new PEReference(state);
   }

   public String getText() {
      return this.text;
   }

   private void readSingleQuote() throws IOException, ScannerException {
      this.state.expect('\'');

      boolean gotData;
      label30:
      do {
         while(true) {
            while(true) {
               gotData = this.singleQuoteData.read();
               if (gotData) {
                  this.text = this.text + this.state.currentToken.getArrayAsString();
               }

               if (this.state.currentChar != '&') {
                  if (this.state.currentChar != '%') {
                     continue label30;
                  }

                  this.peReference.read();
                  gotData = true;
               } else {
                  this.reference.read();
                  this.text = this.text + this.state.currentToken.text;
                  gotData = true;
               }
            }
         }
      } while(this.state.currentChar != '\'' && gotData);

      this.state.expect('\'');
   }

   private void readDoubleQuote() throws IOException, ScannerException {
      this.state.expect('"');

      boolean gotData;
      label30:
      do {
         while(true) {
            while(true) {
               gotData = this.doubleQuoteData.read();
               if (gotData) {
                  this.text = this.text + this.state.currentToken.getArrayAsString();
                  gotData = false;
               }

               if (this.state.currentChar != '&') {
                  if (this.state.currentChar != '%') {
                     continue label30;
                  }

                  this.peReference.read();
                  gotData = true;
               } else {
                  this.reference.read();
                  this.text = this.text + this.state.currentToken.text;
                  gotData = true;
               }
            }
         }
      } while(this.state.currentChar != '"' && gotData);

      this.state.expect('"');
   }

   void read() throws IOException, ScannerException {
      this.text = "";
      if (this.state.currentChar == '"') {
         this.readDoubleQuote();
      } else {
         this.readSingleQuote();
      }

   }
}
