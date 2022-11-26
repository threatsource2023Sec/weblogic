package weblogic.xml.babel.scanner;

import java.io.IOException;
import weblogic.xml.babel.reader.XmlChars;

final class Name {
   private final ScannerState scannerState;
   private Token localName;
   private Token prefix;

   Name(ScannerState state) {
      this.scannerState = state;
   }

   void read() throws IOException, ScannerException {
      this.localName = null;
      this.prefix = null;
      this.read(false);
   }

   private void read(boolean readColon) throws IOException, ScannerException {
      int i = 0;
      ScannerState state = this.scannerState;
      if (isNameBegin(state.currentChar)) {
         state.mark();
         state.read();
         ++i;

         while(XmlChars.isNameChar(state.currentChar)) {
            if (state.currentChar == ':') {
               if (readColon) {
                  throw new ScannerException(" '" + state.currentChar + "' Already got a ':' in name", state);
               }

               this.prefix = state.createStoredToken(18, i);
               state.pushToken(this.prefix);
               state.read();
               this.read(true);
               return;
            }

            state.read();
            ++i;
         }

         this.localName = state.createStoredToken(0, i);
         state.pushToken(this.localName);
      } else {
         throw new ScannerException(" '" + state.currentChar + "' expected a valid beginning name character", state);
      }
   }

   public Token getLocalName() {
      return this.localName;
   }

   public Token getPrefix() {
      return this.prefix;
   }

   String stringRead() throws IOException, ScannerException {
      int i = 0;
      ScannerState state = this.scannerState;
      if (!isNameBegin(state.currentChar)) {
         throw new ScannerException(" '" + state.currentChar + "' expect a valid beginning name character", state);
      } else {
         state.mark();
         state.read();
         ++i;

         while(XmlChars.isNameChar(state.currentChar)) {
            state.read();
            ++i;
         }

         return state.getString(i);
      }
   }

   public static boolean isNameBegin(char c) {
      return XmlChars.isLetter(c) || c == '_';
   }
}
