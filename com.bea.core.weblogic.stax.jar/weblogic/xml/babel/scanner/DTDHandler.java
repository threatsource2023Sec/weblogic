package weblogic.xml.babel.scanner;

import java.io.IOException;

final class DTDHandler implements DTDProcessor {
   private ScannerState state;
   private Name name;
   private StringBuffer value;
   private int count = 0;
   private int maxChar = 512;

   DTDHandler(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.value = new StringBuffer();
   }

   public void setExternalDTD(boolean val) {
   }

   public void read() throws IOException, ScannerException {
      this.value.setLength(0);
      this.count = 0;
      this.value.append("<!DOCTYPE ");
      this.skipDOCTYPE();
   }

   private void skipDOCTYPE() throws IOException, ScannerException {
      while(this.state.currentChar != '>') {
         if (this.state.currentChar == '<') {
            this.state.read();
            if (this.state.currentChar == '!') {
               this.state.read();
               if (this.state.currentChar == '[') {
                  this.state.read();
                  this.skipDTDSect();
               } else if (this.state.currentChar == '-') {
                  this.state.read();
                  this.skipDTDComment();
               } else {
                  String command = this.name.stringRead();
                  if (!command.equals("ELEMENT") && !command.equals("ATTLIST") && !command.equals("ENTITY") && !command.equals("NOTATION")) {
                     throw new ScannerException(" '" + this.state.currentChar + "' Expected an ELEMENT,ATTLIST,NOTATION, or ENTITY declaration", this.state);
                  }

                  this.skipDTDELEMENT();
               }
            }
         } else {
            if (this.count < this.maxChar) {
               this.value.append(this.state.currentChar);
               ++this.count;
            }

            this.state.checkedRead();
         }
      }

      this.state.read();
   }

   private void skipDelimiter(char delimiter) throws IOException, ScannerException {
      if (this.state.currentChar == delimiter) {
         this.state.read();

         while(this.state.currentChar != delimiter) {
            this.state.checkedRead();
         }

         this.state.read();
      }
   }

   private void skipDTDELEMENT() throws IOException, ScannerException {
      while(true) {
         if (this.state.currentChar == '"') {
            this.skipDelimiter('"');
         }

         if (this.state.currentChar == '\'') {
            this.skipDelimiter('\'');
         }

         if (this.state.currentChar == '>') {
            this.state.read();
            return;
         }

         this.state.checkedRead();
      }
   }

   private void skipDTDSect() throws IOException, ScannerException {
      while(true) {
         if (this.state.currentChar == ']') {
            this.state.read();
            if (this.state.currentChar == ']') {
               this.state.read();
               if (this.state.currentChar == '>') {
                  this.state.read();
                  return;
               }
            }
         }

         this.state.checkedRead();
      }
   }

   private void skipDTDComment() throws IOException, ScannerException {
      while(true) {
         if (this.state.currentChar == '-') {
            this.state.read();
            if (this.state.currentChar == '-') {
               this.state.read();
               if (this.state.currentChar == '>') {
                  this.state.read();
                  return;
               }
            }
         }

         this.state.checkedRead();
      }
   }

   public String getStringValue() {
      return this.value.toString();
   }
}
