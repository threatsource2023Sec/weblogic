package weblogic.xml.babel.scanner;

import java.io.IOException;
import weblogic.xml.babel.dtd.EntityTable;
import weblogic.xml.babel.reader.XmlChars;

final class EntityDeclaration {
   private ScannerState state;
   private Name name;
   private DTDSpace space;
   private EntityValue entityValue;
   private ExternalID externalID;
   private EntityTable parameterTable;
   private EntityTable internalTable;

   EntityDeclaration(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
      this.space = new DTDSpace(state);
      this.entityValue = new EntityValue(state);
      this.externalID = new ExternalID(state);
   }

   public void read() throws IOException, ScannerException {
      this.space.read();
      this.internalTable = this.state.getInternalEntityTable();
      String entityName;
      if (this.state.currentChar == '%') {
         this.state.pushToken(this.state.tokenFactory.createToken(53));
         this.state.read();
         this.space.checkedRead();
         this.name.read();
         entityName = this.state.currentToken.text;
         this.space.checkedRead();
         this.readParsedEntityDefinition();
         String currentEntityValue = this.entityValue.getText();
         this.parameterTable = this.state.getParameterEntityTable();
         this.parameterTable.put(entityName, currentEntityValue);
      } else {
         this.state.pushToken(this.state.tokenFactory.createToken(52));
         this.name.read();
         entityName = this.state.currentToken.text;
         this.space.checkedRead();
         if (this.readEntityDefinition()) {
            this.internalTable = this.state.getInternalEntityTable();
            this.internalTable.put(entityName, this.entityValue.getText());
         }
      }

      this.state.skipSpace();
      this.state.expect('>');
      this.state.pushToken(this.state.tokenFactory.createToken(39));
   }

   private void readParsedEntityDefinition() throws IOException, ScannerException {
      if (this.state.currentChar != '\'' && this.state.currentChar != '"') {
         if (!this.externalID.read()) {
            throw new ScannerException("Entity Declarations must have an EntityValue or ExternalID defined", this.state);
         }
      } else {
         this.entityValue.read();
      }
   }

   private boolean readEntityDefinition() throws IOException, ScannerException {
      if (this.state.currentChar != '\'' && this.state.currentChar != '"') {
         if (this.externalID.read()) {
            if (XmlChars.isSpace(this.state.currentChar)) {
               this.space.checkedRead();
               if (this.state.currentChar == 'N') {
                  this.state.expect("NDATA");
                  this.state.pushToken(this.state.tokenFactory.createToken(54));
                  this.space.checkedRead();
                  this.name.read();
               }
            }

            return false;
         } else {
            throw new ScannerException("Entity Declarations must have an EntityValue or ExternalID defined", this.state);
         }
      } else {
         this.entityValue.read();
         return true;
      }
   }
}
