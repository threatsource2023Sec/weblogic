package weblogic.xml.babel.scanner;

import java.io.IOException;
import weblogic.xml.babel.dtd.DTDException;
import weblogic.xml.babel.dtd.EntityTable;

final class PEReference {
   private ScannerState state;
   private Name name;
   private boolean returnToken = false;

   public void setReturnToken(boolean val) {
      this.returnToken = val;
   }

   public boolean getReturnToken() {
      return this.returnToken;
   }

   PEReference(ScannerState state) {
      this.state = state;
      this.name = new Name(state);
   }

   void read() throws IOException, ScannerException {
      this.state.markInsert();
      this.state.expect('%');
      String entityName = this.name.stringRead();
      this.state.expect(';');
      if (this.returnToken) {
         this.state.pushToken(this.state.tokenFactory.createToken(27, entityName));
      }

      this.state.deleteFromLastInsertionPoint();

      try {
         EntityTable table = this.state.getParameterEntityTable();
         this.state.insertData(table.get(entityName).toCharArray());
      } catch (DTDException var3) {
         throw new ScannerException(entityName + " is an undefined parameter entity", this.state);
      }
   }

   void checkedRead() throws IOException, ScannerException {
      if (this.state.currentChar == '%') {
         this.read();
      }

   }
}
