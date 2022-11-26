package weblogic.xml.babel.scanner;

import java.io.IOException;
import java.util.HashMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.babel.baseparser.BaseEntityResolver;
import weblogic.xml.babel.dtd.DTDException;
import weblogic.xml.babel.dtd.EntityTable;

final class Reference {
   private ScannerState state;
   private Name name;
   private HashMap escapedCharacters;
   private boolean inDTD;

   Reference(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.escapedCharacters = new HashMap();
      this.escapedCharacters.put("amp", "&");
      this.escapedCharacters.put("lt", "<");
      this.escapedCharacters.put("gt", ">");
      this.escapedCharacters.put("quot", "\"");
      this.escapedCharacters.put("apos", "'");
      this.inDTD = false;
   }

   public boolean isEscaped() {
      return this.escapedCharacters.containsKey(this.name.getLocalName());
   }

   public String getEscapedRepresentation() {
      return "&" + this.name.getLocalName() + ";";
   }

   void setInDTD(boolean val) {
      this.inDTD = val;
   }

   void skipZero() throws IOException, ScannerException {
      while(this.state.currentChar != ';') {
         if (this.state.currentChar != '0') {
            return;
         }

         this.state.read();
      }

   }

   void read() throws IOException, ScannerException {
      this.state.markInsert();
      this.state.expect('&');
      if (this.state.currentChar == '#') {
         this.state.read();
         int i;
         char value;
         if (this.state.currentChar == 'x') {
            this.state.read();
            this.skipZero();
            if (!isHex(this.state.currentChar)) {
               throw new ScannerException(" [0-9a-zA-Z] expected, got '" + this.state.currentChar + "'", this.state);
            } else {
               i = 0;
               if (this.state.currentChar != ';') {
                  this.state.mark();
                  this.state.read();
                  ++i;

                  while(isHex(this.state.currentChar)) {
                     this.state.read();
                     ++i;
                  }
               }

               this.state.expect(';');

               try {
                  value = 0;
                  if (i != 0) {
                     value = (char)Integer.parseInt(this.state.getString(i), 16);
                  }

                  this.state.pushToken(this.state.tokenFactory.createToken(15, 21, value));
               } catch (Exception var11) {
                  throw new ScannerException(" Invalid character reference '" + this.state.getString(i) + "'", this.state);
               }

               this.state.unMarkInsert();
            }
         } else if (!isNum(this.state.currentChar)) {
            throw new ScannerException("Line:" + this.state.currentLine + " [0-9] expected, got '" + this.state.currentChar + "'");
         } else {
            this.skipZero();
            i = 0;
            if (this.state.currentChar != ';') {
               this.state.mark();
               this.state.read();
               ++i;

               while(isNum(this.state.currentChar)) {
                  this.state.read();
                  ++i;
               }
            }

            this.state.expect(';');

            try {
               value = 0;
               if (i != 0) {
                  value = (char)Integer.parseInt(this.state.getString(i));
               }

               this.state.pushToken(this.state.tokenFactory.createToken(15, 22, value));
            } catch (Exception var12) {
               throw new ScannerException(" Invalid character reference '" + this.state.getString(i) + "'", this.state);
            }

            this.state.unMarkInsert();
         }
      } else {
         String entityName = this.name.stringRead();
         String entityValue = (String)this.escapedCharacters.get(entityName);
         this.state.expect(';');
         EntityTable table = this.state.getInternalEntityTable();
         EntityTable externalEntityTable = this.state.getExternalEntityTable();
         if (this.inDTD) {
            this.state.pushToken(this.state.tokenFactory.createToken(14, "&" + entityName + ";"));
            this.state.unMarkInsert();
         } else if (entityValue != null) {
            this.state.pushToken(this.state.tokenFactory.createToken(14, entityValue));
            this.state.unMarkInsert();
         } else if (externalEntityTable != null && externalEntityTable.contains(entityName)) {
            try {
               weblogic.xml.babel.dtd.ExternalID externalID = externalEntityTable.getExternalID(entityName);

               try {
                  BaseEntityResolver entityResolver = this.state.getBaseEntityResolver();
                  InputSource inputSource = entityResolver.resolveEntity(externalID.getPubidLiteral(), externalID.getSystemLiteral());
                  Scanner scanner = this.state.createScanner(inputSource);

                  for(Token token = scanner.scan(); token != null && !token.isEOF(); token = scanner.scan()) {
                     this.state.pushToken(token.duplicate());
                  }
               } catch (SAXException var13) {
                  throw new ScannerException("Unable to resolve entity: '" + entityName + "' ", this.state);
               }
            } catch (DTDException var14) {
               throw new ScannerException(" Unknown entity reference: '" + entityName + "' ", this.state);
            }
         } else {
            this.state.deleteFromLastInsertionPoint();

            try {
               if (table == null) {
                  throw new ScannerException(" Unknown entity reference: '" + entityName + "' ", this.state);
               }

               this.state.insertData(table.get(entityName).toCharArray());
            } catch (DTDException var10) {
               System.out.println("-----------------TABLE REF---------------");
               System.out.println(table);
               System.out.println("-----------------TABLE REF---------------");
               System.out.println(var10);
               throw new ScannerException(" Unknown entity reference: '" + entityName + "' ", this.state);
            }
         }

      }
   }

   private static boolean isNum(char c) {
      return c >= '0' && c <= '9';
   }

   private static boolean isHex(char c) {
      return isNum(c) || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
   }
}
