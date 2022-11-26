package weblogic.xml.babel.scanner;

import java.io.IOException;
import weblogic.xml.babel.reader.XmlChars;

public final class DTDScanner implements DTDProcessor {
   private ScannerState state;
   private Name name;
   private Space space;
   private PEReference perReference;
   private Comment comment;
   private ElementDeclaration elementDecl;
   private AttributeListDeclaration attListDecl;
   private ExternalID externalID;
   private EntityDeclaration entityDecl;
   private NotationDeclaration notationDecl;
   private boolean externalDTD = false;
   private StringBuffer value;

   public DTDScanner(ScannerState scannerState) throws IOException, ScannerException {
      this.state = scannerState;
      this.name = new Name(this.state);
      this.space = new Space(this.state);
      this.perReference = new PEReference(this.state);
      this.elementDecl = new ElementDeclaration(this.state);
      this.attListDecl = new AttributeListDeclaration(this.state);
      this.externalID = new ExternalID(this.state);
      this.entityDecl = new EntityDeclaration(this.state);
      this.notationDecl = new NotationDeclaration(this.state);
      this.comment = new Comment(this.state, false);
      this.value = new StringBuffer();
   }

   public void read() throws IOException, ScannerException {
      if (this.externalDTD) {
         this.state.skipSpace();
         this.dispatchDTDElements();
      } else {
         this.state.pushToken(this.state.tokenFactory.createToken(24));
         this.state.skipSpace();
         this.value.setLength(0);
         this.value.append("<!DOCTYPE ");
         this.name.read();
         this.value.append(this.name.getLocalName().text);
         this.value.append(" ");
         this.state.countedMark();
         this.state.skipSpace();
         this.externalID.read();
         this.state.skipSpace();
         this.value.append(this.state.getString(this.state.getCharsSinceMark()));
         this.state.unMark();
         this.dispatchWrappedDTDElements();
         this.state.skipSpace();
         this.state.expect('>');
         this.state.pushToken(this.state.tokenFactory.createToken(58));
      }
   }

   public void dispatchDTDElements() throws IOException, ScannerException {
      while(this.isSeparator(this.state.currentChar) || this.startsDeclaration(this.state.currentChar)) {
         switch (this.state.currentChar) {
            case '%':
               this.perReference.read();
               break;
            case '<':
               this.readMarkupDeclaration();
               break;
            default:
               this.state.skipSpace();
         }
      }

   }

   public void dispatchWrappedDTDElements() throws IOException, ScannerException {
      if (this.state.currentChar == '[') {
         this.state.read();
         this.dispatchDTDElements();
         this.state.skipSpace();
         this.state.expect(']');
      }

   }

   public void setExternalDTD(boolean val) {
      this.externalDTD = val;
   }

   private boolean isSeparator(char c) {
      return c == '%' || XmlChars.isSpace(c);
   }

   private boolean startsDeclaration(char c) {
      return c == '<';
   }

   private void readConditionalSection() throws IOException, ScannerException {
      this.state.skipDTDSpace();
      String section = this.name.stringRead();
      if (section.equals("INCLUDE")) {
         this.dispatchWrappedDTDElements();
         this.state.expect("]>");
      } else {
         if (!section.equals("IGNORE")) {
            throw new ScannerException("Conditional sections must have either the keyword INCLUDE or IGNORE in their declaration");
         }

         this.ignoreConditionalSection();
      }

   }

   private void ignoreConditionalSection() throws IOException, ScannerException {
      while(true) {
         if (this.state.currentChar == '<') {
            this.state.read();
            if (this.state.currentChar == '!') {
               this.state.read();
               if (this.state.currentChar == '[') {
                  this.state.read();
                  this.ignoreConditionalSection();
               }
            }
         }

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

         this.state.read();
      }
   }

   private void readMarkupDeclaration() throws IOException, ScannerException {
      if (this.state.currentChar == '<') {
         this.state.read();
         if (this.state.currentChar == '!') {
            this.state.read();
            if (this.state.currentChar == '-') {
               this.state.read();
               this.state.expect('-');
               this.comment.read();
               return;
            }

            if (this.state.currentChar == '[') {
               this.state.read();
               this.readConditionalSection();
            } else {
               String command = this.name.stringRead();
               if (command.equals("ELEMENT")) {
                  this.elementDecl.read();
               } else if (command.equals("ATTLIST")) {
                  this.attListDecl.read();
               } else if (command.equals("ENTITY")) {
                  this.entityDecl.read();
               } else {
                  if (!command.equals("NOTATION")) {
                     throw new ScannerException(" '" + this.state.currentChar + "' Expected an ELEMENT,ATTLIST,NOTATION, or ENTITY declaration", this.state);
                  }

                  this.notationDecl.read();
               }
            }
         }
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

   private void skipDelimiter(char delimiter) throws IOException, ScannerException {
      if (this.state.currentChar == delimiter) {
         this.state.read();

         while(this.state.currentChar != delimiter) {
            this.state.checkedRead();
         }

         this.state.read();
      }
   }

   public String getStringValue() {
      return this.value.toString();
   }
}
