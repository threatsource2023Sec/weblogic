package weblogic.xml.babel.scanner;

import java.io.IOException;

final class AttributeValue {
   private ScannerState state;
   private Reference reference;
   private CharData singleQuoteData;
   private CharData doubleQuoteData;
   private Space space;

   AttributeValue(ScannerState state) {
      this.state = state;
      this.reference = new Reference(state);
      this.singleQuoteData = new CharData(state, "<&'\r\n\t");
      this.doubleQuoteData = new CharData(state, "<&\"\r\n\t");
      this.space = new Space(state);
   }

   private void readSingleQuote() throws IOException, ScannerException {
      this.state.expect('\'');

      boolean gotData;
      label30:
      do {
         while(true) {
            while(true) {
               gotData = this.singleQuoteData.read();
               if (this.state.currentChar != '&') {
                  if (this.state.currentChar != '\r' && this.state.currentChar != '\n' && this.state.currentChar != '\t') {
                     continue label30;
                  }

                  this.space.skipRead();
                  gotData = true;
               } else {
                  this.reference.read();
                  gotData = true;
               }
            }
         }
      } while(this.state.currentChar != '\'' && gotData);

      this.state.expect('\'', " this attribute was not terminated by a matching single quote");
   }

   private void readDoubleQuote() throws IOException, ScannerException {
      this.state.expect('"');

      boolean gotData;
      label30:
      do {
         while(true) {
            while(true) {
               gotData = this.doubleQuoteData.read();
               if (this.state.currentChar != '&') {
                  if (this.state.currentChar != '\r' && this.state.currentChar != '\n' && this.state.currentChar != '\t') {
                     continue label30;
                  }

                  this.space.skipRead();
                  gotData = true;
               } else {
                  this.reference.read();
                  gotData = true;
               }
            }
         }
      } while(this.state.currentChar != '"' && gotData);

      this.state.expect('"', " this attribute was not terminated by a matching double quote");
   }

   void read() throws IOException, ScannerException {
      if (this.state.currentChar == '"') {
         this.readDoubleQuote();
      } else {
         this.readSingleQuote();
      }

   }
}
