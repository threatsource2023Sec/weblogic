package weblogic.xml.babel.dtd;

import java.io.IOException;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.ParseException;
import weblogic.xml.babel.scanner.ScannerException;

public class GeneralEntityDeclaration extends EntityDeclaration {
   private Name dataName;
   private boolean nData;

   public GeneralEntityDeclaration() {
      this.init();
   }

   public void init() {
      super.init();
      this.dataName = new Name();
   }

   protected void parseType(BaseParser parser) throws IOException, ScannerException, ParseException {
      parser.accept(52);
      this.space.parse(parser);
   }

   protected void parseNData(BaseParser parser) throws IOException, ScannerException, ParseException {
      this.space.parse(parser);
      if (parser.compare(54)) {
         parser.accept();
         this.space.parse(parser);
         this.dataName.parse(parser);
         this.nData = true;
      }

   }

   public String toString() {
      String s = "<!ENTITY " + this.name;
      if (!this.isExternal) {
         s = s + " \"" + this.data + "\"";
      } else {
         s = s + " " + this.externalID;
         if (this.nData) {
            s = s + " NDATA " + this.dataName;
         }
      }

      s = s + " >";
      return s;
   }
}
